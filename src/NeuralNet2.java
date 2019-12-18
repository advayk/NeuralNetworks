import java.util.ArrayList;

public  class NeuralNet2 {
    private double learning_rate;


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
        for (int i = 0; i < example.size()-1; i++) {
            sensor_values_list[i] = example.get(i);
        }
        actual_category_value = example.get(example.size()-1);
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
//        System.out.println(example);
//        System.out.println("ACV: " + actual_category_value);
//        System.out.println("--------");
        if(actual_category_value == output) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean run_on_example_testing(ArrayList<Double> example) {
        for (int i = 0; i < example.size()-1; i++) {
            sensor_values_list[i] = example.get(i);
        }
        actual_category_value = example.get(example.size()-1);
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
        if(actual_category_value == output) {
            return true;
        }
        else {
            return false;
        }
//        System.out.println(example);
//        System.out.println("ACV: " + actual_category_value);
//        System.out.println("--------");

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
        for (int h = 0; h < hidden_neuron_list.size(); h++) {
            Neuron HN = hidden_neuron_list.get(h);
            double sum = 0.0;
            for (int o = 0; o < output_neuron_list.size(); o++) {
                Neuron ON = output_neuron_list.get(o);
                sum+= ON.ErrorSignal*ON.weight.get(h);
            }
            HN.ErrorSignal = sum*HN.myActualResult*(1-HN.myActualResult);
        }
    }

    private void update_weights() {
        for (int o = 0; o < output_neuron_list.size(); o++) {
            Neuron ON = output_neuron_list.get(o);
            for (int h = 0; h < hidden_neuron_list.size(); h++) {
                Neuron HN = hidden_neuron_list.get(h);
                double output_weight = ON.weight.get(h) + ON.ErrorSignal*(HN.myActualResult)*learning_rate;
                ON.weight.set(h, output_weight);
            }

            ON.bias_weight = ON.bias_weight + ON.ErrorSignal*ON.bias*learning_rate;
        }
        for (int h = 0; h < hidden_neuron_list.size(); h++) {
            Neuron HN = hidden_neuron_list.get(h);
            for (int i = 0; i < sensor_values_list.length; i++) {
                Double hidden_weight = HN.weight.get(i) + HN.ErrorSignal*(sensor_values_list[i])*learning_rate;
                HN.weight.set(i, hidden_weight);
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

