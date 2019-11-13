import java.util.ArrayList;
import java.util.Objects;

public class Neuron {
    ArrayList<Object> inputs = new ArrayList<Object>();
    ArrayList<Double> weight = new ArrayList<Double>();
    int bias;
    double ActualResult;
    double ErrorSignal;

    public double ActualResult(ArrayList input) {
        double sum = 0;
        for (int i = 0; i < input.size(); i++) {
            sum += (new Double(input.get(i).toString())) * weight.get(i);
            double ActivationResult = activationFunction(sum);
        }
        double ActivationResult = activationFunction(sum);
        return ActivationResult;
    }


    public double activationFunction(double x) {
        double value = (1 / (1 + (Math.exp(-x))));
        return value;
    }
}
