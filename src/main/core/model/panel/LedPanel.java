package main.core.model.panel;

import java.net.SocketException;

import main.core.model.animations.Animation;
import main.core.model.pixel.RGBWPixel;
import main.output.com.UsbConnector;
import main.output.udp.SendUDP;

public class LedPanel {

	public enum ConMethod {
		USB, WIFI, NONE
	};

	public static int MATRIX_WIDTH = 6;
	public static int MATRIX_HEIGHT = 6;

	public static final double MAX_INTENSITY = 0.1;
	
	private UsbConnector usbConnector;
	private SendUDP sendUDP;
	private Animation currentAnimation;
	private int fps;
	private ConMethod conMethod = ConMethod.NONE;

	private RGBWPixel[][] LedMatrix;

	public LedPanel(int fps) {
		this.fps = fps;
		LedMatrix = new RGBWPixel[MATRIX_HEIGHT][MATRIX_WIDTH];
	}
	
	public LedPanel(int fps, int width, int height) {
		this.fps = fps;
		MATRIX_WIDTH = width;
		MATRIX_HEIGHT = height;
		LedMatrix = new RGBWPixel[MATRIX_HEIGHT][MATRIX_WIDTH];
	}

	public void initUsbConnector() {
		usbConnector = new UsbConnector(this);
		if (usbConnector.isConnectionSet()) {
			conMethod = ConMethod.USB;
		}
	}
	
	public void setConnectionMode(ConMethod conMethod) {
		this.conMethod = conMethod;
	}
	
	public UsbConnector getUsbConnector() {
		return usbConnector;
	}

	public void setWiFiConnection() throws SocketException {
		sendUDP = new SendUDP();
		conMethod = ConMethod.WIFI;
	}

	public boolean isConnected() {
		return conMethod != ConMethod.NONE;
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

	public ConMethod getConMethod() {
		return conMethod;
	}

	public void updateDisplay() {
		currentAnimation.setNextPicture(LedMatrix, MATRIX_WIDTH, MATRIX_HEIGHT);
		switch (conMethod) {
		case USB:
			usbConnector.send();
			break;
		case WIFI:
			try {
				sendUDP.sendUDP(LedMatrix);
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
