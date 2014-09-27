package ANN.Utils;

import Parsing.data.DataSet;
import Parsing.data.Instance;

import java.util.ArrayList;

public class Utils {

    public static double evaluateSigmoid(final double exponent) {
        return 1 / (1 + Math.exp(-exponent) );
    }

    public static double getDotProduct(double[] a, double[] b){
        if(a.length != b.length){
            throw new IllegalArgumentException("The dimensions have to be equal!");
        }
        double sum = 0;
        for(int i = 0; i < a.length; i++){
            sum += a[i] * b[i];
        }
        return sum;
    }

    public static double normalizeData(double data, double mean, double std) {
        return (data - mean) / std;
    }

    public static void normalizeDataSet(DataSet dataset) {
        double[] means = new double[dataset.numAttributes()];
        double[] stds = new double[dataset.numAttributes()];
        for (int attribute = 1; attribute < dataset.numAttributes() -1; attribute++) {
            means[attribute] = dataset.mean(attribute);
            stds[attribute] = dataset.stdDeviation(attribute);
        }
            for (int example = 0; example < dataset.size(); example++) {
                Instance instance = dataset.instance(example);
                for (int attributeValue = 1; attributeValue < instance.numAttributes() - 1; attributeValue++) {
                    instance.setValue(attributeValue, normalizeData(instance.value(attributeValue), means[attributeValue], stds[attributeValue]));
            }
        }
    }

    public static double[] getInstanceValues(Instance instance) {
        double[] values = new double[instance.length()];
        for (int i = 0; i < instance.length(); i++) {
            values[i] = instance.value(i);
        }
        return values;
    }

}
