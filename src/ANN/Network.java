package ANN;

import ANN.Utils.Utils;
import Parsing.data.DataSet;
import Parsing.data.Instance;

import java.util.Arrays;

public class Network {

    private Neuron[] neurons;
    private double[][] weights;
    private float weightDecay;
    private int numberOfHiddenNeurons;
    private int numberOfInputNeurons;
    private static final double LEARNING_RATE = 0.01;

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

    public void trainUntilConvergence(DataSet trainingSet) {
        double averageDisparity = Double.POSITIVE_INFINITY;
        double expectedClassValue;
        double actualClassValue;

        while (averageDisparity >= 0.01) {
            averageDisparity = Double.POSITIVE_INFINITY;
            for (int instanceIndex = 0; instanceIndex < trainingSet.size(); instanceIndex++) {
                Instance instance = trainingSet.instance(instanceIndex);
                expectedClassValue = instance.classValue();
                feedForward(Utils.getInstanceValues(instance));
                backPropagate(expectedClassValue);
                actualClassValue = getOutputNeuron().getOutputValue();
                averageDisparity += Math.abs(expectedClassValue - actualClassValue);
            }
            averageDisparity = averageDisparity / trainingSet.size();
        }
    }

    public void train(DataSet trainingSet, int numberOfTrainingIterations) {
        for (int i = 0; i < numberOfTrainingIterations; i++) {
            for (int instanceIndex = 0; instanceIndex < trainingSet.size(); instanceIndex++) {
                Instance instance = trainingSet.instance(instanceIndex);
                feedForward(Utils.getInstanceValues(instance));
                backPropagate(instance.classValue());
            }
        }
    }

    public void backPropagate(double classLabel) {
        double[][] weightChanges = weights.clone();
        Neuron outputNeuron = getOutputNeuron();

        // Back propagate From output layer
        for (int neuron = numberOfInputNeurons; neuron < numberOfHiddenNeurons; neuron++) {
            Neuron hiddenNeuron = neurons[neuron];
            double weightChange = Utils.getWeightChangeValueOutputLayer(hiddenNeuron.getOutputValue(), outputNeuron.getOutputValue(), classLabel);
            weightChanges[neuron][0] = weightChange;
        }

        // Back propagate from hidden layer
        for (int hiddenNeuron = numberOfInputNeurons; hiddenNeuron < numberOfHiddenNeurons; hiddenNeuron++) {
            Neuron hiddenN = neurons[hiddenNeuron];
            for (int neuron = 0; neuron < numberOfInputNeurons; neuron++) {
                Neuron inputNeuron = neurons[neuron];
                double downstream = (weightChanges[hiddenNeuron][0] * this.weights[hiddenNeuron][0]) / hiddenN.getOutputValue(); // TODO figure out what dividing by is
                double weightChange = Utils.getWeightChangeValueHiddenLayer(inputNeuron.getOutputValue(), hiddenN.getOutputValue(), downstream);
                weightChanges[neuron][hiddenNeuron] = weightChange; // TODO add learning rate of 0.01 here?
            }
        }

        // Weight update
        for (int neuronFrom = 0 ; neuronFrom < weightChanges.length; neuronFrom++) {
            for (int neuronTo = 0; neuronTo < weightChanges[neuronFrom].length; neuronTo++) {
                this.weights[neuronFrom][neuronTo] -= LEARNING_RATE * weightChanges[neuronFrom][neuronTo];
            }
        }

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

    public double classify(Instance instance) {
        double[] inputLayerValues = Utils.getInstanceValues(instance);
        double certainty = feedForward(inputLayerValues);
        if (certainty >= 0.5)
            return 1.0;
        return 0.0;
    }
}
