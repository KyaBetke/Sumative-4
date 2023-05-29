import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SwapPuzzle {
    public static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J"); // clear screen
        String fancyRedWelcome = fancify(red("Welcome to my game!"));
        prn(fancyRedWelcome);
        prn("\u001B[0m");
        promptEnter();
        prn("How it works: Choose a Level (3+).");
        prn("The game board has blocks with numbers in it. \nAlso there is a single \"empty space\" that can be used for moving the blocks.");
        prn("The objective of the game is to order the numbers using the \"empty space\" for temporary movement.");
        prn("Move blocks adjacent to the space by typing the number you want moved. ");
        promptEnter();
        prn("Please enter the size of game you want. \n(Ex. \"5\" will make a 5 x 5 grid.)");

        int gameSize = getNumber();
        prn("");
        int[][] board = createBoard(gameSize);

        printBoard(board);

        while (true) {
            ArrayList<Integer> allowed = findAllowedNumbers(board);
            prn("Which number would you like to move?\n" + allowed.toString() + "\n");
            int playerChoice = getNumber();
            if (isValidSelection(allowed, playerChoice)) {
                swapPosition(playerChoice, board);
                printBoard(board);
            }

            if (isComplete(board)) {
                String winner = red("You won!");
                prn(winner);
                prn("\u001B[0m");
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
            prn("\n");
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
        prn("\nInvalid");
        return false;

        // - validation method to determine if the user has selected an allowed
        // number)
    }

    public static int getNumber() {

        int playerChoice = 0;
        try {
            playerChoice = Integer.parseInt(sc.nextLine());

        } catch (NumberFormatException e) {
            prn("This is not a number. Please try again.");
            playerChoice = getNumber();
        }
        return playerChoice;
    }

    public static void prn(String textToPrint) { // prn instead of System.out.println
        System.out.println(textToPrint);

    }

    public static void promptEnter() {
        sc = new Scanner(System.in);
        System.out.println("Press ENTER to continue...");
        sc.nextLine();
    }

    public static String fancify(String string) {
        return "_\\| " + string + " |/_";
    }

    public static String red(String colour) {
        prn("\u001B[31m");
        return colour;

    }
}
