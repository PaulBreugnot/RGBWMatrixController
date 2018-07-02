package main.core.model.animations.bouncingParticles.shootingStars.particles;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class FollowerParticle extends ShootingParticle {
	
	private LeaderParticle particleToFollow;
	private int offset;
	private int resolution = 5;
	
	public FollowerParticle(double radius, Color color, LeaderParticle particleToFollow, int offset) {
		super(0, 0, -1, -1, radius, color);
		this.particleToFollow = particleToFollow;
		this.offset = offset;
		//setUpListeners();
	}
	
	@Override
	public void progress(double deltaT) {
		//Nothing : we don't progress from BouncingParticlesEngine
	}
	
	public void progress() {
		if(particleToFollow.getHistory().size() < resolution * offset + 1) {
			xPos.set(particleToFollow.getxPos());
			yPos.set(particleToFollow.getyPos());
		}
		else {
			System.out.println(offset);
			Pair<Double, Double> oldLeaderCoordinates = particleToFollow.getHistoryCoordinates(resolution * offset);
			xPos.set(oldLeaderCoordinates.getKey());
			yPos.set(oldLeaderCoordinates.getValue());
		}
	}
}
