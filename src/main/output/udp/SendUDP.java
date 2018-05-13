package main.output.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.InetAddress;

import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class SendUDP {
	
	public final static int port = 80;
	public final static String ip = "192.168.1.1";
	DatagramSocket client;
	
	public SendUDP() throws SocketException {
		client = new DatagramSocket();
	}
	
	public void sendUDP(RGBWPixel[][] LedMatrix) {
		byte[] buffer = new byte[LedPanel.MATRIX_HEIGHT * LedPanel.MATRIX_WIDTH  * 4 + 1];
		convertTo1DByteArray(buffer, LedMatrix);
		//byte[] buffer = (new String("Salut NodeMCU!")).getBytes();
		
		try {
			InetAddress address = InetAddress.getByName(ip);
			DatagramPacket packet1 = new DatagramPacket(buffer, 1025, address, port);
			
			System.out.println("Sending UDP packet 1");
			packet1.setData(buffer, 0, 1025);
			System.out.println(packet1.getLength());
			client.send(packet1);
			
			DatagramPacket packet2 = new DatagramPacket(buffer, 1025, 1024, address, port);
			
			System.out.println("Sending UDP packet 1");
			packet2.setData(buffer, 1025, 1024);
			System.out.println(packet2.getLength());
			client.send(packet2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void convertTo1DByteArray(byte[] byteArray, RGBWPixel[][] LedMatrix) {
		byte initialChar = (byte) 0b00000001;
		byteArray[0] = initialChar;
		boolean readingDirection = true;
		for (int line = 0; line < LedPanel.MATRIX_HEIGHT; line++) {
			for (int column = 0; column < LedPanel.MATRIX_WIDTH; column++) {
				RGBWPixel pixelToSend;
				if (readingDirection) {
					pixelToSend = LedMatrix[line][column];
				} else {
					pixelToSend = LedMatrix[line][LedPanel.MATRIX_WIDTH - column - 1];
				}
				byteArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4] = (byte) pixelToSend.getRed();
				byteArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 1] = (byte) pixelToSend.getGreen();
				byteArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 2] = (byte) pixelToSend.getBlue();
				byteArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 3] = (byte) pixelToSend.getWhite();
				/*byteArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4] = (byte) 1;
				byteArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 1] = (byte) 2;
				byteArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 2] = (byte) 3;
				byteArray[1 + line * LedPanel.MATRIX_WIDTH * 4 + column * 4 + 3] = (byte) 4;*/
			}
			readingDirection = !readingDirection;
		}
		//return byteArray;
	}

}
