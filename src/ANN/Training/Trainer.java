package ANN.Training;

import ANN.Network;
import ANN.Utils.NetworkFactory;
import Parsing.data.DataSet;
import Parsing.data.Instance;

public class Trainer {

    private Network originalNetwork;
    private int numberOfTrainingIterations;

    public Trainer(Network network, int numberOfTrainingIterations) {
        this.originalNetwork = network;
        this.numberOfTrainingIterations = numberOfTrainingIterations;
    }

    public static AveragedPerformanceMeasure trainWithStratifiedCrossValidation(Network network, DataSet[] trainingSets, int numberOfTrainingIterations) {

    }

    public static PerformanceMeasure getPerformanceMeasure(Network network, DataSet testSet) {

        int numTruePositives = 0, numTrueNegatives = 0, numFalsePositives = 0, numFalseNegatives = 0;
        for (int i = 0; i < testSet.size(); i++) {
            Instance instance = testSet.instance(i);
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

        return new PerformanceMeasure(numTruePositives, numFalsePositives, numTrueNegatives, numFalseNegatives);
    }
}
