/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AccGUI;

/**
 *
 * @author Bengt Röjder
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import gnu.io.*;
import java.io.*;
import java.util.*;

public class SerialRunner implements Runnable, SerialPortEventListener  {
    CommPortIdentifier portId1;
    InputStream inputStream;
    SerialPort serialPort1;
    Thread readThread;

    public SerialRunner(String portNr, String baudNr) {
        int baudInt = Integer.parseInt(baudNr);

        //TODO: check baudInt for valid values

        System.out.println("Starting on port: "+portNr);
        try {
            portId1 = CommPortIdentifier.getPortIdentifier(portNr);
        } catch (NoSuchPortException ex) {
            Logger.getLogger(SerialRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            serialPort1 = (SerialPort) portId1.open("SerialRunner", 2000); // 2000 is timeout

            System.out.println("Connected to port: " + portId1.getName());
        } catch (PortInUseException e) {
            System.out.println("ERROR: failed to connect to port: " + portId1.getName());
        }

        try {
            inputStream = serialPort1.getInputStream();
        } catch (IOException e) {}

        try {
            serialPort1.addEventListener(this);
        } catch (TooManyListenersException e) {}


        serialPort1.notifyOnDataAvailable(true);
        try {
            serialPort1.setSerialPortParams(baudInt,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
        
        } catch (UnsupportedCommOperationException e) {}

        readThread = new Thread(this);
        readThread.start();
    }

    public void starta(String port, String baud) {
        try {
            SerialRunner reader = new SerialRunner(port,baud);
        }
    	catch (Exception e) {
            System.out.println("msg1 - " + e);
     	}
    }
    public void run() {
        try {
            Thread.sleep(100);
            //System.out.println("debugrunning");
        } catch (InterruptedException e) {}
    }
    public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            StringBuffer readBuffer = new StringBuffer();
            int c;
            try {
                while ((c=inputStream.read()) != 10){
                   if(c!=13) {
                       readBuffer.append((char) c);
                   }
                }
                try {
                    System.out.println(readBuffer.toString());
                }
                catch (IndexOutOfBoundsException f) {
                    System.out.println("Not good...");
                }
            } catch (IOException e) {}
            break;
        }
    }

    public void stop() {
        System.out.println("Stop");
        if (serialPort1 != null) {
			serialPort1.removeEventListener();
			serialPort1.close();
	}
        try {
            readThread = null;
        }
        catch (NullPointerException e) {
                System.out.println("ERROR: Stopping error.");
        }
    }
}