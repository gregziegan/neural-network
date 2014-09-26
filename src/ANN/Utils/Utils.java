package ANN.Utils;

public class Utils {

    public static double evaluateSigmoid(final double exponent) {

        double evaluation = 1 / (1 + Math.exp(-exponent) );
        return evaluation;
    }

    }
}
