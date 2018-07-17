package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.positionInitializers;

import java.util.ArrayList;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.general.AbstractInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public abstract class AbstractPositionInitializer extends AbstractInitializer {

	public AbstractPositionInitializer(ArrayList<Particle> particles) {
		super(particles);
	}

	// Return true if all particles were assigned a position
	public abstract boolean resolvePositions(double width, double height, double xOrigin, double yOrigin);
}
