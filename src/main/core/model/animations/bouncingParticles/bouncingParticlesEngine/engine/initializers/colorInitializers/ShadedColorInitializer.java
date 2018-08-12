package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.util.color.ShadedColorMap;

public class ShadedColorInitializer extends AbstractColorInitializer {

	public ShadedColorInitializer(Collection<? extends Particle> particles, double saturation, double satWidth, double brightness, double brightWidth, double hue, double hueWidth) {
		super(particles);
		colorMap = new ShadedColorMap(hue, hueWidth, saturation, satWidth, brightness, brightWidth);
	}

	@Override
	public void resolveColor() {
		for(Particle p : particles) {
			p.setColor(colorMap.random());
		}
	}
}
