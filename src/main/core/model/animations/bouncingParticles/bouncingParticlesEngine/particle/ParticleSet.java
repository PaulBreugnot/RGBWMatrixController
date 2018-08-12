package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;

import javafx.scene.paint.Color;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.collision.CollisionEvent;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.collision.EdgeCollisionEvent;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.collision.ParticlesCollisionEvent;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.CollisionResult;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.Edge;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.Point;
import main.core.model.panel.LedPanel;

public class ParticleSet {

	public static final double deltaTsimulation = 0.01;
	private boolean bounceParticles;
	private double time = 0;
	private ArrayList<Particle> particles;
	private int maxIteration;
	protected ArrayList<Point> points;
	protected ArrayList<Edge> edges;
	private PriorityQueue<CollisionEvent> collisions = new PriorityQueue<>();
	private HashMap<Particle, HashSet<CollisionEvent>> particleCollisionsMap = new HashMap<>();
	private double orientation = 0;

	private ParticleSet(ArrayList<Particle> particles, boolean bounceParticles) {
		this.particles = particles;
		this.bounceParticles = bounceParticles;
	}
	
	/*public ParticleSet(ArrayList<Particle> particles, ArrayList<Point> points, boolean bounceParticles) {
		this.particles = particles;
		this.points = points;
		edges = Edge.generateEdgesFromPoints(points);
		this.bounceParticles = bounceParticles;
		initCollisions();
	}*/

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}

	public ArrayList<Particle> getParticles() {
		return particles;
	}

	public double getTime() {
		return time;
	}
	
	public double getOrientation() {
		return orientation;
	}

	public void removeParticle(Particle particle) {
		particles.remove(particle);
		for(Particle p : particles) {
			if (p.getLayer() > particle.getLayer()) {
				p.setLayer(p.getLayer() - 1);
			}
		}
	}

	public void progress(double deltaT) {
		double beginTime = time;
		//System.out.println(collisions);
		while (time - beginTime < deltaT) {
			int iterations = 0;
			while (particles.size() > 0 && time + deltaTsimulation >= collisions.peek().getTime()
					&& iterations < maxIteration) {
				iterations++;
				if (iterations >= maxIteration) {
					System.out.println("Engine Crashed...");
				}
				// Trigger next collision
				CollisionResult collidedParticles = collisions.poll().trigger();
				if (!collidedParticles.getCollidedParticle1().isOutdated()) {
					if (collidedParticles.getCollidedParticle2() == null) {
						Particle collidedParticle = collidedParticles.getCollidedParticle1();
						// Edge collision
						// All other events evolving this particle becomes obsoletes
						collisions.removeAll(particleCollisionsMap.get(collidedParticle));
						particleCollisionsMap.get(collidedParticle).clear();
						updateCollisions(collidedParticle, null);
					} else {
						Particle collidedParticle1 = collidedParticles.getCollidedParticle1();
						// Edge collision
						// All other events evolving this particle becomes obsoletes
						collisions.removeAll(particleCollisionsMap.get(collidedParticle1));
						particleCollisionsMap.get(collidedParticle1).clear();
						Particle collidedParticle2 = collidedParticles.getCollidedParticle2();
						// Edge collision
						// All other events evolving this particle becomes obsoletes
						collisions.removeAll(particleCollisionsMap.get(collidedParticle2));
						particleCollisionsMap.get(collidedParticle2).clear();
						updateCollisions(collidedParticle1, collidedParticle2);
					}
				} else {
					Particle collidedParticle = collidedParticles.getCollidedParticle1();
					// Edge collision
					// All other events evolving this particle becomes obsoletes
					collisions.removeAll(particleCollisionsMap.get(collidedParticle));
					particleCollisionsMap.remove(collidedParticle);
					particles.remove(collidedParticle);
				}
			}
			// Now we are safe
			for (Particle particle : particles) {
				particle.progress(deltaTsimulation);
			}
			time += deltaTsimulation;
		}
	}

	public int initCollisions() {
		collisions.clear();
		particleCollisionsMap.clear();
		maxIteration = (int) Math.pow(particles.size(), 2) + particles.size();
		// Generate all collisions from current particles set
		checkEdgesCollisions();
		if (bounceParticles) {
			checkParticleCollisions();
		}
		return collisions.size();
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
						if (particleCollisionsMap.containsKey(particle1)) {
							particleCollisionsMap.get(particle1).add(col);
						} else {
							HashSet<CollisionEvent> collisionSet = new HashSet<>();
							collisionSet.add(col);
							particleCollisionsMap.put(particle1, collisionSet);
						}
						if (particleCollisionsMap.containsKey(p)) {
							particleCollisionsMap.get(p).add(col);
						} else {
							HashSet<CollisionEvent> collisionSet = new HashSet<>();
							collisionSet.add(col);
							particleCollisionsMap.put(p, collisionSet);
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
							if (particleCollisionsMap.containsKey(particle2)) {
								particleCollisionsMap.get(particle2).add(col);
							} else {
								HashSet<CollisionEvent> collisionSet = new HashSet<>();
								collisionSet.add(col);
								particleCollisionsMap.put(particle2, collisionSet);
							}
							if (particleCollisionsMap.containsKey(p)) {
								particleCollisionsMap.get(p).add(col);
							} else {
								HashSet<CollisionEvent> collisionSet = new HashSet<>();
								collisionSet.add(col);
								particleCollisionsMap.put(p, collisionSet);
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
				if (particleCollisionsMap.containsKey(particle)) {
					particleCollisionsMap.get(particle).add(col);
				} else {
					HashSet<CollisionEvent> collisionSet = new HashSet<>();
					collisionSet.add(col);
					particleCollisionsMap.put(particle, collisionSet);
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
					if (particleCollisionsMap.containsKey(p)) {
						particleCollisionsMap.get(p).add(col);
					} else {
						HashSet<CollisionEvent> collisionSet = new HashSet<>();
						collisionSet.add(col);
						particleCollisionsMap.put(p, collisionSet);
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
					if (particleCollisionsMap.containsKey(p1)) {
						particleCollisionsMap.get(p1).add(col);
					} else {
						HashSet<CollisionEvent> collisionSet = new HashSet<>();
						collisionSet.add(col);
						particleCollisionsMap.put(p1, collisionSet);
					}
					if (particleCollisionsMap.containsKey(p2)) {
						particleCollisionsMap.get(p2).add(col);
					} else {
						HashSet<CollisionEvent> collisionSet = new HashSet<>();
						collisionSet.add(col);
						particleCollisionsMap.put(p2, collisionSet);
					}
				}
			}
		}
	}
	
	public void shuffleLayers(Collection<Particle> aloneParticles) {
		ArrayList<Integer> layers = new ArrayList<>();
		for (Particle p : aloneParticles) {
			layers.add(p.getLayer());
		}
		Random rd = new Random();
		for (Particle p : aloneParticles) {
			int layerIndex = rd.nextInt(layers.size());
			p.setLayer(layers.get(layerIndex));
			layers.remove(layerIndex);
		}
	}
	
	public void rotate(double rotationAngle, double xCenter, double yCenter) {
		for(Point p : points) {
			p.rotate(rotationAngle, new Point(xCenter, yCenter));
		}
		for(Particle p : particles) {
			p.rotate(rotationAngle, new Point(xCenter, yCenter));
		}
		edges = Edge.generateEdgesFromPoints(points);
		orientation += rotationAngle;
		while (orientation < 0) {
			orientation += 2 * Math.PI;
		}
		while (orientation >= 2 * Math.PI) {
			orientation -= 2 * Math.PI;
		}
	}
	
	

	public static class RectangularSet extends ParticleSet {
		public RectangularSet(ArrayList<Particle> particles, int width, int height, int widthOffset, int heightOffset,
				boolean bounceParticles) {
			super(particles, bounceParticles);
			points = new ArrayList<>();
			points.add(new Point(widthOffset, heightOffset));
			points.add(new Point(widthOffset, heightOffset + height));
			points.add(new Point(widthOffset + width, heightOffset + height));
			points.add(new Point(widthOffset + width, heightOffset));
			edges = Edge.generateEdgesFromPoints(points);
			rotate(0, widthOffset + width / 2, heightOffset + height / 2);
			System.out.print(edges);
			initCollisions();
		}
	}

}
