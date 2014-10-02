package ANN.Training;

import ANN.Utils.Utils;
import Parsing.data.DataSet;
import Parsing.data.Instance;

public class TrainingFactory {

    private DataSet[] currentTrainingSets;
    private DataSet[] currentValidationSets;

    public TrainingFactory() {

    }

    public void populateTrainingAndValidationSets(final DataSet dataSet, final int numberOfFolds) {
        this.currentTrainingSets = getTrainingSets(getRandomizedStratifiedFolds(dataSet, numberOfFolds));
        this.currentValidationSets = TrainingFactory.getValidationSets(currentTrainingSets);
    }

    public DataSet[] getCurrentValidationSets() {
        return currentValidationSets;
    }

    public DataSet[] getCurrentTrainingSets() {
        return currentTrainingSets;
    }

    public static int getMeanSplitIndex(DataSet dataSet) {
        return (int) Math.ceil((1 - dataSet.mean(dataSet.classIndex())) * dataSet.size());
    }

    public static DataSet[] getRandomizedStratifiedFolds(DataSet dataSet, int numberOfFolds) {
        dataSet.sort(dataSet.classIndex());
        DataSet classADataSet = getSubsetOfDataSet(dataSet, 0, getMeanSplitIndex(dataSet));
        DataSet classBDataSet = getSubsetOfDataSet(dataSet, getMeanSplitIndex(dataSet), dataSet.size());
        DataSet[] classAFolds = getFolds(classADataSet, numberOfFolds);
        DataSet[] classBFolds = getFolds(classBDataSet, numberOfFolds);

        DataSet[] stratifiedFolds = new DataSet[numberOfFolds];
        DataSet[] dataSetsToCombine;
        for (int i = 0; i < numberOfFolds; i++) {
            dataSetsToCombine = new DataSet[2];
            dataSetsToCombine[0] = classAFolds[i];
            dataSetsToCombine[1] = classBFolds[i];
            stratifiedFolds[i] = Utils.getShuffledDataSet(combineDataSets(dataSetsToCombine));
        }

        return stratifiedFolds;
    }

    public static DataSet[] getFolds(DataSet dataSet, int numberOfFolds) {
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

    public static DataSet getSubsetOfDataSet(final DataSet dataSet, int startIndex, int stopIndex) {
        DataSet subset = new DataSet(dataSet);
        Instance[] instances = new Instance[stopIndex - startIndex];
        for (int j = startIndex; j < stopIndex; j++) {
            instances[j - startIndex] = new Instance(dataSet.instance(j));
        }
        subset.add(instances);
        return subset;
    }

    public static DataSet[] getValidationSets(DataSet[] trainingSets) {
        DataSet[] testSets = new DataSet[trainingSets.length];
        for (int i = 0, j = trainingSets.length - 1; i < trainingSets.length; i++, j--) {
            testSets[i] = new DataSet(trainingSets[j]);
            testSets[i].add(trainingSets[j]);
        }
        return testSets;
    }

    public static DataSet combineDataSets(DataSet[] dataSets) {
        DataSet superSet = new DataSet(dataSets[0]);
        for (DataSet subset : dataSets) {
            superSet.add(subset);
        }

        return superSet;
    }

    public static DataSet[] getTrainingSets(DataSet[] folds) {
        int numOfTrainingSetFolds = folds.length - 1;
        DataSet[][] foldCombinations = new DataSet[folds.length][numOfTrainingSetFolds];

        for (int foldIndex = 0; foldIndex < folds.length; foldIndex++) {
            DataSet[] trainingSet = new DataSet[numOfTrainingSetFolds];
            for (int j = numOfTrainingSetFolds, trainingIndex = 0; trainingIndex < numOfTrainingSetFolds; j--, trainingIndex++) {
                int foldIndexToInclude = (j + foldIndex) % folds.length;
                trainingSet[trainingIndex] = folds[foldIndexToInclude];
            }
            foldCombinations[foldIndex] = trainingSet;
        }

        DataSet[] trainingSets = new DataSet[folds.length];

        for (int i = 0; i < foldCombinations.length; i++) {
            trainingSets[i] = combineDataSets(foldCombinations[i]);
        }

        return trainingSets;
    }
}
