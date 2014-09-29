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
        double[][] allTrueClasses = new double[rocDatas.length][];
        double[][] allConfidences = new double[rocDatas.length][];

        dataSetSize = 0;
        for (int i = 0; i < rocDatas.length; i++) {
            dataSetSize += rocDatas[i].getDataSetSize();
            allTrueClasses[i] = rocDatas[i].getTrueClasses();
            allConfidences[i] = rocDatas[i].getConfidences();
        }
        trueClasses = Utils.flattenDoubleArray(allTrueClasses, dataSetSize);
        confidences = Utils.flattenDoubleArray(allConfidences, dataSetSize);
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

    public double getAreaUnderCurve() {
        double areaUnderCurve = 0.0;

        PerformanceMeasure previousPerformanceMeasure = getPerformanceMeasureOnSubset(1);
        PerformanceMeasure performanceMeasure;
        double height;
        for (int i = 2; i < dataSetSize; i++) {
            performanceMeasure = getPerformanceMeasureOnSubset(i);

            height = (1 - performanceMeasure.calculateSpecificity()) - (1 - previousPerformanceMeasure.calculateSpecificity());

            areaUnderCurve += Utils.getTrapezoidalArea(performanceMeasure.calculateRecall(), previousPerformanceMeasure.calculateRecall(), height);

            previousPerformanceMeasure = performanceMeasure;
        }
        return areaUnderCurve;
    }

    private PerformanceMeasure getPerformanceMeasureOnSubset(int stopIndex) {
        int numTruePositives = 0, numFalsePositives = 0, numTrueNegatives = 0, numFalseNegatives = 0;
        for (int i = 0; i < stopIndex; i++) {
            if (trueClasses[i] > 0 && confidences[i] > 0)
                numTruePositives++;
            else if (trueClasses[i] > 0 && confidences[i] < 0)
                numFalsePositives++;
            else if (trueClasses[i] < 0 && confidences[i] < 0)
                numTrueNegatives++;
            else
                numFalseNegatives++;
        }
        return new PerformanceMeasure(numTruePositives, numFalsePositives, numTrueNegatives, numFalseNegatives);
    }
}
