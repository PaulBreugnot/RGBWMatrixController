package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.util.color.ColorMap;

public class RandomColorInitializer extends AbstractColorInitializer {

	public RandomColorInitializer(Collection<? extends Particle> particles) {
		super(particles);
		colorMap = new ColorMap(0, 360, 1, 1, 1, 1);
	}

	@Override
	public void resolveColor() {
		for(Particle p : particles) {
			p.setColor(colorMap.random());
		}
	}
}
