import java.util.ArrayList;

//https://www.java67.com/2016/08/how-to-replace-element-of-arraylist-in-java.html

public class NeuralNet2 {

    ArrayList output_of_network = new ArrayList<Double>();

    private ArrayList<Neuron> hidden_neuron_list = new ArrayList<Neuron>();
    private ArrayList<Neuron> output_neuron_list = new ArrayList<Neuron>();
    private ArrayList<Double> actual_category_value_list = new ArrayList<Double>();

    private int number_of_hidden_neurons = 2;
    private int number_of_output_neuron = 2;
    private double LearningRate = 1.0;

    double [] sensor_values_list = new double [2];
    double [] hidden_sensor_values_list = new double [2];
    double [] output_sensor_values_list = new double [2];



    public void read_in_examples(ArrayList<Double> example) {
        System.out.println(example.size());
        for (int i = 1; i < example.size()-1; i++) {
            System.out.println(i + " : " + example.get(i));
            sensor_values_list[i] = example.get(i);
//            sensor_values_list[i+1] = example.get(i+1);
        }
        for (int i = 0; i < 1; i++) {
            actual_category_value_list.add(example.get(0));
        }
        System.out.println("sensor value list:" + sensor_values_list.length);
        run_neural_net_on_example();
       // set_topology(2,2,2);
    }

    public void set_topology(int num_input_neuron, int num_hidden_neuron, int num_output_neuron) {
        for (int i = 0; i < num_hidden_neuron; i++) {
            Neuron HN = new Neuron();
            HN.bias = 1;
            hidden_neuron_list.add(HN);
        }

        for (int i = 0; i < num_input_neuron; i++) {
            Neuron ON = new Neuron();
            ON.bias = 1;
            output_neuron_list.add(ON);
        }
    }


    public void initialize_weights(int num_input_neuron, int number_of_hidden_neurons) {
        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            double weight = Math.random();
            Neuron HN = hidden_neuron_list.get(i);
            for (int j = 0; j < num_input_neuron; j++) {
                System.out.println("weight: " + weight);
                HN.weight.add(weight);
                System.out.println(HN.weight.size());
            }
        }
        for (int i = 0; i < output_neuron_list.size(); i++) {
            double weight = Math.random();
            Neuron ON = output_neuron_list.get(i);
            for (int j = 0; j < hidden_neuron_list.size(); j++) {
                ON.weight.add(weight);
            }
        }
    }

//    Running the perceptron on an example involves:
//     for each input i: compute the product of inputi × weighti
//    compute the sum of the products
//    apply the activation function to the sum
//    the answer is the ActualResult computed by the perceptron

    public void run_neural_net_on_example() {
        System.out.println("HN Neuron List: " + hidden_neuron_list);
        for (int i = 0; i < hidden_neuron_list.size(); i++) {
            Neuron HN = hidden_neuron_list.get(i);
            HN.ActualResult(HN, sensor_values_list);
            hidden_sensor_values_list[i] = HN.myActualResult;
            System.out.println("HN WEIGHTS: " + HN.weight);
        }
        for (int i = 0; i < output_neuron_list.size(); i++) {
            System.out.println("I: " + i);
            Neuron ON = output_neuron_list.get(i);
            ON.ActualResult(ON, hidden_sensor_values_list);
            output_sensor_values_list[i] = ON.myActualResult;
            System.out.println("neural network output: " + ON.myActualResult);
        }
        calculate_error_signals();
        UpdateWeights();
    }

    public void print_neural_net() {
        System.out.println(hidden_neuron_list);
    }











//
//
//§  First, calculate the error signals:
//    o   For each output neuron o, compute the OutputErrorSignalo where
//    OutputErrorSignalo  ←  (CorrectResulto – ActualResulto) × ActualResulto × (1 – ActualResulto)
//    o   For each hidden neuron h, compute the HiddenErrorSignalh by:
//            §  for each output neuron o:
//
//            ·      compute OutputErrorSignalo × OutputWeighth→o
//
//·      sum together all of these products
//
//§  multiply that sum by HiddenResulth × (1 - HiddenResulth)
//
//
//
//
    public void calculate_error_signals() {
        System.out.println("calculating error signal: ");
        for (int i = 0; i < output_neuron_list.size()-1; i++) {
            Neuron ON = output_neuron_list.get(i);
            double OutputErrorSignal = new Double(actual_category_value_list.get(0)) * (ON.myActualResult) * (1 - ON.myActualResult) - ON.myActualResult * (ON.myActualResult) * (1 - ON.myActualResult);
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
        System.out.println("Updating Weights: ");
        for (int i = 0; i < output_neuron_list.size(); i++) {
            Neuron ON = output_neuron_list.get(i);
            for (int j = 0; j < hidden_neuron_list.size(); j++) {
                Neuron HN = hidden_neuron_list.get(j);
                for (int k = 0; k < ON.weight.size(); k++) {
                    ON.weight.set(k, ON.weight.get(k) + ON.ErrorSignal*HN.myActualResult*LearningRate);
                }
            }
        }

        for (int i = 0; i <= hidden_neuron_list.size()-1; i++) {
            Neuron HN = hidden_neuron_list.get(i);
            for (int j = 0; j < sensor_values_list.length; j++) {
                for (int k = 0; k < HN.weight.size(); k++) {
                    HN.weight.set(k, HN.weight.get(k) + HN.ErrorSignal*(new Double(sensor_values_list[j])*LearningRate));
                }
            }
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


