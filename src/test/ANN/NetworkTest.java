package test.ANN;

import ANN.Network;
import ANN.Utils.NetworkFactory;
import ANN.Utils.Utils;
import Parsing.data.DataSet;
import Parsing.data.Instance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class NetworkTest {


    @Before
    public void before() throws Exception {

    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testFeedForward() throws Exception {
        DataSet dataSet = TestUtils.getTestDataSet();
        Network network = NetworkFactory.createInitialNetwork(dataSet, 1, 2);

        Instance instance = dataSet.instance(0);
        double[] values = Utils.getInstanceValuesWithBias(instance);

        double[] hiddenValues = new double[3];
        for (int i = 12; i < 14; i++) {
            hiddenValues[i - 12] = Utils.evaluateSigmoid(Utils.getDotProduct(values, network.getNeuronInputWeights(i)));
        }

        hiddenValues[2] = -1;

        assertEquals(Utils.evaluateSigmoid(Utils.getDotProduct(hiddenValues, network.getNeuronInputWeights(15))), network.feedForward(values), 0);

    }

}
