/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TermGraph;

/**
 *
 * @author Raul Vera <twyblade64@hotmail.com>
 */
public class DFT {
    public static Vector2D[] getWVectors(int n) {
        Vector2D[] w = new Vector2D[n];
        if (n > 0) {
            double ang = Math.PI*2 / n;
            w[0] = new Vector2D(1,0);
            if (n > 1) {
                w[1] = new Vector2D(Math.cos(ang), -Math.sin(ang));
                for (int i = 2; i<(n/2+1); i++)
                    w[i] = w[i-1].Clone().Rotate(w[1]);
                for (int i = (n/2+1); i < n; i++){
                    w[i] = w[2*(n/2)-i+(n%2)].Clone();
                    w[i].m_y = -w[i].m_y;
                }
            }
        }
        return w;
    }


    public static double[] GetMagnitudes(double[] signal) {
        int n = signal.length;
        double[] mag = new double[n];
        Vector2D[] f = new Vector2D[n];
        Vector2D[] w = getWVectors(n);
        //for (int i = 0; i<(n/2+1); i++) {
        for (int i = 0; i<n; i++) {
            f[i] = new Vector2D();
            for (int j = 0; j < n; j++) {
                f[i].m_x += w[(j*i)%n].m_x * signal[j];
                f[i].m_y += w[(j*i)%n].m_y * signal[j];
            }
        }
        for (int i = 0; i < n; i++) {
            mag[i] = f[i].getLength();
        }
        //for (int i = 0; i<n; i++) {
        //    System.out.println(""+i+": "+f[i].toString()+" "+mag[i]);
        //}

        return mag;
    }

    public static double[] GetMagnitudes(double[] signal, Vector2D[] w) {
        int n = signal.length;
        double[] mag = new double[n];
        Vector2D[] f = new Vector2D[n];
        //for (int i = 0; i<(n/2+1); i++) {
        for (int i = 0; i<n; i++) {
            f[i] = new Vector2D();
            for (int j = 0; j < n; j++) {
                f[i].m_x += w[(j*i)%n].m_x * signal[j];
                f[i].m_y += w[(j*i)%n].m_y * signal[j];
            }
        }
        for (int i = 0; i < n; i++) {
            mag[i] = f[i].getLength();
        }
        //for (int i = 0; i<n; i++) {
        //    System.out.println(""+i+": "+f[i].toString()+" "+mag[i]);
        //}

        return mag;
    }

    public static void main(String[] args) {
        double[] in = new double[9990];
        for (int i = 0; i < in.length; i++)
            in[i] = i;
        long  timePass, timeLeft, timeStart;
            timeStart = System.nanoTime();
            Vector2D[] w = getWVectors(in.length);
            for (int i = 0; i <1; i++) {
                System.out.println(i);
                double[] res = DFT.GetMagnitudes(in,w);
            }
            timePass = System.nanoTime() - timeStart;
            System.out.println(timePass/1000/100);
    }

}
