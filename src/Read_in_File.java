import java.util.ArrayList;
import java.util.Collections;

// Advay Koranne
// Andrew Merill
// Neural Nets

public class Read_in_File {

    public static ArrayList<ArrayList<Double>> set_up_data(String filename) {
        SimpleFile file = new SimpleFile("files", filename);
        ArrayList<ArrayList<Double>> Data = new ArrayList<>();
        for (String line : file) {
            ArrayList<Double> example = new ArrayList<>();
            for (String word : line.split(",")) {
                double int_to_word = Double.parseDouble(word);
                example.add(int_to_word);
            }
            Data.add(example);
        }
        Collections.shuffle(Data);
        return Data;

    }
}

