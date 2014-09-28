package ANN;

import ANN.Utils.NetworkFactory;
import ANN.Utils.Utils;
import Parsing.data.Attribute;
import Parsing.data.DataFileProcessor;
import Parsing.data.DataSet;

import javax.rmi.CORBA.Util;
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

        Random random = new Random();
        random.setSeed(12345);
        DataSet metaInfo = DataFileProcessor.readInMetaInfo(filename);
        ArrayList<Attribute> attributes = new ArrayList<Attribute>(metaInfo.numAttributes());
        int attributeNumbers = metaInfo.numAttributes();
        for(int i = 0; i < attributeNumbers;i++)
            attributes.add(metaInfo.attribute(i));
        DataSet dataSet = DataFileProcessor.readInData(filename, metaInfo);

        DataSet shuffledDataSet = Utils.getShuffledDataSet(dataSet);
        DataSet[] trainingSets = Utils.getTrainingSets(dataSet, 5);

        Network network = NetworkFactory.createInitialNetwork(dataSet, weightDecay, numberOfHiddenNeurons);
        train(network, numberOfTrainingIterations);
    }

    public static void train(Network network, int numberOfTrainingIterations) {
        for (int i = 0; i < numberOfTrainingIterations; i++) {
            // TODO implement training method
        }
    }
}
