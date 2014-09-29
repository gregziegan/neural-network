package ANN.Training;

import ANN.Network;
import ANN.Utils.Utils;
import Parsing.data.DataSet;
import Parsing.data.Instance;

import java.util.Arrays;

public class Trainer {

    private Network network;
    private int numberOfTrainingIterations;
    private DataSet[] trainingSets;

    public Trainer(Network network, DataSet dataSet, int numberOfTrainingIterations) {
        this.network = network;
        this.trainingSets = getTrainingSets(getFolds(dataSet, 5));
        this.numberOfTrainingIterations = numberOfTrainingIterations;
    }

    private DataSet[] getFolds(final DataSet dataSet, final int numberOfFolds) {
        DataSet[] folds = new DataSet[numberOfFolds];

        int sizeOfSubset = dataSet.size() / numberOfFolds;
        int startIndex = 0, stopIndex = sizeOfSubset;
        for (int i = 0; i < numberOfFolds; i++) {
            folds[i] = getSubsetOfDataSet(dataSet, startIndex, stopIndex);
            startIndex += sizeOfSubset;
            if (i == numberOfFolds - 1)
                stopIndex = dataSet.size() - 1;
            else
                stopIndex += sizeOfSubset;
        }

        return folds;
    }

    private DataSet getSubsetOfDataSet(final DataSet dataSet, int startIndex, int stopIndex) {
        DataSet subset = new DataSet(dataSet);
        Instance[] instances = new Instance[dataSet.size()];
        for (int j = startIndex; j < stopIndex; j++) {
            instances[j] = dataSet.instance(j);
        }
        subset.add(instances);
        return subset;
    }

    private DataSet[] getTestSets() {
        DataSet[] testSets = new DataSet[trainingSets.length];
        for (int i = 0, j = trainingSets.length - 1; i < trainingSets.length; i++, j--) {
            testSets[i] = new DataSet(trainingSets[j]);
            testSets[i].add(trainingSets[j]);
        }
        return testSets;
    }

    private DataSet combineDataSets(DataSet[] dataSets) {
        DataSet superSet = new DataSet(dataSets[0]);
        for (DataSet subset : dataSets) {
            superSet.add(subset);
        }

        return superSet;
    }

    private DataSet[] getTrainingSets(DataSet[] folds) {
        int sizeOfTrainingSet = folds.length - 1;
        DataSet[][] foldCombinations = new DataSet[folds.length][sizeOfTrainingSet];

        for (int i = 0; i < folds.length; i++) {
            DataSet[] trainingSet = new DataSet[sizeOfTrainingSet];
            for (int j = 0; j < folds.length - 1; j++) {
                int foldIndexToInclude = sizeOfTrainingSet - 1 - i - j;
                if (foldIndexToInclude < 0)
                    foldIndexToInclude = sizeOfTrainingSet - foldIndexToInclude;
                trainingSet[j] = folds[foldIndexToInclude];
            }
            foldCombinations[i] = trainingSet;
        }

        DataSet[] trainingSets = new DataSet[folds.length];

        for (int i = 0; i < foldCombinations.length; i++) {
            trainingSets[i] = combineDataSets(foldCombinations[i]);
        }

        return trainingSets;
    }

    private PerformanceMeasure getPerformanceMeasure(DataSet testSet) {

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

    public AveragedPerformanceMeasure trainWithStratifiedCrossValidation() {
        DataSet[] testSets = getTestSets();
        PerformanceMeasure[] performanceMeasures = new PerformanceMeasure[testSets.length];

        for (int i = 0; i < trainingSets.length; i++) {
            network.train(trainingSets[i], numberOfTrainingIterations);
            performanceMeasures[i] = getPerformanceMeasure(testSets[i]);
        }

        return new AveragedPerformanceMeasure(performanceMeasures);
    }

}
