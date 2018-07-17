package main.core.model.animations.bouncingParticles.simpleBouncingParticles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import main.core.model.animations.Animation;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers.AbstractColorInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers.RandomColorInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers.ShadedColorInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.directionInitializers.RandomDirectionInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.positionInitializers.CompactPositionInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.radiusInitializers.RandomRadiusInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.speedInitializers.RandomSpeedInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.RandomPosSolver;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;
import main.gui.views.settings.bouncingParticles.BouncingParticlesSettingsController;

public class SimpleBouncingParticles implements Animation {

	public static enum ColorType {
		Random, Raw, Shaded
	}

	private double saturation = 1;
	private double satWidth = 0;
	private double brightness = 1;
	private double brightWidth = 0;
	private double hue = 180;
	private double hueWidth = 30;
	private ColorType colorType = ColorType.Random;
	private boolean blinky = false;

	// Particles
	private int particleNumber = 5;
	private double vMin = 1;
	private double vMax = 1;
	private double minRadius = 0.5;
	private double maxRadius = 2;

	// Area
	private int areaWidth = LedPanel.MATRIX_WIDTH;
	private int areaHeight = LedPanel.MATRIX_HEIGHT;
	private int horizontalOffset = 0;
	private int verticalOffset = 0;
	private boolean particleCollision = true;

	private boolean initialize = true;
	private boolean matrixFull;
	private BouncingParticlesEngine bouncingParticlesEngine;

	public SimpleBouncingParticles() {

	}

	private void initialize() {
		ArrayList<Particle> particles = new ArrayList<>();
		Random rd = new Random();
		for (int i = 0; i < particleNumber; i++) {
			// double radius = 2;
			//double radius = rd.nextDouble() * (maxRadius - minRadius) + minRadius;
			//double angle = (rd.nextDouble() - 0.5) * 2 * Math.PI;
			//double speed = rd.nextDouble() * (vMax - vMin) + vMin;
			//Color color = Color.hsb(rd.nextInt(360), 1, 1);
			Particle particle = new Particle();
			particles.add(particle);
		}
		AbstractColorInitializer colorInit = new ShadedColorInitializer(particles, saturation, satWidth, brightness, brightWidth, hue, hueWidth);
		Particle.setColorMap(colorInit.getColorMap());
		colorInit.resolveColor();
		RandomRadiusInitializer radiusInit = new RandomRadiusInitializer(particles);
		radiusInit.resolveRadius(minRadius, maxRadius);
		RandomDirectionInitializer angleInit = new RandomDirectionInitializer(particles);
		angleInit.resolveDirections();
		RandomSpeedInitializer speedInit = new RandomSpeedInitializer(particles);
		speedInit.resolveSpeed(vMin, vMax);
		CompactPositionInitializer posInit = new CompactPositionInitializer(particles, 2 * vMax * ParticleSet.deltaTsimulation);
		posInit.resolvePositions(areaWidth, areaHeight, horizontalOffset, verticalOffset);
		

		ParticleSet particleSet = new ParticleSet.RectangularSet(particles, areaWidth, areaHeight, horizontalOffset,
				verticalOffset, particleCollision);
		bouncingParticlesEngine = new BouncingParticlesEngine(particleSet);

	}

	// Particles

	public ColorType getColorType() {
		return colorType;
	}
	
	public void setColorType(ColorType colorType) {
		this.colorType = colorType;
	}

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

	public void setInitialize(boolean initialize) {
		this.initialize = initialize;
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

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		if (initialize) {
			LedPanel.setBlackPanel(ledMatrix);
			initialize();
			initialize = false;
		}
		bouncingParticlesEngine.progress(ledMatrix);
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass()
				.getResource("/main/gui/views/settings/bouncingParticles/BouncingParticlesSettings.fxml"));
		configAnchorPane.getChildren().add(loader.load());
		BouncingParticlesSettingsController bouncingParticlesSettingsController = loader.getController();
		bouncingParticlesSettingsController.setSimpleBouncingParticles(this);

	}

	@Override
	public Animation newAnimationInstance() {
		return new SimpleBouncingParticles();
	}

	@Override
	public String toString() {
		return "Bouncing Particles";
	}

}
