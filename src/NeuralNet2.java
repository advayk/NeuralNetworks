import java.util.ArrayList;

public  class NeuralNet2 {
    private double learning_rate;
    ArrayList output_of_network = new ArrayList<Double>();

    private ArrayList<Neuron> hidden_neuron_list = new ArrayList<Neuron>();
    private ArrayList<Neuron> output_neuron_list = new ArrayList<Neuron>();
    private Double actual_category_value;

    private double [] sensor_values_list;
    private double [] hidden_sensor_values_list;
    private double [] output_sensor_values_list;

    public NeuralNet2(int num_input_neuron, int num_hidden_neuron,int num_output_neuron, double LearningRate) {
        set_topology(num_input_neuron, num_hidden_neuron, num_output_neuron);
        initialize_weights(num_input_neuron, num_hidden_neuron);
        learning_rate = LearningRate;
        sensor_values_list = new double [num_input_neuron];
        hidden_sensor_values_list = new double [num_hidden_neuron];
        output_sensor_values_list = new double [num_output_neuron];
    }

    public boolean read_in_example(ArrayList<Double> example) {
        for (int i = 1; i < example.size(); i++) {
            sensor_values_list[i-1] = example.get(i);
        }
        actual_category_value = example.get(0);
        for (int i = 0; i < output_neuron_list.size(); i++) {
            Neuron ON = output_neuron_list.get(i);
            if(ON.label == actual_category_value) {
                ON.CorrectResult = 1;
            }
            else{
                ON.CorrectResult = 0;
            }
        }
        run_neural_net_on_example();
        double output = output_neural_net();
        calculate_error_signals();
        update_weights();

        if(actual_category_value == output) {
            return true;
        }
        else {
            return false;
        }
    }

    public void set_topology(int num_input_neuron, int num_hidden_neuron, int num_output_neuron) {
        for (int i = 0; i < num_hidden_neuron; i++) {
            Neuron HN = new Neuron();
            HN.bias = 1;
            hidden_neuron_list.add(HN);
        }

        for (int i = 0; i < num_output_neuron; i++) {
            Neuron ON = new Neuron();
            ON.bias = 1;
            ON.label = i;
            output_neuron_list.add(ON);
        }
    }

    public void initialize_weights(int num_input_neuron, int number_of_hidden_neurons) {
        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            Neuron HN = hidden_neuron_list.get(i);
            HN.bias_weight = ((Math.random()-0.5)/10);
            for (int j = 0; j < num_input_neuron; j++) {
                double weight = ((Math.random()-0.5)/10);
                HN.weight.add(weight);
            }
        }
        for (int i = 0; i < output_neuron_list.size(); i++) {
            Neuron ON = output_neuron_list.get(i);
            ON.bias_weight = ((Math.random()-0.5)/10);
            for (int j = 0; j < hidden_neuron_list.size(); j++) {
                double weight = ((Math.random()-0.5)/10);
                ON.weight.add(weight);
            }
        }
    }

    private void run_neural_net_on_example() {
        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            Neuron HN = hidden_neuron_list.get(i);
            HN.ActualResult(sensor_values_list);
            hidden_sensor_values_list[i] = HN.myActualResult;
        }
        for (int i = 0; i < output_neuron_list.size(); i++) {
            Neuron ON = output_neuron_list.get(i);
            ON.ActualResult(hidden_sensor_values_list);
            output_sensor_values_list[i] = ON.myActualResult;
        }
    }

    private void calculate_error_signals() {
        for (int i = 0; i < output_neuron_list.size(); i++) {
            Neuron ON = output_neuron_list.get(i);
            double OutputErrorSignal =  ((ON.CorrectResult - ON.myActualResult) * ON.myActualResult * (1 - ON.myActualResult));
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
            HN.ErrorSignal = sum*HN.myActualResult*(1-HN.myActualResult);
        }
    }

    private void update_weights() {
        for (int i = 0; i < output_neuron_list.size(); i++) {
            Neuron ON = output_neuron_list.get(i);
            for (int j = 0; j < hidden_neuron_list.size(); j++) {
                Neuron HN = hidden_neuron_list.get(j);
                Double output_weight = ON.weight.get(i) + ON.ErrorSignal*(HN.myActualResult)*learning_rate;
                ON.weight.set(j, output_weight);
//                for (int k = 0; k < ON.weight.size(); k++) {
//                    Double output_weight = ON.weight.get(k) + ON.ErrorSignal*(HN.myActualResult)*learning_rate; // EACH HIDDEN NEURON ONLY HAS ONE WEIGHT GET RID OF THE FOR LOOP
//                    ON.weight.set(k, output_weight);
//                }
            }
            ON.bias_weight = ON.bias_weight + ON.ErrorSignal*ON.bias*learning_rate;
        }
        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            Neuron HN = hidden_neuron_list.get(i);
            for (int j = 0; j < sensor_values_list.length; j++) {
                Double hidden_weight = HN.weight.get(j) + HN.ErrorSignal*(sensor_values_list[j])*learning_rate;
                HN.weight.set(j, hidden_weight);
//                for (int k = 0; k < HN.weight.size(); k++) {
//                    Double hidden_weight = HN.weight.get(k) + HN.ErrorSignal*(sensor_values_list[j])*learning_rate;
//                    HN.weight.set(k, hidden_weight);
//                }
            }
            HN.bias_weight = HN.bias_weight + HN.ErrorSignal*HN.bias*learning_rate; // Updates Bias Weights
        }
    }

    private double output_neural_net() {
        double value = 0;
        Neuron Greatest_NEURON = null;
        for (int i = 0; i < output_neuron_list.size(); i++) {
            if (output_neuron_list.get(i).return_my_Actualresult() > value) {
                value = output_neuron_list.get(i).return_my_Actualresult();
                Greatest_NEURON = output_neuron_list.get(i);
            }
        }
        assert Greatest_NEURON != null;
        return Greatest_NEURON.label;
    }
}

