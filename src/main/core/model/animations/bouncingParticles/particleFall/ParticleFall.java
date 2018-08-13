package main.core.model.animations.bouncingParticles.particleFall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.animations.bouncingParticles.animation.ParticleAnimation;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers.ShadedColorInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.radiusInitializers.RandomRadiusInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.renderedRadiusInitializer.DefaultRenderedRadiusInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.speedInitializers.RandomSpeedInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.Edge;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class ParticleFall extends ParticleAnimation {

	private double ParticleByFrame = 5. / 25;
	protected ParticleSet particleSet;

	@Override
	protected void initialize() {
		initializeArea();
		particles = new ArrayList<>();
		particleSet = new ParticleSet.RectangularSet(particles, areaWidth, areaHeight, horizontalOffset, verticalOffset,
				particleCollision);
		bouncingParticlesEngine = new BouncingParticlesEngine(particleSet, false);
		particleSet.rotate(0, verticalOffset + areaHeight / 2, horizontalOffset + areaWidth / 2);
	}

	protected void initializeArea() {
		areaHeight = LedPanel.MATRIX_HEIGHT;
		verticalOffset = 0;
		areaWidth = (int) (LedPanel.MATRIX_WIDTH + 4 * Math.ceil(maxRadius));
		horizontalOffset = (int) (-2 * Math.ceil(maxRadius));
	}

	protected void popParticle() {

		Edge edge = particleSet.getEdges().get(0);
		System.out.println(edge);
		ArrayList<Particle> particles = new ArrayList<>();
		Particle particle = new DisappearingParticle(bouncingParticlesEngine);
		System.out.println(edge);
		if (Math.PI / 2 < particleSet.getOrientation() && particleSet.getOrientation() <= 3 * Math.PI / 2) {
			particle.setAlpha(edge.getAlpha() + Math.PI / 2);
		} else {
			particle.setAlpha(edge.getAlpha() - Math.PI / 2);
		}
		particles.add(particle);

		colorInit = new ShadedColorInitializer(particles, saturation, satWidth, brightness, brightWidth, hue, hueWidth);
		Particle.setColorMap(colorInit.getColorMap());
		colorInit.resolveColor();
		RandomRadiusInitializer radiusInit = new RandomRadiusInitializer(particles);
		radiusInit.resolveRadius(minRadius, maxRadius);
		DefaultRenderedRadiusInitializer renderedRadiusInit = new DefaultRenderedRadiusInitializer(particles);
		renderedRadiusInit.resolveRenderedRadius(0, 0); // Just copy radius
		RandomSpeedInitializer speedInit = new RandomSpeedInitializer(particles);
		speedInit.resolveSpeed(vMin, vMax);
		EdgeCoordinatesInitializer posInit = new EdgeCoordinatesInitializer(particles, edge);
		posInit.resolvePositions(areaWidth, areaHeight, horizontalOffset, verticalOffset);
		bouncingParticlesEngine.setUpParticleListener(particle);
		this.particles.addAll(particles);
		particle.setLayer(this.particles.size() - 1);
		particleSet.initCollisions();
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
