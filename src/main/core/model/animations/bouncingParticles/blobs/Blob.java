package main.core.model.animations.bouncingParticles.blobs;

import javafx.scene.paint.Color;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class Blob extends Particle {

	public Color getColor(int x, int y) {
		// x and y are given in the particle
		double r = Math.sqrt(x * x + y * y);
		double brightness = color.getBrightness() * ( 1 / Math.pow(r + 1, 1) - 1 / Math.pow(radius + 1, 1));
		return Color.hsb(color.getHue(), color.getSaturation(), brightness);
	}
	
}
