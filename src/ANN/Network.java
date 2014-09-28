package ANN;

import Parsing.data.Instance;

import java.util.Arrays;

public class Network {

    private Neuron[] neurons;
    private double[][] weights;
    private float weightDecay;
    private int numberOfHiddenNeurons;
    private int numberOfInputNeurons;

    public Network(Neuron[] neurons, double[][] weights, float weightDecay, int numberOfHiddenNeurons) {
        this.neurons = neurons;
        this.weights = weights;
        this.weightDecay = weightDecay;
        this.numberOfHiddenNeurons = numberOfHiddenNeurons;
        this.numberOfInputNeurons = neurons.length - numberOfHiddenNeurons - 1;
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

    public double[] getNeuronInputWeights(int neuronIndex) {
        Layer neuronLayer = neurons[neuronIndex].getLayer();
        assert neuronLayer != Layer.INPUT;

        double[] inputWeights;

        if (neuronLayer == Layer.OUTPUT) {
            inputWeights = new double[numberOfHiddenNeurons];
            for (int i = numberOfInputNeurons; i < numberOfInputNeurons + numberOfHiddenNeurons; i++) {
                inputWeights[i - numberOfInputNeurons] = this.weights[i][neuronIndex];
            }
        } else {
            inputWeights = new double[numberOfInputNeurons];
            for (int i = 0; i < numberOfInputNeurons; i++) {
                inputWeights[i] = this.weights[i][neuronIndex];
            }
        }

        return inputWeights;
    }

    public double[] getNeuronInputs(int neuronIndex) {
        Layer neuronLayer = neurons[neuronIndex].getLayer();
        assert neuronLayer != Layer.INPUT;

        double[] neuronInputs;

        if (neuronLayer == Layer.HIDDEN) {
            neuronInputs = new double[numberOfInputNeurons];
            for (int i = 0; i < numberOfInputNeurons; i++) {
                neuronInputs[i] = this.neurons[i].getOutputValue();
            }
        } else {
            neuronInputs = new double[numberOfHiddenNeurons];
            for (int i = numberOfInputNeurons; i < numberOfInputNeurons + numberOfHiddenNeurons; i++) {
                neuronInputs[i - numberOfInputNeurons] = this.neurons[i].getOutputValue();
            }
        }

        return neuronInputs;
    }

    public double feedForward(double[] inputLayerValues) {
        for (int i = 0; i < numberOfInputNeurons; i++) {
            neurons[i].setInputValue(inputLayerValues[i]);
        }

        /* Feed forward to Hidden Layer */
        for (int neuronIndex = numberOfInputNeurons; neuronIndex < numberOfInputNeurons + numberOfHiddenNeurons; neuronIndex++) {
            double[] inputWeights = getNeuronInputWeights(neuronIndex);
            double[] inputValues = getNeuronInputs(neuronIndex);
            neurons[neuronIndex].updateOutput(inputWeights, inputValues);
        }

        /* Feed forward to Output Layer */
        int outputNeuronIndex = neurons.length - 1;

        double[] inputWeights = getNeuronInputWeights(outputNeuronIndex);
        double[] inputValues = getNeuronInputs(outputNeuronIndex);
        neurons[outputNeuronIndex].updateOutput(inputWeights, inputValues);

        return neurons[outputNeuronIndex].getOutputValue();
    }

    public boolean classify(Instance instance) {
        double[] inputLayerValues = new double[instance.length() - 2];
        for (int i = 1; i < instance.length() - 1; i++) {
            inputLayerValues[i] = instance.value(i);
        }
        double certainty = feedForward(inputLayerValues);

        return certainty >= 0.5;
    }
}
