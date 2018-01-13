/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TermGraph;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Raul Vera <twyblade64@hotmail.com>
 */
public class SoundGraph extends javax.swing.JFrame implements SerialPortEventListener, KeyListener {
    private final String[] keyNoteCode = new String[] {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
    TargetDataLine inLine;
    SourceDataLine outLine;

    JFreeChart chart;
    XYSeries tempSeries;
    SerialPort serialPort;
    Long timeStart = Long.MAX_VALUE;
    byte[] data;
    PipedInputStream pipeIn;
    PipedOutputStream pipeOut;
    ChartPanel cp;
    //ByteArrayOutputStream audioBuffer;

    private BufferedReader input;
    private OutputStream output;
    private DataOutputStream oos;
    private static final String PORT_NAMES[] = {
        "COM3"
    };

    private int count = 0;
    private double muestras[] = new double[8000];
    private int bufferSize = 8000;

    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 8000;

    private Vector2D[] w = DFT.getWVectors(muestras.length);

    TargetDataLine line;

    /**
     * This should be called when you stop using the port. This will prevent port locking on
     * platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                if (timeStart > System.currentTimeMillis())
                    timeStart = System.currentTimeMillis();
                muestras[count] = Double.parseDouble(input.readLine());
                //System.out.println(count+" Read: "+muestras[count]);
                count++;
                if (count >= muestras.length) {
                    count = 0;
                    double[] res = DFT.GetMagnitudes(muestras,w);
                    tempSeries.clear();
                    for (int i = 0; i < muestras.length; i++) {
                        tempSeries.add(i, res[i]);
                    }

                }
                //float value = Float.parseFloat(inputLine);
                //tempSeries.add((float)(System.currentTimeMillis() - timeStart) / 1000, value);
                //System.out.println((System.currentTimeMillis() - timeStart)+"\t"+inputLine);
                //oos.write(Integer.parseInt(inputLine));
                //oos.flush();
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }


    /**
     * Creates new form Main
     */
    public SoundGraph() {
        initComponents();

        //------- Chart
        tempSeries = new XYSeries("voice");

        XYSeriesCollection tempColl = new XYSeriesCollection();
        tempColl.addSeries(tempSeries);


        chart = ChartFactory.createXYLineChart("", "", "", tempColl, PlotOrientation.VERTICAL, true, true,false);
        chart.setNotify(false);
        cp = new ChartPanel(chart);
        chartPanel.removeAll();
        chartPanel.add(cp);
        validate();
        pack();

        addKeyListener(this);
        setFocusable(true);

        //------- Voz
        /*AudioFormat format = new AudioFormat(8000.0f, 8, 1, true, true);
        DataLine.Info infoMic = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(infoMic)) {
            System.err.println("infoOut");
        }

        //TargetDataLine line;
        try {
            inLine = (TargetDataLine) AudioSystem.getLine(infoMic);
            inLine.open(format);
        } catch (LineUnavailableException ex) {
            System.err.println("NOPE");
        }*/

        //------- Serial
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        Thread threadInArduino = new Thread(new Runnable() {
                @Override
                public void run() {
                    int offset = 0;
                    int[] data = new int[bufferSize];
                    while (true) {
                        try {
                            //inLine.flush();
                            //int val = (input.readLine());

                            //input.read(data, 0, data.length);
                            for (int i = 0; i < data.length; i++) {
                                //String in = input.readLine();
                                //System.out.println(i+"-"+in+"-");
                                try {
                                    int d = Integer.parseInt(input.readLine());
                                    //if (d > 50)
                                        data[i] = d;

                                    //data[i] = input.read();

                                    //(byte)Integer.parseInt(in);
                                    System.out.println(i+"-"+data[i]+"-");
                                } catch(NumberFormatException e) {

                                }
                            }
                            System.out.println("Datos leidos!");

                            for (int i=0; i<muestras.length-bufferSize; i++){
                                muestras[i] = muestras[i+bufferSize];
                            }
                            for (int i = 0; i < bufferSize; i++) {
                                //System.out.print((int)data[i]+ " ");
                                muestras[muestras.length-bufferSize+i] = data[i];
                                //muestras[offset++] = data[i];
                                //if (offset>=muestras.length)
                                //    offset = 0;
                            }
                            //System.out.println();
                            //System.out.println();
                        } catch (IOException ex) {
                            Logger.getLogger(SoundGraph.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
        threadInArduino.start();

        /*Thread threadInMic = new Thread(new Runnable() {
                @Override
                public void run() {
                    //byte[] b = new byte[muestras.length];
                    int offset = 0;
                    byte[] data = new byte[bufferSize];
                    inLine.start();
                    while (true) {
                        //inLine.flush();
                        inLine.read(data, 0, data.length);
                        for (int i=0; i<muestras.length-bufferSize; i++){
                            muestras[i] = muestras[i+bufferSize];
                        }
                        for (int i = 0; i < bufferSize; i++) {
                            //System.out.print(data[i]+ " ");
                            muestras[muestras.length-bufferSize+i] = data[i];
                            //muestras[offset++] = data[i];
                            //if (offset>=muestras.length)
                            //    offset = 0;
                        }
                        //System.out.println();
                        //System.out.println();
                    }
                }
            });
        threadInMic.start();*/

        Thread threadGraph = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {

                    double[] max = new double[]{Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE};
                    double[] maxIndex = new double[]{0,0,0};
                    double[] res;
                    synchronized (muestras) {
                        res = DFT.GetMagnitudes(muestras,w);
                    }
                    tempSeries.clear();
                    /*for (int i = 0; i < muestras.length; i++) {
                        tempSeries.add(i, muestras[i]);
                    }*/
                    for (int i = 50; i < muestras.length/2; i++) {
                        tempSeries.add(i, res[i]);
                        if (res[i]> max[0]) {
                            max[2] = max[1];
                            max[1] = max[0];
                            max[0] = res[i];
                            maxIndex[2] = maxIndex[1];
                            maxIndex[1] = maxIndex[0];
                            maxIndex[0] = i;
                        }
                    }
                    //chart.setNo();
                    //chart.fireChartChanged();
                    chart.setNotify(true);
                    chart.fireChartChanged();
                    chart.setNotify(false);
                    System.out.println("Max: "+maxIndex[0]+" - "+max[0]);
                    double note = 45 + 12*Math.log(maxIndex[0]/440)/Math.log(2);
                    System.out.println(Math.log(maxIndex[0]/440) +" || "+Math.log(2));
                    int n = Math.max((int)note,0);
                    if (max[0]> 20000) {
                        System.out.println("Note: "+keyNoteCode[n%12] + (n/12)+"\t"+note);
                        lblNote.setText(keyNoteCode[n%12] + (n/12));
                    } else {
                        lblNote.setText("...");
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SoundGraph.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });
        threadGraph.start();

    }

    /**
     * This method is called from within the constructor to initializeSerial the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chartPanel = new javax.swing.JPanel();
        lblNote = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        chartPanel.setLayout(new java.awt.BorderLayout());

        lblNote.setFont(new java.awt.Font("Dialog", 0, 80)); // NOI18N
        lblNote.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNote.setText("C4");

        jLabel2.setText("Nota:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNote, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNote, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SoundGraph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SoundGraph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SoundGraph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SoundGraph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SoundGraph().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chartPanel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblNote;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("----"+ e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            tempSeries.clear();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
