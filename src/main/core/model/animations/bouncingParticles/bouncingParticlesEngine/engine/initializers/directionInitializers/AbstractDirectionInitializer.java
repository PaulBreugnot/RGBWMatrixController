package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.directionInitializers;

import java.util.ArrayList;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.general.AbstractInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public abstract class AbstractDirectionInitializer extends AbstractInitializer {

	public AbstractDirectionInitializer(ArrayList<Particle> particles) {
		super(particles);
	}
	
	public abstract void resolveDirections();

}
