package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.radiusInitializers;

import java.util.ArrayList;
import java.util.Random;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class RandomRadiusInitializer extends AbstractRadiusInitializer {

	public RandomRadiusInitializer(ArrayList<Particle> particles) {
		super(particles);
	}

	@Override
	public boolean resolveRadius(double minRadius, double maxRadius) {
		Random rd = new Random();
		for(Particle p : particles) {
			p.setRadius(rd.nextDouble() * (maxRadius - minRadius) + minRadius);
		}
		return false;
	}

}
