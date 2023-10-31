/**
 * Game of Backgammon
 * @version 1.00 31-10-23
 * @author Jack Caldwell & Patrick Moxom
 */

import java.util.*;

public class Main{

    public static void main(String[] args) {

        // Allows input from user
        Scanner scanner = new Scanner(System.in);
        String command;

        int[] dice = Roll.rollDice(); // rolls dice
        String[] playerNames = PlayerData.getNamesFromUser();

        Board board = new Board();

        System.out.println("Press Any Key to play:");
        command = scanner.nextLine().toUpperCase(); // Initialize the variable with user input


        while (!command.equals("Q")) {
            // Handle player's turn logic here
            
            // Example: Move a piece
            // board.movePiece(fromSpike, toSpike);
            
            // Example: Roll dice for the next turn
            dice = Roll.rollDice();
            
            board.displayBoard();
            System.out.println("Take Your Turn. ALLOWABLE COMMANDS: Quit (Q): ");
            command = scanner.nextLine().toUpperCase();
        }

    }
}
