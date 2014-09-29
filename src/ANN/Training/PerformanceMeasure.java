package ANN.Training;

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
        return (numTruePositives + numTrueNegatives) / (numTruePositives + numTrueNegatives + numFalsePositives + numFalseNegatives);
    }

    public double calculatePrecision() {
        return numTruePositives / (numTruePositives + numFalsePositives);
    }

    public double calculateRecall() {
        return numTruePositives / (numTruePositives + numFalseNegatives);
    }

}
