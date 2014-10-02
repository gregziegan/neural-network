package ANN.Training;

import Parsing.data.DataSet;

public class CrossValidation {

    private final DataSet[] trainingSets;
    private final DataSet[] validationSets;

    public CrossValidation(DataSet[] trainingSets, DataSet[] validationSets) {
        this.trainingSets = trainingSets;
        this.validationSets = validationSets;
    }

    public DataSet[] getTrainingSets() {
        return trainingSets;
    }

    public DataSet[] getValidationSets() {
        return validationSets;
    }

}
