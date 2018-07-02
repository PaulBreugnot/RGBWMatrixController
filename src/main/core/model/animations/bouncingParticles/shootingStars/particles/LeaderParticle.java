package main.core.model.animations.bouncingParticles.shootingStars.particles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;

public class LeaderParticle extends Particle {
	
	private int historyLength = 200;
	private int followersCount = 20;
	private int step = 0;
	
	private HashSet<FollowerParticle> followers = new HashSet<>();
	private TreeMap<Integer, Pair<Double, Double>> history = new TreeMap<>();

	public LeaderParticle(double speed, double alpha, double xPos, double yPos, double radius, Color color) {
		super(speed, alpha, xPos, yPos, radius, color);
		initFollowers();
	}
	
	private void initFollowers() {
		for(int i = 0; i < followersCount; i++) {
			followers.add(new FollowerParticle(radius, color, this, i));
		}
	}
	
	public TreeMap<Integer, Pair<Double, Double>> getHistory(){
		return history;
	}
	
	public Pair<Double, Double> getHistoryCoordinates(int offset){
		System.out.println("Size : " + history.size());
		System.out.println("step - offset : " + (step-offset));
		return history.get(step - offset);
	}
	
	public HashSet<FollowerParticle> getFollowers(){
		return followers;
	}
	
	@Override
	public void progress(double deltaT) {
		System.out.println("Progress");
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

}
