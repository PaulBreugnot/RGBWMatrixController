package main.core.model.animations.bouncingParticles.shootingStars.followerInitializers;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.speedInitializers.AbstractSpeedInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.shootingStars.particles.FollowerParticle;

public class FollowerSpeedInitializer extends AbstractSpeedInitializer {

	public FollowerSpeedInitializer(Collection<? extends Particle> followers) {
		super(followers);
	}

	@Override
	public void resolveSpeed(double vMin, double vMax) {
		for (Particle p : particles) {
			p.setSpeed(((FollowerParticle) p).getParticleToFollow().getSpeed());
		}
	}

}
