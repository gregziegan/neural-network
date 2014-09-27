package ANN;

import ANN.Utils.ConnectionFactory;
import ANN.Utils.NeuronFactory;
import Parsing.data.Attribute;
import Parsing.data.DataFileProcessor;
import Parsing.data.DataSet;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class ANN {

    public static void main(String[] args) {

        if (args.length != 4)
            throw new IllegalArgumentException("Script takes 4 arguments.");


        String filename = args[0];
        int numberOfHiddenNeurons = Integer.parseInt(args[1]);
        float weightDecay = Float.parseFloat(args[2]);
        int numberOfTrainingIterations = Integer.parseInt(args[3]);

        Random random= new Random();
        random.setSeed(12345);
        DataSet metaInfo = DataFileProcessor.readInMetaInfo(filename);
        ArrayList<Attribute> attributes = new ArrayList<Attribute>(metaInfo.numAttributes());
        int attributeNumbers = metaInfo.numAttributes();
        for(int i = 0; i < attributeNumbers;i++)
            attributes.add(metaInfo.attribute(i));
        DataSet dataSet = DataFileProcessor.readInData(filename, metaInfo);

        Neuron[] neurons = NeuronFactory.createNeurons(dataSet);
        Connection[] connections = ConnectionFactory.createConnections(dataSet);
        Network network = new Network(neurons, connections, weightDecay, numberOfHiddenNeurons);
        train(network, numberOfTrainingIterations);
    }

    public static void train(Network network, int numberOfTrainingIterations) {
        for (int i = 0; i < numberOfTrainingIterations; i++) {
            // TODO implement training method
        }
    }
}
