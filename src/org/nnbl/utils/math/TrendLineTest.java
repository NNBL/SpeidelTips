package org.nnbl.utils.math;

import java.util.Random;

public class TrendLineTest {
	public static void main(String[] args) {
	    TrendLine t = new PolyTrendLine(2);
	    Random rand = new Random();
	    double[] x = new double[1000*1000];
	    double[] err = new double[x.length];
	    double[] y = new double[x.length];
	    for (int i=0; i<x.length; i++) { x[i] = 1000*rand.nextDouble(); }
	    for (int i=0; i<x.length; i++) { err[i] = 100*rand.nextGaussian(); } 
	    for (int i=0; i<x.length; i++) { y[i] = x[i]*x[i]+err[i]; } // quadratic model
	    t.setValues(y,x);
	    System.out.println(t.predict(12)); // when x=12, y should be... , eg 143.61380202745192
	}
}
