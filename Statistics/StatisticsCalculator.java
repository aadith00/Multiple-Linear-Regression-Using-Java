package Statistics;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsCalculator {

    // Method to calculate and display statistics for the given data
    public static void calculateStatistics(double[][] data, int validRowCount) {
        System.out.println("Calculating statistics...");

        int numColumns = data[0].length - 1; // Excluding the bias term

        // Print header row for statistics table
        System.out
                .println("Column\t  Mean\tMedian\tStd Dev\t  Min\t  Max\t  Mode\tVariance\t Range\tSkewness\tKurtosis");

        // Loop through each column and calculate statistics
        for (int col = 1; col <= numColumns; col++) {
            List<Double> columnData = new ArrayList<>();
            for (int row = 0; row < validRowCount; row++) {
                columnData.add(data[row][col]);
            }

            // Calculate statistics for the current column
            double mean = columnData.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
            double median = calculateMedian(columnData);
            double stdDev = calculateStandardDeviation(columnData, mean);
            double min = columnData.stream().mapToDouble(Double::doubleValue).min().orElse(Double.NaN);
            double max = columnData.stream().mapToDouble(Double::doubleValue).max().orElse(Double.NaN);
            double mode = calculateMode(columnData);
            double variance = stdDev * stdDev;
            double range = max - min;
            double skewness = calculateSkewness(columnData, mean, stdDev);
            double kurtosis = calculateKurtosis(columnData, mean, stdDev);

            // Round off the statistics
            mean = round(mean);
            median = round(median);
            stdDev = round(stdDev);
            min = round(min);
            max = round(max);
            mode = round(mode);
            variance = round(variance);
            range = round(range);
            skewness = round(skewness);
            kurtosis = round(kurtosis);

            // Print statistics for current column
            System.out.printf("%6d\t%6.2f\t%6.2f\t%6.2f\t%6.2f\t%6.2f\t%6.2f\t%8.2f\t%6.2f\t%8.2f\t%8.2f\n",
                    col, mean, median, stdDev, min, max, mode, variance, range, skewness, kurtosis);
        }

        // Calculate and display the correlation matrix
        double[][] correlationMatrix = calculateCorrelationMatrix(data, validRowCount);
        displayCorrelationMatrix(correlationMatrix);
    }

    // Method to round off a double value to two decimal places
    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    // Method to calculate the median of a list of values
    private static double calculateMedian(List<Double> data) {
        List<Double> sortedData = data.stream().sorted().collect(Collectors.toList());
        int size = sortedData.size();
        if (size % 2 == 0) {
            return (sortedData.get(size / 2 - 1) + sortedData.get(size / 2)) / 2.0;
        } else {
            return sortedData.get(size / 2);
        }
    }

    // Method to calculate the standard deviation of a list of values
    private static double calculateStandardDeviation(List<Double> data, double mean) {
        double variance = data.stream().mapToDouble(value -> Math.pow(value - mean, 2)).average().orElse(Double.NaN);
        return Math.sqrt(variance);
    }

    // Method to calculate the mode of a list of values
    private static double calculateMode(List<Double> data) {
        Map<Double, Long> frequencyMap = data.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        return frequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(Double.NaN);
    }

    // Method to calculate the skewness of a list of values
    private static double calculateSkewness(List<Double> data, double mean, double stdDev) {
        double n = data.size();
        double skewness = data.stream().mapToDouble(value -> Math.pow((value - mean) / stdDev, 3)).sum() / n;
        return skewness;
    }

    // Method to calculate the kurtosis of a list of values
    private static double calculateKurtosis(List<Double> data, double mean, double stdDev) {
        double n = data.size();
        double kurtosis = data.stream().mapToDouble(value -> Math.pow((value - mean) / stdDev, 4)).sum() / n;
        return kurtosis - 3.0; // Excess kurtosis (subtract 3)
    }

    // Method to calculate the correlation matrix of the given data
    private static double[][] calculateCorrelationMatrix(double[][] data, int validRowCount) {
        int numFeatures = data[0].length - 1; // Excluding the bias term
        double[][] correlationMatrix = new double[numFeatures][numFeatures];

        for (int i = 1; i <= numFeatures; i++) {
            for (int j = 1; j <= numFeatures; j++) {
                double[] col1 = new double[validRowCount];
                double[] col2 = new double[validRowCount];
                for (int k = 0; k < validRowCount; k++) {
                    col1[k] = data[k][i];
                    col2[k] = data[k][j];
                }
                correlationMatrix[i - 1][j - 1] = calculateCorrelationCoefficient(col1, col2);
            }
        }

        return correlationMatrix;
    }

    // Method to calculate the correlation coefficient between two columns
    private static double calculateCorrelationCoefficient(double[] col1, double[] col2) {
        double mean1 = Arrays.stream(col1).average().orElse(Double.NaN);
        double mean2 = Arrays.stream(col2).average().orElse(Double.NaN);
        double sumProduct = 0.0;
        double sumSquareDiff1 = 0.0;
        double sumSquareDiff2 = 0.0;

        for (int i = 0; i < col1.length; i++) {
            double diff1 = col1[i] - mean1;
            double diff2 = col2[i] - mean2;
            sumProduct += diff1 * diff2;
            sumSquareDiff1 += Math.pow(diff1, 2);
            sumSquareDiff2 += Math.pow(diff2, 2);
        }

        return sumProduct / Math.sqrt(sumSquareDiff1 * sumSquareDiff2);
    }

    // Method to display the correlation matrix
    private static void displayCorrelationMatrix(double[][] correlationMatrix) {
        System.out.println("Correlation Matrix:");
        for (double[] row : correlationMatrix) {
            for (double value : row) {
                System.out.printf("%.2f\t", value);
            }
            System.out.println();
        }
    }
}
