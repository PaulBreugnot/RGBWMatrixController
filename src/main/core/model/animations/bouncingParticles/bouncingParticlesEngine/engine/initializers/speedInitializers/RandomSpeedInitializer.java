package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.speedInitializers;

import java.util.Collection;
import java.util.Random;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class RandomSpeedInitializer extends AbstractSpeedInitializer {

	public RandomSpeedInitializer(Collection<? extends Particle> particles) {
		super(particles);
	}

	@Override
	public void resolveSpeed(double vMin, double vMax) {
		Random rd = new Random();
		for (Particle p : particles) {
			p.setSpeed(rd.nextDouble() * (vMax - vMin) + vMin);
		}
		
	}

}
