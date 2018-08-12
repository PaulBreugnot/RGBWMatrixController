package main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine;

import java.util.Comparator;

import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;

public class LayerComparator implements Comparator <Particle> {

	@Override
	public int compare(Particle arg0, Particle arg1) {
		if(arg0.getLayer() < arg1.getLayer()) {
			return 1;
		}
		else if(arg0.getLayer() > arg1.getLayer()) {
			return -1;
		}
		return 0;
	}

}
