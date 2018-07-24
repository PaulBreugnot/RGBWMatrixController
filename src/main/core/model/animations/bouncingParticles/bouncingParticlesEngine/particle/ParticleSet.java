package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.collision.CollisionEvent;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.collision.EdgeCollisionEvent;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.collision.ParticlesCollisionEvent;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.CollisionResult;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.Edge;

public class ParticleSet {

	public static final double deltaTsimulation = 0.01;
	private boolean bounceParticles;
	private double time = 0;
	private ArrayList<Particle> particles;
	private int maxIteration;
	private ArrayList<Edge> edges;
	private PriorityQueue<CollisionEvent> collisions = new PriorityQueue<>();
	private HashMap<Particle, HashSet<CollisionEvent>> ParticleCollisionsMap = new HashMap<>();

	private ParticleSet(ArrayList<Particle> particles, boolean bounceParticles) {
		this.particles = particles;
		maxIteration = (int) Math.pow(particles.size(), 2) + particles.size();
		this.bounceParticles = bounceParticles;
	}

	public ParticleSet(ArrayList<Particle> particles, ArrayList<Edge> edges, boolean bounceParticles) {
		this.particles = particles;
		maxIteration = (int) Math.pow(particles.size(), 2);
		this.edges = edges;
		this.bounceParticles = bounceParticles;
		initCollisions();
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	public ArrayList<Particle> getParticles() {
		return particles;
	}
	
	public double getTime() {
		return time;
	}

	public void progress(double deltaT) {
		double beginTime = time;
		while (time - beginTime < deltaT) {
			int iterations = 0;
			while (time + deltaTsimulation >= collisions.peek().getTime() && iterations < maxIteration) {
				iterations++;
				if(iterations>=maxIteration) {
					System.out.println("Engine Crashed...");
				}
				// Trigger next collision
				CollisionResult collidedParticles = collisions.poll().trigger();
				if (collidedParticles.getCollidedParticle2() == null) {
					Particle collidedParticle = collidedParticles.getCollidedParticle1();
					// Edge collision
					// All other events evolving this particle becomes obsoletes
					collisions.removeAll(ParticleCollisionsMap.get(collidedParticle));
					ParticleCollisionsMap.get(collidedParticle).clear();
					updateCollisions(collidedParticle, null);
				} else {
					Particle collidedParticle1 = collidedParticles.getCollidedParticle1();
					// Edge collision
					// All other events evolving this particle becomes obsoletes
					collisions.removeAll(ParticleCollisionsMap.get(collidedParticle1));
					ParticleCollisionsMap.get(collidedParticle1).clear();
					Particle collidedParticle2 = collidedParticles.getCollidedParticle2();
					// Edge collision
					// All other events evolving this particle becomes obsoletes
					collisions.removeAll(ParticleCollisionsMap.get(collidedParticle2));
					ParticleCollisionsMap.get(collidedParticle2).clear();
					updateCollisions(collidedParticle1, collidedParticle2);
				}
			}
			// Now we are safe
			for (Particle particle : particles) {
				particle.progress(deltaTsimulation);
			}
			time += deltaTsimulation;
		}
	}

	public void initCollisions() {
		// Generate all collisions from current particles set
		checkEdgesCollisions();
		if (bounceParticles) {
			checkParticleCollisions();
		}
	}

	private void updateCollisions(Particle particle1, Particle particle2) {
		// Update collisions after particle1 and particle2 have collided
		// For edges :
		updateEdgeCollisions(particle1);
		// For particles :
		if (bounceParticles) {
			for (Particle p : particles) {
				if (particle1 != p && p != particle2) {
					double t = Particle.collisionTime(particle1, p);
					if (t < Double.MAX_VALUE) {
						CollisionEvent col = new ParticlesCollisionEvent(particle1, p, time + t);
						collisions.add(col);
						if (ParticleCollisionsMap.containsKey(particle1)) {
							ParticleCollisionsMap.get(particle1).add(col);
						} else {
							HashSet<CollisionEvent> collisionSet = new HashSet<>();
							collisionSet.add(col);
							ParticleCollisionsMap.put(particle1, collisionSet);
						}
						if (ParticleCollisionsMap.containsKey(p)) {
							ParticleCollisionsMap.get(p).add(col);
						} else {
							HashSet<CollisionEvent> collisionSet = new HashSet<>();
							collisionSet.add(col);
							ParticleCollisionsMap.put(p, collisionSet);
						}
					}
				}
			}

			if (particle2 != null) {
				updateEdgeCollisions(particle2);
				for (Particle p : particles) {
					if (particle2 != p && p != particle1) {
						double t = Particle.collisionTime(particle2, p);
						if (t < Double.MAX_VALUE) {
							CollisionEvent col = new ParticlesCollisionEvent(particle2, p, time + t);
							collisions.add(col);
							if (ParticleCollisionsMap.containsKey(particle2)) {
								ParticleCollisionsMap.get(particle2).add(col);
							} else {
								HashSet<CollisionEvent> collisionSet = new HashSet<>();
								collisionSet.add(col);
								ParticleCollisionsMap.put(particle2, collisionSet);
							}
							if (ParticleCollisionsMap.containsKey(p)) {
								ParticleCollisionsMap.get(p).add(col);
							} else {
								HashSet<CollisionEvent> collisionSet = new HashSet<>();
								collisionSet.add(col);
								ParticleCollisionsMap.put(p, collisionSet);
							}
						}
					}
				}
			}
		}

	}

	private void updateEdgeCollisions(Particle particle) {
		// Update collisions after particle has collided an edge
		for (Edge edge : edges) {
			double t = Particle.collisionTime(particle, edge);
			if (t < Double.MAX_VALUE) {
				CollisionEvent col = new EdgeCollisionEvent(particle, edge, time + t);
				collisions.add(col);
				if (ParticleCollisionsMap.containsKey(particle)) {
					ParticleCollisionsMap.get(particle).add(col);
				} else {
					HashSet<CollisionEvent> collisionSet = new HashSet<>();
					collisionSet.add(col);
					ParticleCollisionsMap.put(particle, collisionSet);
				}
			}
		}

	}

	private void checkEdgesCollisions() {
		for (Particle p : particles) {
			for (Edge edge : edges) {
				double t = Particle.collisionTime(p, edge);
				if (t < Double.MAX_VALUE) {
					CollisionEvent col = new EdgeCollisionEvent(p, edge, time + t);
					collisions.add(col);
					if (ParticleCollisionsMap.containsKey(p)) {
						ParticleCollisionsMap.get(p).add(col);
					} else {
						HashSet<CollisionEvent> collisionSet = new HashSet<>();
						collisionSet.add(col);
						ParticleCollisionsMap.put(p, collisionSet);
					}
				}
			}
		}
	}

	private void checkParticleCollisions() {
		for (int i = 0; i < particles.size(); i++) {
			Particle p1 = particles.get(i);
			for (int j = i + 1; j < particles.size(); j++) {
				Particle p2 = particles.get(j);
				double t = Particle.collisionTime(p1, p2);
				if (t < Double.MAX_VALUE) {
					CollisionEvent col = new ParticlesCollisionEvent(p1, p2, time + t);
					collisions.add(col);
					if (ParticleCollisionsMap.containsKey(p1)) {
						ParticleCollisionsMap.get(p1).add(col);
					} else {
						HashSet<CollisionEvent> collisionSet = new HashSet<>();
						collisionSet.add(col);
						ParticleCollisionsMap.put(p1, collisionSet);
					}
					if (ParticleCollisionsMap.containsKey(p2)) {
						ParticleCollisionsMap.get(p2).add(col);
					} else {
						HashSet<CollisionEvent> collisionSet = new HashSet<>();
						collisionSet.add(col);
						ParticleCollisionsMap.put(p2, collisionSet);
					}
				}
			}
		}
	}

	public static class RectangularSet extends ParticleSet {
		public RectangularSet(ArrayList<Particle> particles, int width, int height, int widthOffset, int heightOffset, boolean bounceParticles) {
			super(particles, bounceParticles);
			ArrayList<Edge> edges = new ArrayList<>();
			edges.add(new Edge(widthOffset, heightOffset, widthOffset + width, heightOffset));
			edges.add(new Edge(widthOffset, heightOffset, widthOffset, heightOffset + height));
			edges.add(new Edge(widthOffset, heightOffset + height, widthOffset + width, heightOffset + height));
			edges.add(new Edge(widthOffset + width, heightOffset, widthOffset + width, heightOffset + height));
			setEdges(edges);
			initCollisions();
		}
	}

}
