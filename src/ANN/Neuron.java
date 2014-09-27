package ANN;

public class Neuron {

    private double activationThreshold;
    private Layer layer;
    private double inputUnit;

    public Neuron(double activationThreshold, Layer layer) {
        this.activationThreshold = activationThreshold;
        this.layer = layer;
    }

    public Neuron(Layer layer, double inputUnit) {
        assert layer == Layer.INPUT;
        this.inputUnit = inputUnit;
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

    public void setInputUnit(double inputUnit) {
        assert layer == Layer.INPUT;
        this.inputUnit = inputUnit;
    }

    public double getInputUnit() {
        assert layer == Layer.INPUT;
        return inputUnit;
    }

    public Layer getLayer() {
        return layer;
    }

}
