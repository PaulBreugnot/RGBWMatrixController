package main.output.com;

import java.util.ArrayList;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class UsbConnector {

	private SimpleWrite simpleWrite;
	private LedPanel ledPanel;
	private CommPortIdentifier connectedPort;
	private ConnectionChecker connectionChecker = new ConnectionChecker(this);

	private boolean connectionSet = false;
	
	public UsbConnector(LedPanel ledPanel, String comPort) {
		connectionChecker.start();
		this.ledPanel = ledPanel;
		simpleWrite = new SimpleWrite(comPort, connectionChecker.getPortList());
		connectionSet = simpleWrite.isConnected();
	}
	
	public UsbConnector(LedPanel ledPanel) {
		this(ledPanel, null);
	}

	public ConnectionChecker getConnectionChecker() {
		return connectionChecker;
	}
	public void send() {
		simpleWrite.write(convertTo1DByteArray());
	}
	
	public boolean tryToConnect(String comPort) {
		SimpleWrite newSimpleWrite = new SimpleWrite(comPort, connectionChecker.getPortList());
		if (newSimpleWrite.isConnected()){
			disconnect();
			simpleWrite = newSimpleWrite;
			connectedPort = simpleWrite.getConnectedPort();
			connectionSet = true;
			ledPanel.setConnectionMode(LedPanel.ConMethod.USB);
			return true;
		}
		else {
			simpleWrite = null;
			connectedPort = null;
			connectionSet = false;
			ledPanel.setConnectionMode(LedPanel.ConMethod.NONE);
			return false;
		}
	}
	
	public void disconnect() {
		simpleWrite.closePort();
		simpleWrite = null;
		connectionSet = false;
		ledPanel.setConnectionMode(LedPanel.ConMethod.USB);
	}

	public CommPortIdentifier getConnectedPort() {
		return connectedPort;
	}
	
	public boolean isConnectionSet() {
		return connectionSet;
	}

	private byte[] convertTo1DByteArray() {
		RGBWPixel[][] LedMatrix = ledPanel.getLedMatrix();
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
	
	public static class ConnectionChecker extends Thread {
		
		private static ArrayList<CommPortIdentifier> portList = new ArrayList<>();
		private UsbConnector usbConnector;

		public ConnectionChecker(UsbConnector usbConnector) {
			this.usbConnector = usbConnector;
		}
		
		public ArrayList<CommPortIdentifier> getPortList(){
			return portList;
		}

		@Override
		public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Enumeration<CommPortIdentifier> portListEnum = CommPortIdentifier.getPortIdentifiers();
				portList.clear();
				while(portListEnum.hasMoreElements()) {
					portList.add(portListEnum.nextElement());
				}
				CommPortIdentifier connectedPort = usbConnector.getConnectedPort();
				if(connectedPort != null && !portList.contains(connectedPort)) {
					usbConnector.disconnect();
				}
				if(!usbConnector.isConnectionSet() && portList.size() == 1) {
					usbConnector.tryToConnect(portList.get(0).getName());
				}
			}
		}
}
