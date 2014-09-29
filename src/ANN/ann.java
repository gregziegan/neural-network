package ANN;

import ANN.Training.AveragedPerformanceMeasure;
import ANN.Training.PerformanceMeasure;
import ANN.Training.Trainer;
import ANN.Utils.NetworkFactory;
import ANN.Utils.Utils;
import Parsing.data.Attribute;
import Parsing.data.DataFileProcessor;
import Parsing.data.DataSet;

import java.util.ArrayList;
import java.util.Random;

public class ann {

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

        System.out.println("Randomly shuffling data...");
        DataSet shuffledDataSet = Utils.getShuffledDataSet(dataSet);

        System.out.println("Producing training sets...");
        DataSet[] trainingSets = Utils.getTrainingSets(shuffledDataSet, 5);

        System.out.println("Creating Artificial Neural Network...");
        Network network = NetworkFactory.createInitialNetwork(dataSet, weightDecay, numberOfHiddenNeurons);

        System.out.println("Training network with supplied data...\n\n");
        AveragedPerformanceMeasure performance = Trainer.trainWithStratifiedCrossValidation(network, trainingSets, numberOfTrainingIterations);

        System.out.println("\t--\tPerformance Breakdown\t--");
        System.out.println(String.format("Accuracy: %f", performance.getAccuracy()));
        System.out.println(String.format("Precision: %f", performance.getPrecision()));
        System.out.println(String.format("Recall: %f", performance.getRecall()));



    }

}
