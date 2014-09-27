package ANN.Utils;

import ANN.Network;
import ANN.Neuron;
import Parsing.data.DataSet;

import java.util.Random;


public class NetworkFactory {

    public static Network createInitialNetwork(final DataSet trainingSet, final float weightDecay, final int numberOfHiddenNeurons) {
        int numberOfInputNeurons = trainingSet.numAttributes() - 2;  // first attribute is the index, last attribute is the class label
        double[] firstInstanceValues = Utils.getInstanceValues(trainingSet.instance(0));

        Neuron[] neurons = NeuronFactory.createInitialNeurons(numberOfInputNeurons, numberOfHiddenNeurons, firstInstanceValues);
        double[][] initialWeights = getInitialWeights(neurons, numberOfInputNeurons, numberOfHiddenNeurons);

        return new Network(neurons, initialWeights, weightDecay, numberOfHiddenNeurons);
    }

    public static Network getModifiedNetwork(Network network, double[] biases, double[] weights) {
        Neuron[] neurons = NeuronFactory.createModifiedNeurons(network.getNeurons(), biases);
        return new Network(neurons, network.getWeights(), network.getWeightDecay(), network.getNumberOfHiddenNeurons());
    }

    public static double[][] getInitialWeights(final Neuron[] neurons, final int numberOfInputNeurons, final int numberOfHiddenNeurons) {
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

    public static double[][] getUpdatedWeights(final double[][] weights) {
        double[][] updatedWeights = new double[weights.length][weights[0].length];

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                // TODO updatedWeights[i][j] =
            }
        }

        return updatedWeights;
    }
}
