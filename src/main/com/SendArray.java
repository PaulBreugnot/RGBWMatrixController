package main.com;

import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class SendArray {

	private SimpleWrite simpleWrite;

	public SendArray(String comPort) {
		simpleWrite = new SimpleWrite(comPort);
	}

	public void send(RGBWPixel[][] LedMatrix) {
		simpleWrite.write(convertTo1DByteArray(LedMatrix));
	}

	private byte[] convertTo1DByteArray(RGBWPixel[][] LedMatrix) {
		byte[] byteArray = new byte[LedPanel.MATRIX_HEIGHT * LedPanel.MATRIX_WIDTH];
		boolean readingDirection = true;
		for (int line = 0; line < LedPanel.MATRIX_HEIGHT; line++) {
			for (int column = 0; column < LedPanel.MATRIX_WIDTH; column++) {
				RGBWPixel pixelToSend;
				if (readingDirection) {
					pixelToSend = LedMatrix[line][column];
				} else {
					pixelToSend = LedMatrix[line][LedPanel.MATRIX_WIDTH - column - 1];
				}
				byteArray[line + column] = (byte) pixelToSend.getRed();
				byteArray[line + column + 1] = (byte) pixelToSend.getGreen();
				byteArray[line + column + 2] = (byte) pixelToSend.getBlue();
				byteArray[line + column + 3] = (byte) pixelToSend.getWhite();
			}
			readingDirection = !readingDirection;
		}
		return byteArray;
	}
}
