package modules;

import java.util.*;

import modules.Calculator;

public class MatrixCalculator<T extends Number> implements Calculator<T> {

    private Object cast(T x){
        if(x instanceof Integer) return (Integer) x;
        if(x instanceof Double) return (Double) x;
        if(x instanceof Float) return (Float) x;
        if(x instanceof Long) return (Long) x;
        if(x instanceof Short) return (Short) x;
        return (Double) x;
    }

    @Override
    public Double determinant(ArrayList<ArrayList<T>> matrix) throws ArithmeticException {
        if(matrix.size() != matrix.getFirst().size())
            throw new ArithmeticException("Matrix must be square");
        if(matrix.size() == 1) return matrix.getFirst().getFirst().doubleValue();
        if (matrix.size() == 2) {
            return (matrix.get(0).get(0)).doubleValue() * (matrix.get(1).get(1)).doubleValue() -
                   (matrix.get(0).get(1)).doubleValue() * (matrix.get(1).get(0)).doubleValue();
        }
        double det = 0;
        int n = matrix.size();
        for (int i = 0; i < n; i++) {
            ArrayList<ArrayList<T>> subMatrix = new ArrayList<>();
            for (int j = 1; j < n; j++) {
                ArrayList<T> row = new ArrayList<>();
                for (int k = 0; k < n; k++) {
                    if (k != i) {
                        row.add(matrix.get(j).get(k));
                    }
                }
                subMatrix.add(row);
            }
            det += (Math.pow(-1, i) * matrix.getFirst().get(i).doubleValue() * determinant(subMatrix).doubleValue());
        }
        return det;
    }

    @Override
    public ArrayList<ArrayList<Double>> adjoint(ArrayList<ArrayList<T>> matrix) throws ArithmeticException {
        if(matrix.size() != matrix.getFirst().size())
            throw new ArithmeticException("Matrix must be square");
        if(matrix.size() == 1) return new ArrayList<>(List.of(
                new ArrayList<>(List.of((matrix.getFirst().getFirst().doubleValue()))
        )));
        if (matrix.size() == 2) {
            return new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(matrix.get(1).get(1).doubleValue(), -1.0 * matrix.get(0).get(1).doubleValue())),
                new ArrayList<>(Arrays.asList(-1.0 * matrix.get(1).get(0).doubleValue(), matrix.get(0).get(0).doubleValue()))
            ));
        }
        int n = matrix.size();
        ArrayList<ArrayList<Double>> adjoint = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                ArrayList<ArrayList<T>> subMatrix = new ArrayList<>();
                for (int k = 0; k < n; k++) {
                    if (k != i) {
                        ArrayList<T> subRow = new ArrayList<>();
                        for (int l = 0; l < n; l++) {
                            if (l != j) {
                                subRow.add(matrix.get(k).get(l));
                            }
                        }
                        subMatrix.add(subRow);
                    }
                }
                row.add((Math.pow(-1, i + j) * determinant(subMatrix).doubleValue()));
            }
            adjoint.add(row);
        } return adjoint;
    }

    @Override
    public ArrayList<ArrayList<Double>> inverse(ArrayList<ArrayList<T>> matrix) throws ArithmeticException {
        double det = (double)determinant(matrix);
        if (det == 0)
            throw new ArithmeticException("Matrix is not invertible");
        ArrayList<ArrayList<Double>> adjoint = this.adjoint(matrix);
        for (ArrayList<Double> doubles : adjoint) {
            doubles.replaceAll(aDouble -> aDouble / det);
        } return adjoint;
    }

    @Override
    public ArrayList<ArrayList<Double>> multiply(ArrayList<ArrayList<T>> matrix1, ArrayList<ArrayList<T>> matrix2) throws ArithmeticException {
        if (matrix1.getFirst().size() != matrix2.size())
            throw new ArithmeticException("Matrix dimensions do not match");
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for (ArrayList<T> numbers : matrix1) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < matrix2.getFirst().size(); j++) {
                double sum = 0;
                for (int k = 0; k < matrix2.size(); k++) {
                    sum += numbers.get(k).doubleValue() * matrix2.get(k).get(j).doubleValue();
                }
                row.add(sum);
            }
            result.add(row);
        }
        return result;
    }

    @Override
    public ArrayList<ArrayList<Double>> add(ArrayList<ArrayList<T>> matrix1, ArrayList<ArrayList<T>> matrix2) throws ArithmeticException {
        if (matrix1.size() != matrix2.size() || matrix1.getFirst().size() != matrix2.getFirst().size())
            throw new ArithmeticException("Matrix dimensions do not match");
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for (int i = 0; i < matrix1.size(); i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < matrix1.get(i).size(); j++)
                row.add(matrix1.get(i).get(j).doubleValue() + matrix2.get(i).get(j).doubleValue());
            result.add(row);
        }
        return result;
    }

    @Override
    public ArrayList<ArrayList<Double>> subtract(ArrayList<ArrayList<T>> matrix1, ArrayList<ArrayList<T>> matrix2) throws ArithmeticException {
        if (matrix1.size() != matrix2.size() || matrix1.getFirst().size() != matrix2.getFirst().size())
            throw new ArithmeticException("Matrix dimensions do not match");
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for (int i = 0; i < matrix1.size(); i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < matrix1.get(i).size(); j++)
                row.add(matrix1.get(i).get(j).doubleValue() - matrix2.get(i).get(j).doubleValue());
            result.add(row);
        }
        return result;
    }

    @Override
    public ArrayList<ArrayList<Double>> scalarMultiply (ArrayList<ArrayList<T>> matrix, T scalar) {
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for (ArrayList<T> ts : matrix) {
            ArrayList<Double> row = new ArrayList<>();
            for (T t : ts) row.add(t.doubleValue() * scalar.doubleValue());
            result.add(row);
        }
        return result;
    }

    @Override
    public ArrayList<ArrayList<T>> transpose(ArrayList<ArrayList<T>> matrix) {
        ArrayList<ArrayList<T>> result = new ArrayList<>();
        for (int i = 0; i < matrix.getFirst().size(); i++) {
            ArrayList<T> row = new ArrayList<>();
            for (ArrayList<T> numbers : matrix) row.add(numbers.get(i));
            result.add(row);
        }
        return result;
    }
}
