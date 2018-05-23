package main.core.model.animations.bouncingBalls;

import java.util.ArrayList;

public class Particle {

	private double speed;
	private double alpha;
	private double xPos;
	private double yPos;

	public Particle(double speed, double alpha, double xPos, double yPos) {
		this.speed = speed;
		this.alpha = alpha;
		this.xPos = xPos;
		this.yPos = yPos;
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
		return xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void progress(double deltaT) {
		xPos += speed * Math.cos(alpha) * deltaT;
		yPos += speed * Math.sin(alpha) * deltaT;
	}

	public void bounceEdge(double alphaLine) {
		// By convention 0 <= alphaLine < PI
		System.out.println("alpha = " + alpha);
		System.out.println("alphaLine = " + alphaLine);
		alpha = 2 * alphaLine - alpha;
		System.out.println("newAlpha = " + alpha);
	}

	public static void bounceParticles(Particle p1, Particle p2) {

	}

	public void edgeCollisions(ArrayList<Edge> edges, double deltaT) {
		for (Edge edge : edges) {
			System.out.println(edge.distanceFromPointToEdge(xPos, yPos));
			if ((edge.distanceFromPointToEdge(xPos, yPos)) < speed * deltaT) {
				System.out.println("Collision");
				bounceEdge(edge.getAlpha());
			}
		}
	}

}
