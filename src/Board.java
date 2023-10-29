import java.util.*;

public class Board {

    // Initializes new Board
    public Board() {

    }

    // Displays board to user
    public void displayBoard() {
        String[] checkers = {"2X", "3O", "4X", "1O", "5X", "6O", "7X", "8O", "9X", "1O", "1X", "1O"}; // Example strings
        System.out.println(" ——————————————————————————————————————————————————————————");
        System.out.println("| 12   11   10   09   08   07 | |  06    05    04    03    02    01 |");

        System.out.printf("|");
        for (String checker : checkers) {
            System.out.printf(" %2s  ", checker); // Use %2s to specify a field width of 2 characters
        }
        System.out.println("|"); // Print a new line at the end of the row
    }

    public static void main(String[] args) {

        Board board = new Board();
        board.displayBoard();
    }
}