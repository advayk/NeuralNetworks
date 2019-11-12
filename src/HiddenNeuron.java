import java.util.ArrayList;

public class HiddenNeuron {

    ArrayList<Double> weight = new ArrayList<Double>();
    ArrayList<String> sensorNeuronList;

    int error_signals;
    int bias;
    double value;
    double hidden_error_signal() {
        return 0;
    }

    public ArrayList get_weight(){
        return weight;
    }

    public void add_weight(double x) {
        weight.add(x);
    }

    public int get_bias() {

        return bias;
    }

    public void value(Double value){
        value = value;
    }




}



