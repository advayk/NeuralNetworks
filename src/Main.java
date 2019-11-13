import java.util.ArrayList;

// Advay Koranne
// Andrew Merill
// Neural Nets
// http://inside.catlin.edu/site/compsci/topics/AI/NeuralNetworkLearning.html

public class Main {
    public static void main(String[] args) {
        ArrayList<ArrayList> data = new ArrayList<>();
        ArrayList<String> header = new ArrayList<>();

        double value = 0.5;
        SimpleFile file = new SimpleFile("files", "AndDataSet");

        int i = 0;
        int k = 0;

        for (String line : file) {
//            System.out.println(line);
//            System.out.println(k);
            ArrayList<String> example = new ArrayList<>();
            for (String word : line.split(",")) {
              //  System.out.println(word);
                if (k == 0) {
                    header.add(word);
                }
                if (k >= 1) {
                 //   System.out.println("-------------");
                    example.add(word);
                    data.add(example); // prints the line
                }
               // System.out.println(k);
            }
            k++;
        }
//        System.out.println(data);
       // System.out.println("Header: " + header.size());
        NeuralNet2 NN = new NeuralNet2();
        int num_inputs = header.size() -1;
        for (int j = 0; j < data.size(); j++) {
           // System.out.println(data.get(j));
            NN.create_neural_net(data.get(j),2,2);
        }
    }
}

