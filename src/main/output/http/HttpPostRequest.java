package main.output.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
		
		//System.out.println("Sending post");
		// add reuqest header
		con.setRequestMethod("POST");
		//con.setRequestProperty("matrixValues", new String(shortConvertTo1DCharArray(LedMatrix)));
		con.setDoOutput(true);
		//System.out.println(new String(convertTo1DCharArray(LedMatrix)).length());
		
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		//wr.write((new String("matrixValues=")).getBytes(StandardCharsets.UTF_8));
		String postData = "values=";
		wr.write(postData.getBytes(StandardCharsets.UTF_8));
		wr.write(convertTo1DCharArray(LedMatrix));
		//wr.write('\n');
		wr.flush();
		wr.close();
		//System.out.println(con.getResponseMessage());
		con.getResponseCode();

	}

	private byte[] shortConvertTo1DCharArray(RGBWPixel[][] LedMatrix) {
		byte[] charArray = new byte[4];
		byte initialChar = (byte) 0b1;
		charArray[0] = initialChar;
		charArray[1] = ((byte) 0b11111111)|0b1;
		charArray[2] = ((byte) 0b0)|0b1;
		charArray[3] = ((byte) 0b111)|0b1;
		//byte finalChar = (byte) 0b00000000;
	//charArray[4] = finalChar;
	return charArray;
}

	private byte[] convertTo1DCharArray(RGBWPixel[][] LedMatrix) {
		byte[] charArray = new byte[LedPanel.MATRIX_HEIGHT * LedPanel.MATRIX_WIDTH * 4 + 1];
		byte initialChar = (byte) 0b00000001;
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
				charArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4] = (byte) (pixelToSend.getRed()|0b1);
				charArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 1] = (byte) (pixelToSend.getGreen()|0b1);
				charArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 2] = (byte) (pixelToSend.getBlue()|0b1);
				charArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 3] = (byte) (pixelToSend.getWhite()|0b1);
				//charArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 3] = (byte) 0b1111;
			}
			readingDirection = !readingDirection;
		}
		//byte finalChar = '\n';
		//charArray[LedPanel.MATRIX_HEIGHT * LedPanel.MATRIX_WIDTH * 4 + 1] = finalChar;
		return charArray;
	}

}
