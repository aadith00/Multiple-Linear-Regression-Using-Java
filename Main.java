import Datasets.Dataset;
import MultipleLinear.MLR;
import Statistics.StatisticsCalculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int menu; // Variable to store the user's menu choice
        Scanner option = new Scanner(System.in); // Scanner object for user input

        do {
            menu(); // Display the menu options
            menu = option.nextInt(); // Read the user's choice

            switch (menu) {
                case 1:
                    System.out.println("\t---------- D A T A S E T   S T A T I S T I C S   ----------");
                    // Load dataset and calculate statistics
                    Dataset dataset = new Dataset();
                    dataset.loadData("Cleaned Data.csv");
                    StatisticsCalculator.calculateStatistics(dataset.X, dataset.getValidRowCount());
                    break;
                case 2:
                    System.out.println("\t---------- M U L T I L I N E A R   R E G R E S S I O N   ----------");
                    // Perform multiple linear regression
                    new MLR().display();
                    break;
                case 3:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (menu != 3); // Continue until the user chooses to exit

        System.out.println("Program concluded..."); // Display message when program ends
        option.close(); // Close the Scanner instance here to avoid resource leak
    }

    // Method to display the menu options
    static void menu() {
        System.out.println(" -------  MENU ------");
        System.out.println("1. Dataset Statistics");
        System.out.println("2. Multiple Linear Regression");
        System.out.println("3. Exit");
    }
}
