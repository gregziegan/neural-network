package ANN.Utils;

import ANN.Layer;
import ANN.Neuron;

public class NeuronFactory {
    public static Neuron[] createInitialNeurons(int numberOfInputNeurons, int numberOfHiddenNeurons) {
        int totalNeurons = numberOfInputNeurons + numberOfHiddenNeurons + 1;
        Neuron[] neurons = new Neuron[totalNeurons];

        for (int i = 0; i < totalNeurons; i++) {
            if (i < numberOfInputNeurons) {
                neurons[i] = new Neuron(i, Layer.INPUT);
            } else if (i < totalNeurons - 1) {
                neurons[i] = new Neuron(i, Layer.HIDDEN, -1); // TODO is -1 the correct initial bias?
            } else {
                neurons[i] = new Neuron(i, Layer.OUTPUT, -1);
            }
        }

        return neurons;
    }
}
