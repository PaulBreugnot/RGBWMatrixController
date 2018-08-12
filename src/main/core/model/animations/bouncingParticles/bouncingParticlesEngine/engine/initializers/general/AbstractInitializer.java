package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.general;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public abstract class AbstractInitializer {
	
	protected Collection<? extends Particle> particles;
	
	public AbstractInitializer(Collection<? extends Particle> particles) {
		this.particles = particles;
	}
}
