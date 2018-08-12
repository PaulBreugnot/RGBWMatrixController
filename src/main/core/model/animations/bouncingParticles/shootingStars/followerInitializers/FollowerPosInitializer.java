package main.core.model.animations.bouncingParticles.shootingStars.followerInitializers;

import java.util.Collection;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.positionInitializers.AbstractPositionInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.shootingStars.particles.FollowerParticle;

public class FollowerPosInitializer extends AbstractPositionInitializer {

	public FollowerPosInitializer(Collection<FollowerParticle> particles) {
		super(particles);
	}

	@Override
	public boolean resolvePositions(double width, double height, double xOrigin, double yOrigin) {
		for (Particle p : particles) {
			p.setxPos(((FollowerParticle) p).getxPos());
			p.setyPos(((FollowerParticle) p).getyPos());
		}
		return false;
	}

}
