package ANN;

import ANN.Utils.Utils;

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

    public int getOutput(Neuron neuron) {

        Connection[] connections = getInputs(neuron);
        double[] weights = new double[connections.length];
        double[] inputs = new double[connections.length];

        for (int i = 0; i < connections.length; i++) {
            weights[i] = connections[i].getWeight();
            inputs[i] = getOutput(connections[i].getFrom());
        }

        double u = Utils.getDotProduct(weights, inputs) - neuron.getActivationThreshold();
        double activation_score = Utils.evaluateSigmoid(u);
        if (activation_score >= 0.5) {
            return 1;
        } else {
            return 0;
        }
    }

    public Connection[] getInputs(Neuron neuron) {
        ArrayList<Connection> inputs_list = new ArrayList<Connection>();

        for (Connection connection : getConnections()) {
            if (connection.getTo().equals(neuron)) {
                inputs_list.add(connection);
            }
        }

        return inputs_list.toArray(new Connection[inputs_list.size()]);
    }
}
