package main.core.model.animations.bouncingParticles.shootingStars.followerInitializers;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.radiusInitializers.AbstractRadiusInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.shootingStars.particles.FollowerParticle;

public class FollowerRadiusInitializer extends AbstractRadiusInitializer {

	public FollowerRadiusInitializer(Collection<FollowerParticle> particles) {
		super(particles);
	}

	@Override
	public boolean resolveRadius(double minRadius, double maxRadius) {
		for (Particle p : particles) {
			p.setRadius(((FollowerParticle) p).getParticleToFollow().getRadius());
		}
		return false;
	}

}
