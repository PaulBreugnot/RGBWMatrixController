package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle;

import java.util.ArrayList;

public abstract class ParticlesInitializer {
	
	protected ArrayList<Particle> particles;
	
	public ParticlesInitializer(ArrayList<Particle> particles) {
		this.particles = particles;
	}

	// Return true if all particles were assigned a position
	public abstract boolean resolvePositions(double width, double height, double xOrigin, double yOrigin);
	public abstract void resolveDirections();
	public abstract void resolveSpeeds(double vMin, double vMax);

	private class Space {

		private double width;
		private double height;
		private double x;
		private double y;

		public Space(double x, double y, double width, double height) {
			this.width = width;
			this.height = height;
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getWidth() {
			return width;
		}

		public double getHeight() {
			return height;
		}

		public boolean shrink(double value) {
			x += value;
			width -= value;
			return width > 0;
		}

	}

	public class Pair {
		private double x;
		private double y;

		public Pair(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

	}
}
