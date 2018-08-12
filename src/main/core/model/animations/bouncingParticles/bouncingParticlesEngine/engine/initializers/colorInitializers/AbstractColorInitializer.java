package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.general.AbstractInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.util.color.ColorMap;

public abstract class AbstractColorInitializer extends AbstractInitializer {
	
	protected ColorMap colorMap;

	public AbstractColorInitializer(Collection<? extends Particle> particles) {
		super(particles);
	}

	public abstract void resolveColor();
	
	public ColorMap getColorMap() {
		return colorMap;
	}
}
