package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.renderedRadiusInitializer;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class DefaultRenderedRadiusInitializer extends AbstractRenderedRadiusInitializer {

	public DefaultRenderedRadiusInitializer(Collection<? extends Particle> particles) {
		super(particles);
	}

	@Override
	public boolean resolveRenderedRadius(double minRenderedRadius, double maxRenderedRadius) {
		for (Particle p : particles) {
			p.setRenderedRadius(p.getRadius());
		}
		return false;
	}

}
