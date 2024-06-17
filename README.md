Sure, here's a README file for your project:

# Multiple Linear Regression Project

This project is an implementation of Multiple Linear Regression using Java. It includes functionality to load a dataset, calculate various statistics, perform multiple linear regression, and evaluate the model's performance.

## Features

- Load a dataset from a CSV file
- Calculate descriptive statistics (mean, median, standard deviation, min, max, mode, variance, range, skewness, kurtosis) for each feature
- Compute the correlation matrix for the features
- Implement Multiple Linear Regression with regularization (Ridge Regression)
- Evaluate the model's performance using Mean Squared Error (MSE), Mean Absolute Error (MAE), and R-squared (R²)

## Requirements

- Java Development Kit (JDK) 8 or later

## Usage

1. Clone the repository or download the source code.
2. Open the project in your preferred Java IDE or compile the source files from the command line.
3. Update the file path in the `dataset.loadData("Cleaned Data.csv");` line to point to your dataset's CSV file.
4. Run the `Main` class.
5. Follow the menu prompts to choose the desired operation:
   - Option 1: Calculate and display dataset statistics
   - Option 2: Perform Multiple Linear Regression
   - Option 3: Exit the program

## Code Structure

- `Main.java`: Entry point of the program, contains the main menu and user interaction.
- `Dataset.java`: Class responsible for loading the dataset from a CSV file and splitting it into training and testing sets.
- `StatisticsCalculator.java`: Class containing methods to calculate various statistics for the dataset.
- `MLR.java`: Class implementing Multiple Linear Regression with regularization and model evaluation.

## Example Output

```
 -------  MENU ------
1. Dataset Statistics
2. Multiple Linear Regression
3. Exit
1

        ---------- D A T A S E T   S T A T I S T I C S   ----------
Calculating statistics...
Column    Mean    Median    Std Dev    Min    Max    Mode    Variance    Range    Skewness    Kurtosis
    1    1.00    1.00    0.00    1.00    1.00    1.00    0.00    0.00    0.00    0.00
    2    3.52    3.53    0.54    1.00    5.00    3.00    0.29    4.00    -0.14    0.10
    3    5.42    5.42    0.52    3.00    7.90    5.42    0.28    4.90    0.08    0.44
    ...

Correlation Matrix:
1.00    0.12    0.72    ...
0.12    1.00    0.05    ...
0.72    0.05    1.00    ...
...

 -------  MENU ------
1. Dataset Statistics
2. Multiple Linear Regression
3. Exit
2

        ---------- M U L T I L I N E A R   R E G R E S S I O N   ----------
Number of features (columns) in X_train: 19
Number of features (columns) in X_test: 19
Number of coefficients (including intercept): 19

Beta0: 2.35
Beta1: 0.12
Beta2: 0.45
...

y = 2.35 + 0.12 x1 + 0.45 x2 + ... + epsilon

Predictions on test set:
...

Model Evaluation:
Mean Squared Error (MSE): 0.23
Mean Absolute Error (MAE): 0.41
R-squared (R²): 0.87

 -------  MENU ------
1. Dataset Statistics
2. Multiple Linear Regression
3. Exit
3
Exiting the program...
Program concluded...
```

## Contributing

Contributions to this project are welcome. If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.