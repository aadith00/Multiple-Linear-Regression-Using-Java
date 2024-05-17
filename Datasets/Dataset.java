package Datasets;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Dataset {
    public double[][] X = new double[2000][19]; // Matrix to store features
    public double[] y = new double[2000]; // Array to store target values
    private double[][] X_train, X_test; // Training and testing feature matrices
    private double[] y_train, y_test; // Training and testing target arrays
    private int validRowCount = 0; // To keep track of the number of valid rows

    // Method to load data from a CSV file
    public void loadData(String filePath) {
        try {
            File file = new File(filePath);
            Scanner reader = new Scanner(file);
            int row_count = 0;

            // Skip the first line (header)
            if (reader.hasNextLine()) {
                reader.nextLine();
            }

            // Read data from each line of the CSV file
            while (reader.hasNextLine() && row_count < 2000) {
                String data = reader.nextLine();
                String[] row = data.split(","); // Split the line by commas to get individual values

                // Initialize the first column with 1 (for bias term in regression)
                X[row_count][0] = 1.0;

                // Iterate over the remaining elements of the row and store them in the feature matrix
                try {
                    for (int i = 1; i < 19; i++) {
                        X[row_count][i] = Double.parseDouble(row[i - 1]); // Convert string to double
                    }
                    y[row_count] = Double.parseDouble(row[18]); // Last column is the target value
                    row_count++;
                } catch (NumberFormatException e) {
                    // If any value cannot be parsed, skip the entire row
                    System.out.println("Skipping invalid row: " + Arrays.toString(row));
                }
            }
            validRowCount = row_count; // Set the valid row count
            reader.close();
        } catch (Exception e) {
            System.out.println("An error occurred while loading data.");
            e.printStackTrace();
        }
    }

    // Method to split the dataset into training and testing sets
    public void trainTestSplit(double testSize) {
        int testCount = (int) (validRowCount * testSize); // Calculate the number of rows for the test set
        int trainCount = validRowCount - testCount; // Calculate the number of rows for the training set

        // Initialize arrays for training and testing data
        X_train = new double[trainCount][19];
        X_test = new double[testCount][19];
        y_train = new double[trainCount];
        y_test = new double[testCount];

        // Copy data from the main dataset to the training and testing arrays
        System.arraycopy(X, 0, X_train, 0, trainCount);
        System.arraycopy(X, trainCount, X_test, 0, testCount);
        System.arraycopy(y, 0, y_train, 0, trainCount);
        System.arraycopy(y, trainCount, y_test, 0, testCount);
    }

    // Getter methods for training and testing data
    public double[][] getXTrain() {
        return X_train;
    }

    public double[][] getXTest() {
        return X_test;
    }

    public double[] getYTrain() {
        return y_train;
    }

    public double[] getYTest() {
        return y_test;
    }

    // Getter method for the number of valid rows in the dataset
    public int getValidRowCount() {
        return validRowCount;
    }
}
