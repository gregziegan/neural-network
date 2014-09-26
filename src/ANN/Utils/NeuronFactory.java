package ANN.Utils;

import ANN.Layer;
import ANN.Network;
import ANN.Neuron;
import DataParsing.DataSet;

public class NeuronFactory {
    public static Neuron[] createNeurons(final double[][] data, final Network network) {
        Neuron[] neurons = new Neuron[data.size()];

        for (int i = 0; i < data.length; i++) {
            neurons[i] = createNeuron(data[i][0], network);
        }

        return neurons;
    }

    public static Neuron createNeuron(final double neuronInfo, final Network network) {
        double activation_threshold = neuronInfo;
        Layer neuronLayer = Layer.HIDDEN;
        Neuron neuron = new Neuron(activation_threshold, neuronLayer, network);  // TODO need to parse input data to create neuron
        return neuron;
    }
}
