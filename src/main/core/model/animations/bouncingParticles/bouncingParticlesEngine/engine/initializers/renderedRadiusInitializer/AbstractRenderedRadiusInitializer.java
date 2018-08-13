package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.renderedRadiusInitializer;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.general.AbstractInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public abstract class AbstractRenderedRadiusInitializer extends AbstractInitializer {
	
	public AbstractRenderedRadiusInitializer(Collection<? extends Particle> particles) {
		super(particles);
	}
	
	public abstract boolean resolveRenderedRadius(double minRenderedRadius, double maxRenderedRadius);
}
