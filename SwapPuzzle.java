import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class SwapPuzzle {
    public static Scanner sc;


    public static void main(String[] args) {
        sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J"); // clear screen
        System.out.println("Please enter the size of game you want. \n(Ex. \"5\" will make a 5 x 5 grid.)");
       
        int gameSize = getNumber();
        System.out.println();
        int[][] board = createBoard(gameSize);
       


        printBoard(board);
        /*
         * loop until done
         * find allowed numbers
         * ask user to chose
         * validate selection
         * swap numbers
         * check if done
         * if done, exit somehow
         * else print board
         * repeat
         */
        while (true) {
            ArrayList<Integer> allowed = findAllowedNumbers(board);
            System.out.println("Which number would you like to move?\n" + allowed.toString() + "\n");
            int playerChoice = getNumber();
            if (isValidSelection(allowed, playerChoice)) {
                swapPosition(playerChoice, board);
                printBoard(board);
            }


            if (isComplete(board)) {
                break;
            }
           
        }
        sc.close();


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
                    System.out.printf(" %3s ", board[i][j]);
                }


            }
            System.out.println("\n");
        }
    }


    public static int[] findPosition(int number, int[][] board) {
        boolean breakLoop = false;
        int[] coordinates = new int[2];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == number) {
                    coordinates = new int[] { i, j };
                    breakLoop = true;
                    break;
                }


            }
            if (breakLoop == true) {
                break;
            }
        }


        return coordinates;
    }


    public static ArrayList<Integer> findAllowedNumbers(int[][] board) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        int[] coordinates = findPosition(0, board);
        int x = coordinates[0];
        int y = coordinates[1];
        if (x > 0) {
            numbers.add(board[x - 1][y]);
        }
        if (x < board.length - 1) {
            numbers.add(board[x + 1][y]);
        }
        if (y > 0) {
            numbers.add(board[x][y - 1]);
        }
        if (y < board.length - 1) {
            numbers.add(board[x][y + 1]);
        }


        return numbers;
    }


    public static void swapPosition(int number, int[][] board) {


        int[] zero = findPosition(0, board);
        int[] swapNum = findPosition(number, board);


        board[zero[0]][zero[1]] = number;
        board[swapNum[0]][swapNum[1]] = 0;
    }


    public static boolean isComplete(int[][] board) {


        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < board.length; i++) {


            for (int j = 0; j < board[i].length; j++) {


                numbers.add(board[i][j]);
            }
        }


        for (int i = 0; i < numbers.size() - 2; i++) {
            if (numbers.get(i) != i + 1) {
                return false;
            }
        }
        return true;


    }


    public static boolean isValidSelection(ArrayList<Integer> allowedNumber, int selectedNumber) {
        for (int i = 0; i < allowedNumber.size(); i++) {
            if (allowedNumber.get(i) == selectedNumber) {
                return true;
            }
        }
        System.out.println("\nInvalid");
        return false;


        // - validation method to determine if the user has selected an allowed
        // number)
    }
   
    public static int getNumber(){
       
        int playerChoice = 0;
        try{
            playerChoice = Integer.parseInt(sc.nextLine());
           
        } catch (NumberFormatException e){
            System.out.println("This is not a number. Please try again.");
            playerChoice = getNumber();
        }
        return playerChoice;
    }
}





