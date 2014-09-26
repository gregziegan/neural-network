package ANN;

import ANN.Utils.Utils;

public class Perceptron {

    private double activationThreshold;

    public Perceptron(double activationThreshold) {
        this.activationThreshold = activationThreshold;
    }

    public double getActivationThreshold() {
        return activationThreshold;
    }

    public void setActivationThreshold(double activationThreshold) {
        this.activationThreshold = activationThreshold;
    }

    public int getOutput(double[] weights, double[] inputs) {

        double u = Utils.getDotProduct(weights, inputs) - activationThreshold;
        double activation_score = Utils.evaluateSigmoid(u);
        if (activation_score >= 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
