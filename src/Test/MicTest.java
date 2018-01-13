/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;

import java.io.ByteArrayOutputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Raul Vera <twyblade64@hotmail.com>
 */
public class MicTest implements Runnable{
    TargetDataLine line;

    public MicTest() {

        AudioFormat format = new AudioFormat(8000.0f, 8, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class,
            format); // format is an AudioFormat object
        if (!AudioSystem.isLineSupported(info)) {
            // Handle the error ...

        }
        // Obtain and open the line.
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            Thread thread = new Thread(this);
            thread.start();
        } catch (LineUnavailableException ex) {
            // Handle the error ...
        }
    }

    public static void main(String[] args) {
        MicTest micTest = new MicTest();
    }

    @Override
    public void run() {
        AudioFormat format = new AudioFormat(8000.0f, 8, 1, true, true);
        SourceDataLine sdl;
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            //sdl.open();

            ByteArrayOutputStream out  = new ByteArrayOutputStream();

            int numBytesRead;
            byte[] data = new byte[line.getBufferSize() / 5];

            line.start();
            sdl.start();
            while (true) {
                // Read the next chunk of data from the TargetDataLine.
                numBytesRead =  line.read(data, 0, data.length);
                // Save this chunk of data.
                out.write(data, 0, numBytesRead);
                for(byte b : data){
                    System.out.print(b+" ");
                }
                System.out.println();
                sdl.write(data, 0, numBytesRead);


             }
        }
        catch (LineUnavailableException ex) {}
    }
}
