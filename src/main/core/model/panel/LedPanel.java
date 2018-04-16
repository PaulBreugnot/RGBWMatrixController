package main.core.model.panel;

import java.io.IOException;

import main.com.SendArray;
import main.core.model.animations.Animation;
import main.core.model.pixel.RGBWPixel;
import main.http.HttpPostRequest;

public class LedPanel {

	private enum ConMethod {
		USB, WIFI, NONE
	};

	public static final int MATRIX_WIDTH = 32;
	public static final int MATRIX_HEIGHT = 16;

	public static final double MAX_INTENSITY = 0.1;

	private SendArray sendArray;
	private HttpPostRequest httpPostRequest;
	private Animation currentAnimation;
	private int fps;
	private ConMethod conMethod = ConMethod.NONE;

	private RGBWPixel[][] LedMatrix = new RGBWPixel[MATRIX_HEIGHT][MATRIX_WIDTH];

	public LedPanel(int fps) {
		this.fps = fps;
	}

	public void setUSBConnection(String portCom) {
		sendArray = new SendArray(portCom);
		if (sendArray.isConnectionSet()) {
			conMethod = ConMethod.USB;
		}
	}

	public void setWiFiConnection(String url) throws IOException {
		httpPostRequest = new HttpPostRequest(url);
		conMethod = ConMethod.WIFI;
	}

	public boolean isConnected() {
		return conMethod == ConMethod.NONE;
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
		switch (conMethod) {
		case USB:
			sendArray.send(LedMatrix);
			break;
		case WIFI:
			try {
				httpPostRequest.sendPost(LedMatrix);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
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
