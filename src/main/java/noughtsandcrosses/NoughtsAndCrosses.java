package noughtsandcrosses;

import java.util.Locale;
import java.util.Scanner;

public class NoughtsAndCrosses {

    private static final String NOUGHT = "O";
    private static final String CROSS = "X";
    private static final String DIVIDER_LINES = "--+---+--";
    private static final String GENERIC_POSITION_ERROR = "Invalid position, please make sure you use symbols for grid" +
            " positions. (e.g. tm = top middle, br = bottom right)";
    private static final String WELCOME_MESSAGE = "Welcome to Noughts and Crosses";
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[][] gameState = new String[3][3];

    private static boolean noughtsTurn = true;
    private static boolean runGame = true;
    private static boolean gameHasWinner = false;
    private static int turnLimit = 0;

    public static void main(String[] args) {
        NoughtsAndCrosses.start();
    }

    public static void start() {
        showMenu();
    }

    private static void showMenu(){
        boolean choiceMade = false;
        while (!choiceMade) {
            System.out.println(WELCOME_MESSAGE);
            System.out.println("""
                1. Play
                2. Rules
                3. Quit
                """);
            String input = scanner.nextLine();
            if (validateMenuChoice(input)) {
                if (input.equals("1")) {
                    getStartPlayer();
                    playGame();
                } else if (input.equals("2")) {
                    showRules();
                } else {
                    choiceMade = true;
                }
            }
        }
        System.out.println("Thanks for playing.");
    }

    private static void getStartPlayer() {
        boolean choiceMade = false;
        while (!choiceMade) {
            System.out.println("""
                Who's going first?
                1. Noughts
                2. Crosses
                """);
            String input = scanner.nextLine();
            if (validateMenuChoice(input)) {
                setStartingPlayer(input);
                choiceMade = true;
            }
        }
    }

    private static boolean validateMenuChoice(final String input) {
        return input.equals("1") || input.equals("2") || input.equals("3");
    }

    private static void showRules() {
        System.out.println("""
                One player is noughts, the other crosses. Take turns placing your symbol in the grid. The first player to get 3 in a row in any column, row, or diagonal wins!
                Enter in the 2 character choice for your column and row.
                    Rows are selected using T (top), M (middle), B (bottom)
                    Columns are selected using  L (left), M (middle), R (right)
                Examples:
                    TM for top middle
                    BR for bottom right
                """);
    }

    private static void playGame() {
        while(runGame) {
            initialiseGameState();
            showGrid();
            while(!gameHasWinner) {
                if (noughtsTurn) {
                    processPlayerTurn(NOUGHT);
                } else {
                    processPlayerTurn(CROSS);
                }
                showGrid();
                checkForWinner();
            }
            rematch();
        }
    }

    private static void rematch() {
        boolean validAnswer = false;
        while(!validAnswer) {
            System.out.println("""
                    Rematch?
                    1. Yes
                    2. No
                    """);
            validAnswer = validateRematchAnswer(scanner.nextLine());
        }
        gameHasWinner = false;
        noughtsTurn = true;
    }

    private static boolean validateRematchAnswer(String answer) {
        switch (answer) {
            case "1", "yes", "Yes":
                return true;
            case "2", "no", "No":
                runGame = false;
                return true;
            default:
                return false;
        }
    }

    private static void setStartingPlayer(String answer) {
        noughtsTurn = answer.equals("1");
    }

    private static void initialiseGameState() {
        turnLimit = 0;
        for(int i = 0; i <= 2; i++) {
            for(int j = 0; j <= 2; j++) {
                gameState[i][j] = " ";
            }
        }
    }

    private static void processPlayerTurn(String playerToken) {
        boolean inputProcessed = false;
        GridMapping gm;
        while(!inputProcessed) {
            printTurn();
            String playerInput = scanner.nextLine();
            try {
                 gm = GridMapping.valueOf(playerInput.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                System.out.println(GENERIC_POSITION_ERROR);
                return;
            }
            if (isValidPosition(playerToken, gm.getXCoordinate(), gm.getYCoordinate())) {
                inputProcessed = true;
                turnLimit++;
            };
        }
        noughtsTurn = !noughtsTurn;
    }

    private static boolean isValidPosition(String playerToken, int xCoordinate, int yCoordinate) {
        if(!gameState[xCoordinate][yCoordinate].equals(" ")) {
            System.out.println("That position is already filled, try an empty one");
            return false;
        } else {
            gameState[xCoordinate][yCoordinate] = playerToken;
            return true;
        }
    }

    private static void printTurn() {
        if(noughtsTurn) {
            System.out.println("Noughts turn");
        } else {
            System.out.println("Crosses turn");
        }
    }

    private static void showGrid() {
        System.out.println(
                generateRow(gameState[0][0], gameState[0][1], gameState[0][2]) + "\n" +
                        DIVIDER_LINES + "\n" +
                        generateRow(gameState[1][0], gameState[1][1], gameState[1][2]) + "\n" +
                        DIVIDER_LINES + "\n" +
                        generateRow(gameState[2][0], gameState[2][1], gameState[2][2]));
    }

    private static String generateRow(String left, String middle, String right) {
        return left + " | " + middle + " | " + right;
    }

    private static void checkForWinner() {
        if(turnLimit >= 9) {
            System.out.println("It's a draw");
            gameHasWinner = true;
            return;
        }
        checkRowAndColumnWins();
        if(!gameHasWinner) {
            checkDiagonalWins();
        }
        if(gameHasWinner) {
            printWinner();
        }
    }

    private static void printWinner() {
        if(!noughtsTurn) {
            System.out.println("Noughts wins");
        } else {
            System.out.println("Crosses wins");
        }
    }

    private static void checkRowAndColumnWins() {
        for (int i = 0; i < 3; i++) {
            if (gameState[0][i].equals(gameState[1][i]) && gameState[0][i].equals(gameState[2][i]) && !gameState[0][i].equals(" ")
                    || gameState[i][0].equals(gameState[i][1]) && gameState[i][0].equals(gameState[i][2]) && !gameState[i][0].equals(" ")) {
                gameHasWinner = true;
                break;
            }
        }
    }

    private static void checkDiagonalWins() {
        if (gameState[0][0].equals(gameState[1][1]) && gameState[0][0].equals(gameState[2][2]) && !gameState[0][0].equals(" ")
                || gameState[0][2].equals(gameState[1][1]) && gameState[0][2].equals(gameState[2][0]) && !gameState[0][2].equals(" ")) {
            gameHasWinner = true;
        }
    }
}
