package ANN;

import ANN.Utils.Utils;

public class Neuron {

    private double activationThreshold;
    private Layer layer;
    private double inputValue;

    public Neuron(double activationThreshold, Layer layer) {
        this.activationThreshold = activationThreshold;
        this.layer = layer;
    }

    public Neuron(Layer layer, double inputUnit) {
        assert layer == Layer.INPUT;
        this.layer = layer;
        this.inputValue = inputUnit;
        this.activationThreshold = 0;
    }

    public double getActivationThreshold() {
        assert layer != Layer.INPUT;
        return activationThreshold;
    }

    public void setActivationThreshold(double activationThreshold) {
        assert layer != Layer.INPUT;
        this.activationThreshold = activationThreshold;
    }

    public void setInputValue(double inputValue) {
        assert layer == Layer.INPUT;
        this.inputValue = inputValue;
    }

    public double getInputValue() {
        assert layer == Layer.INPUT;
        return inputValue;
    }

    public Layer getLayer() {
        return layer;
    }

    public double getOutput(double[] weights, double[] inputsValues) {
        double u = Utils.getDotProduct(weights, inputsValues) - activationThreshold;
        double activation_score = Utils.evaluateSigmoid(u);
        if (activation_score >= 0.5) {
            return 1;
        } else {
            return 0;
        }
    }

    public String toString() {
        String hashCode = Integer.toHexString(System.identityHashCode(this));
        return "<Neuron: Layer: " + this.layer + " Addr: " + hashCode.substring(0, 5) + ">";
    }
}
