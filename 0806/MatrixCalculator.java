/*
練習 1.2：矩陣運算器
實作二維陣列的基本矩陣運算：

1. 矩陣加法：計算兩個相同維度矩陣的和
2. 矩陣乘法：計算兩個可相乘矩陣的乘積
3. 矩陣轉置：將行列互換
4. 尋找矩陣中的最大值和最小值
*/

import java.util.Scanner;

public class MatrixCalculator {
    
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * Matrix addition - adds two matrices of same dimensions
     */
    public static int[][] add(int[][] matrixA, int[][] matrixB) {
        // Input validation
        if (matrixA == null || matrixB == null) {
            throw new IllegalArgumentException("Matrices cannot be null");
        }
        
        int rowsA = matrixA.length;
        int rowsB = matrixB.length;
        
        if (rowsA == 0 || rowsB == 0) {
            throw new IllegalArgumentException("Matrices cannot be empty");
        }
        
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;
        
        if (rowsA != rowsB || colsA != colsB) {
            throw new IllegalArgumentException(
                String.format("Matrix dimensions must match for addition. " +
                            "Matrix A: %dx%d, Matrix B: %dx%d", 
                            rowsA, colsA, rowsB, colsB));
        }
        
        // Perform addition
        int[][] result = new int[rowsA][colsA];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                result[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        
        return result;
    }
    
    /**
     * Matrix multiplication - multiplies two compatible matrices
     */
    public static int[][] multiply(int[][] matrixA, int[][] matrixB) {
        // Input validation
        if (matrixA == null || matrixB == null) {
            throw new IllegalArgumentException("Matrices cannot be null");
        }
        
        int rowsA = matrixA.length;
        int rowsB = matrixB.length;
        
        if (rowsA == 0 || rowsB == 0) {
            throw new IllegalArgumentException("Matrices cannot be empty");
        }
        
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;
        
        if (colsA != rowsB) {
            throw new IllegalArgumentException(
                String.format("Matrix A columns (%d) must equal Matrix B rows (%d) for multiplication", 
                            colsA, rowsB));
        }
        
        // Perform multiplication
        int[][] result = new int[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        
        return result;
    }
    
    /**
     * Matrix transpose - swaps rows and columns
     */
    public static int[][] transpose(int[][] matrix) {
        // Input validation
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        }
        
        int rows = matrix.length;
        if (rows == 0) {
            throw new IllegalArgumentException("Matrix cannot be empty");
        }
        
        int cols = matrix[0].length;
        
        // Perform transpose
        int[][] result = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        
        return result;
    }
    
    /**
     * Find maximum value in matrix
     */
    public static int findMax(int[][] matrix) {
        // Input validation
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        }
        
        int rows = matrix.length;
        if (rows == 0) {
            throw new IllegalArgumentException("Matrix cannot be empty");
        }
        
        int cols = matrix[0].length;
        if (cols == 0) {
            throw new IllegalArgumentException("Matrix cannot be empty");
        }
        
        // Find maximum
        int max = matrix[0][0];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }
        
