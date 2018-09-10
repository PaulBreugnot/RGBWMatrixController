package main.core.model.animations.bouncingParticles.blobs;

import javafx.scene.paint.Color;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class Blob extends Particle {
	
	private double brightnessAtBlobEdge = 0.1;
	private double decreasingSpeed = 1.5;

	public Color getColor(int x, int y) {
		// x and y are given in the particle
		double r = Math.sqrt(x * x + y * y);
		double brightness = color.getBrightness() * ( 1 / Math.pow(r + 1, decreasingSpeed) - 1 / Math.pow(renderedRadius + 1, decreasingSpeed));
		// double brightness = color.getBrightness() / (Math.pow(r + 1, 2));
		return Color.hsb(color.getHue(), color.getSaturation(), brightness);
	}
	
}
