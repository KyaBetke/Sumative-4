import java.util.Scanner;

public class NumSort {
    public static void main(String[] args) {
        //
        Scanner sc = new Scanner(System.in);
        int gameSize = Integer.parseInt(sc.nextLine());
        int square = gameSize * gameSize;
        System.out.print("\033[H\033[2J"); // clear screen
        int [][] array = {{1,2,3,4,5}, {6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24}};
        
        for (int k = 0; k < square; k++)
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    if (array[i][j] == k) {
                        createFilledArray(gameSize, k);
                        System.out.print(k + "  ");
                   
                    }

                }
                
            }


    }
    public static int[] createFilledArray(int size, int value) {
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = value;
        }

        return array;
    }
}