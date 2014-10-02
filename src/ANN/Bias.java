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
    public String toString() {
        return String.format("<Bias: id: %d, layer: %s>", super.id, getLayer());
    }
}
