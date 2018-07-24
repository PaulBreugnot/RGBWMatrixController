package main.core.model.animations.bouncingParticles.particleWave;

import javafx.scene.paint.Color;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.utils.Edge;
import main.core.model.animations.bouncingParticles.particleWave.ParticleWave.Orientation;

public class WaveParticle extends Particle {
	
	private Orientation orientation;
	private double topAnchor;
	private double bottomAnchor;
	

	public WaveParticle(double topAnchor, double bottomAnchor, Orientation orientation) {
		super();
		this.topAnchor = topAnchor;
		this.bottomAnchor = bottomAnchor;
		this.orientation = orientation;
	}




	@Override
	public void bounceEdge(Edge edge) {
		// By convention 0 <= alphaLine < PI
		double alphaLine = edge.getAlpha();
		switch (orientation) {
			case VERTICAL:
				if(alpha < 0) {
					setyPos(bottomAnchor);
				}
				else {
					setyPos(topAnchor);
				}
				break;
			case HORIZONTAL:
				if(alpha == 0) {
					setxPos(bottomAnchor);
				}
				else {
					setxPos(topAnchor);
				}
				break;
		}
		alpha = 2 * alphaLine - alpha;
		while(alpha <= -Math.PI) {
			alpha += 2 * Math.PI;
		}
		while(alpha > Math.PI) {
			alpha -= 2 * Math.PI;
		}
	}
}
