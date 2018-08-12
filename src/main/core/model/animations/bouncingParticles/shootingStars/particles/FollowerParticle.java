package main.core.model.animations.bouncingParticles.shootingStars.particles;

import javafx.util.Pair;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.particleFall.DisappearingParticle;

public class FollowerParticle extends DisappearingParticle {

	private LeaderParticle particleToFollow;
	private int offset;
	private int resolution = 10;

	public FollowerParticle(BouncingParticlesEngine bouncingParticlesEngine, LeaderParticle particleToFollow,
			int offset) {
		super(bouncingParticlesEngine);
		this.particleToFollow = particleToFollow;
		this.offset = offset;
	}

	public LeaderParticle getParticleToFollow() {
		return particleToFollow;
	}

	@Override
	public void progress(double deltaT) {
		// Nothing : we don't progress from BouncingParticlesEngine
	}

	public void progress() {
		if (!particleToFollow.isOutdated()) {
			if (particleToFollow.getHistory().size() < resolution * offset + 1) {
				xPos.set(particleToFollow.getxPos());
				yPos.set(particleToFollow.getyPos());
			} else {
				Pair<Double, Double> oldLeaderCoordinates = particleToFollow.getHistoryCoordinates(resolution * offset);
				xPos.set(oldLeaderCoordinates.getKey());
				yPos.set(oldLeaderCoordinates.getValue());
			}
		} else {
			bouncingParticlesEngine.removeParticleToShow(this);
		}
	}

	@Override
	public int compareTo(Particle arg0) {
		if (arg0.getColor().getBrightness() > getColor().getBrightness()) {
			return 1;
		} else if (arg0.getColor().getBrightness() < getColor().getBrightness()) {
			return -1;
		}
		return 0;
	}
}
