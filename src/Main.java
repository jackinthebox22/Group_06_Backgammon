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

        String[] playerNames = PlayerData.getNamesFromUser();
        int[] dice;
        // initializing player data
        PlayerData[] player = new PlayerData[2];

        player[0] = new PlayerData(playerNames[0],"red");
        player[1] = new PlayerData(playerNames[1],"blue");
        System.out.println(player[0].getName() + " you are red. " + player[1].getName() + " you are blue");

        Checker[][] spikes = Board.Spikes();
        Bar[] bar = Board.Bar();

        System.out.println("Press Any Key to play:");
        command = scanner.nextLine().toUpperCase(); // Initialize the variable with user input

        // rolling to start
        int current_player = 0;

        int[] dice1 = Roll.rollDice(player[0].getName());
        int[] dice2 = Roll.rollDice(player[1].getName());

        dice = dice1;

        if (dice1[0] + dice1[1] < dice2[0] + dice2[1]) {
            current_player = 1;
            dice = dice2;
        }
        else if (dice1[0] + dice1[1] == dice2[0] + dice2[1]) {
            System.out.print("It was a draw. ");
        }
        System.out.println(player[current_player].getName() + " goes first");



        while (!command.equals("Q")) {
            System.out.println(player[current_player].getName() + ", it is your turn. You are " + player[current_player].getPlayerColour());
            System.out.println("Make a move");
            command = scanner.nextLine().toUpperCase();


            // Handle player's turn logic here
            
            // Example: Move a piece
            // board.movePiece(fromSpike, toSpike);
            
            // Example: Roll dice for the next turn

            
            Board.displayBoard(spikes,bar);
            System.out.println("Take Your Turn. ALLOWABLE COMMANDS: Quit (Q): ");
            command = scanner.nextLine().toUpperCase();

            current_player++;
            current_player = current_player % 2;
            dice = Roll.rollDice(player[current_player].getName());
        }

    }
}
