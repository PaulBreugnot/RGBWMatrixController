package main.core.model.animations.bouncingParticles.simpleBouncingParticles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import main.core.model.animations.Animation;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;
import main.gui.views.settings.bouncingParticles.BouncingParticlesSettingsController;

public class SimpleBouncingParticles implements Animation {

	public static enum ColorType {
		Random, Raw, Shaded
	}

	private Color color = Color.BLUE;
	private ColorType colorType = ColorType.Random;

	private int particleNumber = 5;
	private double vMin = 0.5;
	private double vMax = 2.5;
	private double minRadius = 0.5;
	private double maxRadius = 3;

	private boolean initialize = true;
	private BouncingParticlesEngine bouncingParticlesEngine;

	public SimpleBouncingParticles() {

	}

	private void initialize() {
		ArrayList<Particle> particles = new ArrayList<>();
		Random rd = new Random();
		double currentXpos = 1;
		for (int i = 0; i < particleNumber; i++) {
			// double radius = 2;
			double radius = rd.nextDouble() * (maxRadius - minRadius) + minRadius;
			currentXpos += radius + 1;
			double angle = (rd.nextDouble() - 0.5) * 2 * Math.PI;
			// double angle = 3.1;
			int x = (int) Math.floor(currentXpos);
			currentXpos += radius + 1;
			int y = rd.nextInt(LedPanel.MATRIX_HEIGHT - 2 * (int) Math.floor(radius)) + (int) Math.floor(radius);
			double speed = rd.nextDouble() * (vMax - vMin) + vMin;
			Color color = Color.hsb(rd.nextInt(360), 1, 1);
			Particle particle = new Particle(speed, angle, x, y, radius, color);

			particles.add(particle);
		}
		ParticleSet particleSet = new ParticleSet.RectangularSet(particles, LedPanel.MATRIX_WIDTH,
				LedPanel.MATRIX_HEIGHT, 0, 0, true);
		bouncingParticlesEngine = new BouncingParticlesEngine(particleSet);

	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColorType(ColorType colorType) {
		this.colorType = colorType;
	}
	
	public ColorType getColorType() {
		return colorType;
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
