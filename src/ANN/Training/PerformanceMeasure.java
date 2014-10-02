package ANN.Training;

import ANN.Utils.Utils;

public class PerformanceMeasure {

    private final int numTruePositives;
    private final int numTrueNegatives;
    private final int numFalsePositives;
    private final int numFalseNegatives;

    public PerformanceMeasure(int numTruePositives, int numFalsePositives, int numTrueNegatives, int numFalseNegatives) {
        this.numTruePositives = numTruePositives;
        this.numTrueNegatives = numTrueNegatives;
        this.numFalsePositives = numFalsePositives;
        this.numFalseNegatives = numFalseNegatives;
    }

    public double calculateAccuracy() {
        return ( (double) numTruePositives + numTrueNegatives) / (numTruePositives + numTrueNegatives + numFalsePositives + numFalseNegatives);
    }

    public double calculatePrecision() {
        return numTruePositives / ( (double) numTruePositives + numFalsePositives);
    }

    public double calculateRecall() {
        return Utils.calculateRecall(numTruePositives, numFalseNegatives);
    }

    public double calculateSpecificity() {
        return Utils.calculateSpecificity(numTrueNegatives, numFalsePositives);
    }

}
