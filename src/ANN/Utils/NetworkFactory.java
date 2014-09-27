package ANN.Utils;

import ANN.Connection;
import ANN.Network;
import ANN.Neuron;
import Parsing.data.DataSet;


public class NetworkFactory {

    public static Network createInitialNetwork(DataSet trainingSet, float weightDecay, int numberOfHiddenNeurons) {
        int numberOfInputNeurons = trainingSet.numAttributes() - 2; // first attribute is the index, last attribute is the class label
        double[] firstInstanceValues = Utils.getInstanceValues(trainingSet.instance(0));

        Neuron[] neurons = NeuronFactory.createInitialNeurons(numberOfInputNeurons, numberOfHiddenNeurons, firstInstanceValues);
        Connection[] connections = ConnectionFactory.createInitialConnections(neurons, numberOfInputNeurons, numberOfHiddenNeurons);
        return new Network(neurons, connections, weightDecay);
    }

    public static Network getModifiedNetwork(Network network, double[] biases, double[] weights) {
        Neuron[] neurons = NeuronFactory.createModifiedNeurons(network.getNeurons(), biases);
        Connection[] connections = ConnectionFactory.createModifiedConnections(network.getConnections(), weights);
        return new Network(neurons, connections, network.getWeightDecay());
    }
}
