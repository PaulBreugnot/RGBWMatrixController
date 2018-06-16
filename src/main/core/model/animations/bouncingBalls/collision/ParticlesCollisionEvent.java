package main.core.model.animations.bouncingBalls.collision;

import main.core.model.animations.bouncingBalls.particle.Particle;
import main.core.model.animations.bouncingBalls.utils.CollisionResult;

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
		a.bounceParticle(b);
		return new CollisionResult(a, b);
	}
}
