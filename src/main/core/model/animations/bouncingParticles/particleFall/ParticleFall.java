package main.core.model.animations.bouncingParticles.particleFall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import main.core.model.animations.Animation;
import main.core.model.animations.bouncingParticles.animation.ParticleAnimation;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers.ShadedColorInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.directionInitializers.RandomDirectionInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.positionInitializers.CompactPositionInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.radiusInitializers.RandomRadiusInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.speedInitializers.RandomSpeedInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.Edge;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class ParticleFall extends ParticleAnimation {

	private double ParticleByFrame = 5. / 25;
	private Edge edge;
	private ParticleSet particleSet;

	@Override
	protected void initialize() {
		initializeArea();
		particles = new ArrayList<>();
		particleSet = new ParticleSet.RectangularSet(particles, areaWidth, areaHeight, horizontalOffset, verticalOffset,
				particleCollision);
		bouncingParticlesEngine = new BouncingParticlesEngine(particleSet);
		edge = particleSet.getEdges().get(2);

		/*
		 * Particle particle = new Particle(); particle.setAlpha(- Math.PI / 2);
		 * particle.setColor(Color.RED); particle.setSpeed(1); particle.setxPos(15);
		 * particle.setyPos(14); particles.add(particle); particleSet.initCollisions();
		 * bouncingParticlesEngine.setUpParticleListener(particle);
		 */
	}

	private void initializeArea() {
		// areaHeight = (int) (LedPanel.MATRIX_HEIGHT + 4 * Math.ceil(maxRadius));
		// verticalOffset = (int) (- 2 * Math.ceil(maxRadius));
		areaHeight = LedPanel.MATRIX_HEIGHT;
		verticalOffset = 0;
		// areaWidth = (int) (LedPanel.MATRIX_WIDTH + 4 * Math.ceil(maxRadius));
		// horizontalOffset = (int) (- 2 * Math.ceil(maxRadius));
		areaWidth = (int) (LedPanel.MATRIX_WIDTH + 2);
		horizontalOffset = -1;
	}

	private void popParticle() {

		ArrayList<Particle> particles = new ArrayList<>();
		Particle particle = new DisappearingParticle(bouncingParticlesEngine);
		particle.setAlpha(edge.getAlpha() - Math.PI / 2);
		particles.add(particle);

		colorInit = new ShadedColorInitializer(particles, saturation, satWidth, brightness, brightWidth, hue, hueWidth);
		Particle.setColorMap(colorInit.getColorMap());
		colorInit.resolveColor();
		RandomRadiusInitializer radiusInit = new RandomRadiusInitializer(particles);
		radiusInit.resolveRadius(minRadius, maxRadius);
		RandomSpeedInitializer speedInit = new RandomSpeedInitializer(particles);
		speedInit.resolveSpeed(vMin, vMax);
		EdgeCoordinatesInitializer posInit = new EdgeCoordinatesInitializer(particles, edge);
		posInit.resolvePositions(areaWidth, areaHeight, horizontalOffset, verticalOffset);
		for (Particle p : this.particles) {
			particle.addAboveOf(p);
		}
		bouncingParticlesEngine.setUpParticleListener(particle);
		this.particles.addAll(particles);
		particleSet.initCollisions();
	}

	private boolean checkFreeSpace(Particle p) {
		for (int x = (int) Math.floor(-p.getRadius()); x <= p.getRadius(); x++) {
			for (int y = (int) Math.floor(-p.getRadius()); y <= p.getRadius(); y++) {
				if (Math.sqrt(x * x + y * y) <= p.getRadius()) {
					int xParticle = (int) Math.floor(p.getxPos());
					int yParticle = (int) Math.floor(p.getyPos());
					if (yParticle + y >= 0 && yParticle + y < LedPanel.MATRIX_HEIGHT && xParticle + x >= 0
							&& xParticle + x < LedPanel.MATRIX_WIDTH) {
						if (bouncingParticlesEngine.getPixelsToRender()[yParticle + y][xParticle + x].size() > 0) {
							return false;
						}
						;
					}
				}
			}
		}
		return true;
	}

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		if (initialize) {
			LedPanel.setBlackPanel(ledMatrix);
			initialize();
			initialize = false;
		} else if (initColor) {
			initializeColor();
			initColor = false;
		}
		Random rd = new Random();
		double rand = rd.nextDouble();
		if (rand < ParticleByFrame) {
			popParticle();
		}
		bouncingParticlesEngine.progress(ledMatrix);
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Animation newAnimationInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
