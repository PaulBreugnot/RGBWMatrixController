package main.core.model.animations.bouncingBalls;

import java.io.IOException;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class BouncingBalls implements Animation {

	private ParticleSet particleSet;
	private RGBWPixel[][] bufferLedMatrix;

	public BouncingBalls(RGBWPixel[][] ledMatrix) {
		LedPanel.setBlackPanel(ledMatrix);
		bufferLedMatrix = ledMatrix;
		particleSet = new ParticleSet.RectangularSet(LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT);
		Random rd = new Random();
		for (int i = 0; i < 40; i++) {
			double angle = (rd.nextDouble() - 0.5) * 2 * Math.PI;
			int x = rd.nextInt(LedPanel.MATRIX_WIDTH - 2) + 1;
			int y = rd.nextInt(LedPanel.MATRIX_HEIGHT - 2) + 1;
			Particle particle = new Particle(0.5, angle, x, y);
			particle.xPosProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					int old_x = (int) Math.floor((double) old_val);
					int new_x = (int) Math.floor((double) new_val);
					if (new_x != old_x) {
						int y = (int) Math.floor(particle.getyPos());
						bufferLedMatrix[y][new_x] = RGBWPixel.rgbPixel(255, 0, 0);
						bufferLedMatrix[y][old_x] = RGBWPixel.rgbPixel(0, 0, 0);
					}
				}
			});
			particle.yPosProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					int old_y = (int) Math.floor((double) old_val);
					int new_y = (int) Math.floor((double) new_val);
					if (new_y != old_y) {
						int x = (int) Math.floor(particle.getxPos());
						bufferLedMatrix[new_y][x] = RGBWPixel.rgbPixel(255, 0, 0);
						bufferLedMatrix[old_y][x] = RGBWPixel.rgbPixel(0, 0, 0);
					}
				}
			});
			particleSet.addParticle(particle);
		}
	}

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		particleSet.progress(1);

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
