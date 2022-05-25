/*******************************************************************************
 * 
 * Copyright © 2022 Brian_Entei (br45entei@gmail.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 *******************************************************************************/
package com.gmail.br45entei.math;

import java.math.BigDecimal;
import java.util.Arrays;

/** A class which implements <a href="https://www.youtube.com/watch?v=p-HN_ICaCyM">Somos Sequences</a>.
 *
 * @author Brian_Entei &lt;br45entei&#064;gmail.com&gt; */
public class SomosSequences {
	
	/** Calculates the current index and stores it within the provided array using the Somos-4 algorithm.<br>
	 * (This is provided as a reference when checking the more general {@linkplain #somos(int, int, double[]) somos(s, n, array)} function.)
	 *
	 * @param n The current index within the array
	 * @param array The array in which all results are stored
	 * @return The current fraction which yielded the result within the array at the index <tt>n</tt> */
	public static final double[] somos4(int n, double[] array) {
		double a1 = array[n - 1];
		double a2 = array[n - 2];
		double a3 = array[n - 3];
		double a4 = array[n - 4];
		
		double dividend = (a1 * a3) + (a2 * a2);
		double divisor = a4;
		array[n] = dividend / divisor;
		return new double[] {dividend, divisor};
	}
	
	/** Calculates the current index and stores it within the provided array using the Somos-<tt><em>s</em></tt> algorithm.
	 *
	 * @param s The Somos algorithm number
	 * @param n The current index within the array
	 * @param array The array in which all results are stored
	 * @return The current fraction which yielded the result within the array at the index <tt>n</tt> */
	public static final double[] somos(int s, int n, double[] array) {
		// Set the first 's' array values to 1: (uncomment if needed)
		//Arrays.fill(array, 0, s, 1.0);
		
		double dividend = 0x0.0p0;
		for(int i = 1; i <= s / 2; i++) {
			dividend += array[n - i] * array[n - (s - i)];
		}
		double divisor = array[n - s];
		
		array[n] = dividend / divisor;
		return new double[] {dividend, divisor};
	}
	
	/** Returns <tt>true</tt> if the specified double is infinite or NaN (not-a-number).
	 *
	 * @param d The double to check
	 * @return Whether or not the specified double is {@linkplain Double#isInfinite() Infinite} or {@linkplain Double#NaN NaN} */
	public static final boolean infiniteOrNaN(double d) {
		return d != d || Double.isInfinite(d);
	}
	
	/** Converts and returns the specified double into a String, using {@linkplain BigDecimal}'s {@linkplain BigDecimal#toPlainString()
	 * toPlainString()} method when possible.<br>
	 * If the specified double is {@linkplain #infiniteOrNaN(double) infinite or NaN}, then {@linkplain Double#toString()} is used instead.
	 *
	 * @param d The double to convert
	 * @return The specified double's exact String representation */
	public static final String doubleToString(double d) {
		return infiniteOrNaN(d) ? Double.toString(d) : new BigDecimal(d).toPlainString();
	}
	
	/** @param args Program command line arguments (unused) */
	public static final void main(String[] args) {
		final int iterations = 65536;// (Or however many iterations you'd like)
		
		for(int s = 1; s < 31; s++) {
			
			double[] array = new double[iterations];
			// Initialize the array with the number 1 's' times:
			Arrays.fill(array, 0, s, 1.0);
			
			// Uncomment this for loop if you're interested in the leading '1' digits:
			/*for(int n = 0; n < s; n++) {
				System.out.println(doubleToString(array[n]));
			}*/
			
			// Let's iterate through a few Somos algorithms:
			System.out.println("Somos-".concat(Integer.toString(s)));
			double[] fraction, lastFraction = null;
			for(int n = s; n < array.length; n++) {
				fraction = somos(s, n, array);
				boolean infiniteOrNaN = infiniteOrNaN(array[n]);
				String dividend = doubleToString(fraction[0]);
				String divisor = doubleToString(fraction[1]);
				String result = doubleToString(array[n]);
				
				System.out.println("\t".concat(dividend).concat(" / ").concat(divisor).concat(" = ").concat(result));
				
				// If we encounter an infinity or NaN, we can't really continue the computation using normal double primitives, so we should abort:
				if(infiniteOrNaN) {
					break;
				}
				// Alternately, we could re-implement the algorithm using Java's BigDecimal class instead of the double primitive type ...
				// I have also noticed that it would seem as a general rule, from Somos-8 and onwards, after 's' iterations, the next iteration will likely result in a decimal value ...
				
				// If the last fraction is the same as the current fraction, odds are the next one will be as well (e.g. Somos-2 and Somos-3):
				if(lastFraction != null && Arrays.equals(fraction, lastFraction)) {
					break;
				}
				lastFraction = fraction;
			}
		}
	}
	
}
