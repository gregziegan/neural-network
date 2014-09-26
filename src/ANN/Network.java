package ANN;

import java.util.ArrayList;

public class Network {

    private Neuron[] neurons;
    private Connection[] connections;

    private float weightDecay;
    private int numberOfHiddenNeurons;

    public Network(Neuron[] neurons, Connection[] connections, float weightDecay, int numberOfHiddenNeurons) {
        this.neurons = neurons;
        this.connections = connections;
        this.weightDecay = weightDecay;
        this.numberOfHiddenNeurons = numberOfHiddenNeurons;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public void setNeurons(Neuron[] neurons) {
        this.neurons = neurons;
    }

    public Connection[] getConnections() {
        return connections;
    }

    public void setConnections(Connection[] connections) {
        this.connections = connections;
    }

    public double[] getWeights(final Neuron neuron) {

        ArrayList<Double> weights_list = new ArrayList<Double>();

        for (Connection connection : getConnections()) {
            if (connection.getTo().equals(neuron)) {
                weights_list.add(connection.getWeight());
            }
        }

        double[] weights = new double[weights_list.size()];

        for (int i = 0; i < weights_list.size(); i++) {
            weights[i] = weights_list.get(i);
        }

        return weights;
    }
}
