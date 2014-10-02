package test.ANN.Utils;

import ANN.Utils.Utils;
import Parsing.data.Attribute;
import Parsing.data.DataSet;
import Parsing.data.Instance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testEvaluateSigmoid() throws Exception {
        double value = Utils.evaluateSigmoid(0);
        assertEquals(.5, value, 0);
    }

    @Test
    public void testGetDotProduct() throws Exception {
        double[] arrayOne = new double[]{1, 2, 3, 4, 5};
        double[] arrayTwo = new double[]{6, 7, 8, 9, 10};
        double actualValue = Utils.getDotProduct(arrayOne, arrayTwo);
        double expectedValue = 130;
        assertEquals(expectedValue, actualValue, 0);
    }

    @Test
    public void testNormalizeData() throws Exception {
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
        Double meanNom = dataset.mean(1);
        Double stdNom = dataset.stdDeviation(1);
        Double meanCont = dataset.mean(2);
        Double stdCont = dataset.stdDeviation(2);

        Utils.normalizeDataSet(dataset);

        assertEquals(Utils.normalizeData(1, meanNom, stdNom), dataset.instance(0).value(1), 0);
        assertEquals(Utils.normalizeData(1, meanCont, stdCont), dataset.instance(1).value(2), 0);
    }
}
