package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils;

import java.util.ArrayList;

public class Edge {

	private double xOrigin;
	private double yOrigin;
	private double xFinal;
	private double yFinal;
	private double a;
	private double b;
	private double alpha; // By convention 0 <= alpha < PI

	public Edge(double xOrigin, double yOrigin, double xFinal, double yFinal) {
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.xFinal = xFinal;
		this.yFinal = yFinal;
		if (xFinal != xOrigin) {
			a = (yFinal - yOrigin) / (xFinal - xOrigin);
			b = yOrigin - a * xOrigin;
			alpha = Math.atan(a);
		} else {
			alpha = Math.PI / 2;
		}
	}

	public Edge(double alpha) {
		this.alpha = alpha;
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

	public double getLength() {
		return Math.sqrt(Math.pow(xOrigin - xFinal, 2) + Math.pow(yOrigin - yFinal, 2));
	}

	public double distanceFromPointToEdge(double xPoint, double yPoint) {
		if (xFinal != xOrigin) {
			return Math.abs(a * xPoint - yPoint + b) / Math.sqrt(a * a + 1);
		} else {
			return Math.abs(xPoint - xOrigin);
		}
	}

	public void rotate(double rotationAngle, double xCenter, double yCenter) {
		double dOrigin = Math.sqrt(Math.pow(xCenter - xOrigin, 2) + Math.pow(yCenter - yOrigin, 2));
		xOrigin = xCenter + dOrigin * Math.cos(rotationAngle);
		yOrigin = yCenter + dOrigin * Math.sin(rotationAngle);
		double dFinal = Math.sqrt(Math.pow(xCenter - xFinal, 2) + Math.pow(yCenter - yFinal, 2));
		xFinal = xCenter + dFinal * Math.cos(rotationAngle);
		yFinal = yCenter + dFinal * Math.sin(rotationAngle);
		alpha += rotationAngle;
		while (alpha < 0) {
			alpha += Math.PI;
		}
		while (alpha >= Math.PI) {
			alpha -= Math.PI;
		}
	}

	public static ArrayList<Edge> generateEdgesFromPoints(ArrayList<Point> points) {
		ArrayList<Edge> edges = new ArrayList<>();
		for (int i = 0; i < points.size(); i++) {
			if (i < points.size() - 1) {
				edges.add(new Edge(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(), points.get(i+1).getY()));
			}
			else {
				edges.add(new Edge(points.get(i).getX(), points.get(i).getY(), points.get(0).getX(), points.get(0).getY()));
			}
		}
		return edges;
	}

	@Override
	public String toString() {
		return "Edge [xOrigin=" + xOrigin + ", yOrigin=" + yOrigin + ", xFinal=" + xFinal + ", yFinal=" + yFinal + "]";
	}

}
