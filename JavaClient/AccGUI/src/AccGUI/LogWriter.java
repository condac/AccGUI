/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AccGUI;

/**
 *
 * @author Bengt Röjder
 */
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogWriter {

    FileWriter filestream;
    BufferedWriter out;
    DateFormat df;
    Date today;
    private boolean fileopen;
    long starttime;
    AccGUI accGUI;
    private long timedelay;

    public LogWriter(AccGUI acc) {
        accGUI=acc;
    }
    public void newFile() {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        df = new SimpleDateFormat("yyyyMMdd_HHmmss");

        // Get the date today using Calendar object.
        today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);
        String filename = "acc_"+reportDate+".log";
        try {

            filestream = new FileWriter(filename);
            out = new BufferedWriter(filestream);
            
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            today = Calendar.getInstance().getTime();
            reportDate = df.format(today);
            out.write("#Logging beginned at "+reportDate+"\n");
            out.newLine();
            starttime = System.currentTimeMillis();
            fileopen = true;

        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
    public void addLine(String in) {
        if (fileopen) {
            try {
                long timestamp = System.currentTimeMillis() - starttime;
                String sout = ""+timestamp+"\tdata:\t"+in;
                out.write(sout);
                out.newLine();
            } catch (IOException ex) {
                //Logger.getLogger(LogWriter.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Warning: File closed");
            }
        }
        else {
            accGUI.drawRealtime(in);
            if ((System.currentTimeMillis()-timedelay)>1000) {
                accGUI.setLastLine(in);
                timedelay = System.currentTimeMillis();
            }
        }
    }
    public void closeFile() {
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);

        try {
            out.write("#Logging stopped at "+reportDate+"\n");
            out.newLine();
            out.close();
            fileopen = false;
        } catch (IOException ex) {
            fileopen = false;
            Logger.getLogger(LogWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
