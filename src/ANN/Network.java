package ANN;

import ANN.Utils.Utils;
import Parsing.data.DataSet;
import Parsing.data.Instance;

import java.util.Arrays;

public class Network {

    private final Neuron[] neurons;
    private final double[][] weights;
    private final float weightDecay;
    private final int numberOfHiddenNeurons;
    private final int numberOfInputNeurons;
    private static final double LEARNING_RATE = 0.01;
    private final int numberOfInputNeuronsIncludingBias;
    private final int numberOfHiddenNeuronsIncludingBias;

    public Network(Neuron[] neurons, double[][] weights, float weightDecay, int numberOfHiddenNeurons) {
        this.neurons = neurons;
        this.weights = weights;
        this.weightDecay = weightDecay;
        this.numberOfHiddenNeurons = numberOfHiddenNeurons;

        // length - output - bias - hidden - bias
        this.numberOfInputNeurons = neurons.length - 1 - 1 - numberOfHiddenNeurons - 1;
        this.numberOfHiddenNeuronsIncludingBias = this.numberOfHiddenNeurons + 1;
        this.numberOfInputNeuronsIncludingBias = this.numberOfInputNeurons + 1;
    }

    public int getNumberOfHiddenNeurons() {
        return numberOfHiddenNeurons;
    }

    public double[][] getWeights() {
        return weights;
    }

    public Neuron[] getInputNeurons() {
        return Arrays.copyOfRange(this.neurons, 0, numberOfInputNeurons, Neuron[].class);
    }

    public Neuron getOutputNeuron() {
        return this.neurons[neurons.length-1];
    }

    public float getWeightDecay() {
        return weightDecay;
    }

    public void trainUntilConvergence(DataSet trainingSet, boolean printProgress) {
        double averageDisparity = Double.POSITIVE_INFINITY;
        double expectedClassValue;

        double previousWeightSum = Double.POSITIVE_INFINITY;
        double currentWeightSum;
        while (averageDisparity >= 0.00001) {
            currentWeightSum = getSumOfWeights();
            for (int instanceIndex = 0; instanceIndex < trainingSet.size(); instanceIndex++) {
                Instance instance = trainingSet.instance(instanceIndex);
                expectedClassValue = instance.classValue();
                feedForward(Utils.getInstanceValuesWithBias(instance));
                backPropagate(expectedClassValue);
            }
            averageDisparity = Math.abs(previousWeightSum - currentWeightSum);
            previousWeightSum = currentWeightSum;
        }
    }

    public void train(DataSet trainingSet, int numberOfTrainingIterations, boolean printProgress) {
        for (int i = 0; i < numberOfTrainingIterations; i++) {
            Utils.printProgress(i, numberOfTrainingIterations);
            for (int instanceIndex = 0; instanceIndex < trainingSet.size(); instanceIndex++) {
                Instance instance = trainingSet.instance(instanceIndex);
                feedForward(Utils.getInstanceValuesWithBias(instance));
                backPropagate(instance.classValue());
            }
        }
    }

