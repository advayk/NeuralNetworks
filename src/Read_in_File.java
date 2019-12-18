import java.util.ArrayList;

// Advay Koranne
// Andrew Merill
// Neural Nets

public class Read_in_File {



    public static ArrayList<ArrayList<Double>> set_up_data(String filename) {
        SimpleFile file = new SimpleFile("files", filename);
        ArrayList<ArrayList<Double>> Data = new ArrayList<>();
        int i = 0;
        int k = 0;
        for (String line : file) {
            ArrayList<Double> example = new ArrayList<>();
            for (String word : line.split(",")) {
                if (k >= 1) {
                    double int_to_word = Double.parseDouble(word);
                    example.add(int_to_word);
                }
            }
            if (k >= 1) {
                Data.add(example); // prints the line
            }
            k++;
        }
        return Data;
    }
}

