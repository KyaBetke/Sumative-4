import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class SwapPuzzle {


    public static void main(String[] args) {
        //
        System.out.print("\033[H\033[2J"); // clear screen
        System.out.println("Please enter the size of game you want. Ex. \"5\" will make a 5 x 5 grid.");
        Scanner sc = new Scanner(System.in);
        int gameSize = Integer.parseInt(sc.nextLine());
        System.out.println();
        int[][] createArray = createBoard(gameSize);
        // int square = gameSize * gameSize;


        printBoard(createArray);
        /*
         * loop until done
         * find allowed numbers
         * ask user to chose
         * swap numbers
         * check if done
         * if done, exit somehow
         * else print board
         * repeat
         */


    }


    public static int[][] createBoard(int dimension) {
        int count = 0;
        int[] twoDArray = loadRandomNumbers(dimension * dimension);
        int[][] board = new int[dimension][dimension];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = twoDArray[count];
                count++;
            }
        }
        return board;
        // - load the 2 dimenional array
    }


    public static int[] loadRandomNumbers(int square) {
        int[] randomArray = new int[square];
        Random rand = new Random();
        for (int i = 0; i < square; i++) {
            randomArray[i] = i;


        }


        for (int i = 0; i < randomArray.length; i++) {
            int randSwap = rand.nextInt(randomArray.length - 1);
            int temp = randomArray[randSwap];
            randomArray[randSwap] = randomArray[i];
            randomArray[i] = temp;
        }
        // System.out.println(Arrays.toString(randomArray));
        return randomArray;
        // - load the single dimension array by swaping numbers
        // - called by createBoard
    }


    public static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {


                if (board[i][j] == 0) {
                    System.out.printf(" %3s ", " ");
                } else {
                    System.out.printf(" %3s ",board[i][j]);
                }


            }
            System.out.println("\n");
        }
    }


    public static int[] findPosition(int number, int[][] board) {
        boolean breakLoop = false;
        int[] coorodinates = new int[2];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    coorodinates = new int[] { i, j };
                    breakLoop = true;
                    break;
                }


            }
            if (breakLoop == true) {
                break;
            }
        }


        return coorodinates;
    }


    // public static void swapPosition(int number, int[][] board) {
    // // int[] coordinates = findPosition(0, board);
    // }


    public static  ArrayList <Integer> findAllowedNumbers(int[][] board, int dimension) {
        ArrayList <Integer> numbers = new ArrayList<Integer>();
        int[] coordinates = findPosition(0, board);
        int x = coordinates[0];
        int y = coordinates[1];
        if(x > 0){
            numbers.add(coordinates[x-1]);
        } if (x < dimension){
            numbers.add(coordinates[x + 1]);
        }if (y > 0){
            numbers.add(coordinates[y - 1]);
        }if (y < dimension){
            numbers.add(coordinates[y+1]);
        }
       
        return numbers;
    }
        
    // - find the position of 0 and use it to find all the other numbers the user is allowed to select
    }


    // public static boolean isValidSelection(int[] allowedNumber, int
    // selectedNumber) {
    // // - validation method to determine if the user has selected an allowed
    // number)
    // }


    // public static boolean isComplete(int[][]board);









