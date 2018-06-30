package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet.RectangularSet;
import main.core.model.animations.bouncingParticles.simpleBouncingParticles.BouncingParticle;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class BouncingParticlesEngine {

	private ParticleSet particleSet;

	private PriorityQueue<Particle>[][] pixelsToRender;
	private RGBWPixel[][] bufferLedMatrix;
	public double deltaT = 1;

	@SuppressWarnings("unchecked")
	public BouncingParticlesEngine(RGBWPixel[][] ledMatrix, ParticleSet particleSet) {
		LedPanel.setBlackPanel(ledMatrix);
		bufferLedMatrix = ledMatrix;
		this.particleSet = particleSet;
		pixelsToRender = (PriorityQueue<Particle>[][]) new PriorityQueue[LedPanel.MATRIX_HEIGHT][LedPanel.MATRIX_WIDTH];
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				pixelsToRender[i][j] = new PriorityQueue<>();
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
		ArrayList<Particle> particlesStillUnder = new ArrayList<>();
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
							pixelsToRender[y][x].remove(p);
						}
						else {
							// We are in new and old position intersection
							for (Particle particle : pixelsToRender[y][x]) {
								if(!particlesStillUnder.contains(particle) && p.isAboveOf(particle)) {
									particlesStillUnder.add(particle);
								}
							}
						}
					} else {
						// We are not in the old position
						if (Math.sqrt((x - new_x) * (x - new_x) + (y - new_y) * (y - new_y)) <= radius) {
							// We are in the new position
							for (Particle particle : pixelsToRender[y][x]) {
								if(!particlesStillUnder.contains(particle) && p.isAboveOf(particle)) {
									particlesStillUnder.add(particle);
								}
								if (!p.isAboveOf(particle) && !particle.isAboveOf(p)) {
									p.addAboveOf(particle);
								}
							}
							pixelsToRender[y][x].add(p);
						}
					}
				}
			}

		}
		ArrayList<Particle> particlesNoMoreUnder = new ArrayList<>();
		for(Particle particleUnder : p.getAboveOf()) {
			if(!particlesStillUnder.contains(particleUnder)) {
				particlesNoMoreUnder.add(particleUnder);
			}
		}
		for(Particle particleNoMoreUnder : particlesNoMoreUnder) {
			p.removeAboveOf(particleNoMoreUnder);
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
						pixelsToRender[yParticle + y][xParticle + x].add(p);
					}
				}
			}
		}
	}

	private void render() {
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				Particle p = pixelsToRender[i][j].peek();
				if (p != null) {
					RGBWPixel pixelToRender = new RGBWPixel(pixelsToRender[i][j].peek()
							.getColor(j - (int) Math.floor(p.getxPos()), i - (int) Math.floor(p.getyPos())));
					bufferLedMatrix[i][j] = pixelToRender;
				} else {
					bufferLedMatrix[i][j] = RGBWPixel.rgbPixel(0, 0, 0);
				}
			}
		}
	}

}
