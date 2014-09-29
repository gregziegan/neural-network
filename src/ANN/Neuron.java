package ANN;

import ANN.Utils.Utils;

public class Neuron {

    public final int id;
    private double activationThreshold;
    private Layer layer;
    private double inputValue;
    private double outputValue;

    public Neuron(int id, Layer layer) {
        this.id = id;
        this.layer = layer;
    }

    public Neuron(int id, Layer layer, double activationThreshold) {
        this(id, layer);
        this.activationThreshold = activationThreshold;
    }

    public double getActivationThreshold() {
        if (layer == Layer.INPUT)
            throw new IllegalStateException("Input Neuron does not have an activation threshold.");
        return activationThreshold;
    }

    public void setActivationThreshold(double activationThreshold) {
        if (layer == Layer.INPUT)
            throw new IllegalStateException("Input Neuron does not have an activation threshold.");
        this.activationThreshold = activationThreshold;
    }

    public void setInputValue(double inputValue) {
        if (layer != Layer.INPUT)
            throw new IllegalStateException("Non-input layer neurons do not have a single input value");
        this.inputValue = inputValue;
    }

    public double getInputValue() {
        if (layer != Layer.INPUT)
            throw new IllegalStateException("Non-input layer neurons do not have a single input value");
        return inputValue;
    }

    public double getOutputValue() {
        return outputValue;
    }

    public Layer getLayer() {
        return layer;
    }

    public void updateOutput(double[] weights, double[] inputValues) {
        if (layer == Layer.INPUT)
            this.outputValue = inputValue;
        double u = Utils.getDotProduct(weights, inputValues) - activationThreshold;
        this.outputValue = Utils.evaluateSigmoid(u);
    }

    public String toString() {
        return String.format("<Neuron: id: %d layer: %s>", this.id, this.layer);
    }
}
