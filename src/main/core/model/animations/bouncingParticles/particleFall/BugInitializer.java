package main.core.model.animations.bouncingParticles.particleFall;

import java.util.ArrayList;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.positionInitializers.AbstractPositionInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class BugInitializer extends AbstractPositionInitializer  {

	public BugInitializer(ArrayList<Particle> particles) {
		super(particles);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean resolvePositions(double width, double height, double xOrigin, double yOrigin) {
		// TODO Auto-generated method stub
		return false;
	}

}
