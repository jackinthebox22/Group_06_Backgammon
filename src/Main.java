/**
 * Game of Backgammon
 * @version 1.00 31-10-23
 * @author Jack Caldwell & Patrick Moxom
 */

import java.util.*;

public class Main {
    
    private static int getUserMoveChoice(ArrayList<ArrayList<Integer>> moves) {
        Scanner scanner = new Scanner(System.in);
    
        int choice;
        do {
            System.out.println("Choose a move (1-" + moves.size() + "): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume the invalid input
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } while (choice < 1 || choice > moves.size());
    
        return choice;
    }

    // Add this method to get the number of matches from the user
    private static int getUserInputMatches() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of points to play to: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Consume the invalid input
        }

        int matches = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        return matches;
    }

    public static void main(String[] args) {

        // Allows input from user
        Scanner scanner = new Scanner(System.in);
        String command;



        String[] playerNames = PlayerData.getNamesFromUser();
        int[] dice;
        int doublingCube = 1;
        // initializing player data
        PlayerData[] player = new PlayerData[2];

        player[0] = new PlayerData(playerNames[0],"red");
        player[1] = new PlayerData(playerNames[1],"blue");
        System.out.println(player[0].getName() + " you are red. " + player[1].getName() + " you are blue");

        int pointsToPlay = getUserInputMatches();

        int currentMatchNum = 0;
        int matchNum = 0;
        while(player[1].getgameScore() < pointsToPlay || player[0].getgameScore() < pointsToPlay) {
            currentMatchNum = matchNum;

            Checker[][] spikes = Board.Spikes();
            Tray[] tray = Board.Tray();
            Bar bar = new Bar();

            System.out.println("Match: " + (matchNum + 1));
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
            } else if (dice1[0] + dice1[1] == dice2[0] + dice2[1]) {
                System.out.print("It was a draw. ");
            }
            System.out.println(player[current_player].getName() + " goes first");
            int firstLoop = 0;

            while (!command.equals("Q")) {

                if (currentMatchNum != matchNum) {
                    break;
                }

                Board.displayBoard(spikes, tray, player[current_player], bar);
                System.out.println(player[current_player].getName() + ", it is your turn. You are " + player[current_player].getPlayerColour());

                if (firstLoop == 1) {
                    dice = Roll.rollDice(player[current_player].getName());
                }


                command = " ";

                while (!command.equals("M") && currentMatchNum == matchNum) {
                    firstLoop = 1;

                    System.out.println("Rolls: " + dice[0] + ", " + dice[1]);

                    int allowedTurns = 1;
                    int turnsUsed = 1;
                    if (dice[0] == dice[1]) {
                        System.out.println("You rolled a double. You get 4 moves.");
                        allowedTurns = 2;
                    }
                    System.out.println("Enter Command:");
                    command = scanner.nextLine().toUpperCase();
                    int moveChoice;
                    // Inside the "M" block in the main method
                    if (command.equals("M")) {
                        while (allowedTurns >= turnsUsed) {
                            boolean nextPlayerTurn = false;
                            ArrayList<ArrayList<Integer>> barMoves = ValidMoves.barMoves(dice, player[current_player].playerDirection(), spikes, player[current_player].getPlayerColour());
                            ArrayList<ArrayList<Integer>> allMoves = ValidMoves.allMoves(dice, player[current_player].playerDirection(), spikes, player[current_player].getPlayerColour(), tray);
                            int turns = 0;
                            

                            if (player[current_player].getPlayerColour().equals("blue") && bar.getBlueCheckers() == 2)
                                barMoves = ValidMoves.removeDie(barMoves, 2);
                            else if (player[current_player].getPlayerColour().equals("red") && bar.getRedCheckers() == 2)
                                barMoves = ValidMoves.removeDie(barMoves, 2);

                            while (bar.hasCheckersOfColor(player[current_player].getPlayerColour()) && !nextPlayerTurn) {
                                System.out.println("You have Checker on Bar you must move");
                                if (barMoves.isEmpty()) {
                                    System.out.println("You have no available moves.");
                                    nextPlayerTurn = true;
                                    break;
                                }

                                ValidMoves.printMoves(barMoves);

                                moveChoice = getUserMoveChoice(barMoves);
                                ArrayList<Integer> selectedMove = barMoves.get(moveChoice - 1);

                                int toSpike = selectedMove.get(2);
                                int dieUsed = selectedMove.get(3);
                                System.out.println(toSpike);

                                int[] indices = Board.convertSpikeToIndices(toSpike);

                                Board.addCheckerToSpike(spikes, toSpike, player[current_player], bar);

                                //Removes checker from Bar
                                if (player[current_player].playerColour == "red") {
                                    bar.removeRedChecker();
                                } else {
                                    bar.removeBlueChecker();
                                }
                                if (dieUsed == 2) nextPlayerTurn = true;
                                else {
                                    allMoves = ValidMoves.allMoves(dice, player[current_player].playerDirection(), spikes, player[current_player].getPlayerColour(), tray);

                                    allMoves = ValidMoves.removeDie(allMoves, dieUsed);
                                    allMoves = ValidMoves.removeDie(allMoves, 2);
                                    barMoves = ValidMoves.removeDie(barMoves, dieUsed);
                                    barMoves = ValidMoves.removeDie(barMoves, 2);


                                    if (turns == 1) {
                                        allMoves = ValidMoves.removeDie(allMoves, ++dieUsed % 2);
                                    }
                                }

                                if (barMoves.isEmpty() && (allMoves.isEmpty() || turns == 2)) {
                                    nextPlayerTurn = true;
                                    System.out.println("No more moves. Ending " + player[current_player].getName() + "'s turn.");
                                } else if (barMoves.isEmpty() && !allMoves.isEmpty()) {
                                    Board.displayBoard(spikes, tray, player[current_player], bar);
                                    break;
                                } else {
                                    Board.displayBoard(spikes, tray, player[current_player], bar);
                                }
                                turns++;
                            }
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

                                    if (toSpike == 0) {
                                        tray[current_player].addChecker();
                                    }

                                    if (dieUsed == 2) {
                                        nextPlayerTurn = true;
                                        if (allowedTurns == 2)
                                            Board.displayBoard(spikes, tray, player[current_player], bar);
                                    } else {
                                        allMoves = ValidMoves.allMoves(dice, player[current_player].playerDirection(), spikes, player[current_player].getPlayerColour(), tray);

                                        allMoves = ValidMoves.removeDie(allMoves, dieUsed);
                                        allMoves = ValidMoves.removeDie(allMoves, 2);

                                        if (turns == 1) {
                                            allMoves = ValidMoves.removeDie(allMoves, ++dieUsed % 2);
                                        }

                                        if (allMoves.isEmpty() && (turnsUsed == allowedTurns)) {
                                            nextPlayerTurn = true;
                                            System.out.println("No more moves. Ending " + player[current_player].getName() + "'s turn.");
                                        } else if (allMoves.isEmpty() && turnsUsed < allowedTurns) {
                                            nextPlayerTurn = true;
                                            System.out.println("This is your second turn");
                                            Board.displayBoard(spikes, tray, player[current_player], bar);
                                        } else {
                                            Board.displayBoard(spikes, tray, player[current_player], bar);
                                        }
                                    }
                                }

                                if (tray[current_player].getNumCheckers() == 15) {
                                    Board.displayBoard(spikes, tray, player[current_player], bar);

                                    if (tray[((current_player + 1) % 2)].getNumCheckers() == 0 && bar.hasCheckersOfColor(player[((current_player + 1) % 2)].getPlayerColour())) {
                                        System.out.println(player[current_player].getName() + " Won this Match, Backgammon");
                                        matchNum++;
                                        player[current_player].updategameScore(3*doublingCube);
                                        System.out.println(player[current_player].getName() + " is on " + player[current_player].getgameScore() + " game points" );
                                        System.out.println(player[((current_player + 1) % 2)].getName() + " is on " + player[((current_player + 1 % 2))].getgameScore() + " game points" );

                                    } else if (tray[((current_player + 1) % 2)].getNumCheckers() == 0) {
                                        System.out.println(player[current_player].getName() + " Won this Match, gammon");
                                        matchNum++;
                                        player[current_player].updategameScore(2*doublingCube);
                                        System.out.println(player[current_player].getName() + " is on " + player[current_player].getgameScore() + " game points" );
                                        System.out.println(player[((current_player + 1) % 2)].getName() + " is on " + player[((current_player + 1) % 2)].getgameScore() + " game points" );

                                    } else {
                                        System.out.println(player[current_player].getName() + " Won this Match, Single");
                                        matchNum++;
                                        player[current_player].updategameScore(1*doublingCube);
                                        System.out.println(player[current_player].getName() + " is on " + player[current_player].getgameScore() + " game points" );
                                        System.out.println(player[((current_player + 1) % 2)].getName() + " is on " + player[((current_player + 1) % 2)].getgameScore() + " game points" );

                                    }


                                    if (player[current_player].getgameScore() >= pointsToPlay) {
                                        System.out.println(player[current_player].getName() + " Won The whole Game with " + player[current_player].getgameScore() + " Points");
                                        System.exit(0);
                                    }
                                }
                                turns++;
                            }
                            turnsUsed++;
                        }

                        current_player = ++current_player % 2;

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
                        System.out.println("D = Change Dice Rolls");
                    } else if (command.equals("D")) {
                        doublingCube *= 2; // Double the value of the doubling cube
                        System.out.println(player[current_player].getName() + " offers a double. The game is now worth " + doublingCube + " points.");

                        // Prompt the opponent to accept (A) or decline (D)
                        String opponentChoice;
                        do {
                            System.out.println(player[(current_player + 1) % 2].getName() + " Do you Accept (A) or Decline (D) " + player[current_player].getName() + " offer?:");
                            opponentChoice = scanner.nextLine().toUpperCase();
                        } while (!opponentChoice.equals("A") && !opponentChoice.equals("D"));

                        if (opponentChoice.equals("A")) {
                            // Accept the double
                            System.out.println(player[(current_player + 1) % 2].getName() + " accepted the double. Match is now worth " + doublingCube);
                        } else {
                            // Decline the double
                            System.out.println(player[(current_player + 1) % 2].getName() + " declined the double. " + player[current_player].getName() + " wins " + doublingCube + " points.");
                            matchNum++;
                            player[current_player].updategameScore(doublingCube);
                            doublingCube = 1; // Reset doubling cube for the next game
                        }

                    } else if (command.equals("Q")) {
                        System.out.println("Quitting the game...");
                        System.exit(0);
                    } else if (command.equals("C")) {
                        // Allow the user to manually change the dice roll
                        System.out.println("Enter the new dice values (e.g., 3 4):");
                        int newDice1 = scanner.nextInt();
                        int newDice2 = scanner.nextInt();

                        dice[0] = newDice1;
                        dice[1] = newDice2;
                        scanner.nextLine();

                        System.out.println("Dice values changed to: " + dice[0] + ", " + dice[1]);
                    } else {
                        System.out.println("Command entered is invalid");
                    }
                }
            }
        }
    }
}