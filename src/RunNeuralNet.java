import java.util.ArrayList;

public class RunNeuralNet {

    public RunNeuralNet() {
    // TODO:Create a function to calculate percentage: run training until desired accuracy is hit, after that run it on the testing set
    }

    public void run_neural_net(NeuralNet2 NN, ArrayList<ArrayList<Double>> TrainingData, ArrayList<ArrayList<Double>> TestingData, int desired_percentage_accuracy_training, String filename) {
        double training_percentage = 0;
        double epochs = 0;
        long startTime = System.currentTimeMillis();
        while (training_percentage < desired_percentage_accuracy_training) {
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
            for (int j = 0; j < TrainingData.size(); j++) {
                total_trials_training += 1;
                if (NN.check_output(TrainingData.get(j))) {
                    correct_training += 1;
                }
            }
            System.out.println("correct_training: " + correct_training);

            training_percentage = (correct_training / total_trials_training) * 100;
            System.out.println("epochs: " + epochs);
            System.out.println("training_percentage accuracy: " + training_percentage);


        }
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
        System.out.println("That took " + (endTime - startTime) + " milliseconds"); // Timer taken from: https://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
        System.out.println("");
        System.out.println("epochs: " + epochs);
        System.out.println("testing_percentage accuracy: " + testing_percentage);
        System.out.println("-------------------------------------------------------");
    }


}

