package main.output.com;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SimpleWrite {
	private Enumeration<CommPortIdentifier> portList;
	private CommPortIdentifier portId;
	private SerialPort serialPort;
	private OutputStream outputStream;
	private String COM;

	public SimpleWrite(String COM) {
		this.COM = COM;
		initCOMPort();
	}

	public void initCOMPort() {
		portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (portId.getName().equals(COM)) {
					// if (portId.getName().equals("/dev/term/a")) {
					try {
						serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
					} catch (PortInUseException e) {
					}
					try {
						outputStream = serialPort.getOutputStream();
						if(outputStream != null) {
							System.out.println("USB connected : " + COM);
						}
					} catch (IOException e) {
					}
					try {
						serialPort.setSerialPortParams(1000000, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);
					} catch (UnsupportedCommOperationException e) {
					}
				}
			}
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
