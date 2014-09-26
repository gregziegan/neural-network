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
        Vector<Attribute> attr = new Vector<Attribute>();					// vector of type attribute to be used for classification issue
        int attNum = metaInfo.numAttributes();								// this variable holds the total number of attributes
        for(int i = 0; i< attNum;i++)										// loop over meta information to get the attributes' properties and add it to the attr vector
            attr.add(metaInfo.attribute(i));
        DataSet dataSet = DataFileProcessor.readInData(filename, metaInfo);			// call readInData function by passing the full path of the file and the meta information. store the returned data into dataSet vector

        Neuron[] neurons = NeuronFactory.createNeurons(dataSet.toArray(dataSet));
        Connection[] connections = ConnectionFactory.createConnections(dataSet);
        Network network = new Network(neurons, connections, weightDecay, numberOfHiddenNeurons);
        train(network, numberOfTrainingIterations);
    }

    public static void train(Network network, int numberOfTrainingIterations) {

    }
}
