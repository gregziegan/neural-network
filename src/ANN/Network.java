package ANN;

import java.util.Arrays;

public class Network {

    private Neuron[] neurons;
    private double[][] weights;
    private float weightDecay;
    private int numberOfHiddenNeurons;

    public Network(Neuron[] neurons, double[][] weights, float weightDecay, int numberOfHiddenNeurons) {
        this.neurons = neurons;
        this.weights = weights;
        this.weightDecay = weightDecay;
        this.numberOfHiddenNeurons = numberOfHiddenNeurons;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public int getNumberOfHiddenNeurons() {
        return numberOfHiddenNeurons;
    }

    public double[][] getWeights() {
        return weights;
    }

    public Neuron[] getInputNeurons() {
        int numberOfInputNeurons = neurons.length - numberOfHiddenNeurons - 1;
        return Arrays.copyOfRange(this.neurons, 0, numberOfInputNeurons, Neuron[].class);
    }

    public Neuron[] getHiddenNeurons() {
        int hiddenNeuronStartIndex = this.neurons.length - 1 - numberOfHiddenNeurons;
        return Arrays.copyOfRange(this.neurons, hiddenNeuronStartIndex, this.neurons.length - 1, Neuron[].class);
    }

    public Neuron getOutputNeuron() {
        return this.neurons[neurons.length-1];
    }

    public float getWeightDecay() {
        return weightDecay;
    }

}
