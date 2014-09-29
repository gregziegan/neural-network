package ANN.Training;

import ANN.Utils.Utils;
import Parsing.data.DataSet;
import Parsing.data.Instance;

public class TrainingFactory {

    private DataSet dataSet;
    private int numberOfFolds;
    private DataSet[] trainingSets;
    private DataSet[] validationSets;

    public TrainingFactory(DataSet dataSet, int numberOfFolds) {
        this.dataSet = dataSet;
        this.numberOfFolds = numberOfFolds;
        this.trainingSets = getValidationSets(getRandomizedStratifiedFolds());
        this.validationSets = getTestSets();
    }

    public DataSet[] getValidationSets() {
        return validationSets;
    }

    public DataSet[] getTrainingSets() {
        return trainingSets;
    }

    private int getMeanSplitIndex() {
        return (int) Math.ceil(dataSet.mean(dataSet.classIndex()) * dataSet.size());
    }

    private DataSet[] getRandomizedStratifiedFolds() {
        dataSet.sort(dataSet.classIndex());
        DataSet classADataSet = getSubsetOfDataSet(dataSet, 0, getMeanSplitIndex());
        DataSet classBDataSet = getSubsetOfDataSet(dataSet, getMeanSplitIndex(), dataSet.size());
        DataSet[] classAFolds = getFolds(classADataSet);
        DataSet[] classBFolds = getFolds(classBDataSet);

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

    private DataSet[] getFolds(DataSet homogeneousDataSet) {
        DataSet[] folds = new DataSet[numberOfFolds];

        int sizeOfSubset = homogeneousDataSet.size() / numberOfFolds;
        int startIndex = 0, stopIndex = sizeOfSubset;
        for (int i = 0; i < numberOfFolds; i++) {
            folds[i] = getSubsetOfDataSet(homogeneousDataSet, startIndex, stopIndex);
            startIndex += sizeOfSubset;
            if (i == numberOfFolds - 1)
                stopIndex = homogeneousDataSet.size() - 1;
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

    private DataSet[] getValidationSets(DataSet[] folds) {
        int sizeOfTrainingSet = folds.length - 1;
        DataSet[][] foldCombinations = new DataSet[folds.length][sizeOfTrainingSet];

        for (int i = 0; i < folds.length; i++) {
            DataSet[] trainingSet = new DataSet[sizeOfTrainingSet];
            for (int j = 0; j < sizeOfTrainingSet; j++) {
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



}
