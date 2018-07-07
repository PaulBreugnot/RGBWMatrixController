package main.core.model.animations.bouncingParticles.shootingStars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import main.core.model.animations.Animation;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.animations.bouncingParticles.shootingStars.particles.LeaderParticle;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class ShootingStars implements Animation {
	
	private int particleNumber = 10;
	private double vMin = 1;
	private double vMax = 1;
	private double minRadius = 1;
	private double maxRadius = 1;
	
	// Collision area parameters
	
	// Size extension relative to matrix size
	private double xExtension = 20;
	private double yExtension = 20;
	
	// Collision area position
	private double xOffset = -10;
	private double yOffset = -10;
	
	private BouncingParticlesEngine bouncingParticlesEngine;
	
	public ShootingStars(RGBWPixel[][] ledMatrix) {
		ArrayList<Particle> particles = new ArrayList<>();
		Random rd = new Random();
		double currentXpos = 1;
		for (int i = 0; i < particleNumber; i++) {
			//double radius = 2;
			double radius = rd.nextDouble() * (maxRadius - minRadius) + minRadius;
			currentXpos += radius + 1;
			double angle = (rd.nextDouble() - 0.5) * 2 * Math.PI;
			//double angle = 3.1;
			int x = (int) Math.floor(currentXpos);
			currentXpos += radius + 1;
			int y = rd.nextInt(LedPanel.MATRIX_HEIGHT - 2 * (int) Math.floor(radius)) + (int) Math.floor(radius);
			double speed = rd.nextDouble() * (vMax - vMin) + vMin;
			Color color = Color.hsb(rd.nextInt(360), 1, 1);
			LeaderParticle particle = new LeaderParticle(speed, angle, x, y, radius, color);
			
			particles.add(particle);
			particles.addAll(particle.getFollowers());
		}
		ParticleSet particleSet = new ParticleSet.RectangularSet(particles, LedPanel.MATRIX_WIDTH + 20, LedPanel.MATRIX_HEIGHT + 20, -10, -10, false);
		bouncingParticlesEngine = new BouncingParticlesEngine(ledMatrix, particleSet);
	}

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		bouncingParticlesEngine.progress();
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
