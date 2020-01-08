import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("loading in file");
        mnist_go();
       // go("XORDataSet2", "XORDataSet2", 100, 2, 2,0.05, "XORDataSet2");
       //go("AndDataSet", "AndDataSet", 100, 2, 2,1.0, "AndDataSet");
      //  go("HandwrittenTrainingSet", "HandwrittenTestingSet", 99, 140, 10,0.06,"HandwrittenTrainingSet");

    }
    public static void mnist_go() {
       ArrayList<ArrayList<Double>> trainingExamples = readData("files/train-labels-idx1-ubyte", "files/train-images-idx3-ubyte");
      //  System.out.println(trainingExamples);
        ArrayList<ArrayList<Double>> testingExamples = readData("files/t10k-labels-idx1-ubyte", "files/t10k-images-idx3-ubyte");
        go_MNIST(trainingExamples, testingExamples, 99, 140, 10,0.06,"mnist");
    }


    static ArrayList<ArrayList<Double>>  readData(String labelFileName, String imageFileName) {
//        System.out.println("working");
        DataInputStream labelStream = openFile(labelFileName, 2049);
        DataInputStream imageStream = openFile(imageFileName, 2051);

        ArrayList<ArrayList<Double>> data = new ArrayList<>();

        try {
            int numLabels = labelStream.readInt();
            int numImages = imageStream.readInt();
            assert(numImages == numLabels) : "lengths of label file and image file do not match";

            int rows = imageStream.readInt();
            int cols = imageStream.readInt();
            assert(rows == cols) : "images in file are not square";
            assert(rows == 28) : "images in file are wrong size";

            for (int i = 0; i < numImages; i++) {
                int categoryLabel = Byte.toUnsignedInt(labelStream.readByte());
                ArrayList<Double> inputs = new ArrayList<>();
               // double[] inputs = new double[rows * cols];
                int total = rows*cols;
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        int pixel = 255 - Byte.toUnsignedInt(imageStream.readByte());
                        inputs.add(r * rows + c, (pixel / 255.0));
                    }
                }

                inputs.add(total, (double) categoryLabel);
                data.add(inputs);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    static DataInputStream openFile(String fileName, int expectedMagicNumber) {
        DataInputStream stream = null;
        try {
            stream = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            int magic = stream.readInt();
            if (magic != expectedMagicNumber) {
                throw new RuntimeException("file " + fileName + " contains invalid magic number");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("file " + fileName + " was not found");
        } catch (IOException e) {
            throw new RuntimeException("file " + fileName + " had exception: " + e);
        }
        return stream;
    }

    public static void go(String TrainingFile, String TestingFile, int desired_percentage_accuracy_training, int num_hidden_neurons, int num_output_neuron, double learning_rate, String filename) {
        ArrayList<ArrayList<Double>> myTrainingFile = Read_in_File.set_up_data(TrainingFile);
        ArrayList<ArrayList<Double>> myTestingFile = Read_in_File.set_up_data(TestingFile);
        NeuralNet2 NN = new NeuralNet2(myTestingFile.get(0).size()-1, num_hidden_neurons,  num_output_neuron, learning_rate);
        //System.out.println(myTrainingFile);
        RunNeuralNet RunNet = new RunNeuralNet();
        RunNet.run_neural_net(NN, myTrainingFile, myTestingFile, desired_percentage_accuracy_training, filename);
    }

    public static void go_MNIST(ArrayList<ArrayList<Double>> TrainingFile, ArrayList<ArrayList<Double>> TestingFile, int desired_percentage_accuracy_training, int num_hidden_neurons, int num_output_neuron, double learning_rate, String filename) {
        NeuralNet2 NN = new NeuralNet2(TestingFile.get(0).size()-1, num_hidden_neurons,  num_output_neuron, learning_rate);
        System.out.println("Running Neural Net");
        RunNeuralNet RunNet = new RunNeuralNet();
        RunNet.run_neural_net(NN, TrainingFile, TestingFile, desired_percentage_accuracy_training, filename);
    }
}
