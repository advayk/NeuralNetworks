import java.util.List;

public class HiddenNeuron {
    int weights[];
    List<SensorNeuron> input_neruons;
    int output_neurons[];
    int error_signals[];
    int bias;


    public int[] get_weight() {

        return weights;
    }

    public List<SensorNeuron> get_input_neruons() {
        return input_neruons;
    }

    public int[] get_output_neurons() {

        return output_neurons;
    }

    public int[] get_error_signals() {
        return error_signals;

    }

    public int get_bias() {

        return bias;
    }


}



