import java.util.ArrayList;

// Advay Koranne
// Andrew Merill
// Neural Nets
// http://inside.catlin.edu/site/compsci/topics/AI/NeuralNetworkLearning.html

public class Read_in_File {
    public void read_file(String filename, int desired_percentage_accuracy_training) {
        ArrayList<ArrayList<Double>> data = new ArrayList<>();
        ArrayList<String> header = new ArrayList<>();
        ArrayList<String> sensor_weights = new ArrayList<>();

        double value = 0.5;
        SimpleFile file = new SimpleFile("files", filename);

        int i = 0;
        int k = 0;
        for (String line : file) {
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
            if(k>=1) {
                data.add(example); // prints the line
            }
            k++;
        }

        NeuralNet2 NN = new NeuralNet2();
        NN.set_topology(2,2,2);
        NN.initialize_weights(2,2);
        int num_inputs = header.size() - 1;
     //   System.out.println("Data: " + data);

        int epochs = 0;
        int intial_training = 0;
        int print_counter = 0;
        while(intial_training < 10) {
            for(int j = 0; j < data.size(); j++) {
                NN.read_in_examples(data.get(j));
            }
            intial_training++;
        }

        while(NN.output_percentage() < desired_percentage_accuracy_training) {
            for (int j = 0; j < data.size(); j++) {
                NN.read_in_examples(data.get(j));
            }
            epochs += 1;
            if (print_counter % 10000 == 0) {
                System.out.println("num epochs: " + epochs);
                System.out.println("percentage: " + NN.output_percentage());
            }
            print_counter+=1;
        }
        System.out.println("---------Final Output--------------");
        System.out.println("num of epochs: " + epochs + " for an desired accuracy of: " + desired_percentage_accuracy_training);
        System.out.println("acurracy achieved: " + NN.output_percentage());
    }
}

