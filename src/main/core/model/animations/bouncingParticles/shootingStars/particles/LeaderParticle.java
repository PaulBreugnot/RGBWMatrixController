package main.core.model.animations.bouncingParticles.shootingStars.particles;

import java.util.HashSet;
import java.util.TreeMap;

import javafx.util.Pair;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.particleFall.DisappearingParticle;

public class LeaderParticle extends DisappearingParticle {
	
	public static final int followersCount = 20;
	private static final int historyLength = followersCount * FollowerParticle.resolution;
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
	public void setLayer(int layer) {
		// Overridden to propagate layer to followers
		this.layer = layer;
		for (FollowerParticle follower : followers) {
			follower.setLayer(layer);
		}
	}
	
	@Override
	protected void setOutdated(boolean outdated) {
		// This method is called by DisapperingParticle when leader collides.
		// We override it to propagate the collision to followers.
		this.outdated = outdated;
		for(FollowerParticle follower : followers) {
			// Because leader is now outdated, followers will be removed in this last progress
			follower.progress();
		}
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
		/*System.out.println("Leader layer : " + layer);
		System.out.println("Followers : ");
		for (Particle p : followers) {
			System.out.println(p.getLayer());
		}*/
	}

}
