import java.util.ArrayList;

// Advay Koranne
// Andrew Merill
// Neural Nets
// http://inside.catlin.edu/site/compsci/topics/AI/NeuralNetworkLearning.html

public class Main {
    public static void main(String[] args) {
        ArrayList<ArrayList<Double>> data = new ArrayList<>();
        ArrayList<String> header = new ArrayList<>();
        ArrayList<String> sensor_weights = new ArrayList<>();

        double value = 0.5;
        SimpleFile file = new SimpleFile("files", "AndDataSet");

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
                    data.add(example); // prints the line
                }
            }
            k++;
        }

        NeuralNet2 NN = new NeuralNet2();
        NN.set_topology(2,2,2);
        NN.initialize_weights(2,2);
        int num_inputs = header.size() - 1;

        for (int a = 0; a < 10000; a++) {
            for (int j = 0; j < data.size(); j++) {
                NN.read_in_examples(data.get(j));
                System.out.println("---------------------------");
            }
        }
    }
}

