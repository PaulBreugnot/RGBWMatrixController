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

	private int density = 1;
	private Edge edge;
	private ParticleSet particleSet;

	@Override
	protected void initialize() {
		particles = new ArrayList<>();
		particleSet = new ParticleSet.RectangularSet(particles, areaWidth, areaHeight, horizontalOffset,
				verticalOffset, particleCollision);
		bouncingParticlesEngine = new BouncingParticlesEngine(particleSet);
		edge = particleSet.getEdges().get(2);
		
		/*
		Particle particle = new Particle();
		particle.setAlpha(- Math.PI / 2);
		particle.setColor(Color.RED);
		particle.setSpeed(1);
		particle.setxPos(15);
		particle.setyPos(14);
		particles.add(particle);
		particleSet.initCollisions();
		bouncingParticlesEngine.setUpParticleListener(particle);*/
		System.out.println(edge);
	}

	private void popParticles() {

			ArrayList<Particle> particles = new ArrayList<>();
			for (int i = 0; i < density; i++) {
				Particle particle = new DisappearingParticle(bouncingParticlesEngine);
				particle.setAlpha(edge.getAlpha() - Math.PI / 2);
				particles.add(particle);
				particle.setColor(Color.RED);
				bouncingParticlesEngine.setUpParticleListener(particle);
			}
			
			colorInit = new ShadedColorInitializer(particles, saturation, satWidth, brightness, brightWidth, hue,
					hueWidth);
			Particle.setColorMap(colorInit.getColorMap());
			colorInit.resolveColor();
			RandomRadiusInitializer radiusInit = new RandomRadiusInitializer(particles);
			radiusInit.resolveRadius(minRadius, maxRadius);
			RandomSpeedInitializer speedInit = new RandomSpeedInitializer(particles);
			speedInit.resolveSpeed(vMin, vMax);
			EdgeCoordinatesInitializer posInit = new EdgeCoordinatesInitializer(particles, edge);
			posInit.resolvePositions(areaWidth, areaHeight, horizontalOffset, verticalOffset);

			System.out.println("Radius : " + particles.get(0).getRadius());
			this.particles.addAll(particles);
			particleSet.initCollisions();
		}
	
	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		if (initialize) {
			LedPanel.setBlackPanel(ledMatrix);
			initialize();
			popParticles();
			initialize = false;
		}
		else if (initColor) {
			initializeColor();
			initColor = false;
		}
		bouncingParticlesEngine.progress(ledMatrix);
		for(Particle p : particles) {
			System.out.println("xPos : " + p.getxPos() + " yPos : " + p.getyPos());
		}
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
