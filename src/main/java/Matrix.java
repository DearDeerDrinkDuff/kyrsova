import java.util.Scanner;

public class Matrix {


private static Scanner in = new Scanner(System.in);
    double [][] matrix;
    double [] solution;


    public void  enterMatrix(int b){
        this.matrix = new double[b][b];
        this.solution = new double[b];
        for (int i = 0 ; i<b ; i++){
            for (int j =0 ; j<=b; j++) {
                if (j==b){
                    System.out.println("Введіть значення правої частини рівності рівняння №"+(i+1));
                    this.solution[i] = Double.parseDouble(in.nextLine());
                }else{
                System.out.println("Put the meaning of x["+(i+1)+"] "+"["+(j+1)+"]");
                this.matrix[i][j] = Double.parseDouble(in.nextLine());
            }
            }
            System.out.println();
        }


    }


    public static int enterSize(){
        System.out.println("Enter matrix size");
        return Integer.parseInt(in.nextLine());
    }
    public static void printSolution(double[]array ){
        System.out.println("\n\n");

        for (int i =0 ; i<array.length;i++){

            System.out.print(array[i]+" ");

        }
    }

    public  void printMatrix(){
        for (int i =0 ; i<this.matrix.length;i++){
            System.out.println();
            for (int j = 0 ; j<=this.matrix.length; j++ ){
                if (j==this.matrix.length){
                    System.out.print("|"+this.solution[i]);
                }else{
                System.out.print(this.matrix[i][j]+" ");
            }
            }
        }
    }


}
