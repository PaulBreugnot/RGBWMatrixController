package main.core.model.animations.bouncingBalls.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.animations.bouncingBalls.particle.Particle;
import main.core.model.animations.bouncingBalls.particle.ParticleSet;
import main.core.model.animations.bouncingBalls.particle.ParticleSet.RectangularSet;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class BouncingBalls implements Animation {

	private ParticleSet particleSet;
	private int particleNumber = 3;
	private double vMin = 0.5;
	private double vMax = 2.5;
	private RGBWPixel[][] bufferLedMatrix;
	public double deltaT = 1;

	public BouncingBalls(RGBWPixel[][] ledMatrix) {
		LedPanel.setBlackPanel(ledMatrix);
		bufferLedMatrix = ledMatrix;
		ArrayList<Particle> particles = new ArrayList<>();
		Random rd = new Random();
		double currentXpos = 1;
		for (int i = 0; i < particleNumber; i++) {
			double radius = 2;
			currentXpos += radius + 1;
			double angle = (rd.nextDouble() - 0.5) * 2 * Math.PI;
			//double angle = 3.1;
			int x = (int) Math.floor(currentXpos);
			currentXpos += radius + 1;
			int y = rd.nextInt(LedPanel.MATRIX_HEIGHT - 2 * (int) Math.floor(radius)) + (int) Math.floor(radius);
			//double speed = rd.nextDouble() * (vMax - vMin) + vMin;
			double speed = 1;
			Particle particle = new Particle(speed, angle, x, y, radius);
			
			//All the coordinates are forced to fit matrix width and height
			particle.xPosProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					int old_x = (int) Math.floor((double) old_val);
					int new_x = (int) Math.floor((double) new_val);
					if (new_x != old_x) {
						int y = (int) Math.floor(particle.getyPos());
						clear(particle.getRadius(), old_x, y);
						render(particle);
						//bufferLedMatrix[y][new_x] = RGBWPixel.rgbPixel(255, 0, 0);
						//bufferLedMatrix[y][old_x] = RGBWPixel.rgbPixel(0, 0, 0);
					}
				}
			});
			particle.yPosProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					int old_y = (int) Math.floor((double) old_val);
					int new_y = (int) Math.floor((double) new_val);
					if (new_y != old_y) {
						int x = (int) Math.floor(particle.getxPos());
						clear(particle.getRadius(), x, old_y);
						render(particle);
						//bufferLedMatrix[old_y][x] = RGBWPixel.rgbPixel(0, 0, 0);
						//bufferLedMatrix[new_y][x] = RGBWPixel.rgbPixel(255, 0, 0);
					}
				}
			});
			particles.add(particle);
		}
		particleSet = new ParticleSet.RectangularSet(particles, LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT);
	}
	
	private void clear(double radius, int old_x, int old_y) {
		for(int x = (int) Math.floor(-radius) ; x <= radius; x++) {
			for(int y = (int) Math.floor(-radius) ; y <= radius; y++) {
				if(Math.sqrt(x * x + y * y) <= radius) {
					if(old_y + y >= 0 && old_y + y < LedPanel.MATRIX_HEIGHT && old_x + x >= 0 && old_x + x < LedPanel.MATRIX_WIDTH) {
					bufferLedMatrix[old_y + y][old_x + x] = RGBWPixel.rgbPixel(0, 0, 0);
					}
				}
			}
		}
	}
	
	private void render(Particle p) {
		for(int x = (int) Math.floor(- p.getRadius()) ; x <= p.getRadius(); x++) {
			for(int y = (int) Math.floor(- p.getRadius()) ; y <= p.getRadius(); y++) {
				if(Math.sqrt(x * x + y * y) <= p.getRadius()) {
					int xParticle = (int) Math.floor(p.getxPos());
					int yParticle = (int) Math.floor(p.getyPos());
					if(yParticle + y >= 0 && yParticle + y < LedPanel.MATRIX_HEIGHT && xParticle + x >= 0 && xParticle + x < LedPanel.MATRIX_WIDTH) {
					bufferLedMatrix[yParticle + y][xParticle + x] = new RGBWPixel(p.getColor(x, y));
					}
				}
			}
		}
	}
	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		particleSet.progress(deltaT);

		/*
		 * for (Particle particle : particleSet.getParticles()) {
		 * System.out.println("xPos = " + particle.getxPos());
		 * System.out.println("yPos = " + particle.getyPos()); int x = (int)
		 * Math.floor(particle.getxPos()); int y = (int) Math.floor(particle.getyPos());
		 * ledMatrix[y][x] = RGBWPixel.rgbPixel(255, 0, 0); }
		 */

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
