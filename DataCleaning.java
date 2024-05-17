import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class DataCleaning {
    public static void main(String[] args) {
        try {
            File file = new File("Life Expectancy Data.csv"); // Original dataset
            Scanner reader = new Scanner(file); // Reader for the original dataset

            File newfile = new File("Cleaned Data.csv"); // Creating a new file to add clean row
            FileWriter writer = new FileWriter(newfile, true);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] row = data.split(","); // Reading each line and storing it into an array.

                boolean hasEmptyElement = false; // Variable to indicate if any element in the row is empty or null

                for (String elment : row) { // Checking if any of the element is empty and skipping
                    if (elment == null || elment.isEmpty()) {
                        hasEmptyElement = true;
                        break;
                    }
                }

                if (!hasEmptyElement) { // Writing the rows without empty values to the new file
                    writer.write(data + "\n");
                }
            }
            reader.close();
            writer.close();

        } catch (Exception e) {
            System.out.println("An unexpected error occured!!");
            e.printStackTrace();
        }
    }
}