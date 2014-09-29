package ANN.Training;

import ANN.Network;
import Parsing.data.DataSet;
import Parsing.data.Instance;

import java.util.List;

public class Trainer implements Runnable {

    private Network network;
    private DataSet trainingSet, validationSet;
    private int numberOfTrainingIterations;
    private PerformanceMeasure performanceMeasure;
    private List<PerformanceMeasure> performanceMeasures;

    public Trainer(List<PerformanceMeasure> performanceMeasures, Network network, DataSet trainingSet, DataSet validationSet, int numberOfTrainingIterations) {
        this.performanceMeasures = performanceMeasures;
        this.network = network;
        this.trainingSet = trainingSet;
        this.validationSet = validationSet;
        this.numberOfTrainingIterations = numberOfTrainingIterations;
    }

    private void calculatePerformanceMeasure() {
        int numTruePositives = 0, numTrueNegatives = 0, numFalsePositives = 0, numFalseNegatives = 0;
        for (int i = 0; i < validationSet.size(); i++) {
            Instance instance = validationSet.instance(i);
            double classLabelPrediction = network.classify(instance);
            double classLabel = instance.classValue();
            if (classLabelPrediction == 1.0 && classLabelPrediction == classLabel)
                numTruePositives++;
            else if (classLabelPrediction == 1.0 && classLabelPrediction != classLabel)
                numTrueNegatives++;
            else if (classLabelPrediction != 1.0 && classLabelPrediction == classLabel)
                numFalseNegatives++;
            else
                numFalsePositives++;
        }

        this.performanceMeasure = new PerformanceMeasure(numTruePositives, numFalsePositives, numTrueNegatives, numFalseNegatives);
    }

    public void trainNetwork() {

        for (int i = 0; i < trainingSet.size(); i++) {
            if (numberOfTrainingIterations <= 0)
                network.trainUntilConvergence(trainingSet);
            else
                network.train(trainingSet, numberOfTrainingIterations);
        }

    }

    public void run() {
        trainNetwork();
        calculatePerformanceMeasure();
        performanceMeasures.add(performanceMeasure);
    }

}
