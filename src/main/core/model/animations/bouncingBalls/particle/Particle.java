package main.core.model.animations.bouncingBalls.particle;

import java.util.ArrayList;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import main.core.model.animations.bouncingBalls.utils.Edge;

public class Particle {

	private double speed;
	private double alpha;
	private double radius;
	private SimpleDoubleProperty xPos;
	private SimpleDoubleProperty yPos;
	private Color color = Color.RED;

	public Particle(double speed, double alpha, double xPos, double yPos, double radius) {
		this.speed = speed;
		this.alpha = alpha;
		this.xPos = new SimpleDoubleProperty(xPos);
		this.yPos = new SimpleDoubleProperty(yPos);
		this.radius = radius;
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

	public double getRadius() {
		return radius;
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
	
	public Color getColor(int x, int y) {
		// x and y are given in the particle
		return color;
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
		//First we calculate the alpha of the line that link the two center at collision time
		if (p.getxPos() != getxPos()) {
			double a = (p.getyPos() - getyPos()) / (p.getxPos() - getxPos());
			alpha = Math.atan(a);
		} else {
			alpha = Math.PI / 2;
		}
		//The we take the perpendicular
		if (0 <= alpha && alpha < Math.PI / 2) {
			alpha += Math.PI / 2;
		}
		else {
			alpha += Math.PI;
		}
		if (!(0 <= alpha && alpha < Math.PI)) {
			System.out.println("ANGLE ERROR!!");
		}
		Edge collisionEdge = new Edge(alpha);
		bounceEdge(collisionEdge);
		p.bounceEdge(collisionEdge);
	}

	public static double collisionTime(Particle p1, Particle p2) {
		//Equations from https://introcs.cs.princeton.edu/java/assignments/collisions.html
		double deltaX = p2.getxPos() - p1.getxPos();
		double deltaY = p2.getyPos() - p1.getyPos();
		double deltaVx = Math.cos(p2.getAlpha()) * p2.getSpeed() - Math.cos(p1.getAlpha()) * p1.getSpeed();
		double deltaVy = Math.sin(p2.getAlpha()) * p2.getSpeed() - Math.sin(p1.getAlpha()) * p1.getSpeed();
		if (deltaX * deltaVx + deltaY * deltaVy >= 0) {
			return Double.MAX_VALUE;
		}
		double sigma = p1.getRadius() + p2.getRadius();
		double d = Math.pow(deltaX * deltaVx + deltaY * deltaVy, 2)
				- (deltaVx * deltaVx + deltaVy * deltaVy) * (deltaX * deltaX + deltaY * deltaY - sigma * sigma);
		if (d < 0) {
			return Double.MAX_VALUE;
		}
		double collisionTime = - (deltaX * deltaVx + deltaY * deltaVy + Math.sqrt(d)) / (deltaVx * deltaVx + deltaVy * deltaVy);
		System.out.println(collisionTime);
		return collisionTime;
	}

	public static double collisionTime(Particle p, Edge e) {
		double xCollision;
		double yCollision;
		if (e.getAlpha() != p.getAlpha()) {
			double d = Math.abs(p.getRadius() / Math.sin(p.getAlpha() - e.getAlpha()));
			System.out.println(d);
			if (e.getAlpha() != Math.PI / 2) {
				xCollision = (p.getyPos() - Math.tan(p.getAlpha()) * p.getxPos() - e.b())
						/ (e.a() - Math.tan(p.getAlpha())) - d * Math.cos(p.getAlpha());
				yCollision = e.a() * (xCollision - p.getxPos()) + e.b() - d * Math.sin(p.getAlpha());
			} else {
				xCollision = e.getxOrigin() - d * Math.cos(p.getAlpha());
				yCollision = Math.tan(p.getAlpha()) * (xCollision - p.getxPos()) + p.getyPos() - d * Math.sin(p.getAlpha());
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
