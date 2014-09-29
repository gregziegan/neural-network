package ANN.Training;

public class AveragedPerformanceMeasure {

    private double accuracy, precision, recall;

    public AveragedPerformanceMeasure(PerformanceMeasure[] performanceMeasures) {
        int numPerformanceMeasures = performanceMeasures.length;

        double totalAccuracy = 0.0, totalPrecision = 0.0, totalRecall = 0.0;
        for (PerformanceMeasure performanceMeasure : performanceMeasures) {
            totalAccuracy += performanceMeasure.calculateAccuracy();
            totalPrecision += performanceMeasure.calculatePrecision();
            totalRecall += performanceMeasure.calculateRecall();
        }

        accuracy = totalAccuracy / numPerformanceMeasures;
        precision = totalPrecision / numPerformanceMeasures;
        recall = totalRecall / numPerformanceMeasures;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getPrecision() {
        return precision;
    }

    public double getRecall() {
        return recall;
    }
}
