package ANN.Utils;

import ANN.Connection;
import ANN.Neuron;

import java.util.Arrays;
import java.util.Random;

public class ConnectionFactory {
    public static Connection[] createInitialConnections(final Neuron[] neurons, int numberOfInputNeurons, int numberOfHiddenNeurons) {
        int totalConnections = numberOfInputNeurons * numberOfHiddenNeurons + numberOfHiddenNeurons;
        Connection[] connections = new Connection[totalConnections];
        Random random = new Random(12345);

        double[] initialWeightChoices = new double[]{-0.1, 0.1};
        double randomWeight;
        int k = 0;
        for (int i = 0; i < numberOfInputNeurons; i++) {
            for (int j = 0; j < numberOfHiddenNeurons; j++) {
                randomWeight = initialWeightChoices[random.nextInt(2)];
                connections[i+j+k] = new Connection(neurons[i], neurons[numberOfInputNeurons+j], randomWeight);
            }
            k += numberOfHiddenNeurons;
        }

        return connections;
    }

    public static Connection[] createModifiedConnections(final Connection[] connections, final double[] weights) {
        int numberOfConnections = connections.length;
        Connection[] modifiedConnections = Arrays.copyOf(connections, numberOfConnections, Connection[].class);

        for (int i = 0; i < numberOfConnections; i++) {
            modifiedConnections[i].setWeight(weights[i]);
        }

        return modifiedConnections;
    }

}
