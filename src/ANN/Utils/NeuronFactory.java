package ANN.Utils;

import ANN.Layer;
import ANN.Network;
import ANN.Neuron;
import Parsing.data.DataSet;

public class NeuronFactory {
    public static Neuron[] createNeurons(final double[][] data) {
        Neuron[] neurons = new Neuron[data.length];

        for (int i = 0; i < data.length; i++) {
            neurons[i] = createNeuron(data[i][0]);
        }

        return neurons;
    }

    public static Neuron createNeuron(final double neuronInfo) {
        double activation_threshold = neuronInfo;
        Layer neuronLayer = Layer.HIDDEN;
        Neuron neuron = new Neuron(activation_threshold, neuronLayer);  // TODO need to parse input data to create neuron
        return neuron;
    }
}
