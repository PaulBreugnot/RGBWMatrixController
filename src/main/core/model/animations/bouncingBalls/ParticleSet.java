package main.core.model.animations.bouncingBalls;

import java.util.ArrayList;

public class ParticleSet {

	private ArrayList<Particle> particles;
	private ArrayList<Edge> edges;

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
		for (Particle particle : particles) {
			particle.edgeCollisions(edges, deltaT);
			particle.progress(deltaT);
		}
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
