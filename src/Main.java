/**
 * Game of Backgammon
 * @version 1.00 31-10-23
 * @author Jack Caldwell & Patrick Moxom
 */

import java.util.*;

public class Main{

    private static int getUserMoveChoice(ArrayList<ArrayList<Integer>> moves) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("Choose a move (1-" + moves.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return choice;
    }
    

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
        Bar bar = new Bar();

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
        int firstLoop = 0;

        while (!command.equals("Q")) {

            Board.displayBoard(spikes, tray, player[current_player], bar);
            System.out.println(player[current_player].getName() + ", it is your turn. You are " + player[current_player].getPlayerColour());

            if (firstLoop == 1) {
                dice = Roll.rollDice(player[current_player].getName());
            }
        
            ArrayList<ArrayList<Integer>> allMoves = ValidMoves.allMoves(dice, player[current_player].playerDirection(), spikes, player[current_player].getPlayerColour());

            command = " ";
        
            while (!command.equals("M")) {
                firstLoop = 1;

                System.out.println("Rolls: " + dice[0] + ", " + dice[1]);

                System.out.println("Enter Command:");
                command = scanner.nextLine().toUpperCase();
                int moveChoice;
                // Inside the "M" block in the main method
                if (command.equals("M")) {
                    boolean nextPlayerTurn = false;

                    if (bar.hasCheckersOfColor(player[current_player].getPlayerColour())) {
                        System.out.println("You have Checker on Bar you must move");

                        int BarToSpike;
                        System.out.println("Choose Spike to move to");
                        BarToSpike = scanner.nextInt();

                        Board.addCheckerToSpike(spikes, BarToSpike, player[current_player]);
                        
                        //Removes checker from Bar
                        if (player[current_player].playerColour == "red") {
                            bar.removeRedChecker();
                        } else {
                            bar.removeBlueChecker();
                        }

                    } else {
                        while (!nextPlayerTurn) {
                            ValidMoves.printMoves(allMoves);
    
                            if (allMoves.isEmpty()) {
                                System.out.println("No available moves. Ending " + player[current_player].getName() + "'s turn.");
                                nextPlayerTurn = true;
                            } else {
                                moveChoice = getUserMoveChoice(allMoves);
                                ArrayList<Integer> selectedMove = allMoves.get(moveChoice - 1);
                                int fromSpike = selectedMove.get(1);
                                int toSpike = selectedMove.get(2);
                                int dieUsed = selectedMove.get(3);
    
                                Board.movePiece(spikes, fromSpike, toSpike, bar);
    
                                if (dieUsed == 2) {
                                    nextPlayerTurn = true;
                                } else {
                                    allMoves = ValidMoves.removeDie(allMoves, dieUsed);
                                    allMoves = ValidMoves.removeDie(allMoves, 2);
                                    if (allMoves.isEmpty()) {
                                        nextPlayerTurn = true;
                                        System.out.println("No more moves. Ending " + player[current_player].getName() + "'s turn.");
                                    } else {
                                        Board.displayBoard(spikes, tray, player[current_player], bar);
                                    }
                                }
                            }
                        }

                    }

                    current_player++;
                    current_player = current_player % 2;
                } else if (command.equals("P")) {
                    // Update and display both players' pip scores
                    player[0].calculatePipScore(spikes);
                    player[1].calculatePipScore(spikes);

                    System.out.println(player[0].getName() + "'s Pip Score: " + player[0].getPipScore());
                    System.out.println(player[1].getName() + "'s Pip Score: " + player[1].getPipScore());
        
                } else if (command.equals("H")) {
                    System.out.println("Lists of Commands are:");
                    System.out.println("H = Help");
                    System.out.println("M = Move");
                    System.out.println("P = Calculate Pip Scores");
                } else if (command.equals("Q")) {
                    System.out.println("Quitting the game...");
                    System.exit(0);
                } else {
                    System.out.println("Command entered is invalid");
                }
            }
        }
    }
}