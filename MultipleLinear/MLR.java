package MultipleLinear;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import Datasets.Dataset;

public class MLR {

    // Dataset instance
    Dataset dataset = new Dataset();
    private double lambda = 1.0; // Regularization parameter

    public void display() {
        dataset.loadData("Cleaned Data.csv"); // Update the file path accordingly
        dataset.trainTestSplit(0.2); // 80% training, 20% testing

        double[][] X_train = dataset.getXTrain();
        double[] yTrainArray = dataset.getYTrain();
        double[][] y_train = new double[yTrainArray.length][1];
        for (int i = 0; i < yTrainArray.length; i++) {
            y_train[i][0] = yTrainArray[i];
        }

        double[][] X_test = dataset.getXTest();
        double[] yTestArray = dataset.getYTest();

        // Print dimensions of X_train and X_test for debugging
        System.out.println("Number of features (columns) in X_train: " + X_train[0].length);
        System.out.println("Number of features (columns) in X_test: " + X_test[0].length);

        // 1. Transpose of X_train
        double[][] xT = transposeMatrix(X_train);

        // 2. Calculate X^T * X
        double[][] xTx = multiplyMatrices(xT, X_train);

        // 3. Add regularization term (lambda * I) to X^T * X
        double[][] xTxPlusLambdaI = addRegularizationTerm(xTx, lambda);

        // 4. Calculate X^T * y
        double[][] xTy = multiplyMatrices(xT, y_train);

        // 5. Calculate the inverse of (X^T * X + lambda * I)
        double[][] xTxPlusLambdaIInv = inverse(xTxPlusLambdaI);

        // 6. Calculate the regression coefficients beta = (X^T X + lambda * I)^-1 * X^T
        // y
        double[][] beta = multiplyMatrices(xTxPlusLambdaIInv, xTy);

        // Print the number of coefficients for debugging
        System.out.println("Number of coefficients (including intercept): " + beta.length);

        // Round beta values to 2 decimal places
        for (int i = 0; i < beta.length; i++) {
            beta[i][0] = Math.round(beta[i][0] * 100.0) / 100.0;
        }

        for (int i = 0; i < beta.length; i++) {
            System.out.println("Beta" + i + ": " + beta[i][0]);
        }

        System.out.println("\ny = " + beta[0][0] + " + " + beta[1][0] + " x1 + " + beta[2][0] + " x2 + ... + epsilon");

        // Predictions on test set
        System.out.println("Predictions on test set: ");
        double[] predictions = new double[X_test.length];
        for (int i = 0; i < X_test.length; i++) {
            double prediction = beta[0][0]; // Start with the intercept
            for (int j = 1; j < X_test[i].length; j++) {
                prediction += beta[j][0] * X_test[i][j];
            }
            predictions[i] = prediction;
            // System.out.println("Actual: " + yTestArray[i] + ", Predicted: " + prediction);
        }

        // Model evaluation
        evaluateModel(yTestArray, predictions);
    }

    private double[][] addRegularizationTerm(double[][] xTx, double lambda) {
        int n = xTx.length;
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = xTx[i][j];
                if (i == j) {
                    result[i][j] += lambda;
                }
            }
        }
        return result;
    }

    private void evaluateModel(double[] yActual, double[] yPredicted) {
        double mse = meanSquaredError(yActual, yPredicted);
        double mae = meanAbsoluteError(yActual, yPredicted);
        double r2 = rSquared(yActual, yPredicted);

        System.out.println("\nModel Evaluation:");
        System.out.println("Mean Squared Error (MSE): " + mse);
        System.out.println("Mean Absolute Error (MAE): " + mae);
        System.out.println("R-squared (R²): " + r2);
    }

    private double meanSquaredError(double[] yActual, double[] yPredicted) {
        double sum = 0.0;
        for (int i = 0; i < yActual.length; i++) {
            sum += Math.pow(yActual[i] - yPredicted[i], 2);
        }
        return sum / yActual.length;
    }

    private double meanAbsoluteError(double[] yActual, double[] yPredicted) {
        double sum = 0.0;
        for (int i = 0; i < yActual.length; i++) {
            sum += Math.abs(yActual[i] - yPredicted[i]);
        }
        return sum / yActual.length;
    }

    private double rSquared(double[] yActual, double[] yPredicted) {
        double ssTotal = 0.0;
        double ssResidual = 0.0;
        double mean = Arrays.stream(yActual).average().orElse(0.0);

        for (int i = 0; i < yActual.length; i++) {
            ssTotal += Math.pow(yActual[i] - mean, 2);
            ssResidual += Math.pow(yActual[i] - yPredicted[i], 2);
        }

        return 1 - (ssResidual / ssTotal);
    }

    // Method to multiply two matrices
    private double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        int r1 = firstMatrix.length;
        int c1 = firstMatrix[0].length;
        int c2 = secondMatrix[0].length;
        double[][] result = new double[r1][c2];

        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }
        return result;
    }

    // Method to calculate the inverse of a matrix
    private double[][] inverse(double[][] matrix) {
        int n = matrix.length;
        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            inverse[i][i] = 1;
        }
        for (int i = 0; i < n; i++) {
            double t = matrix[i][i];
            for (int j = 0; j < n; j++) {
                matrix[i][j] /= t;
                inverse[i][j] /= t;
            }
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    t = matrix[j][i];
                    for (int k = 0; k < n; k++) {
                        matrix[j][k] -= t * matrix[i][k];
                        inverse[j][k] -= t * inverse[i][k];
                    }
                }
            }
        }
        return inverse;
    }

    // Method to transpose a matrix
    private double[][] transposeMatrix(double[][] matrix) {
        return IntStream.range(0, matrix[0].length)
                .mapToObj(i -> Stream.of(matrix).mapToDouble(row -> row[i]).toArray())
                .toArray(double[][]::new);
    }
}
