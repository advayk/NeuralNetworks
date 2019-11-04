import java.util.List;

public class HiddenNeuron {
    double weight;
    List<SensorNeuron> input_neruons;
    List<OutputNeuron> ouputNeuronList;
    int error_signals[];
    int bias;
    int label;


    public int get_weight() {

        return weight;
    }



    public int[] get_error_signals() {
        return error_signals;

    }

    public int get_bias() {

        return bias;
    }

    public int getLabel(){

        return label;
    }
    public List<OutputNeuron>  ouputNeuronList(){
        return ouputNeuronList;
    }




}



