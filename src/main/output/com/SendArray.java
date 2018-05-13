package main.output.com;

import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class SendArray {

	private SimpleWrite simpleWrite;

	private boolean connectionSet = false;
	
	public SendArray(String comPort) {
		simpleWrite = new SimpleWrite(comPort);
		connectionSet = simpleWrite.isConnected();
	}

	public void send(RGBWPixel[][] LedMatrix) {
		simpleWrite.write(convertTo1DByteArray(LedMatrix));
	}
	
	public boolean isConnectionSet() {
		return connectionSet;
	}

	private byte[] convertTo1DByteArray(RGBWPixel[][] LedMatrix) {
		byte[] byteArray = new byte[LedPanel.MATRIX_HEIGHT * LedPanel.MATRIX_WIDTH * 4 +1];
		byte initialByte = (byte) 0b00000001;
		byteArray[0] = initialByte;
		boolean readingDirection = true;
		for (int line = 0; line < LedPanel.MATRIX_HEIGHT; line++) {
			for (int column = 0; column < LedPanel.MATRIX_WIDTH; column++) {
				RGBWPixel pixelToSend;
				if (readingDirection) {
					pixelToSend = LedMatrix[line][column];
				} else {
					pixelToSend = LedMatrix[line][LedPanel.MATRIX_WIDTH - column - 1];
				}
				byteArray[1 + line*LedPanel.MATRIX_WIDTH*4 + column*4] = (byte) pixelToSend.getRed();
				byteArray[1 + line*LedPanel.MATRIX_WIDTH*4 + column*4 + 1] = (byte) pixelToSend.getGreen();
				byteArray[1 + line*LedPanel.MATRIX_WIDTH*4 + column*4 + 2] = (byte) pixelToSend.getBlue();
				byteArray[1 + line*LedPanel.MATRIX_WIDTH*4 + column*4 + 3] = (byte) pixelToSend.getWhite();
			}
			readingDirection = !readingDirection;
		}
		return byteArray;
	}
}
