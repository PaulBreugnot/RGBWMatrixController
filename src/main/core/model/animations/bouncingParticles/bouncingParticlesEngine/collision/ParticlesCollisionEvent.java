package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.collision;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.CollisionResult;

public class ParticlesCollisionEvent extends CollisionEvent {
	
	private Particle a;
	private Particle b;
	
	public ParticlesCollisionEvent(Particle a, Particle b, double t) {
		super(t);
		this.a = a;
		this.b = b;
	}
	
	public Particle getParticleA() {
		return a;
	}
	
	public Particle getParticleB() {
		return b;
	}
	
	@Override
	public CollisionResult trigger() {
		Particle.bounceParticle(a, b);
		return new CollisionResult(a, b);
	}
}
