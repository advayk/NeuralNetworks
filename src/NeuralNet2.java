import java.util.ArrayList;

//https://www.java67.com/2016/08/how-to-replace-element-of-arraylist-in-java.html

public class NeuralNet2 {
    ArrayList sensor_values_list = new ArrayList<Double>();
    ArrayList hidden_values_list = new ArrayList<Double>();
    ArrayList output_of_network = new ArrayList<Double>();

    ArrayList<Neuron> hidden_neuron_list = new ArrayList<Neuron>();
    ArrayList<Neuron> output_neuron_list = new ArrayList<Neuron>();
    ArrayList<String> actual_category_value_list = new ArrayList<String>();

    int number_of_hidden_neurons = 2;
    int number_of_output_neuron = 2;
    double LearningRate = 1.0;

    public void create_neural_net(ArrayList<String> examples, int num_inputs, int categories) {
        System.out.println(examples);
        System.out.println("Running");
        for (int i = 1; i <= (examples.size()-1); i++) {
            System.out.println(examples.get(i));
            sensor_values_list.add(examples.get(i));
        }

        for (int i = 0; i < 1; i++) {
            actual_category_value_list.add(examples.get(i));

        }

        for (int i = 0; i < number_of_hidden_neurons; i++) {
            Neuron HN = new Neuron();
            HN.inputs = sensor_values_list;
            HN.bias = 1;
            System.out.println("size:"  + (sensor_values_list.size()));
            for (int j = 0; j < sensor_values_list.size(); j++) {
                double weight = Math.random();
                HN.weight.add(weight);
            }
            hidden_neuron_list.add(HN);
        }
        for (int i = 0; i < number_of_output_neuron; i++) {
            Neuron ON = new Neuron();
            ON.inputs = hidden_values_list;
            ON.bias = 1;
            System.out.println("size:"  + (sensor_values_list.size()));
            for (int j = 0; j < sensor_values_list.size(); j++) {
                double weight = Math.random();
                ON.weight.add(weight);
            }
            output_neuron_list.add(ON);
        }

        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            Neuron HN = hidden_neuron_list.get(i);
            double HiddenResult = HN.ActualResult(sensor_values_list);
            HN.ActualResult = HiddenResult;
            hidden_values_list.add(HiddenResult);
        }

        //        for each output neuron o, treat it like a perceptron, using the HiddenResults of the hidden layer for inputs

        for (int i = 0; i < output_neuron_list.size(); i++) {
            Neuron ON = output_neuron_list.get(i);
            double HiddenResult = ON.ActualResult(hidden_values_list);
            ON.ActualResult = HiddenResult;
            output_of_network.add(ON);
        }
        System.out.println(output_neuron_list);
        System.out.println(output_neuron_list.size());
        System.out.println(hidden_neuron_list);

        calculate_error_signals();
        UpdateWeights();

    }


    public void calculate_error_signals() {
        for (int i = 0; i < output_neuron_list.size()-1; i++) {
            Neuron ON = output_neuron_list.get(i);
            double OutputErrorSignal = (new Double(actual_category_value_list.get(0).toString()) - ON.ActualResult)*(ON.ActualResult)*(1-ON.ActualResult);
            ON.ErrorSignal = OutputErrorSignal;
        }

        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            Neuron HN = hidden_neuron_list.get(i);
            double sum = 0.0;
            for (int j = 0; j < output_neuron_list.size(); j++) {
                Neuron ON = output_neuron_list.get(j);
                for (int k = 0; k < ON.weight.size(); k++) {
                    sum+= ON.ErrorSignal*ON.weight.get(k);
                }
            }
            HN.ErrorSignal = HN.ActualResult*(1-HN.ActualResult);
        }
    }


    public void UpdateWeights() {
        for (int i = 0; i < output_neuron_list.size(); i++) {
            Neuron ON = output_neuron_list.get(i);
            for (int j = 0; j < hidden_neuron_list.size(); j++) {
                Neuron HN = hidden_neuron_list.get(j);
                for (int k = 0; k < ON.weight.size(); k++) {
                    ON.weight.set(k, ON.weight.get(k) + ON.ErrorSignal*HN.ActualResult*LearningRate);
                }
            }
        }
        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            Neuron HN = hidden_neuron_list.get(i);
            for (int j = 0; j < sensor_values_list.size(); j++) {
                for (int k = 0; k < HN.weight.size(); k++) {
                    HN.weight.set(k, HN.weight.get(k) + HN.ErrorSignal*(new Double(sensor_values_list.get(j).toString())*LearningRate));
                }
            }
        }
    }
}
