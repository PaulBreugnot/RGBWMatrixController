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
		alpha = 2 * alphaLine - alpha;
	}

	public void bounceParticle(Particle p) {

	}

	public static double collisionTime(Particle p1, Particle p2) {

		return 0;
	}

	public static double collisionTime(Particle p, Edge e) {
		double xCollision;
		double yCollision;
		if (e.getAlpha() != p.getAlpha()) {
			if (e.getAlpha() != Math.PI / 2) {
				xCollision = (p.getyPos() - Math.tan(p.getAlpha()) * p.getxPos() - e.b())
						/ (e.a() - Math.tan(p.getAlpha()));
				yCollision = e.a() * (xCollision - p.getxPos()) + e.b();
			} else {
				xCollision = e.getxOrigin();
				yCollision = Math.tan(p.getAlpha()) * (xCollision - p.getxPos()) + p.getyPos();
			}
			if (Math.cos(p.getAlpha()) * (xCollision - p.getxPos()) >= 0
					&& Math.sin(p.getAlpha()) * (yCollision - p.getyPos()) >= 0) {
				double distance = Math
						.sqrt(Math.pow(p.getxPos() - xCollision, 2) + Math.pow(p.getyPos() - yCollision, 2));
				return distance / p.getSpeed();
			} else {
				return Double.MAX_VALUE;
			}
		} else {
			return Double.MAX_VALUE;
		}
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
