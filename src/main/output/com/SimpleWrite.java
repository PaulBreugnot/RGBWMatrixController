package main.output.com;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SimpleWrite {
	private CommPortIdentifier connectedPort;
	private SerialPort serialPort;
	private OutputStream outputStream;
	private String COM;

	public SimpleWrite(String COM, ArrayList<CommPortIdentifier> portList) {
		this.COM = COM;
		initCOMPort(portList);
	}
	
	public CommPortIdentifier getConnectedPort() {
		return connectedPort;
	}

	public void initCOMPort(ArrayList<CommPortIdentifier> portList) {
		boolean connected = false;
		int index = 0;
		while(!connected && index < portList.size()) {
			connectedPort = portList.get(index);
			if (connectedPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (connectedPort.getName().equals(COM)) {
					// if (portId.getName().equals("/dev/term/a")) {
					try {
						serialPort = (SerialPort) connectedPort.open("SimpleWriteApp", 2000);
					} catch (PortInUseException e) {
						connectedPort = null;
					}
					try {
						outputStream = serialPort.getOutputStream();
						if(outputStream != null) {
							connected = true;
							System.out.println("USB connected : " + COM);
						}
					} catch (IOException e) {
						connectedPort = null;
					}
					try {
						serialPort.setSerialPortParams(1000000, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);
					} catch (UnsupportedCommOperationException e) {
						connectedPort = null;
					}
				}
			}
			index ++;
		}
	}
	
	public void closePort() {
		serialPort.close();
	}

	public void write(byte[] message) {
		try {
			if(isConnected()) {
				outputStream.write(message);
			}
		} catch (IOException e) {
		}

	}
	
	public boolean isConnected() {
		return outputStream != null;
	}
}
