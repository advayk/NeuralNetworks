// Advay Koranne
// CS III
// Neural Networks
// Andrew Merill

import java.awt.font.TransformAttribute;
import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("loading in file"); // Passing 0 into the go files for the percentage validation will run the validation set with the training data set
    //    mnist_go();
     //    go("XORDataSet2", "XORDataSet2", 100, 2, 2,0.05, "XORDataSet2",0 );
     //   go("AndDataSet", "AndDataSet", 100, 2, 2,1.0, "AndDataSet", 0);
        go("HandwrittenTrainingSet", "HandwrittenTestingSet", 99, 140, 10,0.06,"HandwrittenTrainingSet", 0.9);
    }

    public static ArrayList<ArrayList<Double>> split_file_training(ArrayList<ArrayList<Double>> data, double ratio) { // copies the old file from [0, file.size*ration] into a new file for training
        ArrayList<ArrayList<Double>> training_data = new ArrayList<>();
        for (int i = 0; i < data.size() * ratio; i++) {
            training_data.add(i, data.get(i));
        }
        return training_data;
    }

    public static ArrayList<ArrayList<Double>> split_file_validation(ArrayList<ArrayList<Double>> data, double ratio) {  // copies the old file from [file.size*ration,file.size] into a new file for training
        ArrayList<ArrayList<Double>> testing_data = new ArrayList<>();
        for (int i = (int) (data.size() * ratio); i < data.size(); i++) {
            testing_data.add((int) (i-((int)(data.size() * ratio))), data.get(i));
        }
        return testing_data;
    }

    public static void mnist_go() {
       ArrayList<ArrayList<Double>> trainingExamples = readData("files/train-labels-idx1-ubyte", "files/train-images-idx3-ubyte");
        ArrayList<ArrayList<Double>> testingExamples = readData("files/t10k-labels-idx1-ubyte", "files/t10k-images-idx3-ubyte");
        go_MNIST(trainingExamples, testingExamples, 99, 140, 10,0.06,"mnist", 0.9);
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

    public static void go(String TrainingFile, String TestingFile, int desired_percentage_accuracy_validation, int num_hidden_neurons, int num_output_neuron, double learning_rate, String filename, double percentage_validation ) {
        if (percentage_validation != 0) {
            ArrayList<ArrayList<Double>> myTrainingFile = Read_in_File.set_up_data(TrainingFile);
            myTrainingFile = split_file_training(myTrainingFile, percentage_validation);
            ArrayList<ArrayList<Double>> myValidationSet = split_file_validation(myTrainingFile, percentage_validation);
            ArrayList<ArrayList<Double>> myTestingFile = Read_in_File.set_up_data(TestingFile);
            NeuralNet2 NN = new NeuralNet2(myTestingFile.get(0).size() - 1, num_hidden_neurons, num_output_neuron, learning_rate);
            RunNeuralNet RunNet = new RunNeuralNet();
            RunNet.run_neural_net(NN, myTrainingFile, myValidationSet, myTestingFile, desired_percentage_accuracy_validation, filename);
        } else {
            ArrayList<ArrayList<Double>> myTrainingFile = Read_in_File.set_up_data(TrainingFile);
            ArrayList<ArrayList<Double>> myTestingFile = Read_in_File.set_up_data(TestingFile);
            NeuralNet2 NN = new NeuralNet2(myTestingFile.get(0).size() - 1, num_hidden_neurons, num_output_neuron, learning_rate);
            RunNeuralNet RunNet = new RunNeuralNet();
            RunNet.run_neural_net(NN, myTrainingFile, myTrainingFile, myTestingFile, desired_percentage_accuracy_validation, filename);
        }
    }

    public static void go_MNIST(ArrayList<ArrayList<Double>> TrainingFile, ArrayList<ArrayList<Double>> TestingFile, int desired_percentage_accuracy_training, int num_hidden_neurons, int num_output_neuron, double learning_rate, String filename, double percentage_validation) {

        TrainingFile = split_file_training(TrainingFile, percentage_validation);
        ArrayList<ArrayList<Double>> myValidationSet = split_file_validation(TrainingFile, percentage_validation);
        NeuralNet2 NN = new NeuralNet2(TestingFile.get(0).size()-1, num_hidden_neurons,  num_output_neuron, learning_rate);
        System.out.println("Running Neural Net");
        RunNeuralNet RunNet = new RunNeuralNet();
        RunNet.run_neural_net(NN, TrainingFile, myValidationSet, TestingFile, desired_percentage_accuracy_training, filename);
    }
}
