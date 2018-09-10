package main.core.model.animations.bouncingParticles.blobs;

import java.util.Collection;

import javafx.scene.paint.Color;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class BlobsEngine extends BouncingParticlesEngine {

	private boolean hardColorMode;
	private double hardColorThreshold = 0.05;
	private double minTreshold = 0.05;

	public BlobsEngine(ParticleSet particleSet, boolean hardColorMode) {
		super(particleSet, false);
		this.hardColorMode = hardColorMode;
	}
	
	public void setMinTreshold(double minTreshold) {
		this.minTreshold = minTreshold;
	}
	
	public double getMinTreshold() {
		return minTreshold;
	}

	@Override
	protected void render(RGBWPixel[][] ledMatrix) {
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				Particle p = pixelsToRender[i][j].peek();
				if (p != null) {
					RGBWPixel pixelToRender = addColors(i, j);
					ledMatrix[i][j] = pixelToRender;
				} else {
					ledMatrix[i][j] = RGBWPixel.rgbPixel(0, 0, 0);
				}
			}
		}
	}

	private RGBWPixel addColors(int i, int j) {
		Collection<? extends Particle> pixels = pixelsToRender[i][j];
		double hue = 0;
		double saturation = 0;
		double brightness = 0;
		int notNullCount = 0;
		for (Particle p : pixels) {
			Color c = p.getColor(j - (int) Math.floor(p.getxPos()), i - (int) Math.floor(p.getyPos()));
			hue += c.getHue();
			if (c.getHue() != 0) {
				notNullCount++;
			}
			saturation += c.getSaturation();
			brightness += c.getBrightness();
		}
		hue = hue / notNullCount;
		saturation = Math.min(saturation, 1);
		brightness = Math.min(brightness, 1);
		if (brightness < minTreshold) {
			brightness = 0;
		}
		if (hardColorMode) {
			if (brightness > hardColorThreshold) {
				brightness = 1;
			} else {
				brightness = 0;
			}
		}
		return new RGBWPixel(Color.hsb(hue, saturation, brightness));
	}
}
