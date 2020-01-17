import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


// Citations:
// Timer code used from: https://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
// making a file and writing to it: https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java (Note: This was written so that I could take the two data files and create graphs whether in desmos or possibly in the future using plotly.

public class RunNeuralNet {
    ArrayList<Double> x = new ArrayList<>();
    ArrayList<Double> y = new ArrayList<>();

    public RunNeuralNet() {
    }

    public void run_neural_net(NeuralNet2 NN, ArrayList<ArrayList<Double>> TrainingData, ArrayList<ArrayList<Double>> ValidationSet, ArrayList<ArrayList<Double>> TestingData, int desired_percentage_accuracy_validation, String filename) {

        double training_percentage = 0;
        double validation_percentage =0;
        double epochs = 0;
        long startTime = System.currentTimeMillis();
        long per_epoch_Time = System.currentTimeMillis();

        PrintWriter trainingwriter = null;
        try {
            trainingwriter = new PrintWriter("training-data.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        PrintWriter validationwriter = null;
        try {
            validationwriter = new PrintWriter("validation-data.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        while (validation_percentage < desired_percentage_accuracy_validation) {
            if (epochs > 1000000000) {
                System.out.println("----------------------------------");
                System.out.println("over 1 mil epochs can not complete");
                System.out.println("----------------------------------");
                break;
            }
            epochs += 1;

            double total_trials_training = 0;
            double correct_training = 0;
            for (int j = 0; j < TrainingData.size(); j++) {
                NN.train_on_example(TrainingData.get(j));
            }
            long endTime_epoch = System.currentTimeMillis();
            double time = (endTime_epoch - per_epoch_Time); // Timer taken from: https://stackoverflow
            for (int j = 0; j < TrainingData.size(); j++) {
                total_trials_training += 1;
                if (NN.check_output(TrainingData.get(j))) {
                    correct_training += 1;
                }
            }
            training_percentage = (correct_training / total_trials_training) * 100;
            System.out.println(epochs + "," + training_percentage);
            if(epochs % 1 == 0) {
                trainingwriter.println(epochs + "," + training_percentage);
            }


            double correct_validation = 0;
            double total_trials_validation = 0;
            for (int j = 0; j < ValidationSet.size(); j++) {
                total_trials_validation += 1;
                if (NN.check_output(ValidationSet.get(j))) {
                    correct_validation += 1;
                }
            }
            validation_percentage = (correct_validation / total_trials_validation) * 100;
            if(epochs% 1 == 0) {
                validationwriter.println(epochs + "," + validation_percentage);
            }

          //  System.out.println(" validation percentage " + validation_percentage);

        }
        trainingwriter.close();
        validationwriter.close();

        double total_trials_testing = 0;
        double correct_testing = 0;
        for (int j = 0; j < TestingData.size(); j++) {
            total_trials_testing += 1;
            if (NN.check_output(TestingData.get(j))) {
                correct_testing += 1;
            }
        }

        double testing_percentage = (correct_testing / total_trials_testing) * 100;

        System.out.println("----------final data-" + filename + "-----------------------");
        System.out.println("training percentage: " + training_percentage);
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + ((endTime - startTime)) + " ms"); // Timer taken from: https://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
        System.out.println("");
        System.out.println("epochs: " + epochs);
        System.out.println("testing_percentage accuracy: " + testing_percentage);
        System.out.println("-------------------------------------------------------");
    }
}

