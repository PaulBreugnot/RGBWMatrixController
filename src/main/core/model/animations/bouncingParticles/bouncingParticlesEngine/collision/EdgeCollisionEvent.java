package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.collision;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.CollisionResult;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.Edge;

public class EdgeCollisionEvent extends CollisionEvent {
	
	public Particle particle;
	public Edge edge;
	
	public EdgeCollisionEvent(Particle particle, Edge edge, double t) {
		super(t);
		this.particle = particle;
		this.edge = edge;
	}
	
	public Particle getParticle() {
		return particle;
	}
	
	public Edge getEdge() {
		return edge;
	}
	
	@Override
	public String toString() {
		return "P : " + particle.toString();
	}
	
	@Override
	public CollisionResult trigger() {
		if (isValid()) {
			particle.bounceEdge(edge);
			return new CollisionResult(particle);
		}
		return null;
	}

}
