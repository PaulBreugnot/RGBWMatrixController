package main.core.model.animations.bouncingParticles.shootingStars.particles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.animations.bouncingParticles.particleFall.DisappearingParticle;

public class LeaderParticle extends DisappearingParticle {
	
	private int historyLength = 200;
	private int followersCount = 20;
	private int step = 0;
	
	private HashSet<FollowerParticle> followers = new HashSet<>();
	private TreeMap<Integer, Pair<Double, Double>> history = new TreeMap<>();

	public LeaderParticle(BouncingParticlesEngine bouncingParticlesEngine) {
		super(bouncingParticlesEngine);
		initFollowers();
	}
	
	private void initFollowers() {
		for(int i = 0; i < followersCount; i++) {
			FollowerParticle follower = new FollowerParticle(bouncingParticlesEngine, this, i);
			follower.setLayer(layer);
			followers.add(follower);
		}
	}
	
	public TreeMap<Integer, Pair<Double, Double>> getHistory(){
		return history;
	}
	
	public Pair<Double, Double> getHistoryCoordinates(int offset){
		return history.get(step - offset);
	}
	
	public HashSet<FollowerParticle> getFollowers(){
		return followers;
	}
	
	@Override
	public void progress(double deltaT) {
		if(history.size() > historyLength) {
			history.remove(history.firstKey());
		}
		history.put(step, new Pair<>(xPos.get(), yPos.get()));
		xPos.set(xPos.get() + speed * Math.cos(alpha) * deltaT);
		yPos.set(yPos.get() + speed * Math.sin(alpha) * deltaT);
		for(FollowerParticle follower : followers) {
			follower.progress();
		}
		step++;
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
