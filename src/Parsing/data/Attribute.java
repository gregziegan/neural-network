package Parsing.data;

import java.util.Arrays;

import Parsing.util.Type;

/**
 * This class defines the attribute type for data
 * support two types: nominal and continuous 
 * @author Feng
 *
 */
public class Attribute {
	public static int COUNT = 0;
	
	public static final int NOMINAL = 0;
	public static final int CONTINUOUS = 1;
	
	public static final String[] TYPE;
	static{
		TYPE = new String[2];
		TYPE[NOMINAL] = "Nominal";
		TYPE[CONTINUOUS] = "Continuous";
	}
	
	
	private String m_name;
	private int m_type;			// type of attribute: NOMINAL | CONTINUOUS
	private String[] m_values;  	// value set of attribute if it is NOMINAL
	
	/**
	 * Constructor
	 * @param type
	 */
	public Attribute(int type) {
		this("Attr" + (++COUNT), type, new String[0]);
	}
	
	/**
	 * Constructor: for continuous attributes
	 * @param name
	 * @param type
	 */
	public Attribute(String name, int type)
	{
		this(name, type, new String[0]);
	}
	
	/**
	 * Constructor: for nominal attributes
	 * @param name
	 * @param type
	 * @param values
	 */
	public Attribute(String name, int type, String[] values)
	{
		this.m_name = name;
		this.m_type = type;
		m_values = values.clone();
		Arrays.sort(m_values);
	}
	
	/**
	 * get type of the attribute: NOMINAL | CONTINUOUS
	 * @return
	 */
	public int type()
	{
		return m_type;
	}
	
	/**
	 * get name string of the attribute
	 * @return
	 */
	public String name() {
		return m_name;
	}
	
	/**
	 * get number of values of the attribute
	 * return 0 if it's continuous attribute
	 * @return
	 */
	public int size()
	{
		return m_values.length;
	}
	
	/**
	 * whether the value is valid or not
	 * @param value
	 * @return
	 */
	public boolean contains(String value){
		if(m_type == CONTINUOUS)
			return Type.isDouble(value);
		else if(m_type == NOMINAL){
			return Arrays.binarySearch(m_values, value)>=0;
				
		}
		return false;
	}
	
	/**
	 * return the index of the value, if doesn't exist or it's double type, return -1
	 * @param value
	 * @return
	 */
	public int index(String value) {
		if(m_type==CONTINUOUS)
			return -1;
		else if(m_type==NOMINAL) {
			return Arrays.binarySearch(m_values, value);
		}
		return -1;
	}
	
	/**
	 * return value string according to index, if doesn't exist or it's double, return null
	 * @param index
	 * @return
	 */
	public String value(int index) {
		if(m_type==CONTINUOUS)
			return null;
		else if(m_type==NOMINAL)
			return m_values[index];
		return null;
	}
	
	/**
	 * override the toString method
	 * print the name and type of the attribute
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(m_name + "\t" + (m_name.length()>=8?"":"\t") + TYPE[m_type]);
		if(m_type == NOMINAL) {	
			sb.append("\t\t");
			int length = m_values.length;
			if(length>10) {
				sb.append(length + " values, only show first 10: ");
				length = 10;
			}
			for (int i=0; i<length; i++) {
				if(i>0)
					sb.append(",");
				sb.append(m_values[i]);
			}
		}
		return sb.toString();
	}
	
	/**
	 * two attributes are equal if their names are the same
	 */
	public boolean equals(Object object) {
		Attribute att = (Attribute)object;
		return m_name.equals(att.name());
	}
}

	