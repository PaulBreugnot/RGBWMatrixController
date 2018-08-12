package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.speedInitializers;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.general.AbstractInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public abstract class AbstractSpeedInitializer extends AbstractInitializer {

	public AbstractSpeedInitializer(Collection<? extends Particle> particles) {
		super(particles);
	}

	public abstract void resolveSpeed(double vMin, double vMax);
}
