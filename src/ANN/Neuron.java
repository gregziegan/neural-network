package ANN;

import ANN.Utils.Utils;



public class Neuron {

    private double activationThreshold;
    private Layer layer;
    private Network network;

    public Neuron(double activationThreshold, Layer layer, Network network) {
        this.activationThreshold = activationThreshold;
        this.layer = layer;
        this.network = network;
    }

    public double getActivationThreshold() {
        return activationThreshold;
    }

    public void setActivationThreshold(double activationThreshold) {
        this.activationThreshold = activationThreshold;
    }

    public Layer getLayer() {
        return layer;
    }

    public int getOutput(double[] inputs) {

        double[] weights = network.getWeights(this);
        double u = Utils.getDotProduct(weights, inputs) - activationThreshold;
        double activation_score = Utils.evaluateSigmoid(u);
        if (activation_score >= 0.5) {
            return 1;
        } else {
            return 0;
        }
    }
}
