public class Main {

    public static void main(String[] args) {


        Matrix matrix = new Matrix();
        matrix.enterMatrix(Matrix.enterSize());
        matrix.printMatrix();
        Matrix.printSolution(OptimalExclusion.solve(matrix.matrix, matrix.solution));




    }
}
