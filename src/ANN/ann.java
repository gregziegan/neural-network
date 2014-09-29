package ANN;

import ANN.Training.*;
import ANN.Utils.NetworkFactory;
import Parsing.data.DataFileProcessor;
import Parsing.data.DataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ann {

    private static final int NUM_INDEPENDENT_TESTS = 5;

    public static void main(String[] args) {

        if (args.length != 4)
            throw new IllegalArgumentException("Script takes 4 arguments.");

        String filename = args[0];
        int numberOfHiddenNeurons = Integer.parseInt(args[1]);
        float weightDecay = Float.parseFloat(args[2]);
        int numberOfTrainingIterations = Integer.parseInt(args[3]);

        System.out.println("Reading in data...");
        DataSet metaInfo = DataFileProcessor.readInMetaInfo(filename);
        DataSet dataSet = DataFileProcessor.readInData(filename, metaInfo);

        System.out.println("Creating Artificial Neural Networks...");
        Network[] networks = NetworkFactory.getSeveralNetworkCopies(NUM_INDEPENDENT_TESTS, dataSet, weightDecay, numberOfHiddenNeurons);

        System.out.println("Generating five-fold stratified training and validation sets...");
        TrainingFactory trainingFactory = new TrainingFactory(dataSet, NUM_INDEPENDENT_TESTS);

        System.out.println("Training network with supplied data...\n\n");
        OverallPerformance performance;
        try {
            performance = performStratifiedCrossValidation(networks, trainingFactory, numberOfTrainingIterations);
            printPerformanceBreakdown(performance);
        } catch (InterruptedException e) {
            System.out.println("Training stopped early!");
            System.exit(1);
        } catch (TimeoutException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public static OverallPerformance performStratifiedCrossValidation(Network[] networks, TrainingFactory trainingFactory, int numberOfTrainingIterations) throws InterruptedException, TimeoutException {
        List<PerformanceMeasure> performanceMeasuresList = Collections.synchronizedList(new ArrayList<PerformanceMeasure>(NUM_INDEPENDENT_TESTS));
        List<ROCData> rocDataList = Collections.synchronizedList(new ArrayList<ROCData>(NUM_INDEPENDENT_TESTS));
        DataSet[] trainingSets = trainingFactory.getTrainingSets();
        DataSet[] validationSets = trainingFactory.getValidationSets();

        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < NUM_INDEPENDENT_TESTS; i++) {
            es.execute(new Trainer(performanceMeasuresList, rocDataList, networks[i], trainingSets[i], validationSets[i], numberOfTrainingIterations));
        }
        es.shutdown();
        boolean finished = es.awaitTermination(60, TimeUnit.MINUTES);

        if (!finished)
            throw new TimeoutException("Testing timed out... > 60 minutes");

        PerformanceMeasure[] performanceMeasures = performanceMeasuresList.toArray(new PerformanceMeasure[performanceMeasuresList.size()]);
        ROCData[] rocDatas = rocDataList.toArray(new ROCData[rocDataList.size()]);
        return new OverallPerformance(performanceMeasures, rocDatas);
    }

    public static void printPerformanceBreakdown(OverallPerformance performance) {
        System.out.println("\t--\tPerformance Breakdown\t--");
        System.out.println(String.format("Accuracy: %f %f", performance.getMeanAccuracy(), performance.getStdDevAccuracy()));
        System.out.println(String.format("Precision: %f %f", performance.getMeanPrecision(), performance.getStdDevPrecision()));
        System.out.println(String.format("Recall: %f %f", performance.getMeanRecall(), performance.getStdDevRecall()));
        System.out.println(String.format("Area under ROC: %f", performance.getAreaUnderROC()));
    }
}
