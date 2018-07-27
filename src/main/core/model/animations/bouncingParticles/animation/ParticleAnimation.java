package main.core.model.animations.bouncingParticles.animation;

import java.util.ArrayList;

import main.core.model.animations.Animation;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers.AbstractColorInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers.ShadedColorInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public abstract class ParticleAnimation implements Animation {

	protected double saturation = 1;
	protected double satWidth = 0;
	protected double brightness = 1;
	protected double brightWidth = 0;
	protected double hue = 180;
	protected double hueWidth = 30;
	protected boolean blinky = false;
	protected AbstractColorInitializer colorInit;

	// Particles
	protected int particleNumber = 5;
	protected double vMin = 1;
	protected double vMax = 1;
	protected double minRadius = 3;
	protected double maxRadius = 3;

	// Area
	protected int areaWidth = LedPanel.MATRIX_WIDTH;
	protected int areaHeight = LedPanel.MATRIX_HEIGHT;
	protected int horizontalOffset = 0;
	protected int verticalOffset = 0;
	protected boolean particleCollision = true;

	protected boolean initialize = true;
	protected boolean initColor = true;
	protected BouncingParticlesEngine bouncingParticlesEngine;
	protected ArrayList<Particle> particles;
	
	
	protected void initializeColor() {
		colorInit = new ShadedColorInitializer(particles, saturation, satWidth, brightness, brightWidth, hue, hueWidth);
		Particle.setColorMap(colorInit.getColorMap());
		colorInit.resolveColor();
	}
	
	protected void enableBlinky() {
		for(Particle p : particles) {
			p.setBlinky(true);
		}
	}
	
	protected void disableBlinky() {
		for(Particle p : particles) {
			p.setBlinky(false);
		}
	}

	// Color

	public double getSaturation() {
		return saturation;
	}

	public void setSaturation(double saturation) {
		this.saturation = saturation;
	}

	public double getSatWidth() {
		return satWidth;
	}

	public void setSatWidth(double satWidth) {
		this.satWidth = satWidth;
	}

	public double getBrightness() {
		return brightness;
	}

	public void setBrightness(double brightness) {
		this.brightness = brightness;
	}

	public double getBrightWidth() {
		return brightWidth;
	}

	public void setBrightWidth(double brightWidth) {
		this.brightWidth = brightWidth;
	}

	public double getHue() {
		return hue;
	}

	public void setHue(double hue) {
		this.hue = hue;
	}

	public double getHueWidth() {
		return hueWidth;
	}

	public void setHueWidth(double hueWidth) {
		this.hueWidth = hueWidth;
	}

	public void setBlinky(boolean blinky) {
		this.blinky = blinky;
		if (blinky) {
			enableBlinky();
		}
		else {
			disableBlinky();
		}
	}
	
	// Particles
	
	public void setMinRadius(double minRadius) {
		this.minRadius = minRadius;
	}

	public double getMinRadius() {
		return minRadius;
	}

	public void setMaxRadius(double maxRadius) {
		this.maxRadius = maxRadius;
	}

	public double getMaxRadius() {
		return maxRadius;
	}

	public void setParticleNumber(int particleNumber) {
		this.particleNumber = particleNumber;
	}

	public int getParticleNumber() {
		return particleNumber;
	}
	
	public double getvMin() {
		return vMin;
	}

	public void setvMin(double vMin) {
		this.vMin = vMin;
	}

	public double getvMax() {
		return vMax;
	}

	public void setvMax(double vMax) {
		this.vMax = vMax;
	}

	// Area

	public boolean getParticleCollision() {
		return particleCollision;
	}

	public void setParticleCollision(boolean particleCollision) {
		this.particleCollision = particleCollision;
	}

	public int getAreaWidth() {
		return areaWidth;
	}

	public void setAreaWidth(int areaWidth) {
		this.areaWidth = areaWidth;
	}

	public int getAreaHeight() {
		return areaHeight;
	}

	public void setAreaHeight(int areaHeight) {
		this.areaHeight = areaHeight;
	}

	public int getHorizontalOffset() {
		return horizontalOffset;
	}

	public void setHorizontalOffset(int horizontalOffset) {
		this.horizontalOffset = horizontalOffset;
	}

	public int getVerticalOffset() {
		return verticalOffset;
	}

	public void setVerticalOffset(int verticalOffset) {
		this.verticalOffset = verticalOffset;
	}
	
	// App

	public void setInitialize(boolean initialize) {
		this.initialize = initialize;
	}
	
	public void setInitColor(boolean initColor) {
		this.initColor = initColor;
	}
	
	protected abstract void initialize();
	
	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		if (initialize) {
			LedPanel.setBlackPanel(ledMatrix);
			initialize();
			initialize = false;
		}
		else if (initColor) {
			initializeColor();
			initColor = false;
		}
		bouncingParticlesEngine.progress(ledMatrix);
	}
}
