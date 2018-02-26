package main.com;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SimpleWrite {
	private Enumeration portList;
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
					} catch (IOException e) {
					}
					try {
						serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);
					} catch (UnsupportedCommOperationException e) {
					}
				}
			}
		}
	}

	public void write(byte[] message) {
		try {
			outputStream.write(message);
		} catch (IOException e) {
		}

	}
}
