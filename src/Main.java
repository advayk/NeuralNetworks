import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Go("HandwrittenTrainingSet", "HandwrittenTestingSet", 98, 140, 10,0.06);



    }
    public static void Go(String TrainingFile, String TestingFile, int desired_percentage_accuracy_training, int num_hidden_neurons, int num_output_neuron, double learning_rate) {
        ArrayList<ArrayList<Double>> myTrainingFile = Read_in_File.set_up_data(TrainingFile);
        ArrayList<ArrayList<Double>> myTestingFile = Read_in_File.set_up_data(TestingFile);
        NeuralNet2 NN = new NeuralNet2(myTestingFile.get(0).size(), num_hidden_neurons,  num_output_neuron, learning_rate);
        System.out.println("Running Neural Net");
        RunNeuralNet RunNet = new RunNeuralNet();
        RunNet.run_neural_net(NN, myTrainingFile, myTestingFile, desired_percentage_accuracy_training);
    }
}
