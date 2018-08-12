package main.core.model.animations.bouncingParticles.shootingStars.followerInitializers;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.directionInitializers.AbstractDirectionInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.shootingStars.particles.FollowerParticle;

public class FollowerDirectionInitializer extends AbstractDirectionInitializer {

	public FollowerDirectionInitializer(Collection<FollowerParticle> particles) {
		super(particles);
	}

	@Override
	public void resolveDirections() {
		for (Particle p : particles) {
			p.setAlpha(((FollowerParticle) p).getParticleToFollow().getAlpha());
		}
		
	}

}
