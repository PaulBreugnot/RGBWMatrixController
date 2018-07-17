package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.positionInitializers;

import java.util.ArrayList;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class CompactPositionInitializer extends AbstractPositionInitializer {
	
	private ArrayList<Space> freeSpaces = new ArrayList<>();
	private double safeSpace;
	
	public CompactPositionInitializer(ArrayList<Particle> particles, double safeSpace) {
		super(particles);
		this.safeSpace = safeSpace;
		this.particles = particles;
		// Sort particles by radius
		this.particles.sort((Particle p1, Particle p2) -> {
			if (p1.getRadius() < p2.getRadius()) {
				return 1;
			} else {
				return -1;
			}
		});
	}
	
	@Override
	public boolean resolvePositions(double width, double height, double xOrigin, double yOrigin) {
		freeSpaces.add(new Space(xOrigin, yOrigin, width, height));
		boolean full = false;
		ArrayList<Particle> particlesToRemove = new ArrayList<>();
		for (Particle p : particles) {
			if (!full) {
				int s = 0;
				boolean assigned = false;
				while (!assigned && s < freeSpaces.size()) {
					if (freeSpaces.get(s).getWidth() >= 2 * p.getRadius() + safeSpace) {
						if (freeSpaces.get(s).getHeight() >= 2 * p.getRadius() + safeSpace) {
							setParticleInFreespace(p, s);
							assigned = true;
						}
					}
					s++;
				}
				if (!assigned) {
					// Matrix is full
					full = true;
				}
			} else {
				particlesToRemove.add(p);
			}
		}
		particles.removeAll(particlesToRemove);
		return full;
	}
	
	private void setParticleInFreespace(Particle p, int s) {
		Space freeSpace = freeSpaces.get(s);
		freeSpaces.add(new Space(freeSpace.getX(), freeSpace.getY() + 2 * p.getRadius() + safeSpace, 2 * p.getRadius() + safeSpace,
				freeSpace.getHeight() - (2 * p.getRadius() + safeSpace)));
		p.setxPos(freeSpace.getX() + 1 + p.getRadius());
		p.setyPos(freeSpace.getY() + 1 + p.getRadius());

		if (!freeSpace.shrink(2 * p.getRadius() + safeSpace)) {
			freeSpaces.remove(s);
		}
	}
	
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
}
