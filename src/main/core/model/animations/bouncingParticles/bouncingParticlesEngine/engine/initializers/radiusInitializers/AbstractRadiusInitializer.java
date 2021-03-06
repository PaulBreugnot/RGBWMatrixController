package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.radiusInitializers;

import java.util.ArrayList;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.general.AbstractInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public abstract class AbstractRadiusInitializer extends AbstractInitializer {

	public AbstractRadiusInitializer(ArrayList<Particle> particles) {
		super(particles);
	}
	
	public abstract boolean resolveRadius(double minRadius, double maxRadius);

}
