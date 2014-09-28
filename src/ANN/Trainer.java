package ANN;

import ANN.Utils.NetworkFactory;
import Parsing.data.DataSet;
import Parsing.data.Instance;

public class Trainer {

    private Network originalNetwork;
    private int numberOfTrainingIterations;

    public Trainer(Network network, int numberOfTrainingIterations) {
        this.originalNetwork = network;
        this.numberOfTrainingIterations = numberOfTrainingIterations;
    }

    public static Network getTrainedNetwork(Network network, int numberOfTrainingIterations) {

        double[] biases = new double[network.getNeurons().length];
        double[] weights = new double[network.getWeights().length];

        for (int i = 0; i < numberOfTrainingIterations; i++) {
            // TODO implement training method
        }

        return NetworkFactory.getModifiedNetwork(network, biases, weights);
    }

    public static double getPerformaceMeasure(Network network, DataSet testSet) {

        for (int i = 0; i < testSet.size(); i++) {
            Instance instance = testSet.instance(i);
            network.clas
        }

    }
}
