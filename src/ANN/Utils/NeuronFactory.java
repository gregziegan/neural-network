package ANN.Utils;

import ANN.Layer;
import ANN.Neuron;

import java.util.Arrays;

public class NeuronFactory {
    public static Neuron[] createInitialNeurons(int numberOfInputNeurons, int numberOfHiddenNeurons, double[] inputValues) {
        int totalNeurons = numberOfInputNeurons + numberOfHiddenNeurons + 1;
        Neuron[] neurons = new Neuron[totalNeurons];

        for (int i = 0; i < totalNeurons; i++) {
            if (i <= numberOfInputNeurons) {
                neurons[i] = new Neuron(Layer.INPUT, inputValues[i + 1]);  // offset to avoid index attribute
            } else if (i < totalNeurons - 1) {
                neurons[i] = new Neuron(0, Layer.HIDDEN);
            } else {
                neurons[i] = new Neuron(0, Layer.OUTPUT);
            }
        }

        return neurons;
    }

    public static Neuron[] createModifiedNeurons(Neuron[] neurons, double[] biases) {
        int numberOfNeurons = neurons.length;
        Neuron[] modifiedNeurons = Arrays.copyOf(neurons, numberOfNeurons, Neuron[].class);
        for (int i = 0; i < numberOfNeurons; i++) {
            modifiedNeurons[i].setActivationThreshold(biases[i]);
        }
        return modifiedNeurons;
    }
}
