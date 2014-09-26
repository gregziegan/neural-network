package ANN;

import ANN.Utils.ConnectionFactory;
import ANN.Utils.NeuronFactory;
import DataParsing.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class ANN {

    public static void main(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("Script takes 3 options.");
        }

        int numberOfHiddenNeurons = Integer.parseInt(args[0]);
        float weightDecay = Float.parseFloat(args[1]);
        int numberOfTrainingIterations = Integer.parseInt(args[2]);

        Random rnd = new Random();
        rnd.setSeed(12345);
        String filename = "voting";
        DataSet metaInfo = data.DataFileProcessor.readInMetaInfo(filename);
        Vector<Attribute> attributes = new Vector<Attribute>();
        int attNum = metaInfo.numAttributes();
        for(int i = 0; i< attNum;i++)
            attributes.add(metaInfo.attribute(i));
        DataSet dataSet = DataFileProcessor.readInData(filename, metaInfo);

        Neuron[] neurons = NeuronFactory.createNeurons(dataSet.toArray(dataSet));
        Connection[] connections = ConnectionFactory.createConnections(dataSet);
        Network network = new Network(neurons, connections, weightDecay, numberOfHiddenNeurons);
        train(network, numberOfTrainingIterations);
    }

    public static void train(Network network, int numberOfTrainingIterations) {

    }
}
