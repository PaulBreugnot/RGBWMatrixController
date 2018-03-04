package main.core.model.panel;

import main.com.SendArray;
import main.core.model.animations.Animation;
import main.core.model.pixel.RGBWPixel;

public class LedPanel {

	public static final int MATRIX_WIDTH = 32;
	public static final int MATRIX_HEIGHT = 16;

	private SendArray sendArray;
	private Animation currentAnimation;
	private int fps;
	private boolean isConnected;

	private RGBWPixel[][] LedMatrix = new RGBWPixel[MATRIX_HEIGHT][MATRIX_WIDTH];

	public LedPanel(String portCom, int fps) {
		this.fps = fps;
		sendArray = new SendArray(portCom);
	}
	
	public void setConnection(String portCom) {
		sendArray = new SendArray(portCom);
		isConnected = sendArray.isConnectionSet();
	}
	
	public boolean isConnected() {
		return isConnected;
	}

	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public RGBWPixel[][] getLedMatrix() {
		return LedMatrix;
	}

	public void updateDisplay() {
		currentAnimation.setNextPicture(LedMatrix, MATRIX_WIDTH, MATRIX_HEIGHT);
		sendArray.send(LedMatrix);
	}

	public void setPixel(int line, int column, RGBWPixel pixel) {
		LedMatrix[line][column] = pixel;
	}

	public static void setBlackPanel(RGBWPixel[][] ledMatrix) {
		for (int line = 0; line < MATRIX_HEIGHT; line++) {
			for (int column = 0; column < MATRIX_WIDTH; column++) {
				ledMatrix[line][column] = RGBWPixel.rgbPixel(0, 0, 0);
			}
		}
	}
}
