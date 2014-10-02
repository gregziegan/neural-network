package ANN;

import ANN.Utils.Utils;

public class Neuron {

    public final int id;
    private Layer layer;
    protected double inputValue;
    protected double outputValue;

    public Neuron(int id, Layer layer) {
        this.id = id;
        this.layer = layer;
    }

    public void setInputValue(double inputValue) {
        if (layer != Layer.INPUT)
            throw new IllegalStateException("Non-input layer neurons do not have a single input value");
        this.inputValue = inputValue;
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
        else {
            double u = Utils.getDotProduct(weights, inputValues);
            this.outputValue = Utils.evaluateSigmoid(u);
        }
    }

    public String toString() {
        return String.format("<Neuron: id: %d layer: %s>", this.id, this.layer);
    }
}
