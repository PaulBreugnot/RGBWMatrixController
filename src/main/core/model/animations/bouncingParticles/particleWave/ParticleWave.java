package main.core.model.animations.bouncingParticles.particleWave;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers.AbstractColorInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers.ShadedColorInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.directionInitializers.RandomDirectionInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.positionInitializers.CompactPositionInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.radiusInitializers.RandomRadiusInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.speedInitializers.RandomSpeedInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class ParticleWave implements Animation {

	public enum Orientation {
		HORIZONTAL, VERTICAL
	}

	private double saturation = 1;
	private double satWidth = 0;
	private double brightness = 1;
	private double brightWidth = 0;
	private double hue = 180;
	private double hueWidth = 30;
	private boolean blinky = false;
	private double resolution;
	private double waveLength = Double.MAX_VALUE;
	private double angleOffset;
	private int direction;
	private AbstractColorInitializer colorInit;

	// Particles
	private int particleNumber;
	private double radius = 2.5;
	private double speed = 1;

	// Area
	private int areaWidth = LedPanel.MATRIX_WIDTH + (int) Math.ceil(4 * radius);
	private int areaHeight = LedPanel.MATRIX_HEIGHT + (int) Math.ceil(4 * radius);
	private int horizontalOffset = -(int) Math.ceil(2 * radius);
	//private int verticalOffset = -LedPanel.MATRIX_HEIGHT / 2;
	private int verticalOffset = - (int) Math.ceil(2 * radius);

	private boolean initialize = true;
	private boolean initColor = true;
	private boolean matrixFull;
	private BouncingParticlesEngine bouncingParticlesEngine;
	private ArrayList<Particle> particles;
	private ArrayList<Particle> lastAddedParticles;

	private void initialize() {
		particles = new ArrayList<>();
		lastAddedParticles = new ArrayList<>();
		waveLength = areaWidth/2;
		angleOffset = 0;
		direction = 1;
		add_wave(Orientation.VERTICAL);
		/*
		//waveLength = 2 * areaWidth;
		angleOffset = Math.PI;
		direction = -1;
		hue = 270;
		add_wave(Orientation.VERTICAL);

		//waveLength = 2 * areaHeight;
		angleOffset = 0;
		direction = 1;
		hue = 40;
		add_wave(Orientation.HORIZONTAL);
		//waveLength = 2 * areaHeight;
		angleOffset = Math.PI;
		direction = -1;
		hue = 90;
		add_wave(Orientation.HORIZONTAL);
		*/

	}

	public void add_wave(Orientation orientation) {
		lastAddedParticles.clear();
		resolution = 2 * radius;
		int particleNumber = 0;
		switch(orientation) {
		case VERTICAL:
			particleNumber = (int) (Math.floor(areaWidth / resolution) - Math.ceil(4 * radius / resolution));
			break;
		case HORIZONTAL:
			particleNumber = (int) (Math.floor(areaHeight / resolution) - Math.ceil(4 * radius / resolution));
			break;
		}
		// int particleNumber = 1;
		for (int i = 0; i < particleNumber; i++) {
			Particle particle = null;
			switch (orientation) {
			case VERTICAL:
				particle = new WaveParticle(verticalOffset + areaHeight - 1.2 * radius, verticalOffset + 1.2 * radius,
						orientation);
				if(direction > 0) {
					particle.setAlpha(Math.PI / 2);
				}
				else {
					particle.setAlpha(-Math.PI / 2);	
				}
				break;
			case HORIZONTAL:
				particle = new WaveParticle(horizontalOffset + 1.2 * radius,
						horizontalOffset + areaWidth - 1.2 * radius, orientation);
				if(direction > 0) {
					particle.setAlpha(0);
				}
				else {
					particle.setAlpha(-Math.PI);
				}
				break;
			}

			particle.setRadius(radius);
			particle.setSpeed(speed);
			lastAddedParticles.add(particle);
		}
		colorInit = new ShadedColorInitializer(lastAddedParticles, saturation, satWidth, brightness, brightWidth, hue, hueWidth);
		Particle.setColorMap(colorInit.getColorMap());
		colorInit.resolveColor();
		WavePositionInitializer posInit = new WavePositionInitializer(lastAddedParticles, waveLength, radius,
				resolution, orientation, angleOffset);
		posInit.resolvePositions(areaWidth, areaHeight, horizontalOffset, verticalOffset);
		particles.addAll(lastAddedParticles);
		
		ParticleSet particleSet = new ParticleSet.RectangularSet(particles, areaWidth, areaHeight, horizontalOffset,
				verticalOffset, false);
		bouncingParticlesEngine = new BouncingParticlesEngine(particleSet, false);
	}

	private void initializeColor() {
		colorInit = new ShadedColorInitializer(lastAddedParticles, saturation, satWidth, brightness, brightWidth, hue, hueWidth);
		Particle.setColorMap(colorInit.getColorMap());
		colorInit.resolveColor();
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
