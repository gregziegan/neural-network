package Parsing.util;

import Parsing.data.Instance;

import java.util.Vector;

/**
 * This class implement sorting the data in DataSet
 * @author Feng
 *
 */
public class Sort {
	public static void sortDataSet(Vector<Instance> allInstances, int index) {
		int size = allInstances.size();
		if(size<=1 || index>=allInstances.get(0).numAttributes()) 
			return;
		sortDataSet1(allInstances, 0, size, index);
	}
	
	private static void sortDataSet1(Vector<Instance> x, int off, int len, int index) {
		// Insertion sort on smallest arrays
		if(len<7) {
			for(int i=off; i<len+off; i++) {
				for(int j=i; j>off && x.get(j-1).value(index)>x.get(j).value(index); j--) {
					swap(x, j, j-1);
				}
			}
			return ;
		}
		
		// Choose a partition element, v
		int m = off + (len>>1);
		if(len>7) {
			int l = off;
			int n = off + len -1;
			if(len>40) { // Big arrays, pseudomedian of 9
				int s = len/8;
				l = med3(x, l, l+s, l+2*s, index);
				m = med3(x, m-s, m, m+s, index);
				n = med3(x, n-2*s, n-s, n, index);
			}
			m = med3(x, l, m, n, index);
		}
		Instance v = x.get(m);
		double vValue = v.value(index);
		
		// Establish Invariant: v*(<v)*(>v)*v*
		int a=off, b=a, c=off+len-1, d=c;
		while(true) {
			while(b<=c && x.get(b).value(index)<vValue) {
				if(x.get(b).value(index)==vValue) 
					swap(x, a++, b);
				b++;
			}
			while(c>=b && x.get(c).value(index)>=vValue) {
				if(x.get(c).value(index)==vValue)
					swap(x, c, d--);
				c--;
			}
			if(b>c) 
				break;
			swap(x, b++, c--);
		}
		
		// Swap partition elements back to middle
		int s, n=off+len;
		s = Math.min(a-off, b-a); 
		vecswap(x, off, b-s, s);
		s = Math.min(d-c, n-d-1);
		vecswap(x, b, n-s, s);
		
		// Recursively sort non-partition-elements
		if((s=b-a)>1)
			sortDataSet1(x, off, s, index);
		if((s=d-c)>1)
			sortDataSet1(x, n-s, s, index);
		
	}
	
	/**
	 * swap x.get(a) with x.get(b)
	 * @param x
	 */
	private static void swap(Vector<Instance> x, int a, int b) {
		Instance t = x.get(a);
		x.set(a, x.get(b));
		x.set(b, t);
	}
	
	/**
	 * returns the index of the midian of the three indexed instances.
	 * @param x
	 * @param a
	 * @param b
	 * @param c
	 * @param index
	 * @return
	 */
	private static int med3(Vector<Instance> x, int a, int b, int c, int index) {
		double xa = x.get(a).value(index);
		double xb = x.get(b).value(index);
		double xc = x.get(c).value(index);
		
		return (xa<xb ? 
			(xb<xc ? b : xa<xc ? c : a) : 
			(xb>xc ? b : xa>xc ? c : a));
	}
	
	private static void vecswap(Vector<Instance> x, int a, int b, int n) {
		for(int i=0; i<n; i++, a++, b++)
			swap(x, a, b);
	}
}















