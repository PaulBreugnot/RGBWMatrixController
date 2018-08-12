package main.core.model.animations.bouncingParticles.shootingStars;

import java.util.ArrayList;
import java.util.HashSet;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.colorInitializers.ShadedColorInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.radiusInitializers.RandomRadiusInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.initializers.speedInitializers.RandomSpeedInitializer;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.Edge;
import main.core.model.animations.bouncingParticles.particleFall.EdgeCoordinatesInitializer;
import main.core.model.animations.bouncingParticles.particleFall.ParticleFall;
import main.core.model.animations.bouncingParticles.shootingStars.followerInitializers.FollowerColorInitializer;
import main.core.model.animations.bouncingParticles.shootingStars.followerInitializers.FollowerDirectionInitializer;
import main.core.model.animations.bouncingParticles.shootingStars.followerInitializers.FollowerPosInitializer;
import main.core.model.animations.bouncingParticles.shootingStars.followerInitializers.FollowerRadiusInitializer;
import main.core.model.animations.bouncingParticles.shootingStars.followerInitializers.FollowerSpeedInitializer;
import main.core.model.animations.bouncingParticles.shootingStars.particles.FollowerParticle;
import main.core.model.animations.bouncingParticles.shootingStars.particles.LeaderParticle;
import main.core.model.panel.LedPanel;

public class ShootingStars extends ParticleFall {

	// Collision area parameters

	/*
	 * public ShootingStars(RGBWPixel[][] ledMatrix) {
	 * ArrayList<DisappearingParticle> particles = new ArrayList<>(); Random rd =
	 * new Random(); double currentXpos = 1; for (int i = 0; i < particleNumber;
	 * i++) { //double radius = 2; double radius = rd.nextDouble() * (maxRadius -
	 * minRadius) + minRadius; currentXpos += radius + 1; double angle =
	 * (rd.nextDouble() - 0.5) * 2 * Math.PI; //double angle = 3.1; int x = (int)
	 * Math.floor(currentXpos); currentXpos += radius + 1; int y =
	 * rd.nextInt(LedPanel.MATRIX_HEIGHT - 2 * (int) Math.floor(radius)) + (int)
	 * Math.floor(radius); double speed = rd.nextDouble() * (vMax - vMin) + vMin;
	 * Color color = Color.hsb(rd.nextInt(360), 1, 1); LeaderParticle particle = new
	 * LeaderParticle(speed, angle, x, y, radius, color);
	 * 
	 * particles.add(particle); particles.addAll(particle.getFollowers()); }
	 * ParticleSet particleSet = new ParticleSet.RectangularSet(particles,
	 * LedPanel.MATRIX_WIDTH + 20, LedPanel.MATRIX_HEIGHT + 20, -10, -10, false);
	 * bouncingParticlesEngine = new BouncingParticlesEngine(particleSet, false); }
	 */

	public ShootingStars() {
		super();
	}

	@Override
	protected void initializeArea() {
		int approximatedMaxSize = (int) Math.ceil((LeaderParticle.followersCount + 1) * FollowerParticle.resolution
				* vMax * ParticleSet.deltaTsimulation);
		areaHeight = LedPanel.MATRIX_HEIGHT;
		verticalOffset = 0;
		areaWidth = (int) (LedPanel.MATRIX_WIDTH + approximatedMaxSize);
		horizontalOffset = 0;
	}

	@Override
	protected void popParticle() {

		Edge edge = particleSet.getEdges().get(0);
		ArrayList<Particle> particles = new ArrayList<>();
		LeaderParticle particle = new LeaderParticle(bouncingParticlesEngine);
		if (Math.PI / 2 < particleSet.getOrientation() && particleSet.getOrientation() <= 3 * Math.PI / 2) {
			particle.setAlpha(edge.getAlpha() + Math.PI / 2);
		} else {
			particle.setAlpha(edge.getAlpha() - Math.PI / 2);
		}

		particle.setLayer(this.particles.size());
		particles.add(particle);

		// Leader Init
		colorInit = new ShadedColorInitializer(particles, saturation, satWidth, brightness, brightWidth, hue, hueWidth);
		Particle.setColorMap(colorInit.getColorMap());
		colorInit.resolveColor();
		RandomRadiusInitializer radiusInit = new RandomRadiusInitializer(particles);
		radiusInit.resolveRadius(minRadius, maxRadius);
		RandomSpeedInitializer speedInit = new RandomSpeedInitializer(particles);
		speedInit.resolveSpeed(vMin, vMax);
		EdgeCoordinatesInitializer posInit = new EdgeCoordinatesInitializer(particles, edge);
		posInit.resolvePositions(areaWidth, areaHeight, horizontalOffset, verticalOffset);
		bouncingParticlesEngine.setUpParticleListener(particle);

		// Followers init
		HashSet<FollowerParticle> followers = particle.getFollowers();
		FollowerColorInitializer followerColorInit = new FollowerColorInitializer(followers);
		followerColorInit.resolveColor();
		FollowerDirectionInitializer followerDirectionInitializer = new FollowerDirectionInitializer(followers);
		followerDirectionInitializer.resolveDirections();
		FollowerPosInitializer followerPosInitializer = new FollowerPosInitializer(followers);
		followerPosInitializer.resolvePositions(0, 0, 0, 0);
		FollowerRadiusInitializer followerRadiusInitializer = new FollowerRadiusInitializer(followers);
		followerRadiusInitializer.resolveRadius(0, 0);
		FollowerSpeedInitializer followerSpeedInitializer = new FollowerSpeedInitializer(followers);
		followerSpeedInitializer.resolveSpeed(0, 0);
		for (Particle p : followers) {
			// Followers are not added to the ParticleSet of the engine, because their
			// leader make them move.
			// However, listeners are set to show them.
			bouncingParticlesEngine.setUpParticleListener(p);
		}

		this.particles.addAll(particles);
		particleSet.initCollisions();
	}

}
