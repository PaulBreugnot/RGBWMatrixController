package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils;

public class Point {

	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void rotate(double angle, Point center) {
		double newX = Math.cos(angle) * (this.x - center.getX()) - Math.sin(angle) * (this.y - center.getY())
				+ center.getX();
		double newY = Math.sin(angle) * (this.x - center.getX()) + Math.cos(angle) * (this.y - center.getY())
				+ center.getY();
		this.x = newX;
		this.y = newY;
	}
	
	@Override
	public String toString() {
		return "("+ x + ", " + y + ")";
	}

}
