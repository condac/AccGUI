/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AccGUI;

import java.awt.*; 
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;



public class RealtimePanel extends Canvas implements Runnable {
        JFrame frame;
        double[] values = new double[10];
        double[] valuesMax = new double[10];
        Color color1 = new Color (0, 0, 0);
        Color color2 = new Color (100, 100, 255);
        Color color3 = new Color (100, 255, 100);
        Color color4 = new Color (255, 100, 100);
        Color color5 = new Color (150, 150, 255);
        Color color6 = new Color (50, 50, 255);
    private final Thread drawThread;

        public RealtimePanel()         {
                super();
                super.setSize(400,200);
                frame = new JFrame("canvas line");
                frame.getContentPane().add(this);
                frame.pack();
                frame.setVisible(true);
                
                drawThread = new Thread(this);
                drawThread.start();
        }

        @Override
        public void paint(Graphics g) {
                super.paint(g);
                //System.out.println("repainting");
                g.setColor(color1);
                g.fillRect(1, 100-(int) ( (values[1]/valuesMax[1])*100), 20,  (int) ( (values[1]/valuesMax[1])*100) );
                g.setColor(color2);
                g.fillRect(20, 100, 20, (int) ( (values[1]/valuesMax[1])*100)   );
                g.setColor(color3);
                g.fillRect(40, 100, 20, (int) ( (values[2]/valuesMax[2])*-100)   );
                g.setColor(color4);
                g.fillRect(60, 100, 20, (int) ( (values[3]/valuesMax[3])*-100)   );
                g.setColor(color5);
                g.fillRect(80, 100, 20, (int) ( (values[4]/valuesMax[4])*-100)   );
                g.setColor(color6);
                g.fillRect(100, 100, 20, (int) ( (values[5]/valuesMax[5])*-100)   );
                g.setColor(color1);
                g.fillRect(120, 100, 20, (int) ( (values[6]/valuesMax[6])*-100)   );
                g.setColor(color2);
                g.fillRect(140, 100, 20, (int) ( (values[7]/valuesMax[7])*-100)   );
        }
        public void insertData(String in) {
            StringTokenizer st = new StringTokenizer(in);
            int i=0;
            while (st.hasMoreTokens()) {
               
                try{
                    values[i] = Double.parseDouble(st.nextToken());
                    if (values[i]>valuesMax[i]) {
                        valuesMax[i] = values[i];
                    }
                }catch (NumberFormatException e) {
                    
                }
                //System.out.println("debugstring"+values[i]);
                i++;
                
            }
            //repaint();
        }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1);
                Thread.yield();
            } catch (InterruptedException ex) {
                Logger.getLogger(RealtimePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println("repaintcall");
            repaint();
        }
    }
}
