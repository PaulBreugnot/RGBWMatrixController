package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle;

import java.util.ArrayList;
import java.util.Random;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.Edge;
import main.core.model.animations.bouncingParticles.simpleBouncingParticles.BouncingParticle;

public class Particle implements Comparable<Particle> {

	protected ArrayList<Particle> AboveOf = new ArrayList<>();
	
	protected double speed;
	protected double alpha;
	protected double radius;
	protected SimpleDoubleProperty xPos;
	protected SimpleDoubleProperty yPos;
	protected Color color;

	public Particle(double speed, double alpha, double xPos, double yPos, double radius, Color color) {
		this.speed = speed;
		this.alpha = alpha;
		this.xPos = new SimpleDoubleProperty(xPos);
		this.yPos = new SimpleDoubleProperty(yPos);
		this.radius = radius;
		this.color = color;
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
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		//Main color
		return color;
	}
	
	public Color getColor(int x, int y) {
		// x and y are given in the particle
		return color;
	}
	
	public void addAboveOf(Particle particle) {
		AboveOf.add(particle);
	}
	
	public void removeAboveOf(Particle particle) {
		AboveOf.remove(particle);
	}
	
	public boolean isAboveOf(Particle particle) {
		return AboveOf.contains(particle);
	}
	
	public ArrayList<Particle> getAboveOf(){
		return AboveOf;
	}

	public void progress(double deltaT) {
		xPos.set(xPos.get() + speed * Math.cos(alpha) * deltaT);
		yPos.set(yPos.get() + speed * Math.sin(alpha) * deltaT);
	}

	public void bounceEdge(Edge edge) {
		// By convention 0 <= alphaLine < PI
		double alphaLine = edge.getAlpha();
		alpha = 2 * alphaLine - alpha;
		while(alpha <= -Math.PI) {
			alpha += 2 * Math.PI;
		}
		while(alpha > Math.PI) {
			alpha -= 2 * Math.PI;
		}
	}

	public static void bounceParticle(Particle p1, Particle p2) {
		//First we calculate the alpha of the line that link the two center at collision time
		double alphaEdge;
		if (p1.getxPos() != p2.getxPos()) {
			double a = (p1.getyPos() - p2.getyPos()) / (p1.getxPos() - p2.getxPos());
			alphaEdge = Math.atan(a);
		} else {
			alphaEdge = Math.PI / 2;
		}
		//The we take the perpendicular
		alphaEdge += Math.PI / 2;
		if (!(0 <= alphaEdge && alphaEdge < Math.PI)) {
			System.out.println("ANGLE ERROR!!");
		}
		Edge collisionEdge = new Edge(alphaEdge);
		p1.bounceEdge(collisionEdge);
		p2.bounceEdge(collisionEdge);
		
		Random rd = new Random();
		p1.setColor(Color.hsb(rd.nextInt(360), 1, 1));
		p2.setColor(Color.hsb(rd.nextInt(360), 1, 1));
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
		return collisionTime;
	}

	public static double collisionTime(Particle p, Edge e) {
		double xCollision;
		double yCollision;
		if (e.getAlpha() != p.getAlpha()) {
			double d = Math.abs(p.getRadius() / Math.sin(p.getAlpha() - e.getAlpha()));
			if (e.getAlpha() != Math.PI / 2) {
				xCollision = (p.getyPos() - Math.tan(p.getAlpha()) * p.getxPos() - e.b())
						/ (e.a() - Math.tan(p.getAlpha()));
				yCollision = e.a() * (xCollision - p.getxPos()) + e.b();
			} else {
				xCollision = e.getxOrigin();
				yCollision = Math.tan(p.getAlpha()) * (xCollision - p.getxPos()) + p.getyPos();
			}
			double uncorrectedX = xCollision;
			double uncorrectedY = yCollision;
			xCollision += - d * Math.cos(p.getAlpha());
			yCollision += - d * Math.sin(p.getAlpha());
			if (Math.cos(p.getAlpha()) * (uncorrectedX - p.getxPos()) >= 0
					&& Math.sin(p.getAlpha()) * (uncorrectedY - p.getyPos()) >= 0) {
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
			//System.out.println(edge.distanceFromPointToEdge(xPos.get(), yPos.get()));
			if ((edge.distanceFromPointToEdge(xPos.get(), yPos.get())) < speed * deltaT) {
				//System.out.println("Collision");
				bounceEdge(edge);
			}
		}
	}

	@Override
	public int compareTo(Particle arg0) {
		if(AboveOf.contains(arg0)) {
			return -1;
		}
		return 0;
	}

}
