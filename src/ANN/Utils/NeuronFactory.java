package ANN.Utils;

import ANN.Bias;
import ANN.Layer;
import ANN.Neuron;

public class NeuronFactory {
    public static Neuron[] createInitialNeurons(int numberOfInputNeurons, int numberOfHiddenNeurons) {
        int numberOfBiases = 2;
        int totalNeurons = numberOfInputNeurons + numberOfHiddenNeurons + 1 + numberOfBiases;
        Neuron[] neurons = new Neuron[totalNeurons];

        for (int i = 0; i < totalNeurons; i++) {
            if (i < numberOfInputNeurons)
                neurons[i] = new Neuron(i, Layer.INPUT);
            else if (i < numberOfInputNeurons + 1)
                neurons[i] = new Bias(i, Layer.INPUT, -1);
            else if (i < totalNeurons - 2)
                neurons[i] = new Neuron(i, Layer.HIDDEN);
            else if (i < totalNeurons - 1)
                neurons[i] = new Bias(i, Layer.HIDDEN, -1);
            else
                neurons[i] = new Neuron(i, Layer.OUTPUT);
        }

        return neurons;
    }
}
