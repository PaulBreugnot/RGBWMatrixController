package main.core.model.animations.bouncingParticles.particleWave;

import java.util.ArrayList;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.positionInitializers.AbstractPositionInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.particleWave.ParticleWave.Orientation;

public class WavePositionInitializer extends AbstractPositionInitializer {

	private double waveLength;
	private double radius;
	private double resolution;
	private Orientation orientation;
	private double angleOffset;

	public WavePositionInitializer(ArrayList<Particle> particles, double waveLength, double radius, double resolution,
			Orientation orientation, double angleOffset) {
		super(particles);
		this.waveLength = waveLength;
		this.radius = radius;
		this.resolution = resolution;
		this.orientation = orientation;
		this.angleOffset = angleOffset;
	}

	@Override
	public boolean resolvePositions(double width, double height, double xOrigin, double yOrigin) {
		double x;
		double y;

		switch (orientation) {
		case VERTICAL:
			x = 2 * radius;

			for (Particle p : particles) {
				if (x < width - 2 * radius) {
					y = radius + (height / 2 - 2 * (0.8 * radius)) * (1 + Math.sin(2 * Math.PI * x / waveLength + angleOffset));
					p.setxPos(xOrigin + x);
					p.setyPos(yOrigin + y);
					x += resolution;
				} else {
					return false;
				}
			}

			return true;

		case HORIZONTAL:
			y = 2 * radius;

			for (Particle p : particles) {
				if (y < width - 2 * radius) {
					x = radius + (width / 2 - 2 * (0.8 * radius)) * (1 + Math.sin(2 * Math.PI * y / waveLength + angleOffset));
					p.setxPos(xOrigin + x);
					p.setyPos(yOrigin + y);
					y += resolution;
				} else {
					return false;
				}
			}

			return true;

		default:
			return false;
		}

	}

}
