package main.core.model.animations.bouncingParticles.blobs;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.layout.AnchorPane;
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

public class BouncingBlobs extends ParticleAnimation {


	@Override
	protected void initialize() {
		ArrayList<Particle> particles = new ArrayList<>();
		for (int i = 0; i < particleNumber; i++) {
			Particle particle = new Blob();
			particles.add(particle);
		}
		colorInit = new ShadedColorInitializer(particles, saturation, satWidth, brightness, brightWidth, hue, hueWidth);
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
		bouncingParticlesEngine = new BlobsEngine(particleSet);
		
		this.particles = particles;
			disableBlinky();
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
