package main.core.model.animations.bouncingBalls.particle;

import java.util.ArrayList;
import java.util.PriorityQueue;

import main.core.model.animations.bouncingBalls.collision.CollisionEvent;
import main.core.model.animations.bouncingBalls.utils.Edge;

public class ParticleSet {

	private ArrayList<Particle> particles;
	private ArrayList<Edge> edges;
	private PriorityQueue<CollisionEvent> collisions;
	

	public ParticleSet() {
		particles = new ArrayList<>();
	}

	public ParticleSet(ArrayList<Edge> edges) {
		particles = new ArrayList<>();
		this.edges = edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	public void addParticle(Particle particle) {
		particles.add(particle);
	}

	public ArrayList<Particle> getParticles() {
		return particles;
	}

	public void progress(double deltaT) {
		int stepCounts = (int) Math.floor(collisions.peek().getTime()/deltaT);
		//TODO : Don't forget to change the time origin of all collision events
		for(int i = 0; i < stepCounts; i ++) {
			//We are safe : no collisions
			for (Particle particle : particles) {
				particle.progress(deltaT);
			}
		}
		//Trigger next collision
		collisions.poll().trigger();
	}

	public static class RectangularSet extends ParticleSet {
		public RectangularSet(int width, int height) {
			super();
			ArrayList<Edge> edges = new ArrayList<>();
			edges.add(new Edge(0, 0, width, 0));
			edges.add(new Edge(0, 0, 0, height));
			edges.add(new Edge(0, height, width, height));
			edges.add(new Edge(width, 0, width, height));
			setEdges(edges);
		}
	}

}
