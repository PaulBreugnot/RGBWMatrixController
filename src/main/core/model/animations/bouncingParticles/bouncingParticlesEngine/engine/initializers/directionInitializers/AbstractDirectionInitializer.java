package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.directionInitializers;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.general.AbstractInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public abstract class AbstractDirectionInitializer extends AbstractInitializer {

	public AbstractDirectionInitializer(Collection<? extends Particle> particles) {
		super(particles);
	}
	
	public abstract void resolveDirections();

}
