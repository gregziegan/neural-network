package ANN;

public class Bias extends Neuron {

    private final int activationThreshold;

    public Bias(int id, Layer layer, int activationThreshold) {
        super(id, layer);
        this.activationThreshold = activationThreshold;

    }

    @Override
    public void updateOutput(double[] weights, double[] inputValues) {
    }

    @Override
    public double getOutputValue() {
        return activationThreshold;
    }

    @Override
    public String toString() {
        return String.format("<Bias: id: %d, layer: %s>", super.id, getLayer());
    }
}
