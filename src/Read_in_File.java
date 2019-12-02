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
            if (k >= 1) {
                data.add(example); // prints the line
            }
            k++;
        }

        NeuralNet2 NN = new NeuralNet2();
        NN.set_topology(2, 2, 2);
        NN.initialize_weights(2, 2);
        int num_inputs = header.size() - 1;
        //   System.out.println("Data: " + data);



        double percentage = 0;
        double epochs = 0;
        while(percentage < desired_percentage_accuracy_training) {
            if(epochs > 1000000) {
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
                epochs += 1;
                total_trials += 1;
                if (NN.read_in_examples(data.get(j)) == true) {
                    correct += 1;
                } else {
                    incorrect += 1;
                }
            }
            percentage = (correct / total_trials) * 100;
            if (epochs % 1000 == 0) {
                System.out.println("epochs: " + epochs );
                System.out.println("percentage accuracy: " + percentage);
            }

        }
        System.out.println("");
        System.out.println("-------------Final Results for " + filename + "--------");
        System.out.println("epochs: " + epochs );
        System.out.println("percentage accuracy: " + percentage);

    }
}



//        System.out.println("---------Final Output--------------");
//        System.out.println("num of epochs: " + epochs + " for an desired accuracy of: " + desired_percentage_accuracy_training);
//        System.out.println("acurracy achieved: " + NN.output_percentage());
//                if (print_counter % 10000 == 0) {
//                    System.out.println("num epochs: " + epochs);
//                    System.out.println("percentage: " + NN.output_percentage());
//                }