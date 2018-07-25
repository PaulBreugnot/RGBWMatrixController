package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.Edge;

public class DisappearingParticle extends Particle {

	private ParticleSet particleSet;
	
	@Override
	public void bounceEdge(Edge edge) {
		particleSet.removeParticle(this);
	}

}
