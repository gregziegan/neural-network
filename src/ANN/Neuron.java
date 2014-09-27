package ANN;

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

}
