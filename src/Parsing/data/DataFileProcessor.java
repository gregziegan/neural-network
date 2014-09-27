package Parsing.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import Parsing.util.Type;

/**
 * This class provides some static methods for reading in .names and .data files, store them i DataSet class.
 * Doesn't support missing value.
 * File format:
 * 	.names file: first line should be class, and following lines define attributes.
 * 	.data file:  each line is an instance. the class attribute is put in the end of each instance.
 * @author Feng
 *
 */
public class DataFileProcessor {
	
	/** The filename extension for data specification file */
	public final static String DATA_SPEC_FILE_EXTENSION = ".names";
	/** The filename extension for data file */
	public final static String DATA_FILE_EXTENSION = ".data";
	
	/** The indicator of the starter of comments in the .names and .data files */
	public final static String COMMENT_INDICATOR = "//";
	
	public final static Vector<String> CONTINUOUS = new Vector<String>();
	static {
		CONTINUOUS.add("CONTINUOUS");
		CONTINUOUS.add("continuous");
	}
	
	
	/**
	 * read in data meta info from .names file
	 * you just need to provide the name of the data
	 * you don't need to specify the file extension
	 * also, the data is store in the folder as specified in Config class
	 * @param dataSpecFileName: e.g. ab 
	 * @return
	 */
	public static DataSet readInMetaInfo(String dataSpecFileName) {
		String dataSpecFilePath = dataSpecFileName + DATA_SPEC_FILE_EXTENSION;
		HashMap<String, Integer> attrNameMap = new HashMap<String, Integer>();
		Vector<Attribute> attributes = new Vector<Attribute>();
		int classIndex = -1;
		String relationName = dataSpecFileName;
		
		BufferedReader inputBufferedReader = null;
		try {
			inputBufferedReader = new BufferedReader(new FileReader(dataSpecFilePath));
			String lineString;
			StringTokenizer stringTokenizer;
			
			// the first line, should be class
			lineString = nextLine(inputBufferedReader); 
			if(lineString==null)
				throw new DataException("Empty file.");
			stringTokenizer = new StringTokenizer(lineString);
			String[] classValues = new String[stringTokenizer.countTokens()];
			int index=0;
			while(stringTokenizer.hasMoreTokens()) {
				classValues[index]=stringTokenizer.nextToken(); 
				index++;
			}
			
			// other lines, attributes except for class
			while((lineString = nextLine(inputBufferedReader))!=null) {
				stringTokenizer = new StringTokenizer(lineString);
				// this line defines an attribute
				String attrName = stringTokenizer.nextToken();
				String attrType = stringTokenizer.nextToken();
				if(CONTINUOUS.contains(attrType)) { // continuous
					attributes.add(new Attribute(attrName, Attribute.CONTINUOUS));
				} else {  // nominal
					String[] values = new String[stringTokenizer.countTokens()+1];
					values[0] = attrType;
					index = 1;
					while(stringTokenizer.hasMoreTokens()) {
						values[index]=stringTokenizer.nextToken();
						index++;
					}
					attributes.add(new Attribute(attrName, Attribute.NOMINAL, values));
				}
				attrNameMap.put(attrName, attributes.size()-1);
				continue;
			}
			
			attributes.add(new Attribute("Class", Attribute.NOMINAL, classValues));
			attrNameMap.put("Class", attributes.size()-1);
			classIndex = attributes.size()-1;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputBufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new DataSet(relationName, attrNameMap, attributes, classIndex);
	}

	
	
	/**
	 * read in data from file.
	 * read line by line, each line corresponds to an instance.
	 * doesn't do any check.
	 * @param dataFileName
	 * @return
	 */
	public static DataSet readInData(String dataFileName, DataSet dataSet) {
		String dataFilePath = dataFileName + DataFileProcessor.DATA_FILE_EXTENSION;
		BufferedReader inputBufferedReader = null;
		Vector<Instance> instances = new Vector<Instance>();
		try {
			inputBufferedReader = new BufferedReader(new FileReader(dataFilePath));
			String lineString;
			StringTokenizer stringTokenizer;
			while((lineString = nextLine(inputBufferedReader))!=null) {
				stringTokenizer = new StringTokenizer(lineString);
				int dim = stringTokenizer.countTokens();
				double[] values = new double[dim];
				for(int i=0; i<dim; i++) {
					String temp = stringTokenizer.nextToken();
					switch(dataSet.attribute(i).type()) {
					case Attribute.CONTINUOUS:
						values[i] = Type.strToDouble(temp);
						break;
					case Attribute.NOMINAL:
						values[i] = dataSet.attribute(i).index(temp);
						break;
					default:
						break;
					}
				}
				instances.add(new Instance(values));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputBufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		DataSet newDataSet = new DataSet(dataSet);
		newDataSet.add(instances);
		return newDataSet;
	}
	
	
	
	/**
	 * return the next trimmed line without comment.
	 * Note that comment is starting with COMMENT_INDICATOR.
	 * @param inputBufferedReader
	 * @return
	 * @throws IOException
	 */
	private static String nextLine(BufferedReader inputBufferedReader) throws IOException {
		String lineString = null;
		while((lineString = inputBufferedReader.readLine())!=null) {
			lineString = lineString.trim();
			if(lineString.startsWith(COMMENT_INDICATOR) || lineString.equals("")) {
				// it's a comment line
				continue; 
			}
			if(lineString.indexOf(COMMENT_INDICATOR)>-1) {
				// means that there is comment at the end of this line
				lineString = lineString.substring(0, lineString.indexOf(COMMENT_INDICATOR)-1);
			}
			// following is basically for read in .data file
			lineString = lineString.replace(',', ' ');
			lineString = lineString.replace('"', ' ');
			lineString = lineString.replace(':', ' ');
			if(lineString.endsWith("."))
				lineString = lineString.substring(0, lineString.length()-1);
			lineString = lineString.trim();
			if(lineString.equals(""))
				continue;
			return lineString;
		}
		return lineString;
	}
}








