import java.util.ArrayList;

// Advay Koranne
// Andrew Merill
// Neural Nets

public class Read_in_File {
    ArrayList<ArrayList<Double>> data = new ArrayList<>();
    ArrayList<String> header = new ArrayList<>();
    ArrayList<String> category_value_list = new ArrayList<>();
    double learning_rate;
    int num_hidden_neurons;
    ArrayList<String> sensor_weights = new ArrayList<>();
    String filename;


    public void read_file(String filename, int desired_percentage_accuracy_training, double learning_rate, int num_hidden_neurons) {
        SimpleFile file = new SimpleFile("files", filename);
        this.learning_rate = learning_rate;
        this.filename = filename;
        int num_lines = 0;
        int i = 0;
        int k = 0;
        for (String line : file) {
            num_lines +=1;
            ArrayList<Double> example = new ArrayList<>();
            for (String word : line.split(",")) {
                if (k == 0) {
                    header.add(word);
                }
                if (k >= 1) {
                    double int_to_word = Double.parseDouble(word);
                    example.add(int_to_word);
                }
            }
            if (k >= 1) {
                data.add(example); // prints the line
            }
            k++;
        }
        NeuralNet2 NN = new NeuralNet2(header.size()-1, num_hidden_neurons,  2, learning_rate);
        RunNeuralNet(NN,desired_percentage_accuracy_training);
    }

//    private void CreateNeuralNet(int num_input_neuron, int num_hidden_neuron, int num_output_neuron, double LearningRate) {
//
//    }

    public void RunNeuralNet(NeuralNet2 NN,int desired_percentage_accuracy_training) {
        System.out.println("------------- Results for " + filename + "--------");
        double percentage = 0;
        double epochs = 0;
        while(percentage < desired_percentage_accuracy_training) {
            if(epochs > 1000000000) {
                System.out.println("----------------------------------");
                System.out.println("over 1 mil epochs can not complete");
                System.out.println("----------------------------------");
                break;
            }
            epochs += 1;
            double total_trials = 0;
            double correct = 0;
            int incorrect = 0;
            for (int j = 0; j < data.size(); j++) {
                total_trials += 1;
                if (NN.read_in_example(data.get(j)) == true) {
                    correct += 1;
                } else {
                    incorrect += 1;
                }
            }
            percentage = (correct / total_trials) * 100;
            if (epochs % 100000 == 0) {
                System.out.println("epochs: " + epochs );
                System.out.println("percentage accuracy: " + percentage);
            }
        }
        System.out.println("");
        System.out.println("epochs: " + epochs );
        System.out.println("percentage accuracy: " + percentage);
        System.out.println("-------------------------------------------------------");
    }
}