    public void backPropagate(double classLabel) {
        double[][] weightChanges = new double [weights.length][];

        for (int i = 0; i < weights.length; i++) {
            weightChanges[i] = Arrays.copyOf(weights[i], weights[i].length);
        }
        Neuron outputNeuron = getOutputNeuron();


        // Back propagate From output layer
        for (int neuron = numberOfInputNeuronsIncludingBias; neuron < neurons.length - 1; neuron++) {
            Neuron hiddenNeuron = neurons[neuron];
            double weightChange = Utils.getWeightChangeValueOutputLayer(hiddenNeuron.getOutputValue(), outputNeuron.getOutputValue(), classLabel);
            weightChanges[neuron][0] = weightChange;
        }

        // Back propagate from hidden layer
        for (int hiddenNeuronIndex = numberOfInputNeuronsIncludingBias; hiddenNeuronIndex < neurons.length - 2; hiddenNeuronIndex++) {
            Neuron hiddenN = neurons[hiddenNeuronIndex];
            for (int neuronIndex = 0; neuronIndex < numberOfInputNeuronsIncludingBias; neuronIndex++) {
                Neuron inputNeuron = neurons[neuronIndex];
                double downstream = (weightChanges[hiddenNeuronIndex][0] * this.weights[hiddenNeuronIndex][0]) / hiddenN.getOutputValue();
                double weightChange = Utils.getWeightChangeValueHiddenLayer(inputNeuron.getOutputValue(), hiddenN.getOutputValue(), downstream);
                weightChanges[neuronIndex][hiddenNeuronIndex - numberOfInputNeuronsIncludingBias] = weightChange;
            }
        }

        // Weight update
        for (int neuronFromIndex = 0; neuronFromIndex < weightChanges.length; neuronFromIndex++) {
            for (int neuronToIndex = 0; neuronToIndex < weightChanges[neuronFromIndex].length; neuronToIndex++) {
                double currentWeight = this.weights[neuronFromIndex][neuronToIndex];
                this.weights[neuronFromIndex][neuronToIndex] -= LEARNING_RATE * weightChanges[neuronFromIndex][neuronToIndex] + (currentWeight * 2 * weightDecay);
            }
        }

    }

    public double[] getNeuronInputWeights(int neuronIndex) {
        Layer neuronLayer = neurons[neuronIndex].getLayer();
        if (neuronLayer == Layer.INPUT)
            throw new IllegalStateException("Only HIDDEN & OUTPUT Layer Neurons have input weights");

        double[] inputWeights;

        if (neuronLayer == Layer.OUTPUT) {
            inputWeights = new double[numberOfHiddenNeuronsIncludingBias];
            for (int i = numberOfInputNeuronsIncludingBias; i < numberOfInputNeuronsIncludingBias + numberOfHiddenNeuronsIncludingBias; i++) {
                inputWeights[i - numberOfInputNeuronsIncludingBias] = this.weights[i][0];
            }
        } else {
            inputWeights = new double[numberOfInputNeuronsIncludingBias];
            for (int i = 0; i < numberOfInputNeuronsIncludingBias; i++) {
                inputWeights[i] = this.weights[i][neuronIndex - numberOfInputNeuronsIncludingBias];
            }
        }

        return inputWeights;
    }

    public double[] getNeuronInputs(int neuronIndex) {
        Layer neuronLayer = neurons[neuronIndex].getLayer();
        assert neuronLayer != Layer.INPUT;

        double[] neuronInputs;

        if (neuronLayer == Layer.HIDDEN) {
            neuronInputs = new double[numberOfInputNeuronsIncludingBias];
            for (int i = 0; i < numberOfInputNeuronsIncludingBias; i++) {
                neuronInputs[i] = this.neurons[i].getOutputValue();
            }
        } else {
            neuronInputs = new double[numberOfHiddenNeuronsIncludingBias];
            for (int i = numberOfInputNeuronsIncludingBias; i < numberOfInputNeuronsIncludingBias + numberOfHiddenNeuronsIncludingBias; i++) {
                neuronInputs[i - numberOfInputNeuronsIncludingBias] = this.neurons[i].getOutputValue();
            }
        }

        return neuronInputs;
    }

    public double feedForward(double[] inputLayerValues) {
        for (int i = 0; i < numberOfInputNeurons; i++) {
            neurons[i].setInputValue(inputLayerValues[i]);
            neurons[i].updateOutput(new double[0], new double[0]);
        }

        /* Feed forward to Hidden Layer */
        for (int neuronIndex = numberOfInputNeuronsIncludingBias; neuronIndex < numberOfInputNeuronsIncludingBias + numberOfHiddenNeurons ; neuronIndex++) {
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
        double[] inputLayerValues = Utils.getInstanceValuesWithBias(instance);
        return feedForward(inputLayerValues);
    }

    public double getSumOfWeights() {
        double sum = 0;
        for (int i = 0; i < this.weights.length; i++)
            for (int j = 0; j < this.weights[i].length; j++)
                sum += this.weights[i][j];
        return sum;
    }
}
