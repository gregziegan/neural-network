package ANN;

import ANN.Training.AveragedPerformanceMeasure;
import ANN.Training.Trainer;
import ANN.Utils.NetworkFactory;
import Parsing.data.DataFileProcessor;
import Parsing.data.DataSet;

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

        System.out.println("Creating Artificial Neural Network...");
        Network network = NetworkFactory.createInitialNetwork(dataSet, weightDecay, numberOfHiddenNeurons);

        System.out.println("Training network with supplied data...\n\n");
        Trainer trainer = new Trainer(network, dataSet, numberOfTrainingIterations, 5);
        AveragedPerformanceMeasure performance = trainer.trainWithStratifiedCrossValidation();

        System.out.println("\t--\tPerformance Breakdown\t--");
        System.out.println(String.format("Accuracy: %f", performance.getAccuracy()));
        System.out.println(String.format("Precision: %f", performance.getPrecision()));
        System.out.println(String.format("Recall: %f", performance.getRecall()));

    }

}
