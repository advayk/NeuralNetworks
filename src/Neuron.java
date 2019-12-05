import java.util.ArrayList;
import java.util.Objects;

public class Neuron {
    ArrayList<Object> inputs = new ArrayList<Object>();
    public ArrayList<Double> weight = new ArrayList<Double>();
    double bias;
    double myActualResult;
    double ErrorSignal;
    double bias_weight;
    double label;
    double CorrectResult;

    public Neuron() {

    }




    public void ActualResult(double [] input) {
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum  += input[i] * weight.get(i);
        }
        myActualResult = activationFunction(sum + (bias*bias_weight));
    }

    public double return_my_Actualresult() {
        return(myActualResult);
    }
    public double activationFunction(double x) {
        return (1 / (1 + (Math.exp(-x))));
    }
}
