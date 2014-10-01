package ANN;

public class Bias extends Neuron {

    private final int activationThreshold;

    public Bias(int id, Layer layer, int activationThreshold) {
        super(id, layer);
        this.activationThreshold = activationThreshold;

    }

    public int getActivationThreshold() {
        return activationThreshold;
    }

    @Override
    public double getInputValue() {
        return this.activationThreshold;
    }

    @Override
    public void updateOutput(double[] weights, double[] inputValues) {
        // ...
    }

    @Override
    public double getOutputValue() {
        if (getLayer() == Layer.INPUT)
            super.
    }
}
