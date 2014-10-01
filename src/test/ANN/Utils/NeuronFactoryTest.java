package test.ANN.Utils;

import ANN.Neuron;
import ANN.Utils.NeuronFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NeuronFactoryTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testCreateInitialNeurons() throws Exception {
        int numInputNeurons = 10;
        int numHiddenNeurons = 10;
        int numBiases = 2;
        Neuron[] neurons = NeuronFactory.createInitialNeurons(numInputNeurons, numHiddenNeurons);
        assertEquals(numInputNeurons + numHiddenNeurons + 1 + numBiases, neurons.length, 0);
    }

} 
