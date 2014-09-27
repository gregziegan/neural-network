package Parsing.data;

/**
 * This class defines the data instance
 * @author Feng
 *
 */
public class Instance {
	
	private double[] m_values;
	private double m_weight;
	private DataSet m_dataSet;
	
	/**
	 * Constructor: create a copy of given instance
	 * @param instance
	 */
	public Instance(Instance instance)
	{
		this(instance.m_values.clone(), instance.m_weight, instance.m_dataSet);
	}
	
	/**
	 * Constructor: create an instance with given values, with default weight 1.0 
	 * @param values
	 */
	public Instance(double[] values)
	{
		this(values, 1.0, null);
	}
	
	/**
	 * Constructor: create an instance with given values and given weight
	 * @param values
	 * @param weight
	 */
	public Instance(double[] values, double weight)
	{
		this(values, weight, null);
	}
	
	/**
	 * Constructor: private, create an instance with given info
	 * @param values
	 * @param weight
	 * @param dataSet
	 */
	private Instance(double[] values, double weight, DataSet dataSet) {
		m_values = values;
		m_weight = weight;
		m_dataSet = dataSet;
	}
	
	/**
	 * Assign a data set to an instance
	 * @param dataSet
	 */
	public void setDataSet(DataSet dataSet) {
		m_dataSet = dataSet;
	}
	
	/**
	 * gets data set assigned to an instance
	 * @return
	 */
	public DataSet dataSet() {
		return m_dataSet;
	}
	
	/**
	 * Gets a clone of an instance
	 */
	public Instance clone()
	{
		return new Instance(this);
	}
	
	/**
	 * Gets the number of attributes in the instance
	 * @return
	 */
	public int numAttributes() {
		return m_values.length;
	}
	
	/**
	 * Gets the number of attributes in the instance. 
	 * Same as numAttributes
	 * @return
	 */
	public int length() {
		return m_values.length;
	}
	
	/**
	 * Gets the weight of an instance
	 * @return
	 */
	public double weight()
	{
		return m_weight;
	}
	
	/**
	 * Sets the weight of an instance
	 * @param weight
	 */
	public void setWeight(double weight)
	{
		this.m_weight = weight;
	}
	
	/**
	 * Gets the attribute value
	 * @param index
	 * @return
	 */
	public double value(int index)
	{
		return m_values[index];
	}
	
	/**
	 * Sets the value for an attribute
	 * @param index
	 * @param value
	 */
	public void setValue(int index, double value)
	{
		if(index<0 || index>=m_values.length) 
			throw new DataException("Index out of bound: " + index + "\tActual length of instance: " + m_values.length);
			
		m_values[index] = value;
	}
	
	/**
	 * gets class value for an instance
	 * @return
	 */
	public double classValue()
	{
		if(m_dataSet == null) 
			throw new DataException("Can't get class value, because instance doesn't have access to a dataset!");
		
		return m_values[m_dataSet.classIndex()];
	}
	
	/**
	 * Sets the class value for an instance
	 * @param classValue
	 */
	public void setClassValue(double classValue)
	{
		if(m_dataSet == null) 
			throw new DataException("Can't set class value, because instance doesn't have access to a dataset!");
		
		m_values[m_dataSet.classIndex()] = classValue;
	}
	
	/**
	 * Gets the calss index of an instance if set.
	 * @return
	 */
	public int classIndex() {
		if(m_dataSet == null) 
			throw new DataException("Instance doesn't have access to a dataset!");
		
		return m_dataSet.classIndex();
	}
	
	/**
	 * sample output:
	 * 1, 2.1, 1.5 {0.8}
	 * 1, 2.1, 1.5    // if weight==1.0
	 * 1, sunny, good   // if second and third attributes are nominal
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
	    
	    for (int i = 0; i < m_values.length; i++) {
	      if (i > 0) sb.append(", ");
	      if(m_dataSet==null)
	    	  sb.append(m_values[i]);
	      else {
	    	  sb.append(m_dataSet.valueToString(i, m_values[i]));
	      }
	    }
	    if (m_weight != 1.0) {
	      sb.append(",{" + m_weight + "}");
	    }
	
		return sb.toString();
	}
	
	
	/**
	 * sample output:
	 * 1, 2.1, 1.5
	 * @return
	 */
	public String valueToString() {
		StringBuffer sb = new StringBuffer();
	    
	    for (int i = 0; i < m_values.length; i++) {
	      if (i > 0) 
	    	  sb.append(", ");
	      sb.append(m_values[i]);
	    }
		return sb.toString();
	}
	
	/**
	 * if all the values are equal, then return true.
	 * no matter what the values are for m_weight and m_dataSet.
	 */
	public boolean equals(Object object) {
		Instance instance = (Instance)object;
		if(m_values.length==instance.m_values.length) {
			for(int i=0; i<m_values.length; i++)
				if(m_values[i]!=instance.m_values[i])
					return false;
			return true;
		}
		return false;
	}
}
