import java.util.ArrayList;
import java.util.List;

public class NeuralNet {
    public void func(ArrayList<ArrayList> examples, int num_inputs) {

        int num_hidden_neurons = 0;
        double value = 0.5;


        System.out.println(examples);
        int i = 0;
        int k = 0;
        String header = null;

        // sensor inputs
        value = 0.5;
        List<SensorNeuron> sensor_neurons_list = new ArrayList<>();
        for (int j = 0; j < examples.size(); j++) {
            ArrayList arrayList = examples.get(j);
            SensorNeuron s_neuron = new SensorNeuron();
            s_neuron.label = j;
            s_neuron.weight = value;

            List<HiddenNeuron> sensor_to_hidden_list = new ArrayList<>();
            for (int l = 0; l < num_hidden_neurons; l++) {
                HiddenNeuron HN = new HiddenNeuron();
                HN.bias = 1;
                HN.label = l;
                HN.weight = value;
                sensor_to_hidden_list.add(HN);

                List<OutputNeuron> hidden_to_output_list = new ArrayList<>();
                for (int v = 0; v < sensor_to_hidden_list.size(); v++) {
                    OutputNeuron ON = new OutputNeuron();
                    ON.label = v;
                    ON.bias = 1;
                    hidden_to_output_list.add(ON);
                }
                HN.ouputNeuronList = hidden_to_output_list;
            }
            s_neuron.hiddenNeuronList = sensor_to_hidden_list;
            sensor_neurons_list.add(s_neuron);
        }
//        an activation function, such as the logistic function 1 / (1 + e-x)
        activationFunction(2);
    }

    public void activationFunction(int x) {
        double value = (1 / (1 + (1/Math.exp(x))));
        System.out.println("Activation function value: " + value);

    }
}