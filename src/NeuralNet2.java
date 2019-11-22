import java.util.ArrayList;
import java.util.Collections;

//https://www.java67.com/2016/08/how-to-replace-element-of-arraylist-in-java.html

public class NeuralNet2<actual_category_value> {
//
//    classify-one-example: takes one example and returns the category that the neural net thinks it belongs to calculate-
////   accuracy: takes a list of pre-categorized testing examples, and returns the fraction (or percent) that are correctly classified by this network
//    learn-one-example: takes one pre-categorized example, classifies it, and then learns by modifying the network's weights
//    learn-many-examples: takes a list of pre-categorized training examples (and maybe a list of pre-categorized validation examples), and a desired accuracy and/or time limit.  It repeatedly learns from the training examples until a termination condition is reached.
//    initialize-random-weights: sets all weights to small random values (perhaps between +/- 0.05)


    ArrayList output_of_network = new ArrayList<Double>();

    private ArrayList<Neuron> hidden_neuron_list = new ArrayList<Neuron>();
    private ArrayList<Neuron> output_neuron_list = new ArrayList<Neuron>();
    private Double actual_category_value;
    private ArrayList<Double> actual_category_value_list = new ArrayList<Double>();

    private int number_of_hidden_neurons = 2;
    private int number_of_output_neuron = 2;
    private double LearningRate = 1;

    double [] sensor_values_list = new double [2];
    double [] hidden_sensor_values_list = new double [2];
    double [] output_sensor_values_list = new double [2];



    public void read_in_examples(ArrayList<Double> example) {
        System.out.println("Size of example: " + example);
        for (int i = 1; i < example.size()-1; i++) {
            sensor_values_list[i] = example.get(i);
        }
        System.out.println("example: " + example.get(0));
        actual_category_value = example.get(0);
        run_neural_net_on_example();
        for (int i = 0; i < 2; i++) {
            Neuron ON = new Neuron();
            if(ON.label == actual_category_value) {
                ON.CorrectResult = 1;
            }
            else{
                ON.CorrectResult = 0;
            }
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
            ON.CorrectResult = i;
            output_neuron_list.add(ON);
            ON.label = i;
            System.out.println(actual_category_value);
        }
    }


    public void initialize_weights(int num_input_neuron, int number_of_hidden_neurons) {
        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            Neuron HN = hidden_neuron_list.get(i);
            HN.bias_weight = Math.random();
            for (int j = 0; j < num_input_neuron; j++) {
                double weight = Math.random();
                HN.weight.add(weight);
            }
        }
        for (int i = 0; i < output_neuron_list.size(); i++) {
            Neuron ON = output_neuron_list.get(i);
            ON.bias_weight = Math.random();
            for (int j = 0; j < hidden_neuron_list.size(); j++) {
                double weight = Math.random();
                ON.weight.add(weight);
            }
        }
    }


    public void run_neural_net_on_example() {
        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            Neuron HN = hidden_neuron_list.get(i);
            HN.ActualResult(HN, sensor_values_list);
            hidden_sensor_values_list[i] = HN.myActualResult;
        }
        for (int i = 0; i < output_neuron_list.size(); i++) {
            Neuron ON = output_neuron_list.get(i);
            ON.ActualResult(ON, hidden_sensor_values_list);
            output_sensor_values_list[i] = ON.myActualResult;
        }
        calculate_error_signals();
        UpdateWeights();
        Output_Neural_Net();
    }


    public void calculate_error_signals() {
//        System.out.println("calculating error signal: ");
        for (int i = 0; i < output_neuron_list.size()-1; i++) {
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
            HN.ErrorSignal = HN.myActualResult*(1-HN.myActualResult);
        }
    }

    public void UpdateWeights() {
        System.out.println("*** Changing Weights ***");
        for (int i = 0; i < output_neuron_list.size(); i++) {
            double bias_weight = Math.random();
            Neuron ON = output_neuron_list.get(i);
            for (int j = 0; j < hidden_neuron_list.size(); j++) {
                Neuron HN = hidden_neuron_list.get(j);
                for (int k = 0; k < ON.weight.size(); k++) {
                    Double output_weight = ON.weight.get(k) + ON.ErrorSignal*(hidden_sensor_values_list[k]) *LearningRate;
                    ON.weight.set(k, output_weight);
                }
            }
            ON.bias = ON.bias_weight + ON.ErrorSignal + ON.ErrorSignal*ON.bias*LearningRate;
        }

        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            double bias_weight = Math.random();
            Neuron HN = hidden_neuron_list.get(i);
            for (int j = 0; j < sensor_values_list.length; j++) {
                for (int k = 0; k < HN.weight.size(); k++) {
                    Double hidden_weight = HN.weight.get(k) + HN.ErrorSignal*(sensor_values_list[j])*LearningRate;
                    HN.weight.set(k, hidden_weight);

                }
            }
            HN.bias = HN.bias_weight + HN.ErrorSignal + HN.ErrorSignal*HN.bias*LearningRate; // Updates Bias Weights
        }
    }

    public void Output_Neural_Net() {
        ArrayList<Double> output = new ArrayList<Double>();
        double value = 0;
        for (int i = 0; i < output_neuron_list.size(); i++) {
//            System.out.println("OUTPUT: " + output_neuron_list.get(i).label);
//            System.out.print("NEURAL NETWORK RESULT: ");
            if (output_neuron_list.get(i).return_my_Actualresult() > value) {
                value = output_neuron_list.get(i).return_my_Actualresult();
            }

            System.out.println("greatest value: ");
            System.out.println(output_neuron_list.get(0).label + "  :  " + output_sensor_values_list[0]);
            System.out.println(output_neuron_list.get(1).label + "  :  " + output_sensor_values_list[1]);
            System.out.println("Actual Value of the element: " + actual_category_value);
        }
    }
}





