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
	private RGBWPixel[][] bufferLedMatrix;
	public double deltaT = 1;

	public BouncingBalls(RGBWPixel[][] ledMatrix) {
		LedPanel.setBlackPanel(ledMatrix);
		bufferLedMatrix = ledMatrix;
		ArrayList<Particle> particles = new ArrayList<>();
		Random rd = new Random();
		for (int i = 0; i < 1; i++) {
			//double angle = (rd.nextDouble() - 0.5) * 2 * Math.PI;
			//int x = rd.nextInt(LedPanel.MATRIX_WIDTH - 2) + 1;
			//int y = rd.nextInt(LedPanel.MATRIX_HEIGHT - 2) + 1;
			double angle = 0.7;
			int x = 15;
			int y = 7;
			System.out.println("Angle : " + angle);
			Particle particle = new Particle(0.5, angle, x, y);
			
			//All the coordinates are forced to fit matrix width and height
			particle.xPosProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					int old_x = (int)  Math.min(Math.max(Math.floor((double) old_val), 0), LedPanel.MATRIX_WIDTH - 1);
					int new_x = (int) Math.min(Math.max(Math.floor((double) new_val), 0), LedPanel.MATRIX_WIDTH - 1);
					if (new_x != old_x) {
						int y = (int) Math.min(Math.max(Math.floor(particle.getyPos()), 0), LedPanel.MATRIX_HEIGHT - 1);
						bufferLedMatrix[y][new_x] = RGBWPixel.rgbPixel(255, 0, 0);
						bufferLedMatrix[y][old_x] = RGBWPixel.rgbPixel(0, 0, 0);
					}
				}
			});
			particle.yPosProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					int old_y = (int) Math.min(Math.max(Math.floor((double) old_val), 0), LedPanel.MATRIX_HEIGHT - 1);
					int new_y = (int) Math.min(Math.max(Math.floor((double) new_val), 0), LedPanel.MATRIX_HEIGHT - 1);
					if (new_y != old_y) {
						int x = (int) Math.min(Math.max(Math.floor(particle.getxPos()), 0), LedPanel.MATRIX_WIDTH - 1);
						bufferLedMatrix[old_y][x] = RGBWPixel.rgbPixel(0, 0, 0);
						bufferLedMatrix[new_y][x] = RGBWPixel.rgbPixel(255, 0, 0);
					}
				}
			});
			particles.add(particle);
		}
		particleSet = new ParticleSet.RectangularSet(particles, LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT);
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
