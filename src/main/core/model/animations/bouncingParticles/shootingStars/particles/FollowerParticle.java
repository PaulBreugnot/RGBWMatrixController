package main.core.model.animations.bouncingParticles.shootingStars.particles;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class FollowerParticle extends ShootingParticle {
	
	private LeaderParticle particleToFollow;
	private int offset;
	private int resolution = 10;
	
	public FollowerParticle(double radius, Color color, LeaderParticle particleToFollow, int offset) {
		super(0, 0, -1, -1, radius, color);
		this.particleToFollow = particleToFollow;
		this.offset = offset;
		//setUpListeners();
	}
	
	public void setAboveOf(ArrayList<Particle> AboveOf) {
		//used to synchronize with leader particle
		this.AboveOf = AboveOf;
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
			Pair<Double, Double> oldLeaderCoordinates = particleToFollow.getHistoryCoordinates(resolution * offset);
			xPos.set(oldLeaderCoordinates.getKey());
			yPos.set(oldLeaderCoordinates.getValue());
		}
	}
	
	@Override
	public int compareTo(Particle arg0) {
		if(arg0.getColor().getBrightness() > getColor().getBrightness()) {
			return 1;
		}
		else if (arg0.getColor().getBrightness() < getColor().getBrightness()) {
			return -1;
		}
		return 0;
	}
}
