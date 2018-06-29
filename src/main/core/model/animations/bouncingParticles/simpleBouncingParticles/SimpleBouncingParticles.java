package main.core.model.animations.bouncingParticles.simpleBouncingParticles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import main.core.model.animations.Animation;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.engine.BouncingParticlesEngine;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.Particle;
import main.core.model.animations.bouncingParticles.bouncingParticlesEngine.particle.ParticleSet;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class SimpleBouncingParticles implements Animation {
	
	private int particleNumber = 13;
	private double vMin = 0.5;
	private double vMax = 2.5;
	
	private BouncingParticlesEngine bouncingParticlesEngine;
	
	public SimpleBouncingParticles(RGBWPixel[][] ledMatrix) {
		ArrayList<Particle> particles = new ArrayList<>();
		Random rd = new Random();
		double currentXpos = 1;
		for (int i = 0; i < particleNumber; i++) {
			double radius = 0.5;
			currentXpos += radius + 1;
			double angle = (rd.nextDouble() - 0.5) * 2 * Math.PI;
			//double angle = 3.1;
			int x = (int) Math.floor(currentXpos);
			currentXpos += radius + 1;
			int y = rd.nextInt(LedPanel.MATRIX_HEIGHT - 2 * (int) Math.floor(radius)) + (int) Math.floor(radius);
			//double speed = rd.nextDouble() * (vMax - vMin) + vMin;
			double speed = 1;
			Color color = Color.hsb(rd.nextInt(360), 1, 1);
			Particle particle = new Particle(speed, angle, x, y, radius, color);
			
			
			particles.add(particle);
		}
		ParticleSet particleSet = new ParticleSet.RectangularSet(particles, LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT);
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
