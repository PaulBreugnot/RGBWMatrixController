package main.core.model.animations.bouncingBalls;

public class Edge {

	private double xOrigin;
	private double yOrigin;
	private double xFinal;
	private double yFinal;
	private double a;
	private double b;
	private double alpha;

	public Edge(double xOrigin, double yOrigin, double xFinal, double yFinal) {
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.xFinal = xFinal;
		this.yFinal = yFinal;
		a = (yFinal - yOrigin) / (xFinal - xOrigin);
		b = yOrigin - a * xOrigin;
		//TODO calc alpha
		alpha = Math.atan(a);
	}

	public double getxOrigin() {
		return xOrigin;
	}

	public void setxOrigin(double xOrigin) {
		this.xOrigin = xOrigin;
	}

	public double getyOrigin() {
		return yOrigin;
	}

	public void setyOrigin(double yOrigin) {
		this.yOrigin = yOrigin;
	}

	public double getxFinal() {
		return xFinal;
	}

	public void setxFinal(double xFinal) {
		this.xFinal = xFinal;
	}

	public double getyFinal() {
		return yFinal;
	}
	
	public double getAlpha() {
		return alpha;
	}

	public void setyFinal(double yFinal) {
		this.yFinal = yFinal;
	}

	public double a() {
		return a;
	}

	public double b() {
		return b;
	}

	public double distanceFromPointToEdge(double xPoint, double yPoint) {
		return Math.abs(a * xPoint - yPoint + b) / Math.sqrt(a * a + 1);
	}

}
