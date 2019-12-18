public class Main {
    public static void main(String[] args) {
        Read_in_File read = new Read_in_File();
        read.read_in_training_file("HandwrittenTrainingSet",95, 0.06, 140);
      // read.read_file("XORDataSet2",100, 0.05, 2);
        read.read_in_testing_file("HandwrittenTestingSet", 95, 0.06, 140);
    //    read.read_in_training_file("HandwrittenTrainingSet",100, 0.06, 140);

    }
}
