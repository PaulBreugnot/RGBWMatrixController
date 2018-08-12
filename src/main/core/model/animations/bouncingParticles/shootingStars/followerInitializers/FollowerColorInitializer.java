package main.core.model.animations.bouncingParticles.shootingStars.followerInitializers;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers.AbstractColorInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.shootingStars.particles.FollowerParticle;

public class FollowerColorInitializer extends AbstractColorInitializer {

	public FollowerColorInitializer(Collection<FollowerParticle> particles) {
		super(particles);
	}

	@Override
	public void resolveColor() {
		for (Particle p : particles) {
			p.setColor(((FollowerParticle) p).getParticleToFollow().getColor());
		}
		
	}

}
