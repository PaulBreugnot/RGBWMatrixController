package main.core.model.animations.bouncingParticles.blobs;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.renderedRadiusInitializer.AbstractRenderedRadiusInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class RenderedRadiusInitializer extends AbstractRenderedRadiusInitializer {
	private double scale;

	public RenderedRadiusInitializer(Collection<? extends Particle> particles, double scale) {
		super(particles);
		this.scale = scale;
	}

	@Override
	public boolean resolveRenderedRadius(double minRenderedRadius, double maxRenderedRadius) {
		for(Particle p : particles) {
			p.setRenderedRadius(scale * p.getRadius());
		}
		return false;
	}

}
