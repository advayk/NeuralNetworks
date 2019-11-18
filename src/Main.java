import java.util.ArrayList;

// Advay Koranne
// Andrew Merill
// Neural Nets
// http://inside.catlin.edu/site/compsci/topics/AI/NeuralNetworkLearning.html

public class Main {
    public static void main(String[] args) {
        ArrayList<ArrayList<Double>> data = new ArrayList<>();
        ArrayList<String> header = new ArrayList<>();

        double value = 0.5;
        SimpleFile file = new SimpleFile("files", "AndDataSet");

        int i = 0;
        int k = 0;

        for (String line : file) {
//            System.out.println(line);
//            System.out.println(k);
            ArrayList<Double> example = new ArrayList<>();
            for (String word : line.split(",")) {
                //  System.out.println(word);
                if (k == 0) {
                    header.add(word);
                }
                if (k >= 1) {
                    //   System.out.println("-------------");
                    double int_to_word = Double.parseDouble(word);
                    example.add(int_to_word);
                    data.add(example); // prints the line
                }
                // System.out.println(k);
            }
            k++;
        }
//        System.out.println(data);
        // System.out.println("Header: " + header.size());
        NeuralNet2 NN = new NeuralNet2();
        NN.set_topology(2,2,2);
        NN.initialize_weights(2,2);
        int num_inputs = header.size() - 1;
        //System.out.println("data: " + data);
        for (int j = 0; j < data.size(); j++) {
          //  System.out.println(j + " example: " + data.get(j));
            NN.read_in_examples(data.get(j));
        }
    }
}

//    initialize weights to small random values (say, ± 0.05)
//
//    repeat until training is complete:
//
//    repeat for each examplei:
//
//    run the network on examplei
//
//    modify the weights to learn from examplei


