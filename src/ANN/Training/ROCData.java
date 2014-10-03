package ANN.Training;

import ANN.Utils.Utils;

public class ROCData {

    private double[] trueClasses;
    private double[] confidences;
    private int dataSetSize;
    private PerformanceMeasure[] performanceMeasures;

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
        dataSetSize = confidences.length;
    }

    public int getDataSetSize() {
        return dataSetSize;
    }

    public double getConfidence(int index) {
        return confidences[index];
    }

    public double getTrueClass(int index) {
        return trueClasses[index];
    }

    public double getAreaUnderCurve() {
        double areaUnderCurve = 0.0;

        performanceMeasures = new PerformanceMeasure[dataSetSize];
        performanceMeasures[0] = getPerformanceMeasureOnSubset(0);
        double height;
        for (int i = 1; i < dataSetSize; i++) {
            PerformanceMeasure currentPerformanceMeasure = getPerformanceMeasureOnSubset(i);

            performanceMeasures[i] = currentPerformanceMeasure;
            double falsePositiveRate = performanceMeasures[i].calculateFalsePositiveRate();
            double previousFalsePositiveRate = performanceMeasures[i-1].calculateFalsePositiveRate();
            height = falsePositiveRate - previousFalsePositiveRate;

            areaUnderCurve += Utils.getTrapezoidalArea(performanceMeasures[i].calculateRecall(), performanceMeasures[i-1].calculateRecall(), height);
        }
        return areaUnderCurve;
    }

    private PerformanceMeasure getPerformanceMeasureOnSubset(int stopIndex) {
        int numTruePositives = 0, numFalsePositives = 0, numTrueNegatives = 0, numFalseNegatives = 0;
        for (int i = 0; i < stopIndex; i++) {
            if (trueClasses[i] == 1 && confidences[i] > 0.5)
                numTruePositives++;
            else if (trueClasses[i] == 1 && confidences[i] <= 0.5)
                numFalseNegatives++;
            else if (trueClasses[i] == 0 && confidences[i] <= 0.5)
                numTrueNegatives++;
            else
                numFalsePositives++;
        }
        return new PerformanceMeasure(numTruePositives, numFalsePositives, numTrueNegatives, numFalseNegatives);
    }

    public PerformanceMeasure[] getPerformanceMeasures() {
        return performanceMeasures;
    }
}
