package ANN.Training;

import ANN.Utils.Utils;

public class ROCData {

    private double[] trueClasses;
    private double[] confidences;
    private int dataSetSize;

    public ROCData(double[] trueClasses, double[] confidences) {
        if (trueClasses.length != confidences.length)
            throw new IllegalArgumentException("True Class and Confidence arrays must be the same length.");

        this.trueClasses = trueClasses;
        this.confidences = confidences;

        dataSetSize = confidences.length;
    }

    public ROCData(ROCData[] rocDatas) {
        trueClasses = new double[rocDatas.length * rocDatas[0].getDataSetSize()];
        confidences = new double[rocDatas.length * rocDatas[0].getDataSetSize()];
        int offset = 0;
        for (int instanceIndex = 0; instanceIndex < rocDatas[0].getDataSetSize(); instanceIndex++) {
            for (int rocDataIndex = 0; rocDataIndex < rocDatas.length; rocDataIndex++) {
               trueClasses[instanceIndex + rocDataIndex + offset] = rocDatas[rocDataIndex].getTrueClass(instanceIndex);
               confidences[instanceIndex + rocDataIndex + offset] = rocDatas[rocDataIndex].getConfidence(instanceIndex);
            }
            offset += rocDatas.length - 1;
        }
    }

    public int getDataSetSize() {
        return dataSetSize;
    }

    public double[] getConfidences() {
        return confidences;
    }

    public double[] getTrueClasses() {
        return trueClasses;
    }

    public double getConfidence(int index) {
        return confidences[index];
    }

    public double getTrueClass(int index) {
        return trueClasses[index];
    }

    public double getAreaUnderCurve() {
        double areaUnderCurve = 0.0;

        PerformanceMeasure previousPerformanceMeasure = getPerformanceMeasureOnSubset(1);
        PerformanceMeasure performanceMeasure;
        double height;
        for (int i = 2; i < dataSetSize; i++) {
            performanceMeasure = getPerformanceMeasureOnSubset(i);

            double falsePositiveRate = performanceMeasure.calculateFalsePositiveRate();
            double previousFalsePositiveRate = previousPerformanceMeasure.calculateFalsePositiveRate();
            height = falsePositiveRate - previousFalsePositiveRate;

            areaUnderCurve += Utils.getTrapezoidalArea(performanceMeasure.calculateRecall(), previousPerformanceMeasure.calculateRecall(), height);

            previousPerformanceMeasure = performanceMeasure;
        }
        return areaUnderCurve;
    }

    private PerformanceMeasure getPerformanceMeasureOnSubset(int stopIndex) {
        int numTruePositives = 0, numFalsePositives = 0, numTrueNegatives = 0, numFalseNegatives = 0;
        for (int i = 0; i < stopIndex; i++) {
            if (trueClasses[i] > 0 && confidences[i] > 0.5)
                numTruePositives++;
            else if (trueClasses[i] > 0 && confidences[i] <= 0.5)
                numFalsePositives++;
            else if (trueClasses[i] < 0 && confidences[i] <= 0.5)
                numTrueNegatives++;
            else
                numFalseNegatives++;
        }
        return new PerformanceMeasure(numTruePositives, numFalsePositives, numTrueNegatives, numFalseNegatives);
    }
}
