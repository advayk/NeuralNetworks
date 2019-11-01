import java.util.ArrayList;

// Advay Koranne
// Andrew Merill
// Neural Nets 
// http://inside.catlin.edu/site/compsci/topics/AI/NeuralNetworkLearning.html

public class Main {
    public static void main(String[] args) {
        ArrayList<ArrayList> data = new ArrayList<>();


        double value = 0.5;
        SimpleFile file = new SimpleFile("files", "AndDataSet");

        int i = 0;
        int k = 0;
        String header = null;

        for (String line : file) {
            for (String word : line.split(" ")) {
                if (k < 1) {
                    header = line;
                }
                if(k>=1) {
                    ArrayList<String> example = new ArrayList<>();
                    System.out.println(line);
                    System.out.println("-------------");
                    example.add(line);
                    data.add(example); // prints the line
                }
                k++;
        }
        NeuralNet Nn = new NeuralNet();
        Nn.func(data, 5);
    }
}

