package test.ANN;

import Parsing.data.DataFileProcessor;
import Parsing.data.DataSet;
import Parsing.data.Instance;

public class TestUtils {

    public static DataSet getTestDataSet() {
        String filepath;
        if (System.getProperty("os.name").startsWith("Windows"))
            filepath = "C:\\Users\\MJ\\workspace\\EECS400PA2\\data\\voting\\voting";
        else
            filepath = "/home/greg/dev/school/machine_learning/eecs440-ann/data/voting/voting";

        DataSet metaInfo = DataFileProcessor.readInMetaInfo(filepath);
        DataSet dataSet = DataFileProcessor.readInData(filepath, metaInfo);

        DataSet testSet = new DataSet(dataSet);
        for (int i = 0; i < 10; i++) {
            testSet.add(new Instance(dataSet.instance(i)));
        }

        return testSet;
    }

}
