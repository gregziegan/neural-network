package Parsing.data;

import Parsing.util.Sort;
import Parsing.util.Type;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

/**
 * This class define a data set, methods to create, query, and modify data data is provided.
 * @author Feng
 *
 */
public class DataSet {
    /** meta information: include attributes and class index */
    private String m_relationName;
    private HashMap<String, Integer> m_attrNameMap;
    private Vector<Attribute> m_attributes;  // note that class is also included in the attributes
    private int m_classIndex;

    /** data */
    private Vector<Instance> m_instances;

    /**
     * Constructor: create an empty data set with given meta information
     * @param relationName
     * @param attrNameMap
     * @param attributes
     * @param classIndex
     */
    public DataSet(String relationName, HashMap<String, Integer> attrNameMap, Vector<Attribute> attributes, int classIndex)
    {
        this(relationName, attrNameMap, attributes, classIndex, new Vector<Instance>());
    }

    /**
     * Constructor: create data set with given meta info and data
     * @param attributes
     * @param classIndex
     * @param instances
     */
    public DataSet(Vector<Attribute> attributes, int classIndex, Vector<Instance> instances)
    {
        this(null, null, attributes, classIndex, instances);
    }

    /**
     * Constructor: create an empty data set with given meta info
     * @param attributes
     * @param classIndex
     */
    public DataSet(Vector<Attribute> attributes, int classIndex) {
        this(null, null, attributes, classIndex, new Vector<Instance>());
    }

    /**
     * Constructor: create an empty data set with the meta information provided by argument dataSet
     * @param dataSet
     */
    public DataSet(DataSet dataSet) {
        this(dataSet.m_relationName, dataSet.m_attrNameMap, dataSet.m_attributes, dataSet.m_classIndex, new Vector<Instance>());
    }


    /**
     * Constructor: private, create data set with given meta info and data
     * @param relationName
     * @param attrNameMap
     * @param attributes
     * @param classIndex
     * @param instances
     */
    private DataSet(String relationName, HashMap<String, Integer> attrNameMap, Vector<Attribute> attributes, int classIndex, Vector<Instance> instances) {
        m_relationName = relationName;
        m_attrNameMap = attrNameMap;
        m_attributes = attributes;
        m_classIndex = classIndex;
        m_instances = instances;
    }


    /**
     * add an instance into data set
     * Note that this method doesn't check the consistency between new data and old data
     * @param instance
     * @return size of the data set
     */
    public int add(Instance instance) {
        m_instances.add(instance);
        instance.setDataSet(this);
        return size();
    }

    /**
     * add all instances of the given data set into data set
     * Note that this method doesn't check the consistency between new data and old data
     * @param dataSet
     * @return size of the data set
     */
    public int add(DataSet dataSet) {
        for(int i=0; i<dataSet.size(); i++) {
            m_instances.add(dataSet.instance(i));
            dataSet.instance(i).setDataSet(this);
        }
        return size();
    }

    /**
     * add given instances into data set
     * Note that this method doesn't check the consistency between new data and old data
     * @param instances
     * @return size of the data set
     */
    public int add(Vector<Instance> instances) {
        for(int i=0; i<instances.size(); i++) {
            m_instances.add(instances.get(i));
            instances.get(i).setDataSet(this);
        }
        return size();
    }

    /**
     * add given instances into data set
     * Note that this method doesn't check the consistency between new data and old data
     * @param instances
     * @return size of the data set
     */
    public int add(Instance[] instances) {
        for(int i=0; i<instances.length; i++) {
            m_instances.add(instances[i]);
            instances[i].setDataSet(this);
        }
        return size();
    }

    /**
     * clear the data set, retain only the meta information
     */
    public void clear() {
        m_instances.clear();
    }

    /**
     * remove the given instance in the data set
     * return true if given instance is in the data set and is removed successfully.
     * @param instance
     * @return
     */
    public boolean remove(Instance instance) {
        return m_instances.remove(instance);
    }

    /**
     * remove the instance in given position
     * @param index
     * @return size of the data base
     */
    public int remove(int index) {
        m_instances.remove(index);
        return size();
    }

    /**
     * get number of instances in the data set
     * @return
     */
    public int size()
    {
        return m_instances.size();
    }

    /**
     * get class index
     * @return
     */
    public int classIndex() {
        return m_classIndex;
    }

    /**
     * set class index
     * @param classIndex
     */
    public void setClassIndex(int classIndex) {
        m_classIndex = classIndex;
    }

    /**
     * set class index given class name
     * @param className
     * @return class index
     */
    public int setClassIndex(String className) {
        for(int i=0; i<m_attributes.size(); i++) {
            if(m_attributes.get(i).name().equals(className)) {
                m_classIndex = i;
                return m_classIndex;
            }
        }
        throw new DataException("Set class index fail. No attribute with name: " + className);
    }

    /**
     * get number of attributes
     * @return
     */
    public int numAttributes()
    {
        return m_attributes.size();
    }


    /**
     * get an instance from data set given its index in the data set
     * @param index
     * @return
     */
    public Instance instance(int index)
    {
        return m_instances.get(index);
    }

