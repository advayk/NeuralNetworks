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

//    for each input i: compute the product of inputi × weighti
//    compute the sum of the products
//    apply the activation function to the sum
//    the answer is the ActualResult computed by the perceptron

    public void ActualResult(Neuron neuron, double [] input) {
       // System.out.println("lenght of weight list: " + neuron.weight);
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum = sum + input[i] * neuron.weight.get(i);
        }
        myActualResult = activationFunction(sum + (bias*bias_weight));
       // System.out.println("My actual result in neuron class: "+ myActualResult);
    }

    public double return_my_Actualresult() {

        return(myActualResult);
    }

    public double activationFunction(double x) {
        double value = (1 / (1 + (Math.exp(-x))));
        return value;
    }
}
