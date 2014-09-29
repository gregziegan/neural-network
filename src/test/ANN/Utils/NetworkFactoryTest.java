package test.ANN.Utils; 

import ANN.Layer;
import ANN.Network;
import ANN.Utils.NetworkFactory;
import Parsing.data.Attribute;
import Parsing.data.DataSet;
import Parsing.data.Instance;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import static org.junit.Assert.*;

import java.util.Vector;

public class NetworkFactoryTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testCreateInitialNetwork() throws Exception {
        Vector<Attribute> attributes = new Vector<Attribute>();
        attributes.add(0, new Attribute(0));
        attributes.add(1, new Attribute(0));
        attributes.add(2, new Attribute(1));
        attributes.add(3, new Attribute(1));

        DataSet dataset = new DataSet(attributes, 0);

        Instance[] instances = new Instance[5];
        double nominal = 1;
        double continuous = .5;
        for (int instance = 0; instance < instances.length; instance++) {
            double[] values = new double[]{nominal, nominal, continuous, continuous};
            nominal += nominal;
            continuous += continuous;

            instances[instance] = new Instance(values);
        }
        dataset.add(instances);

        Network network = NetworkFactory.createInitialNetwork(dataset, 1.0f, 5);
        assertEquals(5, network.getNumberOfHiddenNeurons());
        assertEquals(1.0f, network.getWeightDecay(), 0.0f);
        assertEquals(dataset.numAttributes() - 2, network.getInputNeurons().length);
        assertEquals(Layer.OUTPUT, network.getOutputNeuron().getLayer());
    }

    @Test
    public void testGetInitialWeights() throws Exception {
        int numInputNeurons = 10, numHiddenNeurons = 5;
        int expectedWeightArraySize = numInputNeurons + numHiddenNeurons;
        double[][] weights = NetworkFactory.getInitialWeights(numInputNeurons, numHiddenNeurons);
        assertEquals(expectedWeightArraySize, weights.length);
        for (int i = 0; i < weights.length; i++) {
            if (i < numInputNeurons)
                assertEquals(weights[i].length, numHiddenNeurons);
            else
                assertEquals(weights[i].length, 1);
        }
    }


} 
