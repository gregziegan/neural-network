package test.ANN; 

import ANN.Layer;
import ANN.Network;
import ANN.Neuron;
import ANN.Utils.NetworkFactory;
import ANN.Utils.Utils;
import Parsing.data.DataSet;
import Parsing.data.Instance;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 


public class NetworkTest { 


@Before
public void before() throws Exception {

} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getInputNeurons() 
* 
*/ 
@Test
public void testGetInputNeurons() throws Exception { 
   DataSet dataSet =  TestUtils.getTestDataSet();
    Network network = NetworkFactory.createInitialNetwork(dataSet, 1, 2);
    double[][] weights = network.getWeights();
    for (int i = 0; i < weights.length; i++) {
        for (int j = 0; j < weights[i].length; j++) {
            System.out.print(String.format("%f ", weights[i][j]));
        }
        System.out.println();
    }
    Instance instance = dataSet.instance(0);
    double[] values = Utils.getInstanceValuesWithBias(instance);

    for (int i = 0; i < values.length; i++) {
        System.out.println(values[i]);

    }

    double[] hiddenValues = new double[3];
    for (int i = 12; i < 14; i++) {
      hiddenValues[i - 12] = Utils.evaluateSigmoid(Utils.getDotProduct(values, network.getNeuronInputWeights(i)));
    }

    hiddenValues[2] = -1;

    System.out.println(Utils.evaluateSigmoid(Utils.getDotProduct(hiddenValues, network.getNeuronInputWeights(15))));

    System.out.println(network.feedForward(values));
}


    /**
* 
* Method: getHiddenNeurons() 
* 
*/ 
@Test
public void testGetHiddenNeurons() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getOutputNeuron() 
* 
*/ 
@Test
public void testGetOutputNeuron() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getWeightDecay() 
* 
*/ 
@Test
public void testGetWeightDecay() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: trainUntilConvergence(DataSet trainingSet) 
* 
*/ 
@Test
public void testTrainUntilConvergence() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: train(DataSet trainingSet, int numberOfTrainingIterations) 
* 
*/ 
@Test
public void testTrain() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: backPropagate(double classLabel) 
* 
*/ 
@Test
public void testBackPropagate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getNeuronInputWeights(int neuronIndex) 
* 
*/ 
@Test
public void testGetNeuronInputWeights() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getNeuronInputs(int neuronIndex) 
* 
*/ 
@Test
public void testGetNeuronInputs() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: feedForward(double[] inputLayerValues) 
* 
*/ 
@Test
public void testFeedForward() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: classify(Instance instance) 
* 
*/ 
@Test
public void testClassify() throws Exception { 
//TODO: Test goes here... 
} 


} 
