package main.core.model.animations.bouncingBalls.particle;

import java.util.ArrayList;

import javafx.beans.property.SimpleDoubleProperty;
import main.core.model.animations.bouncingBalls.utils.Edge;

public class Particle {

	private double speed;
	private double alpha;
	private SimpleDoubleProperty xPos;
	private SimpleDoubleProperty yPos;

	public Particle(double speed, double alpha, double xPos, double yPos) {
		this.speed = speed;
		this.alpha = alpha;
		this.xPos = new SimpleDoubleProperty(xPos);
		this.yPos = new SimpleDoubleProperty(yPos);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getxPos() {
		return xPos.get();
	}

	public SimpleDoubleProperty xPosProperty() {
		return xPos;
	}

	public SimpleDoubleProperty yPosProperty() {
		return yPos;
	}

	public double getyPos() {
		return yPos.get();
	}

	public void progress(double deltaT) {
		xPos.set(xPos.get() + speed * Math.cos(alpha) * deltaT);
		yPos.set(yPos.get() + speed * Math.sin(alpha) * deltaT);
	}

	public void bounceEdge(Edge edge) {
		// By convention 0 <= alphaLine < PI
		double alphaLine = edge.getAlpha();
		System.out.println("alpha = " + alpha);
		System.out.println("alphaLine = " + alphaLine);
		alpha = 2 * alphaLine - alpha;
		System.out.println("newAlpha = " + alpha);
	}

	public void bounceParticle(Particle p) {

	}

	public void edgeCollisions(ArrayList<Edge> edges, double deltaT) {
		for (Edge edge : edges) {
			System.out.println(edge.distanceFromPointToEdge(xPos.get(), yPos.get()));
			if ((edge.distanceFromPointToEdge(xPos.get(), yPos.get())) < speed * deltaT) {
				System.out.println("Collision");
				bounceEdge(edge);
			}
		}
	}

}
