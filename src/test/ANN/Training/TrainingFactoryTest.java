package test.ANN.Training;

import ANN.Training.TrainingFactory;
import Parsing.data.DataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.ANN.TestUtils;

import static org.junit.Assert.assertEquals;

public class TrainingFactoryTest {

private static final int NUM_INDEPENDENT_TESTS = 5;

@Before
public void before() throws Exception {
}

@After
public void after() throws Exception { 
} 

@Test
public void testPopulateTrainingAndValidationSets() throws Exception {
    DataSet dataSet = TestUtils.getTestDataSet();
    TrainingFactory trainingFactory = new TrainingFactory();
    trainingFactory.populateTrainingAndValidationSets(dataSet, NUM_INDEPENDENT_TESTS);
    assertEquals(NUM_INDEPENDENT_TESTS, trainingFactory.getCurrentTrainingSets().length);
    assertEquals(NUM_INDEPENDENT_TESTS, trainingFactory.getCurrentValidationSets().length);
}

@Test
public void testGetMeanSplitIndex() throws Exception {
    DataSet sortedDataSet = TestUtils.getTestDataSet();
    sortedDataSet.sort(sortedDataSet.classIndex());
    int splitIndex = TrainingFactory.getMeanSplitIndex(sortedDataSet);
    assertEquals(0, sortedDataSet.instance(splitIndex - 1).classValue(), 0);
    assertEquals(1, sortedDataSet.instance(splitIndex).classValue(), 0);
}

@Test
public void testGetRandomizedStratifiedFolds() throws Exception {
    DataSet dataSet = TestUtils.getTestDataSet();
    DataSet[] randomizedFolds = TrainingFactory.getRandomizedStratifiedFolds(dataSet, NUM_INDEPENDENT_TESTS);
    assertEquals(NUM_INDEPENDENT_TESTS, randomizedFolds.length);

    for (DataSet fold : randomizedFolds) {
        if (fold.mean(fold.classIndex()) == 0.0) // One set will be missing any class 1 value
            continue;
        assertEquals(dataSet.mean(dataSet.classIndex()), fold.mean(fold.classIndex()), 0.1);
    }
}

@Test
public void testGetFolds() throws Exception {
    DataSet dataSet = TestUtils.getTestDataSet();
    DataSet[] folds = TrainingFactory.getFolds(dataSet, NUM_INDEPENDENT_TESTS);
    assertEquals(NUM_INDEPENDENT_TESTS, folds.length);
    for (DataSet fold : folds) {
        assertEquals(dataSet.size()/5, fold.size());
    }
}

@Test
public void testGetSubsetOfDataSet() throws Exception {
    DataSet dataSet = TestUtils.getTestDataSet();
    DataSet subset = TrainingFactory.getSubsetOfDataSet(dataSet, 0, NUM_INDEPENDENT_TESTS);
    assertEquals(NUM_INDEPENDENT_TESTS, subset.size());
}

@Test
public void testGetTrainingSets() throws Exception {
    DataSet dataSet = TestUtils.getTestDataSet();
    DataSet[] trainingSets = TrainingFactory.getTrainingSets(TrainingFactory.getRandomizedStratifiedFolds(dataSet, NUM_INDEPENDENT_TESTS));
    assertEquals(NUM_INDEPENDENT_TESTS, trainingSets.length);
}

@Test
public void testGetValidationSets() throws Exception {
    DataSet dataSet = TestUtils.getTestDataSet();
    DataSet[] trainingSets = TrainingFactory.getTrainingSets(TrainingFactory.getRandomizedStratifiedFolds(dataSet, NUM_INDEPENDENT_TESTS));
    DataSet[] validationSets = TrainingFactory.getValidationSets(trainingSets);
    assertEquals(trainingSets.length, validationSets.length);
}

}
