
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class OptimalExclusion {
    private static final double EPS = 1e-5;

    // Gauss-Jordan elimination with partial pivoting
    public static double[] solve(double[][] matrix, double[] complement) {
        double[] b = Arrays.copyOf(complement, complement.length);

        boolean[] usedRows = new boolean[matrix.length];
        int[] usedRowsIndices = new int[matrix.length];

        int rows = matrix.length;
        int columns = matrix[0].length;

        for (int column = 0; column < columns; column++) {
            Element element = findBiggestElement(matrix , column,usedRows);
            usedRows[element.raw] = true;

           // int closestToRowIndex = findBestUnusedPivotIndex(matrix, column, usedRows);
            int closestToRowIndex = element.raw;
            usedRowsIndices[column] =  element.raw ;//closestToRowIndex;

            AtomicInteger threadsToRun = new AtomicInteger(rows - 1);
            for (int row = 0; row < rows; row++) {
                int finalRow = row;
                if (finalRow != closestToRowIndex) {
                    int finalColumn = column;
                    new Thread(
                            () -> {
                                double diff = matrix[finalRow][finalColumn] / matrix[closestToRowIndex][finalColumn];
                                for (int col = 0; col < columns; col++) {
                                    matrix[finalRow][col] -= matrix[closestToRowIndex][col] * diff;
                                }
                                b[finalRow] -= b[closestToRowIndex] * diff;

                                if (threadsToRun.decrementAndGet() == 0) {
                                    synchronized (threadsToRun) {
                                        threadsToRun.notifyAll();
                                    }
                                }
                            }).start();

                }
            }

            synchronized (threadsToRun) {
                if (threadsToRun.get() != 0) {
                    try {
                        threadsToRun.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


            double diff = 1 / matrix[closestToRowIndex][column];
            for (int col = 0; col < columns; col++) {
                matrix[closestToRowIndex][col] *= diff;
            }
            b[closestToRowIndex] *= diff;

        }
        double[] x = new double[b.length];
        for (int column = 0; column < columns; column++) {
            x[column] = b[usedRowsIndices[column]];
        }
        return x;
    }

    /**
     * Impure function (Modifies @param usedRows)
     * Returns an index of a best-fitting non-used pivot element
     *
     * @param matrix
     * @param column
     * @param usedRows
     * @return
     */


    public static int findBestUnusedPivotIndex(double[][] matrix, int column, boolean[] usedRows) {
        int closestToRowIndex = 0;
        double closestToRowValue = matrix[0][column];
        for (int i = 0; i < usedRows.length; i++) {
            if (!usedRows[i]) {
                closestToRowIndex = i;
                closestToRowValue = matrix[i][column];
                break;
            }
        }
        for (int row = closestToRowIndex; row < matrix.length; row++) { // Optimization
            if (!usedRows[row] && Math.abs(1 - closestToRowValue) > Math.abs(1 - matrix[row][column])) {
                closestToRowIndex = row;
                closestToRowValue = matrix[row][column];
            }
        }
        usedRows[closestToRowIndex] = true;
        return closestToRowIndex;
    }


    /**
     * Impure function (Modifies @param first and @param second)
     * Swaps two rows of a matrix via algebraic operations
     *
     * @param first
     * @param second
     */
    public static void swap(double[] first, double[] second) {
        for (int i = 0; i < first.length; i++) {
            first[i] += second[i];
            second[i] -= first[i];
            first[i] += second[i];
            second[i] *= -1;
        }
    }

    //Шукає по всьому полю коефіцієнтів найбільше за модулем значення пропускаючи
    // рядки в яких ми вже їх знаходили і використовували елемент як головний
    public static Element findBiggestElement(double [][] matrix, int column,boolean [] used){
        Element element = new Element(0,0,0);
        double [][]temp = matrix.clone();
        for (int i =0 ; i<temp.length; i++){
            if(used[i]){
                for (int j =0 ; j<matrix.length; j++){
                    temp[i][j]=0;
                }
            }

        }
        for (int i =0 ; i<matrix.length;i++){
            for (int j =0 ; j<matrix.length; j++){
                if (Math.abs(temp[i][j])>element.value){
                    element.value = temp[i][j];
                    element.raw = i;
                    element.column = j;

                }
            }
        }
        return element;


    }

}