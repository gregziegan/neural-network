package ANN.Utils;

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
}
