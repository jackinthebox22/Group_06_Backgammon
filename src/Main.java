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
        Tray[] tray = Board.Tray();

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

            Board.displayBoard(spikes,tray);
            System.out.println(player[current_player].getName() + ", it is your turn. You are " + player[current_player].getPlayerColour());

            System.out.println("Make a move (M to move, P to calculate Pip Scores, Q to quit):");

            int[][] allMoves = ValidMoves.allMoves(dice, player[current_player].playerDirection(), spikes);

            command = scanner.nextLine().toUpperCase();

            if (command.equals("M")) {
                int move1 = dice[0];
                int move2 = dice[1];

                System.out.println("Rolls: " + move1 + ", " + move2);
                System.out.println("Choose a spike to move from (1-24):");
                int fromSpike = scanner.nextInt(); // Subtract 1 to convert to 0-based index
                scanner.nextLine(); // Consume the newline character
                
            System.out.println("Take Your Turn. ALLOWABLE COMMANDS: Quit (Q): ");
            command = scanner.nextLine().toUpperCase();

                System.out.println("Choose a spike to move to (1-24):");
                int toSpike = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                // Move the piece
                Board.movePiece(spikes, fromSpike, toSpike);

                // Update pip score after the move
                int newPipScore = player[current_player].calculatePipScore(player[current_player], spikes);
                player[current_player].updatePipScore(newPipScore);

                current_player++;
                current_player = current_player % 2;

            } else if (command.equals("P")) {
                // Calculate and display both players' pip scores
                System.out.println(player[0].getName() + "'s Pip Score: " + player[0].getPipScore());
                System.out.println(player[1].getName() + "'s Pip Score: " + player[1].getPipScore());

            } 

            dice = Roll.rollDice(player[current_player].getName());
        }

    }
}