//    public void create_neural_net(ArrayList<String> examples, int num_inputs, int categories) {
//        System.out.println(examples);
//        System.out.println("Running");
//        for (int i = 1; i <= (examples.size()-1); i++) {
//            System.out.println(examples.get(i));
//            sensor_values_list.add(examples.get(i));
//        }
//
//        for (int i = 0; i < 1; i++) {
//            actual_category_value_list.add(examples.get(i));
//        }
//
//        for (int i = 0; i < number_of_hidden_neurons; i++) {
//            Neuron HN = new Neuron();
//            HN.inputs = sensor_values_list;
//            HN.bias = 1;
//            System.out.println("size:"  + (sensor_values_list.size()));
//            for (int j = 0; j < sensor_values_list.size(); j++) {
//                double weight = Math.random();
//                HN.weight.add(weight);
//            }
//            hidden_neuron_list.add(HN);
//        }
//        for (int i = 0; i < number_of_output_neuron; i++) {
//            Neuron ON = new Neuron();
//            ON.inputs = hidden_values_list;
//            ON.bias = 1;
//            System.out.println("size:"  + (sensor_values_list.size()));
//            for (int j = 0; j < sensor_values_list.size(); j++) {
//                double weight = Math.random();
//                ON.weight.add(weight);
//            }
//            output_neuron_list.add(ON);
//        }
//
//        for (int i = 0; i < hidden_neuron_list.size(); i++) {
//            Neuron HN = hidden_neuron_list.get(i);
//            double HiddenResult = HN.ActualResult(sensor_values_list);
//            HN.ActualResult = HiddenResult;
//            hidden_values_list.add(HiddenResult);
//        }
//
//        //        for each output neuron o, treat it like a perceptron, using the HiddenResults of the hidden layer for inputs
//
//        for (int i = 0; i < output_neuron_list.size(); i++) {
//            Neuron ON = output_neuron_list.get(i);
//            double HiddenResult = ON.ActualResult(hidden_values_list);
//            ON.ActualResult = HiddenResult;
//            output_of_network.add(ON);
//        }
//        System.out.println(output_neuron_list);
//        System.out.println(output_neuron_list.size());
//        System.out.println(hidden_neuron_list);
//
//        calculate_error_signals();
//        UpdateWeights();
//    }
//
//
//    public void calculate_error_signals() {
//        for (int i = 0; i < output_neuron_list.size()-1; i++) {
//            Neuron ON = output_neuron_list.get(i);
//            double OutputErrorSignal = (new Double(actual_category_value_list.get(0).toString()) - ON.ActualResult)*(ON.ActualResult)*(1-ON.ActualResult);
//            ON.ErrorSignal = OutputErrorSignal;
//        }
//
//        for (int i = 0; i < hidden_neuron_list.size(); i++) {
//            Neuron HN = hidden_neuron_list.get(i);
//            double sum = 0.0;
//            for (int j = 0; j < output_neuron_list.size(); j++) {
//                Neuron ON = output_neuron_list.get(j);
//                for (int k = 0; k < ON.weight.size(); k++) {
//                    sum+= ON.ErrorSignal*ON.weight.get(k);
//                }
//            }
//            HN.ErrorSignal = HN.ActualResult*(1-HN.ActualResult);
//        }
//    }
//
//
//    public void UpdateWeights() {
//        for (int i = 0; i < output_neuron_list.size(); i++) {
//            Neuron ON = output_neuron_list.get(i);
//            for (int j = 0; j < hidden_neuron_list.size(); j++) {
//                Neuron HN = hidden_neuron_list.get(j);
//                for (int k = 0; k < ON.weight.size(); k++) {
//                    ON.weight.set(k, ON.weight.get(k) + ON.ErrorSignal*HN.ActualResult*LearningRate);
//                }
//            }
//        }
//        for (int i = 0; i < hidden_neuron_list.size(); i++) {
//            Neuron HN = hidden_neuron_list.get(i);
//            for (int j = 0; j < sensor_values_list.size(); j++) {
//                for (int k = 0; k < HN.weight.size(); k++) {
//                    HN.weight.set(k, HN.weight.get(k) + HN.ErrorSignal*(new Double(sensor_values_list.get(j).toString())*LearningRate));
//                }
//            }
////        }
////    }
//}


