package main.core.model.animations.bouncingParticles.particleFall;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.Edge;

public class DisappearingParticle extends Particle {

	private BouncingParticlesEngine bouncingParticlesEngine;
	
	public DisappearingParticle(BouncingParticlesEngine bouncingParticlesEngine) {
		this.bouncingParticlesEngine = bouncingParticlesEngine;
	}
	
	@Override
	public void bounceEdge(Edge edge) {
		outDated = true;
		bouncingParticlesEngine.removeParticleToShow(this);
	}

}
