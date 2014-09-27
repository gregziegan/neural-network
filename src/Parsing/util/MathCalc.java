package Parsing.util;

/**
 * static methods for some simple mathematical computing.
 * you can add your methods here 
 * @author Feng
 *
 */
public class MathCalc {
	
	/**
	 * compute the information entropy given p
	 * @param p
	 * @return
	 */
	public static double pLog2p(double p)
	{
		if(p==0)
			return 0;
		return p*Math.log10(p)/Math.log10(2);
	}
	
	/**
	 * compute the probability for univariate gaussian distribution
	 * @param x
	 * @param mu
	 * @param variance
	 * @return
	 */
	public static double gaussianProb(double x,double mu, double variance)
	{
		double p;
		p = 1/Math.sqrt(2*Math.PI*variance);
		p *= Math.exp(-(x-mu)*(x-mu)/(2*variance));
		return p;
	}
	
	
	/**
	 * compute the Euclidean norm of given vector
	 * @param original
	 * @return
	 */
	public static double norm(double[] original) {
		double norm = 0;
		for(int i=0; i<original.length; i++)
			 norm += original[i]*original[i];
		norm = Math.sqrt(norm);
		return norm;
	}
	
	/**
	 * compute the cross product of two vectors
	 * @param x
	 * @param y
	 * @return cross product of x and y
	 */
	public static double crossProduct(double[] x, double[] y) {
		if(x.length != y.length) {
			System.err.println("the dimension of two vectors doesn't match.");
			return 0;
		}
		double product = 0;
		for(int i=0; i<x.length; i++) {
			product += x[i]*y[i];
		}
		return product;
	}
}


