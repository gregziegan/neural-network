package ANN.Utils;

import ANN.Connection;
import ANN.Network;
import ANN.Neuron;

public class ConnectionFactory {
    public static Connection[] createConnections(final String[] data, final Network network) {
        Connection[] connections = new Connection[data.length];

        for (int i = 0; i < connections.length; i++) {
            connections[i] = createConnection(data[i]);
        }

        return connections;
    }

    public static Connection createConnection(final String on_info) {
        int weight = 1;
        Neuron fromNeuron = connection_info[0];
        Neuron toNeuron = connection_info[1];
        Connection connection = new Connection(fromNeuron, toNeuron, weight); // TODO parse input data to create a connection
        return connection;
    }
}