    /**
     * if attr is nominal, return corresponding attribute value.
     * if attr is continuous, return corresponding string of the double value.
     * @param attrIndex
     * @param value
     * @return
     */
    public String valueToString(int attrIndex, double value) {
        if(m_attributes.get(attrIndex).type()==Attribute.NOMINAL)
            return m_attributes.get(attrIndex).value((int)value);
        else
            return Type.doubleToString(value, 4);
    }

    /**
     * get value (in double format) of an attribute of an instance
     * @param instanceIndex
     * @param attrIndex
     * @return
     */
    public double value(int instanceIndex, int attrIndex)
    {
        return m_instances.get(instanceIndex).value(attrIndex);
    }

    /**
     * set value (in double format) of an attribute of an instance
     * @param instanceIndex
     * @param attrIndex
     * @param value
     */
    public void setValue(int instanceIndex, int attrIndex, double value)
    {
        m_instances.get(instanceIndex).setValue(attrIndex, value);
    }

    /**
     * get class value (in double format) of an instance
     * @param instanceIndex
     * @return
     */
    public double classValue(int instanceIndex)
    {
        return m_instances.get(instanceIndex).value(m_classIndex);
    }

    /**
     * get attribute
     * @param index
     * @return
     */
    public Attribute attribute(int index)
    {
        return m_attributes.get(index);
    }

    /**
     * get class attribute
     * @return
     */
    public Attribute classAttribute()
    {
        return m_attributes.get(m_classIndex);
    }

    /**
     * compute mean value of an attribute
     * @param attrIndex
     * @return
     */
    public double mean(int attrIndex) {
        if(size()==0)
            return 0;
        double mean = 0;
        for(int i=0; i<size(); i++) {
            mean += value(i, attrIndex);
        }
        mean /= size();
        return mean;
    }

    /**
     * compute variance of an attribute
     * @param attrIndex
     * @return
     */
    public double variance(int attrIndex) {
        if(size()<=1)
            return 0;
        double mean = mean(attrIndex);
        double sumSquared = 0;
        for(int i=0; i<size(); i++)
            sumSquared += (value(i, attrIndex)-mean)*(value(i, attrIndex)-mean);
        return sumSquared /= (size()-1);
    }

    /**
     * get standard deviation of an attribute
     * @param attrIndex
     * @return
     */
    public double stdDeviation(int attrIndex) {
        return Math.sqrt(variance(attrIndex));
    }

    /**
     * get the index of the attribute with given name
     * return -1 if no attribute with such name
     * @param name
     * @return
     */
    public int attrIndex(String name) {
        if(m_attrNameMap==null)
            return -1;
        Integer index = m_attrNameMap.get(name);
        return index==null ? -1 : index.intValue();
    }

    /**
     * sort the instances according to the value of an attribute into ascending numerical order.
     * @param attrIndex
     */
    public void sort(int attrIndex) {
        int size = m_instances.size();
        if(size<=1) return;
        Sort.sortDataSet(m_instances, attrIndex);
    }

    /**
     * get relation name
     * @return
     */
    public String relation() {
        return m_relationName;
    }

    /**
     * transform the data set to 2-D array
     * @param dataSet
     * @return
     */
    public static double[][] toArray(DataSet dataSet) {
        boolean[] selected = new boolean[dataSet.numAttributes()];
        Arrays.fill(selected, true);
        return toArray(dataSet, selected);
    }

    /**
     * transform one column of the data set to 2-D array (actually N*1)
     * @param dataSet
     * @param index
     * @return
     */
    public static double[][] toArray(DataSet dataSet, int index) {
        boolean[] selected = new boolean[dataSet.numAttributes()];
        Arrays.fill(selected, false);
        selected[index] = true;
        return toArray(dataSet, selected);
    }

    /**
     * transform the selected attributes of the data set to 2-D array.
     * @param dataSet
     * @param selected
     * @return
     */
    public static double[][] toArray(DataSet dataSet, boolean[] selected) {
        int numAttr = 0;
        for (int i = 0; i < selected.length; i++) {
            if(selected[i]==true) numAttr++;
        }
        int[] indices = new int[numAttr];
        int a = 0;
        for (int i = 0; i < selected.length; i++) {
            if(selected[i]==true) {
                indices[a]=i;
                a++;
            }
        }
        int size = dataSet.size();
        double[][] matrix = new double[size][numAttr];
        for(int row=0; row<size; row++) {
            Instance tempInstance = dataSet.instance(row);
            for(int col=0; col<numAttr; col++) {
                matrix[row][col] = tempInstance.value(indices[col]);
            }
        }
        return matrix;
    }

    /**
     * return a string containing the meta info of the data set
     * @return
     */
    public String metaInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("Relation: \t" + m_relationName + "\n");
        sb.append("=========\n");
        for(int i=0; i<numAttributes(); i++) {
            sb.append("Attribute " + i + ": \t" + attribute(i).toString() + "\n");
        }
        if(m_classIndex>=0) {
            sb.append("=========\n");
            sb.append("Class: " + m_classIndex + "\t" + m_attributes.get(m_classIndex).toString()+"\n");
        }
        return sb.toString();
    }

}
