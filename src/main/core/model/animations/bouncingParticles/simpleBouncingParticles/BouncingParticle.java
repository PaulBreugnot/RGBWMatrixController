package main.core.model.animations.bouncingParticles.simpleBouncingParticles;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class BouncingParticle implements Comparable<BouncingParticle> {
	
	private ArrayList<BouncingParticle> AboveOf = new ArrayList<>();
	
	public void addAboveOf(BouncingParticle particle) {
		AboveOf.add(particle);
	}
	
	public void removeAboveOf(BouncingParticle particle) {
		AboveOf.remove(particle);
	}
	
	public boolean isAboveOf(BouncingParticle particle) {
		return AboveOf.contains(particle);
	}
	

	@Override
	public int compareTo(BouncingParticle arg0) {
		if(AboveOf.contains(arg0)) {
			return -1;
		}
		return 0;
	}

}
