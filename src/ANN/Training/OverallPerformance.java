package ANN.Training;

public class OverallPerformance {

    private double meanAccuracy, meanPrecision, meanRecall;
    private double stdDevAccuracy, stdDevPrecision, stdDevRecall;
    private double areaUnderROC;
    private ROCData rocData;

    public OverallPerformance(PerformanceMeasure[] performanceMeasures, ROCData[] rocDatas) {
        int numPerformanceMeasures = performanceMeasures.length;

        double totalAccuracy = 0.0, totalPrecision = 0.0, totalRecall = 0.0;
        for (PerformanceMeasure performanceMeasure : performanceMeasures) {
            totalAccuracy += performanceMeasure.calculateAccuracy();
            totalPrecision += performanceMeasure.calculatePrecision();
            totalRecall += performanceMeasure.calculateRecall();
        }

        meanAccuracy = totalAccuracy / numPerformanceMeasures;
        meanPrecision = totalPrecision / numPerformanceMeasures;
        meanRecall = totalRecall / numPerformanceMeasures;

        for (PerformanceMeasure performanceMeasure : performanceMeasures)
        {
            stdDevAccuracy = stdDevAccuracy + Math.pow(performanceMeasure.calculateAccuracy() - meanAccuracy, 2);
            stdDevPrecision = stdDevPrecision + Math.pow(performanceMeasure.calculatePrecision() - meanPrecision, 2);
            stdDevRecall = stdDevRecall + Math.pow(performanceMeasure.calculateRecall() - meanRecall, 2);
        }

        rocData = new ROCData(rocDatas);
        areaUnderROC = rocData.getAreaUnderCurve();
    }

    public double getMeanAccuracy() {
        return meanAccuracy;
    }

    public double getMeanPrecision() {
        return meanPrecision;
    }

    public double getMeanRecall() {
        return meanRecall;
    }

    public double getStdDevAccuracy() {
        return stdDevAccuracy;
    }

    public double getStdDevPrecision() {
        return stdDevPrecision;
    }

    public double getStdDevRecall() {
        return stdDevRecall;
    }

    public double getAreaUnderROC() {
        return areaUnderROC;
    }

    public ROCData getRocData() { return rocData; }
}
