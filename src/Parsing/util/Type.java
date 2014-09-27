package Parsing.util;

import java.util.regex.Pattern;

/**
 * This class provides some static methods for checking and converting between different types.
 * @author Feng
 *
 */
public class Type {

	/**
	 * This is used to check if the input string corresponds to an integer
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str)
	{
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");   
		return pattern.matcher(str).matches(); 
	}

	/**
	 * This is used to check if the input string corresponds to a double
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {  
		Pattern pattern = Pattern.compile("^[-+]?(\\d+[.]\\d*?|[.]\\d+?|\\d+?)([eE][-+]?\\d+)?$"); 
		return pattern.matcher(str).matches();   
	}
	
	/**
	 * convert a string to integer
	 * @param str
	 * @return
	 */
	public static int strToInt(String str)
	{
		return Integer.parseInt(str);
	}
	/**
	 * convert a string to double
	 * @param str
	 * @return
	 */
	public static double strToDouble(String str)
	{
		return Double.parseDouble(str);
	}
	
	
	/**
	 * Note: this method is from Weka
	 * Rounds a double and converts it into String.
	 *
	 * @param value the double value
	 * @param afterDecimalPoint the (maximum) number of digits permitted
	 * after the decimal point
	 * @return the double as a formatted string
	 */
	public static /*@pure@*/ String doubleToString(double value, int afterDecimalPoint) {

		StringBuffer stringBuffer;
		double temp;
		int dotPosition;
		long precisionValue;

		temp = value * Math.pow(10.0, afterDecimalPoint);
		if (Math.abs(temp) < Long.MAX_VALUE) {
			precisionValue = 	(temp > 0) ? (long)(temp + 0.5) 
					: -(long)(Math.abs(temp) + 0.5);
			if (precisionValue == 0) {
				stringBuffer = new StringBuffer(String.valueOf(0));
			} else {
				stringBuffer = new StringBuffer(String.valueOf(precisionValue));
			}
			if (afterDecimalPoint == 0) {
				return stringBuffer.toString();
			}
			dotPosition = stringBuffer.length() - afterDecimalPoint;
			while (((precisionValue < 0) && (dotPosition < 1)) ||
					(dotPosition < 0)) {
				if (precisionValue < 0) {
					stringBuffer.insert(1, '0');
				} else {
					stringBuffer.insert(0, '0');
				}
				dotPosition++;
			}
			stringBuffer.insert(dotPosition, '.');
			if ((precisionValue < 0) && (stringBuffer.charAt(1) == '.')) {
				stringBuffer.insert(1, '0');
			} else if (stringBuffer.charAt(0) == '.') {
				stringBuffer.insert(0, '0');
			}
			int currentPos = stringBuffer.length() - 1;
			while ((currentPos > dotPosition) &&
					(stringBuffer.charAt(currentPos) == '0')) {
				stringBuffer.setCharAt(currentPos--, ' ');
			}
			if (stringBuffer.charAt(currentPos) == '.') {
				stringBuffer.setCharAt(currentPos, ' ');
			}

			return stringBuffer.toString().trim();
		}
		return new String("" + value);
	}
}
