import modules.Calculator;
import modules.MatrixCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (java.io.IOException | InterruptedException ignored) {}
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator<Double> calculator = new MatrixCalculator<Double>();
        while (true){
            clearScreen();
            System.out.println("1. Determinant of a matrix");
            System.out.println("2. Adjoint of a matrix");
            System.out.println("3. Inverse of a matrix");
            System.out.println("4. Multiply two matrices");
            System.out.println("5. Add two matrices");
            System.out.println("6. Subtract two matrices");
            System.out.println("7. Scalar multiply a matrix");
            System.out.println("8. Transpose of a matrix");
            //System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1: {
                        System.out.print("Enter the number of rows: ");
                        int rows = scanner.nextInt();
                        System.out.print("Enter the number of columns: ");
                        int columns = scanner.nextInt();
                        if(rows != columns){
                            System.out.println("Rows and columns must be equal");
                            break;
                        }
                        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
                        System.out.println("Enter the matrix");
                        for (int i = 0; i < rows; i++){
                            ArrayList<Double> row = new ArrayList<>();
                            for (int j = 0; j < columns; j++)
                                row.add(scanner.nextDouble());
                            matrix.add(row);
                        }
                        System.out.println("Determinant = " + calculator.determinant(matrix));
                    } break;
                case 2: {
                        System.out.print("Enter the number of rows: ");
                        int rows = scanner.nextInt();
                        System.out.print("Enter the number of columns: ");
                        int columns = scanner.nextInt();
                        if(rows != columns){
                            System.out.println("Rows and columns must be equal");
                            break;
                        }
                        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
                        for (int i = 0; i < rows; i++){
                            ArrayList<Double> row = new ArrayList<>();
                            for (int j = 0; j < columns; j++){
                                System.out.print("Enter element at row " + (i + 1) + " column " + (j + 1) + ": ");
                                row.add(scanner.nextDouble());
                            }
                            matrix.add(row);
                        }
                        ArrayList<ArrayList<Double>> adjoint = calculator.adjoint(matrix);
                        System.out.println("Adjoint Matrix:");
                        calculator.printMatrix(adjoint);
                    } break;
                case 3: {
                        System.out.print("Enter the number of rows: ");
                        int rows = scanner.nextInt();
                        System.out.print("Enter the number of columns: ");
                        int columns = scanner.nextInt();
                        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
                        for (int i = 0; i < rows; i++) {
                            ArrayList<Double> row = new ArrayList<>();
                            for (int j = 0; j < columns; j++) {
                                System.out.print("Enter element at row " + (i + 1) + " column " + (j + 1) + ": ");
                                row.add(scanner.nextDouble());
                            }
                            matrix.add(row);
                        }
                        ArrayList<ArrayList<Double>> inverse = calculator.inverse(matrix);
                        System.out.println("Inverse Matrix:");
                        calculator.printMatrix(inverse);
                    } break;
                case 4: {
                        System.out.print("Enter the number of rows in matrix 1: ");
                        int rows1 = scanner.nextInt();
                        System.out.print("Enter the number of columns in matrix 1: ");
                        int columns1 = scanner.nextInt();
                        System.out.print("Enter the number of rows in matrix 2: ");
                        int rows2 = scanner.nextInt();
                        System.out.print("Enter the number of columns in matrix 2: ");
                        int columns2 = scanner.nextInt();

                        if(columns1 != rows2){
                            System.out.println("Columns of matrix 1 must be equal to rows of matrix 2");
                            break;
                        }

                        ArrayList<ArrayList<Double>> matrix1 = new ArrayList<>();
                        System.out.println("Enter first matrix");
                        for (int i = 0; i < rows1; i++) {
                            ArrayList<Double> row = new ArrayList<>();
                            for (int j = 0; j < columns1; j++)
                                row.add(scanner.nextDouble());
                            matrix1.add(row);
                        }

                        ArrayList<ArrayList<Double>> matrix2 = new ArrayList<>();
                        System.out.println("Enter second matrix");
                        for (int i = 0; i < rows2; i++) {
                            ArrayList<Double> row = new ArrayList<>();
                            for (int j = 0; j < columns2; j++)
                                row.add(scanner.nextDouble());
                            matrix2.add(row);
                        }
                        ArrayList<ArrayList<Double>> product = calculator.multiply(matrix1, matrix2);
                        System.out.println("Product Matrix:");
                        calculator.printMatrix(product);
                    } break;
                case 5:{
                        System.out.print("Enter the number of rows in matrix 1: ");
                        int rows1 = scanner.nextInt();
                        System.out.print("Enter the number of columns in matrix 1: ");
                        int columns1 = scanner.nextInt();
                        System.out.print("Enter the number of rows in matrix 2: ");
                        int rows2 = scanner.nextInt();
                        System.out.print("Enter the number of columns in matrix 2: ");
                        int columns2 = scanner.nextInt();

                        if(rows1 != rows2 || columns1 != columns2){
                            System.out.println("Rows and columns of both matrices must be equal");
                            break;
                        }

                        ArrayList<ArrayList<Double>> matrix1 = new ArrayList<>();
                        System.out.println("Enter first matrix");
                        for (int i = 0; i < rows1; i++) {
                            ArrayList<Double> row = new ArrayList<>();
                            for (int j = 0; j < columns1; j++)
                                row.add(scanner.nextDouble());
                            matrix1.add(row);
                        }

                        ArrayList<ArrayList<Double>> matrix2 = new ArrayList<>();
                        System.out.println("Enter second matrix");
                        for (int i = 0; i < rows2; i++) {
                            ArrayList<Double> row = new ArrayList<>();
                            for (int j = 0; j < columns2; j++)
                                row.add(scanner.nextDouble());
                            matrix2.add(row);
                        }
                        ArrayList<ArrayList<Double>> sum = calculator.add(matrix1, matrix2);
                        System.out.println("Sum Matrix:");
                        calculator.printMatrix(sum);
                    } break;
                case 6: {
                        System.out.print("Enter the number of rows in matrix 1: ");
                        int rows1 = scanner.nextInt();
                        System.out.print("Enter the number of columns in matrix 1: ");
                        int columns1 = scanner.nextInt();
                        System.out.print("Enter the number of rows in matrix 2: ");
                        int rows2 = scanner.nextInt();
                        System.out.print("Enter the number of columns in matrix 2: ");
                        int columns2 = scanner.nextInt();

                        if(rows1 != rows2 || columns1 != columns2){
                            System.out.println("Rows and columns of both matrices must be equal");
                            break;
                        }

                        ArrayList<ArrayList<Double>> matrix1 = new ArrayList<>();
                        System.out.println("Enter first matrix");
                        for (int i = 0; i < rows1; i++) {
                            ArrayList<Double> row = new ArrayList<>();
                            for (int j = 0; j < columns1; j++)
                                row.add(scanner.nextDouble());
                            matrix1.add(row);
                        }

                        ArrayList<ArrayList<Double>> matrix2 = new ArrayList<>();
                        System.out.println("Enter second matrix");
                        for (int i = 0; i < rows2; i++) {
                            ArrayList<Double> row = new ArrayList<>();
                            for (int j = 0; j < columns2; j++)
                                row.add(scanner.nextDouble());
                            matrix2.add(row);
                        }
                        ArrayList<ArrayList<Double>> difference = calculator.subtract(matrix1, matrix2);
                        System.out.println("Difference Matrix:");
                        calculator.printMatrix(difference);
                    } break;
                case 7: {
                        System.out.print("Enter the number of rows: ");
                        int rows = scanner.nextInt();
                        System.out.print("Enter the number of columns: ");
                        int columns = scanner.nextInt();
                        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
                        System.out.println("Enter the matrix");
                        for (int i = 0; i < rows; i++) {
                            ArrayList<Double> row = new ArrayList<>();
                            for (int j = 0; j < columns; j++)
                                row.add(scanner.nextDouble());
                            matrix.add(row);
                        }
                        System.out.print("Enter the scalar: ");
                        Double scalar = scanner.nextDouble();
                        ArrayList<ArrayList<Double>> product = calculator.scalarMultiply(matrix, scalar);
                        System.out.println("Product Matrix:");
                        calculator.printMatrix(product);
                    } break;
                case 8: {
                        System.out.print("Enter the number of rows: ");
                        int rows = scanner.nextInt();
                        System.out.print("Enter the number of columns: ");
                        int columns = scanner.nextInt();
                        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
                        System.out.println("Enter the matrix");
                        for (int i = 0; i < rows; i++) {
                            ArrayList<Double> row = new ArrayList<>();
                            for (int j = 0; j < columns; j++)
                                row.add(scanner.nextDouble());
                            matrix.add(row);
                        }
                        ArrayList<ArrayList<Double>> transpose = calculator.transpose(matrix);
                        System.out.println("Transpose Matrix:");
                        calculator.printMatrix(transpose);
                    } break;
                default: System.out.println("Invalid choice");
            }
            System.out.print("Do you want to continue? (y/n): ");
            char ch = scanner.next().charAt(0);
            if (ch != 'y' && ch != 'Y') break;
        }
    }
}