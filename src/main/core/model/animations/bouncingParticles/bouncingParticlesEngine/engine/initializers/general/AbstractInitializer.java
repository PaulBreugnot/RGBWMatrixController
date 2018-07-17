package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.general;

import java.util.ArrayList;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public abstract class AbstractInitializer {
	
	protected ArrayList<Particle> particles;
	
	public AbstractInitializer(ArrayList<Particle> particles) {
		this.particles = particles;
	}
}
