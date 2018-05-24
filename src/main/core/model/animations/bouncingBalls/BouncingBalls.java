package main.core.model.animations.bouncingBalls;

import java.io.IOException;
import java.util.Random;

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
			particleSet.addParticle(particle);
		}
	}

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		particleSet.progress(1);
		System.out.println("Salut!");
		for (Particle particle : particleSet.getParticles()) {
			System.out.println("xPos = " + particle.getxPos());
			System.out.println("yPos = " + particle.getyPos());
			int x = (int) Math.floor(particle.getxPos());
			int y = (int) Math.floor(particle.getyPos());
			ledMatrix[y][x] = RGBWPixel.rgbPixel(255, 0, 0);
		}

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
