package main.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class HttpPostRequest {

	URL obj;

	public HttpPostRequest(String url) throws IOException {
		obj = new URL(url);
	}

	// HTTP POST request
	public void sendPost(RGBWPixel[][] LedMatrix) throws Exception {
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		System.out.println("Sending post");
		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("matrixValues", new String(convertTo1DCharArray(LedMatrix)));
		con.setDoOutput(true);

		
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.flush();
		wr.close();

		con.getResponseCode();

	}

	private char[] convertTo1DCharArray(RGBWPixel[][] LedMatrix) {
		char[] charArray = new char[LedPanel.MATRIX_HEIGHT * LedPanel.MATRIX_WIDTH * 4 + 1 + 1];
		char initialChar = (char) 0b00000001;
		charArray[0] = initialChar;
		boolean readingDirection = true;
		for (int line = 0; line < LedPanel.MATRIX_HEIGHT; line++) {
			for (int column = 0; column < LedPanel.MATRIX_WIDTH; column++) {
				RGBWPixel pixelToSend;
				if (readingDirection) {
					pixelToSend = LedMatrix[line][column];
				} else {
					pixelToSend = LedMatrix[line][LedPanel.MATRIX_WIDTH - column - 1];
				}
				charArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4] = (char) pixelToSend.getRed();
				charArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 1] = (char) pixelToSend.getGreen();
				charArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 2] = (char) pixelToSend.getBlue();
				charArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 3] = (char) pixelToSend.getWhite();
			}
			readingDirection = !readingDirection;
		}
		char finalChar = (char) 0b00000000;
		charArray[LedPanel.MATRIX_HEIGHT * LedPanel.MATRIX_WIDTH * 4 + 1] = finalChar;
		return charArray;
	}

}
