package ANN.Utils;

import ANN.Network;
import ANN.Neuron;
import Parsing.data.DataSet;

import java.util.Random;

public class NetworkFactory {

    public static Network createInitialNetwork(final DataSet trainingSet, final float weightDecay, final int numberOfHiddenNeurons) {
        int numberOfInputNeurons = trainingSet.numAttributes() - 2;  // first attribute is the index, last attribute is the class label

        Neuron[] neurons = NeuronFactory.createInitialNeurons(numberOfInputNeurons, numberOfHiddenNeurons);
        double[][] initialWeights = getInitialWeights(numberOfInputNeurons, numberOfHiddenNeurons);

        return new Network(neurons, initialWeights, weightDecay, numberOfHiddenNeurons);
    }

    public static double[][] getInitialWeights(final int numberOfInputNeurons, final int numberOfHiddenNeurons) {
        double[][] weights = new double[numberOfInputNeurons+numberOfHiddenNeurons][];
        Random random = new Random(12345);

        double[] initialWeightChoices = new double[]{-0.1, 0.1};
        double randomWeight;

        for (int i = 0; i < numberOfInputNeurons; i++) {
            weights[i] = new double[numberOfHiddenNeurons];
            for (int j = 0; j < numberOfHiddenNeurons; j++) {
                randomWeight = initialWeightChoices[random.nextInt(2)];
                weights[i][j] = randomWeight;
            }
        }

        for (int i = numberOfInputNeurons; i < numberOfInputNeurons + numberOfHiddenNeurons; i++) {
            weights[i] = new double[1];
            randomWeight = initialWeightChoices[random.nextInt(2)];
            weights[i][0] = randomWeight;
        }

        return weights;
    }

}
