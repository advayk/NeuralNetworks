import java.util.ArrayList;
import java.util.List;

public class NeuralNet {
    public void func(ArrayList<ArrayList> examples, int num_inputs) {

        System.out.println(examples);
        double value = 0.5;
        List<SensorNeuron> sensor_neurons_list = new ArrayList<>();
        List<HiddenNeuron> hidden_neuron_list = new ArrayList<>();
        List<SensorNeuron> input_list = new ArrayList<>();

        System.out.println(examples);
        int i = 0;
        int k = 0;
        String header = null;

        for (int j = 0; j < examples.size(); j++) {
            ArrayList arrayList = examples.get(j);
        }

        SensorNeuron s_neuron = new SensorNeuron();
        s_neuron.label = i;
//        s_neuron.value = line;  // adds the example to the sensor
        s_neuron.weight = value;
        sensor_neurons_list.add(s_neuron);
        i++;
        k++;

        for (int j = 0; j < sensor_neurons_list.size(); j++) {
            System.out.println(sensor_neurons_list.get(j).getLabel());
            System.out.println(i);
        }
        System.out.println("header");
        System.out.println(header);

        for (int a = 0; a < 100; a++) {
            HiddenNeuron h_neuron = new HiddenNeuron();
            for (int l = 0; l < sensor_neurons_list.size(); l++) {
                input_list.add(sensor_neurons_list.get(l));
            }
            h_neuron.input_neruons = input_list;
            hidden_neuron_list.add(h_neuron);
        }
    }
}
