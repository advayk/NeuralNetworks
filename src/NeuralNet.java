import java.util.ArrayList;

public class NeuralNet {
    ArrayList<HiddenNeuron> hidden_neuron_list = new ArrayList<HiddenNeuron>();
    ArrayList<OutputNeuron> output_neuron_list = new ArrayList<OutputNeuron>();

    ArrayList sensor_values_list = new ArrayList<Double>();
    ArrayList hidden_values_list = new ArrayList<Double>();

    ArrayList<String> actual_category_value_list = new ArrayList<String>();

    int number_of_inputs;
    int number_of_categories;
    int number_of_hidden_neurons = 2;
    int number_of_output_neuron;
    Double LearningRate = 0.1;

    public void create_neural_net(ArrayList<String> examples, int num_inputs, int categories) {

        System.out.println("size: " + examples.size());
        System.out.println(examples);
        int num_input = num_inputs;
        int number_of_output_neurons = categories;

        for (int i = 1; i <= (examples.size()-1); i++) {
            System.out.println(examples.get(i));
            sensor_values_list.add(examples.get(i));
        }
        for (int i = 0; i < 1; i++) {
            actual_category_value_list.add(examples.get(i));

        }



//        System.out.println("size:" + examples.size());
        System.out.println("Sensor value: " + sensor_values_list);

        for (int i = 0; i < number_of_hidden_neurons; i++) {
            HiddenNeuron HN = new HiddenNeuron();
            HN.bias = 1;
            HN.sensorNeuronList = examples;
            System.out.println("size:"  + (sensor_values_list.size()));
            for (int j = 0; j < sensor_values_list.size(); j++) {
                double weight = Math.random();
                HN.weight.add(weight);
            }
            hidden_neuron_list.add(HN);
        }

        for (int i = 0; i < number_of_output_neurons; i++) {
            OutputNeuron ON = new OutputNeuron();
            for (int j = 0; j < number_of_hidden_neurons; j++) {
                double weight = Math.random();
                ON.weight.add(weight);
            }
            ON.bias = 1;
            output_neuron_list.add(ON);
        }
        System.out.println(hidden_neuron_list.size());
        System.out.println(output_neuron_list);
        modify();
    }
//
//    Running the perceptron on an example involves:
//
//    for each input i: compute the product of inputi × weighti
//    compute the sum of the products
//    apply the activation function to the sum
//    the answer is the ActualResult computed by the perceptron
//
    public void modify() {
        System.out.println("RUNNING ON SENSOR -> HIDDEN");
        System.out.println("HN List " + hidden_neuron_list);
//        System.out.println("sensors" + sensor_values_list);
        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            HiddenNeuron HN = hidden_neuron_list.get(i);
            ArrayList HN_weights = hidden_neuron_list.get(i).get_weight();
            Double ActualResult = 0.0;
            for (int j = 0; j < sensor_values_list.size(); j++) {
                ActualResult = ActualResult + new Double(sensor_values_list.get(j).toString()) * ((Double) HN_weights.get(j));
            }
            Double CorrectResult = (Double.parseDouble(actual_category_value_list.get(0)));


            if(activationFunction(ActualResult) > 0.5) {
                hidden_values_list.add(1);
            }
            else {
                hidden_values_list.add(0);
            }
            System.out.println("SUM: " + ActualResult);
            HN.value(ActualResult);
        }
        modify2();
    }


    public void modify2() {
        System.out.println("RUNNING ON HIDDEN -> OUTPUT");
        System.out.println("ON List " + output_neuron_list);
        System.out.println("ON value list: " + hidden_values_list);
        for (int i = 0; i < output_neuron_list.size(); i++) {
            OutputNeuron ON = output_neuron_list.get(i);
            ArrayList ON_weights = output_neuron_list.get(i).get_weight();
            Double ActualResult = 0.0;
            for (int j = 0; j < hidden_values_list.size(); j++) {
                ActualResult = ActualResult + new Double(hidden_values_list.get(j).toString()) * ((Double) ON_weights.get(j));
                System.out.println("AR: " + ActualResult);
            }
            ON.value(ActualResult);
        }
        CalculateOutputErrorSignal();
    }



    public double activationFunction(double x) {
        double value = (1 / (1 + (Math.exp(-x))));
        return value;
    }

//    public Double OutputErrorSignal(Double CorrectResult, Double ActualResult) {
//        Double ErrorSignal = (CorrectResult - ActualResult)*ActualResult*(1-ActualResult);
//        return ErrorSignal;
//    }

    public void CalculateOutputErrorSignal() {
        for (int i = 0; i < output_neuron_list.size(); i++) {
            double ActualResult = output_neuron_list.get(i).get_value();
            Double CorrectResult = new Double(actual_category_value_list.get(i).toString());
            Double OutputErrorSignal = (CorrectResult - ActualResult)*ActualResult*(1-ActualResult);
            System.out.println("OutputErrorSignal: " + OutputErrorSignal);
        }
    }


    public void CalculateHiddenErrorSignal() {
        for (int i = 0; i < output_neuron_list.size(); i++) {
            double sum = 0.0;
            OutputNeuron ON = output_neuron_list.get(i);
            double ActualResult = output_neuron_list.get(i).get_value();
            Double CorrectResult = new Double(actual_category_value_list.get(i).toString());
            Double OutputErrorSignal = (CorrectResult - ActualResult)*ActualResult*(1-ActualResult);
            System.out.println("OutputErrorSignal: " + OutputErrorSignal);
            ArrayList<Double> ON_weight = ON.get_weight();
            for (int j = 0; j < ON_weight.size(); j++) {
                sum = sum + OutputErrorSignal* ON_weight.get(j);
            }
            //error_signal = sum*hidden_result*(1-hidden_Result); // TODO: 11/11/19  fix what is this hidden result
        }
    }


    public void update_weight_of_i() {
        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            

        }
    }

}







//        for (int i = 0; i < hidden_neuron_list.size(); i++) {
//            HiddenNeuron HN;
//            ArrayList HN_weights = hidden_neuron_list.get(i).get_weight();
//            System.out.println("weights" + HN_weights);
//            double sum = 0;
//            for (int j = 0; j < sensor_values_list.size(); j++) {
//                double input_weight;
//                for (int k = 0; k < HN_weights.size(); k++) {
//                    input_weight = new Double(sensor_values_list.get(j).toString())*((Double) HN_weights.get(k));
//                    sum = sum + input_weight;
//                }
//            }
//            if(activationFunction(sum) > 0.5) {
//                hidden_values_list.add(1);
//            }
//            else {
//                hidden_values_list.add(0);
//            }
//            System.out.println("SUM" + sum);
//        }




//
//            System.out.println("weights" + ON_weights);
//            double sum = 0;
//            for (int j = 0; j < hidden_values_list.size(); j++) {
//                double input_weight;
//                for (int k = 0; k < ON_weights.size(); k++) {
//                    input_weight = new Double(hidden_values_list.get(j).toString())*((Double) ON_weights.get(k));
//                    sum = sum + input_weight;
//                }
//                Double ActualResult = activationFunction(sum);
//                System.out.println(sum);
//            }