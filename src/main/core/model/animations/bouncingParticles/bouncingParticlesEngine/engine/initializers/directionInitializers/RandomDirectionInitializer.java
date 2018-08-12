package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.directionInitializers;

import java.util.Collection;
import java.util.Random;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class RandomDirectionInitializer extends AbstractDirectionInitializer {

	public RandomDirectionInitializer(Collection<? extends Particle> particles) {
		super(particles);
	}

	@Override
	public void resolveDirections() {
		Random rd = new Random();
		for(Particle p : particles) {
			p.setAlpha((rd.nextDouble() - 0.5) * 2 * Math.PI);
		}
	}
}
