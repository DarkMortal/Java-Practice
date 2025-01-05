package modules;
import java.util.ArrayList;

public interface Calculator <T extends Number> {
    default void printMatrix(ArrayList<ArrayList<T>> matrix){
        for (ArrayList<T> row : matrix){
            for (T element : row){
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
    Double determinant(ArrayList<ArrayList<T>> matrix) throws ArithmeticException;
    ArrayList<ArrayList<Double>> adjoint(ArrayList<ArrayList<T>> matrix) throws ArithmeticException;
    ArrayList<ArrayList<Double>> inverse(ArrayList<ArrayList<T>> matrix) throws ArithmeticException;
    ArrayList<ArrayList<Double>> multiply(ArrayList<ArrayList<T>> matrix1, ArrayList<ArrayList<T>> matrix2) throws ArithmeticException;
    ArrayList<ArrayList<Double>> add(ArrayList<ArrayList<T>> matrix1, ArrayList<ArrayList<T>> matrix2) throws ArithmeticException;
    ArrayList<ArrayList<Double>> subtract(ArrayList<ArrayList<T>> matrix1, ArrayList<ArrayList<T>> matrix2) throws ArithmeticException;
    ArrayList<ArrayList<Double>> scalarMultiply(ArrayList<ArrayList<T>> matrix, T scalar);
    ArrayList<ArrayList<T>> transpose(ArrayList<ArrayList<T>> matrix);
}
