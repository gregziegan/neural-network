package ANN.Utils;

import Parsing.data.DataSet;
import Parsing.data.Instance;

import java.util.Random;

public class Utils {

    public static double evaluateSigmoid(final double exponent) {

        return 1 / (1 + Math.exp(-exponent) );
    }

    public static double getDotProduct(final double[] a, final double[] b){
        if(a.length != b.length){
            throw new IllegalArgumentException("The dimensions have to be equal!");
        }
        double sum = 0;
        for(int i = 0; i < a.length; i++){
            sum += a[i] * b[i];
        }
        return sum;
    }

    public static double[] getInstanceValues(final Instance instance) {
        double[] values = new double[instance.length()];
        for (int i = 0; i < instance.length(); i++) {
            values[i] = instance.value(i);
        }
        return values;
    }

    public static DataSet getShuffledDataSet(final DataSet dataSet) {
        DataSet shuffledDataSet = new DataSet(dataSet);

        Instance[] instances = new Instance[dataSet.size()];
        for (int i = 0; i < dataSet.size(); i++) {
            instances[i] = dataSet.instance(i);
        }

        shuffleArray(instances);

        shuffledDataSet.add(instances);

        return shuffledDataSet;
    }

    public static void shuffleArray(final Object[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            Object a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static DataSet[] getTrainingSets(final DataSet dataSet, final int numberOfSets) {
        DataSet[] trainingSets = new DataSet[numberOfSets];

        for (int i = 0; i < numberOfSets; i++) {
            trainingSets[i] = new DataSet(dataSet);
            Instance[] instances = new Instance[dataSet.size()];
            for (int j = 0; j < dataSet.size() / 5; j++) {
                instances[j] = dataSet.instance(j);
            }
            trainingSets[i].add(instances);
        }

        return trainingSets;
    }

}
