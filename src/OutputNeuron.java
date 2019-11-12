import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OutputNeuron {
    int label;
    double myvalue;
    ArrayList<Double> weight = new ArrayList<Double>();
    public ArrayList<HiddenNeuron> hiddenNeuronList;


    int bias;

    public int getLabel(int label){
        return label;
    }

    public void value(Double value){
        myvalue = value;
    }

    public Double get_value() {
        return myvalue;
    }


    public ArrayList get_weight(){
        return weight;
    }

    public double bias(Double bias){
        return bias;
    }

}



