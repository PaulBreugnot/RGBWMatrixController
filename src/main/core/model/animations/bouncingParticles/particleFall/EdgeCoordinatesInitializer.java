package main.core.model.animations.bouncingParticles.particleFall;

import java.util.ArrayList;
import java.util.Random;

import javafx.util.Pair;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.positionInitializers.AbstractPositionInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.Edge;

public class EdgeCoordinatesInitializer extends AbstractPositionInitializer {

	// Edge from which particles are popping
	private Edge edge;
	
	public EdgeCoordinatesInitializer(ArrayList<Particle> particles, Edge edge) {
		super(particles);
		this.edge = edge;
	}


	
	private Pair<Double, Double> randomEdgeCoordinates(double radius) {
		Random rd = new Random();
		double edgeCoordinate = rd.nextDouble() * (edge.getLength() - 2 * radius);
		double x = Math.cos(edge.getAlpha()) * edgeCoordinate + (edge.getxOrigin() + Math.cos(edge.getAlpha()) * radius);
		double y = Math.sin(edge.getAlpha()) * edgeCoordinate + (edge.getyOrigin() + Math.sin(edge.getAlpha()) * radius);
		
		return new Pair<>(x, y);
	}

	@Override
	public boolean resolvePositions(double width, double height, double xOrigin, double yOrigin) {
		for(Particle p : particles) {
			Pair<Double, Double> coordinates = randomEdgeCoordinates(p.getRadius());
			p.setxPos(coordinates.getKey());
			p.setyPos(coordinates.getValue());
			while(edge.distanceFromPointToEdge(p.getxPos(), p.getyPos()) < p.getRadius() + 0.1) {
				// We assume that angle and speed are already set so that the particle go away from edge
				System.out.println("Adjusting...");
				p.progress(1);
			}
			System.out.println(p.getRadius());
			System.out.println("x : " + p.getxPos());
			System.out.println("y : " + p.getyPos());
		}
		return true;
	}
}
