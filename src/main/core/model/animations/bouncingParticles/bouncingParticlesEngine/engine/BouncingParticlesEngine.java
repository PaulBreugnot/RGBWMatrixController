package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet.RectangularSet;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class BouncingParticlesEngine {

	private ParticleSet particleSet;
	private RGBWPixel[][] bufferLedMatrix;
	public double deltaT = 1;

	public BouncingParticlesEngine(RGBWPixel[][] ledMatrix) {
		LedPanel.setBlackPanel(ledMatrix);
		bufferLedMatrix = ledMatrix;

	}

	public BouncingParticlesEngine(RGBWPixel[][] ledMatrix, ParticleSet particleSet) {
		LedPanel.setBlackPanel(ledMatrix);
		bufferLedMatrix = ledMatrix;
		this.particleSet = particleSet;
		setUpParticleListeners();
	}

	private void setUpParticleListeners() {
		for (Particle particle : particleSet.getParticles()) {
			// All the coordinates are forced to fit matrix width and height
			particle.xPosProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					int old_x = (int) Math.floor((double) old_val);
					int new_x = (int) Math.floor((double) new_val);
					if (new_x != old_x) {
						int y = (int) Math.floor(particle.getyPos());
						clear(particle.getRadius(), old_x, y);
						render(particle);
					}
				}
			});
			particle.yPosProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					int old_y = (int) Math.floor((double) old_val);
					int new_y = (int) Math.floor((double) new_val);
					if (new_y != old_y) {
						int x = (int) Math.floor(particle.getxPos());
						clear(particle.getRadius(), x, old_y);
						render(particle);
					}
				}
			});
		}
	}
	
	public void progress() {
		particleSet.progress(deltaT);
	}

	private void clear(double radius, int old_x, int old_y) {
		for (int x = (int) Math.floor(-radius); x <= radius; x++) {
			for (int y = (int) Math.floor(-radius); y <= radius; y++) {
				if (Math.sqrt(x * x + y * y) <= radius) {
					if (old_y + y >= 0 && old_y + y < LedPanel.MATRIX_HEIGHT && old_x + x >= 0
							&& old_x + x < LedPanel.MATRIX_WIDTH) {
						bufferLedMatrix[old_y + y][old_x + x] = RGBWPixel.rgbPixel(0, 0, 0);
					}
				}
			}
		}
	}

	private void render(Particle p) {
		for (int x = (int) Math.floor(-p.getRadius()); x <= p.getRadius(); x++) {
			for (int y = (int) Math.floor(-p.getRadius()); y <= p.getRadius(); y++) {
				if (Math.sqrt(x * x + y * y) <= p.getRadius()) {
					int xParticle = (int) Math.floor(p.getxPos());
					int yParticle = (int) Math.floor(p.getyPos());
					if (yParticle + y >= 0 && yParticle + y < LedPanel.MATRIX_HEIGHT && xParticle + x >= 0
							&& xParticle + x < LedPanel.MATRIX_WIDTH) {
						bufferLedMatrix[yParticle + y][xParticle + x] = new RGBWPixel(p.getColor(x, y));
					}
				}
			}
		}
	}

}
