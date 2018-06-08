package main.core.model.animations.bouncingBalls.collision;

import main.core.model.animations.bouncingBalls.particle.Particle;
import main.core.model.animations.bouncingBalls.utils.Edge;

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
	public void trigger() {
		if (isValid()) {
			System.out.println("Bounce");
			particle.bounceEdge(edge);
		}
	}

}
