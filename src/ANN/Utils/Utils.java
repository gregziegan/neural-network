package ANN.Utils;

import Parsing.data.Instance;

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

    public static double[] getInstanceValues(Instance instance) {
        double[] values = new double[instance.length()];
        for (int i = 0; i < instance.length(); i++) {
            values[i] = instance.value(i);
        }
        return values;
    }

}
