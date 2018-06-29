package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine;

import java.util.ArrayDeque;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet.RectangularSet;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class BouncingParticlesEngine {

	private ParticleSet particleSet;

	private ArrayDeque<RGBWPixel>[][] pixelsToRender;
	private RGBWPixel[][] bufferLedMatrix;
	public double deltaT = 1;

	@SuppressWarnings("unchecked")
	public BouncingParticlesEngine(RGBWPixel[][] ledMatrix, ParticleSet particleSet) {
		LedPanel.setBlackPanel(ledMatrix);
		bufferLedMatrix = ledMatrix;
		this.particleSet = particleSet;
		pixelsToRender = (ArrayDeque<RGBWPixel>[][]) new ArrayDeque[LedPanel.MATRIX_HEIGHT][LedPanel.MATRIX_WIDTH];
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				pixelsToRender[i][j] = new ArrayDeque<>();
			}
		}
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
						updateParticlesToShow(particle, old_x, y, new_x, y);
						// addParticleToShow(particle);
					}
				}
			});
			particle.yPosProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					int old_y = (int) Math.floor((double) old_val);
					int new_y = (int) Math.floor((double) new_val);
					if (new_y != old_y) {
						int x = (int) Math.floor(particle.getxPos());
						updateParticlesToShow(particle, x, old_y, x, new_y);
						// addParticleToShow(particle);
					}
				}
			});
			addParticleToShow(particle);
		}
	}

	public void progress() {
		particleSet.progress(deltaT);
		render();
	}

	private void updateParticlesToShow(Particle p, int old_x, int old_y, int new_x, int new_y) {
		double radius = p.getRadius();
		int min_x = Math.min(old_x, new_x) + (int) Math.floor(-radius);
		int max_x = Math.max(old_x, new_x) + (int) Math.ceil(radius);
		int min_y = Math.min(old_y, new_y) + (int) Math.floor(-radius);
		int max_y = Math.max(old_y, new_y) + (int) Math.ceil(radius);
		for (int x = min_x; x <= max_x + radius; x++) {
			// Reduce x to check
			for (int y = min_y; y <= max_y; y++) {
				// Reduce y to check
				if (y >= 0 && y < LedPanel.MATRIX_HEIGHT && x >= 0 && x < LedPanel.MATRIX_WIDTH) {
					// We are in the matrix Neo
					if (Math.sqrt((x - old_x) * (x - old_x) + (y - old_y) * (y - old_y)) <= radius) {
						// We are in the old position
						if (Math.sqrt((x - new_x) * (x - new_x) + (y - new_y) * (y - new_y)) > radius) {
							// We are NOT in the new position
							//pixelsToRender[y][x].pollFirst();
							// TODO : what we need to remove exactly?
							pixelsToRender[y][x].pollFirst();
							// bufferLedMatrix[y][x] = RGBWPixel.rgbPixel(0, 0, 0);
						}
					} else {
						// We are not in the old position
						if (Math.sqrt((x - new_x) * (x - new_x) + (y - new_y) * (y - new_y)) <= radius) {
							// We are in the new position
							pixelsToRender[y][x].addLast(new RGBWPixel(p.getColor(x, y)));
							// bufferLedMatrix[y][x] = new RGBWPixel(p.getColor(x - new_x, y - new_y));
						}
					}
				}
			}

		}
	}

	private void addParticleToShow(Particle p) {
		for (int x = (int) Math.floor(-p.getRadius()); x <= p.getRadius(); x++) {
			for (int y = (int) Math.floor(-p.getRadius()); y <= p.getRadius(); y++) {
				if (Math.sqrt(x * x + y * y) <= p.getRadius()) {
					int xParticle = (int) Math.floor(p.getxPos());
					int yParticle = (int) Math.floor(p.getyPos());
					if (yParticle + y >= 0 && yParticle + y < LedPanel.MATRIX_HEIGHT && xParticle + x >= 0
							&& xParticle + x < LedPanel.MATRIX_WIDTH) {

						pixelsToRender[yParticle + y][xParticle + x].addLast(new RGBWPixel(p.getColor(x, y)));
					}
				}
			}
		}
	}

	private void render() {
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				RGBWPixel pixelToRender = pixelsToRender[i][j].peekFirst();
				if (pixelToRender != null) {
					bufferLedMatrix[i][j] = pixelToRender;
				} else {
					bufferLedMatrix[i][j] = RGBWPixel.rgbPixel(0, 0, 0);
				}
			}
		}
	}

}
