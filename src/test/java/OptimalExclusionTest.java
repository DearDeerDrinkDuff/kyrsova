

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OptimalExclusionTest {

    @Test
    void solveWith4x4Floats() {
        double[][] matrix = {{0.02, 0.01, 0, 0},
                {1, 2, 1, 0},
                {0, 1, 2, 1},
                {0, 0, 100, 200}};
        double[] b = {0.02, 1, 4, 800};

        assertArrayEquals(new double[]{1, 0, 0, 4}, OptimalExclusion.solve(matrix, b), 1e-9);
    }

    @Test
    void solveWith3x3Integers() {
        double[][] matrix = {{4, -7, 8},
                {2, -4, 5},
                {-3, 11, 1}};
        double[] b = {-23, -13, 16};

        assertArrayEquals(new double[]{-2, 1, -1}, OptimalExclusion.solve(matrix, b), 1e-9);
    }

    @Test
    void solveWith4x4Integers() {
        double[][] matrix = {{1, -4, 4, 7},
                {0, 2, -1, 0},
                {2, 1, 1, 4},
                {2, -3, 2, -5}};
        double[] b = {4, 5, 2, 9};

        assertArrayEquals(new double[]{-14.48, 19.56, 34.12, -5.68}, OptimalExclusion.solve(matrix, b), 1e-9);
    }

    @Test
    void solveWith5x5Integers() {
        double[][] matrix = {{1, 2, 4, 3, 5},
                {3, 5, 3, 1, 2},
                {1, 4, 4, 2, 1},
                {4, 1, 2, 5, 3},
                {5, 2, 1, 4, 1}};
        double[] b = {5, 6, 7, 8, 9};

        assertArrayEquals(new double[]{59.5, -67.5, 87, -55, -20.5}, OptimalExclusion.solve(matrix, b), 1e-9);
    }
}