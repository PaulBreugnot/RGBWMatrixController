package main.core.model.animations.bouncingBalls.collision;

import main.core.model.animations.bouncingBalls.particle.Particle;

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
}