        return max;
    }
    
    /**
     * Find minimum value in matrix
     */
    public static int findMin(int[][] matrix) {
        // Input validation
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        }
        
        int rows = matrix.length;
        if (rows == 0) {
            throw new IllegalArgumentException("Matrix cannot be empty");
        }
        
        int cols = matrix[0].length;
        if (cols == 0) {
            throw new IllegalArgumentException("Matrix cannot be empty");
        }
        
        // Find minimum
        int min = matrix[0][0];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
            }
        }
        
        return min;
    }
    
    /**
     * Print matrix in formatted way
     */
    public static void printMatrix(int[][] matrix, String title) {
        System.out.println("\n" + title + ":");
        System.out.println("-".repeat(title.length() + 1));
        
        if (matrix == null) {
            System.out.println("null");
            return;
        }
        
        if (matrix.length == 0) {
            System.out.println("Empty matrix");
            return;
        }
        
        // Find the maximum width for formatting
        int maxWidth = 0;
        for (int[] row : matrix) {
            for (int value : row) {
                maxWidth = Math.max(maxWidth, String.valueOf(value).length());
            }
        }
        
        // Print matrix with proper formatting
        for (int[] row : matrix) {
            System.out.print("[");
            for (int j = 0; j < row.length; j++) {
                System.out.printf("%" + maxWidth + "d", row[j]);
                if (j < row.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
    
    /**
     * Read matrix from user input
     */
    public static int[][] readMatrix(String matrixName) {
        System.out.println("\n--- Input " + matrixName + " ---");
        
        int rows, cols;
        
        // Get matrix dimensions
        while (true) {
            try {
                System.out.print("Enter number of rows: ");
                rows = Integer.parseInt(scanner.nextLine().trim());
                if (rows <= 0) {
                    System.out.println(" Rows must be positive!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a valid integer!");
            }
        }
        
        while (true) {
            try {
                System.out.print("Enter number of columns: ");
                cols = Integer.parseInt(scanner.nextLine().trim());
                if (cols <= 0) {
                    System.out.println(" Columns must be positive!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a valid integer!");
            }
        }
        
        int[][] matrix = new int[rows][cols];
        
        System.out.println("\nEnter matrix elements:");
        System.out.println("(You can enter all elements for a row separated by spaces)");
        
        // Read matrix elements
        for (int i = 0; i < rows; i++) {
            while (true) {
                try {
                    System.out.printf("Row %d: ", i + 1);
                    String[] elements = scanner.nextLine().trim().split("\\s+");
                    
                    if (elements.length != cols) {
                        System.out.printf(" Please enter exactly %d numbers!\n", cols);
                        continue;
                    }
                    
                    for (int j = 0; j < cols; j++) {
                        matrix[i][j] = Integer.parseInt(elements[j]);
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" Please enter valid integers only!");
                }
            }
        }
        
        System.out.println("\n Matrix input completed!");
        printMatrix(matrix, matrixName);
        return matrix;
    }
    
    /**
     * Display main menu
     */
    public static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           MATRIX CALCULATOR MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Matrix Addition (A + B)");
        System.out.println("2. Matrix Multiplication (A x B)");
        System.out.println("3. Matrix Transpose");
        System.out.println("4. Find Maximum and Minimum values");
        System.out.println("5. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Choose an option (1-5): ");
    }
    
    /**
     * Handle matrix addition operation
     */
    public static void handleAddition() {
        System.out.println("\n MATRIX ADDITION");
        System.out.println("Note: Both matrices must have the same dimensions");
        
        try {
            int[][] matrixA = readMatrix("Matrix A");
            int[][] matrixB = readMatrix("Matrix B");
            
            int[][] result = add(matrixA, matrixB);
            
            System.out.println("\n" + "=".repeat(40));
            System.out.println("RESULT: A + B");
            printMatrix(result, "Sum");
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Handle matrix multiplication operation
     */
    public static void handleMultiplication() {
        System.out.println("\n MATRIX MULTIPLICATION");
        System.out.println("Note: Matrix A columns must equal Matrix B rows");
        
        try {
            int[][] matrixA = readMatrix("Matrix A");
            int[][] matrixB = readMatrix("Matrix B");
            
            int[][] result = multiply(matrixA, matrixB);
            
            System.out.println("\n" + "=".repeat(40));
            System.out.println("RESULT: A x B");
            printMatrix(result, "Product");
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Handle matrix transpose operation
     */
    public static void handleTranspose() {
        System.out.println("\n MATRIX TRANSPOSE");
        
        try {
            int[][] matrix = readMatrix("Matrix");
            int[][] result = transpose(matrix);
            
            System.out.println("\n" + "=".repeat(40));
            System.out.println("RESULT: Matrix Transpose");
            printMatrix(result, "Transposed Matrix");
            
        } catch (IllegalArgumentException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }
    
    /**
     * Handle find max/min operation
     */
    public static void handleMaxMin() {
        System.out.println("\n FIND MAXIMUM AND MINIMUM");
        
        try {
            int[][] matrix = readMatrix("Matrix");
            int max = findMax(matrix);
            int min = findMin(matrix);
            
            System.out.println("\n" + "=".repeat(40));
            System.out.println("RESULT: Maximum and Minimum Values");
            System.out.println("Maximum value: " + max);
            System.out.println("Minimum value: " + min);
            
        } catch (IllegalArgumentException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }
    
    /**
     * Main interactive loop
     */
    public static void runCalculator() {
        System.out.println("Welcome to Interactive Matrix Calculator!");
        System.out.println("This calculator supports various matrix operations.");
        
        while (true) {
            displayMenu();
            
            try {
                String input = scanner.nextLine().trim();
                
                switch (input) {
                    case "1":
                        handleAddition();
                        break;
                    case "2":
                        handleMultiplication();
                        break;
                    case "3":
                        handleTranspose();
                        break;
                    case "4":
                        handleMaxMin();
                        break;
                    case "5":
                        System.out.println("\nThank you for using Matrix Calculator!");
                        System.out.println("Goodbye! ");
                        return;
                    default:
                        System.out.println(" Invalid option! Please choose 1-5");
                        continue;
                }
                
                // Ask if user wants to continue
                System.out.print("\nWould you like to perform another operation? (y/n): ");
                String continueChoice = scanner.nextLine().trim().toLowerCase();
                
                if (!continueChoice.equals("y") && !continueChoice.equals("yes")) {
                    System.out.println("\nThank you for using Matrix Calculator!");
                    System.out.println("Goodbye! ");
                    break;
                }
                
            } catch (Exception e) {
                System.out.println(" An unexpected error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }
    
    /**
     * Main method 
     */
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("              INTERACTIVE MATRIX CALCULATOR");
        System.out.println("=".repeat(60));
        
        runCalculator();
        scanner.close();
    }
}