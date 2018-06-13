package main.core.model.animations.bouncingBalls.particle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import main.core.model.animations.bouncingBalls.collision.CollisionEvent;
import main.core.model.animations.bouncingBalls.collision.EdgeCollisionEvent;
import main.core.model.animations.bouncingBalls.utils.Edge;

public class ParticleSet {

	private double time = 0;
	private ArrayList<Particle> particles;
	private ArrayList<Edge> edges;
	private PriorityQueue<CollisionEvent> collisions = new PriorityQueue<>();
	private HashMap<Particle, HashSet<CollisionEvent>> ParticleCollisionsMap = new HashMap<>();

	private ParticleSet(ArrayList<Particle> particles) {
		this.particles = particles;
	}

	public ParticleSet(ArrayList<Particle> particles, ArrayList<Edge> edges) {
		this.particles = particles;
		this.edges = edges;
		initCollisions();
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	public ArrayList<Particle> getParticles() {
		return particles;
	}

	public void progress(double deltaT) {
		while (time + deltaT >= collisions.peek().getTime()) {
			// Trigger next collision
			Particle collidedParticle = collisions.poll().trigger();
			//All other events evolving this particle becomes obsoletes
			collisions.removeAll(ParticleCollisionsMap.get(collidedParticle));
			ParticleCollisionsMap.get(collidedParticle).clear();
			initCollisions();
		}

		// Now we are safe
		for (Particle particle : particles) {
			particle.progress(deltaT);
		}
		time += deltaT;
	}

	public void initCollisions() {
		// Generate all collisions from current particles set
		checkEdgesCollisions();
		checkParticleCollisions();
	}

	private void updateCollisions(Particle particle1, Particle particle2) {
		// Update collisions after particle1 and particle2 have collided

	}

	private void updateCollisions(Particle particle) {
		// Update collisions after particle has collided an edge
		
	}

	private void checkEdgesCollisions() {
		for (Particle p : particles) {
			for (Edge edge : edges) {
				double t = Particle.collisionTime(p, edge);
				if (t < Double.MAX_VALUE) {
					CollisionEvent col = new EdgeCollisionEvent(p, edge, time + t);
					collisions.add(col);
					if(ParticleCollisionsMap.containsKey(p)) {
						ParticleCollisionsMap.get(p).add(col);
					}
					else {
						HashSet<CollisionEvent> collisionSet = new HashSet<>();
						collisionSet.add(col);
						ParticleCollisionsMap.put(p,  collisionSet);
					}
				}
			}
		}
		//System.out.println(collisions);
	}

	private void checkParticleCollisions() {
		for (int i = 0; i < particles.size(); i++) {
			Particle p1 = particles.get(i);
			for (int j = i + 1; j < particles.size(); j++) {
				Particle p2 = particles.get(j);
			}
		}
	}

	public static class RectangularSet extends ParticleSet {
		public RectangularSet(ArrayList<Particle> particles, int width, int height) {
			super(particles);
			ArrayList<Edge> edges = new ArrayList<>();
			edges.add(new Edge(0, 0, width, 0));
			edges.add(new Edge(0, 0, 0, height));
			edges.add(new Edge(0, height, width, height));
			edges.add(new Edge(width, 0, width, height));
			setEdges(edges);
			initCollisions();
		}
	}

}
