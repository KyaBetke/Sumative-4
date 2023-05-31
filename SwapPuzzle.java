import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class SwapPuzzle {
    public static Scanner sc;// make global variable


    public static void main(String[] args) {
        sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J"); // clear screen
        String fancyRedWelcome = fancify(red("Welcome to my game!"));// print fancy red welcome
        prn(fancyRedWelcome);
        prn("\u001B[0m");// return to white
        promptEnter();
        prn("How it works: Choose a Level (3+).");
        prn("The game board has blocks with numbers in it. \nAlso there is a single \"empty space\" that can be used for moving the blocks.");
        prn("The objective of the game is to order the numbers using the \"empty space\" for temporary movement.");
        prn("Move blocks adjacent to the space by typing the number you want moved. ");
        promptEnter();
        prn("Please enter the size of game you want (3-20). \n(Ex. \"5\" will make a 5 x 5 grid.)");


        int gameSize = getNumber();// ask for input
        while (gameSize <= 2 || gameSize > 20) {//set max and min gamesize values
            prn("This size is not valid. Please choose another number.");
            gameSize = getNumber();
        }
       
        prn("");
        int[][] board = createBoard(gameSize);


        printBoard(board);// output game board


        while (true) {
            ArrayList<Integer> allowed = findAllowedNumbers(board);
            prn("Which number would you like to move?\n" + allowed.toString() + "\n");// print the array
            int playerChoice = getNumber();
            if (isValidSelection(allowed, playerChoice)) {
                swapPosition(playerChoice, board);// swap position of input and 0
                printBoard(board);// print new board
            }


            if (isComplete(board)) {// board is in order
                String winner = red("You won!");// make ‘win’ red
                prn(winner);// print win
                prn("\u001B[0m");// return to white
                break;// end loop
            }


        }
        sc.close();// close scanner


    }

    // create the board
    public static int[][] createBoard(int dimension) {
        int count = 0;
        int[] numberArray = loadRandomNumbers(dimension * dimension);
        int[][] board = new int[dimension][dimension];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = numberArray[count];
                count++;
            }
        }
        return board;


    }

    // randomizes the positions of each number on the board
    public static int[] loadRandomNumbers(int square) {
        int[] randomArray = new int[square];// creates 1D array the size of the total numbers
        Random rand = new Random();
        for (int i = 0; i < square; i++) {
            randomArray[i] = i;


        }


        for (int i = 0; i < randomArray.length; i++) {// swap numbers in array repeatedly to randomize
            int randSwap = rand.nextInt(randomArray.length - 1);
            int temp = randomArray[randSwap];
            randomArray[randSwap] = randomArray[i];
            randomArray[i] = temp;
        }


        return randomArray;


    }

    // print out the game board
    public static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {


                if (board[i][j] == 0) {
                    System.out.printf(" %3s ", " ");// evenly spread out numbers and make 0 a blank space
                } else {
                    System.out.printf(" %3s ", board[i][j]);// evenly spread out numbers
                }


            }
            prn("\n");
        }
    }

    // find position of imputed number in 2D array
    public static int[] findPosition(int number, int[][] board) {
        boolean breakLoop = false;
        int[] coordinates = new int[2];// create array of 2 spots
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == number) {// if the position is found
                    coordinates = new int[] { i, j };// fill array with the coordinates
                    breakLoop = true;
                    break;// end early if complete
                }


            }
            if (breakLoop == true) {
                break;
            }
        }


        return coordinates;
    }

    // find numbers surrounding blank space
    public static ArrayList<Integer> findAllowedNumbers(int[][] board) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();// create ArrayList to store adjacent numbers in
        int[] coordinates = findPosition(0, board);// find position of 0/blank space
        int x = coordinates[0];// make x coordinate
        int y = coordinates[1];// make y coordinate
        if (x > 0) {// if the blank space is not in the far left column
            numbers.add(board[x - 1][y]);// add number that is 1 position before the space in the x direction
        }
        if (x < board.length - 1) {// if the blank space is not in the far right column
            numbers.add(board[x + 1][y]);// add number that is 1 position after the space in the x direction
        }
        if (y > 0) {// if the blank space is not in the top row
            numbers.add(board[x][y - 1]);// add number that is 1 position before the space in the y direction
        }
        if (y < board.length - 1) {// if the blank space is not in the bottom row
            numbers.add(board[x][y + 1]);// add number that is 1 position after the space in the y direction
        }


        return numbers;
    }

    // swap the blank space with the users valid chosen number
    public static void swapPosition(int number, int[][] board) { 


        int[] zero = findPosition(0, board);// find position of blank space
        int[] swapNum = findPosition(number, board);// find position of valid chosen number


        // swap positions
        board[zero[0]][zero[1]] = number;
        board[swapNum[0]][swapNum[1]] = 0;
    }

    // check if numbers are in final order
    public static boolean isComplete(int[][] board) { 


        ArrayList<Integer> numbers = new ArrayList<Integer>(); // make 2D array into ArrayList
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {


                numbers.add(board[i][j]);
            }
        }


        for (int i = 0; i < numbers.size() - 2; i++) { // fill ArrayList with numbers in order starting at 1
            if (numbers.get(i) != i + 1) {
                return false;
            }
        }
        return true;


    }

    // figure out if number selected is allowed
    public static boolean isValidSelection(ArrayList<Integer> allowedNumber, int selectedNumber) {
        
        for (int i = 0; i < allowedNumber.size(); i++) {
            if (allowedNumber.get(i) == selectedNumber) {// if the selected number matches one of the allowed numbers,
                                                         // continue
                return true;
            }
        }
        prn("\nInvalid");
        return false;
    }

    // take user integer input and make sure it is a number, not something else
    public static int getNumber() {


        int playerChoice = 0;
        try {
            playerChoice = Integer.parseInt(sc.nextLine());


        } catch (NumberFormatException e) {// if not a number, ask for number again
            prn("This is not a number. Please try again.");
            playerChoice = getNumber(); // call method again
        }
        return playerChoice;
    }

    // prn instead of System.out.println
    public static void prn(String textToPrint) { 
        System.out.println(textToPrint);


    }

    // make user press enter to continue
    public static void promptEnter() {
        sc = new Scanner(System.in);
        System.out.println("Press ENTER to continue...");
        sc.nextLine();
    }

    // make text fancy
    public static String fancify(String string) {
        return "_\\| " + string + " |/_";
    }

    // print red text
    public static String red(String colour) { 
        prn("\u001B[31m");
        return colour;


    }
}



