package main.core.model.animations.bouncingBalls.utils;

import main.core.model.animations.bouncingBalls.particle.Particle;

public class CollisionResult {
	Particle collidedParticle1 = null;
	Particle collidedParticle2 = null;
	
	public CollisionResult(Particle collidedParticle1, Particle collidedParticle2) {
		this.collidedParticle1 = collidedParticle1;
		this.collidedParticle2 = collidedParticle2;
	}

	public CollisionResult(Particle collidedParticle1) {
		this.collidedParticle1 = collidedParticle1;
	}

	public Particle getCollidedParticle1() {
		return collidedParticle1;
	}

	public Particle getCollidedParticle2() {
		return collidedParticle2;
	}
	
}
