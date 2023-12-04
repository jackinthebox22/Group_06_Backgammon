/**
 * Game of Backgammon
 * @version 1.00 31-10-23
 * @author Jack Caldwell & Patrick Moxom
 */

import java.util.*;

// File reading
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {

    private static final int DOUBLE_MOVE_ALLOWED_TURNS = 2;
    private static final int MAX_CHECKERS = 15;

    private static final int USING_TWO_DIE = 2;

    private static final int FROM_SPIKE = 1;
    private static final int TO_SPIKE = 2;
    private static final int DICE_USED = 3;
    private static List<String> readCommandsFromFile(String filePath) throws IOException {
        List<String> commands = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String command;
            while ((command = br.readLine()) != null) {
                commands.add(command);
            }
        }
        return commands;
    }
    
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
        List<String> commands = new ArrayList<>();



        String[] playerNames = PlayerData.getNamesFromUser();
        int[] dice;
        int doublingCube = 1;

        // initializing player data
        PlayerData[] player = new PlayerData[2];

        player[0] = new PlayerData(playerNames[0],"red");
        player[1] = new PlayerData(playerNames[1],"blue");
        System.out.println(player[0].getName() + " you are red. " + player[1].getName() + " you are blue");

        boolean newMatch = true;

        while(newMatch) {
            boolean newRound = true;

            int roundNum = 0;
            int pointsToPlay = getUserInputMatches();

            while(newRound){
                int currentMatchNum = roundNum;

                Checker[][] spikes = Board.Spikes();
                Tray[] tray = Board.Tray();
                Bar bar = new Bar();

                System.out.println("Match: " + (roundNum + 1));
                System.out.println("Press <Enter> to play:");
                command = scanner.nextLine().toUpperCase(); // Initialize the variable with user input

                // rolling to start
                int currentPlayer = 0;

                int[] dice1 = Roll.rollDice(player[0].getName());
                int[] dice2 = Roll.rollDice(player[1].getName());

                dice = dice1;

                if (dice1[0] + dice1[1] < dice2[0] + dice2[1]) { // if dice2 is bigger that becomes the initial dice
                    currentPlayer = 1;
                    dice = dice2;
                } else if (dice1[0] + dice1[1] == dice2[0] + dice2[1]) { // draw defaults in dice1 being chosen
                    System.out.print("It was a draw. ");
                }

                System.out.println(player[currentPlayer].getName() + " goes first");

                boolean useInitialDice = true;
                int opposingPlayer = ++currentPlayer % 2;

                while (!command.equals("Q")) {// while loop containing main game loop

                    if (currentMatchNum != roundNum) {
                        break;
                    }

                    Board.displayBoard(spikes, tray, player[currentPlayer], bar);
                    System.out.println(player[currentPlayer].getName() + ", it is your turn. You are " + player[currentPlayer].getPlayerColour());

                    if (player[currentPlayer].getdoubleOwnership() && player[opposingPlayer].getdoubleOwnership()) {
                        System.out.println("Double Die Ownership: Both Players");
                        System.out.println("Double Die Value: " + doublingCube);
                    } else if (player[currentPlayer].getdoubleOwnership()) {
                        System.out.println("Double Die Ownership: " + player[currentPlayer].getName());
                        System.out.println("Double Die Value: " + doublingCube);
                    } else {
                        System.out.println("Double Die Ownership: " + player[opposingPlayer].getName());
                        System.out.println("Double Die Value: " + doublingCube);
                    }

                    if (!useInitialDice) {
                        dice = Roll.rollDice(player[currentPlayer].getName());
                    }


                    command = " ";

                    while (!command.equals("M") && currentMatchNum == roundNum) {
                        useInitialDice = false;

                        System.out.println("Rolls: " + dice[0] + ", " + dice[1]);

                        int allowedTurns = 1;
                        int turnsUsed = 1;

                        if (dice[0] == dice[1]) {
                            System.out.println("You rolled a double. You get 4 moves.");
                            allowedTurns = DOUBLE_MOVE_ALLOWED_TURNS;
                        }

                        System.out.println("Enter Command (H for List of Commands):");
                        command = scanner.nextLine().toUpperCase();
                        int moveChoice;

                        switch (command) {
                            case "M" -> {
                                while (allowedTurns >= turnsUsed) {
                                    boolean nextPlayerTurn = false;

                                    int movesMade = 0;

                                    // generating the moves that are allowed to be made weather on the bar or not
                                    ArrayList<ArrayList<Integer>> barMoves = ValidMoves.barMoves(dice, player[currentPlayer].playerDirection(), spikes, player[currentPlayer].getPlayerColour());
                                    ArrayList<ArrayList<Integer>> allMoves = ValidMoves.allMoves(dice, player[currentPlayer].playerDirection(), spikes, player[currentPlayer].getPlayerColour(), tray, -1);

                                    // cant use both die at same time when more than 1 checker on the bar
                                    if (player[currentPlayer].getPlayerColour().equals("blue") && bar.getBlueCheckers() == USING_TWO_DIE) {
                                        ValidMoves.removeDie(barMoves, USING_TWO_DIE);
                                    } else if (player[currentPlayer].getPlayerColour().equals("red") && bar.getRedCheckers() == USING_TWO_DIE) {
                                        ValidMoves.removeDie(barMoves, USING_TWO_DIE);
                                    }

                                    while (bar.hasCheckersOfColor(player[currentPlayer].getPlayerColour()) && !nextPlayerTurn) {
                                        System.out.println("You have Checker on Bar you must move");

                                        if (barMoves.isEmpty()) {
                                            System.out.println("You have no available moves.");
                                            nextPlayerTurn = true;
                                            break;
                                        }

                                        ValidMoves.printMoves(barMoves);

                                        moveChoice = getUserMoveChoice(barMoves);
                                        ArrayList<Integer> selectedMove = barMoves.get(moveChoice - 1);

                                        int toSpike = selectedMove.get(TO_SPIKE);
                                        int dieUsed = selectedMove.get(DICE_USED);

                                        Board.addCheckerToSpike(spikes, toSpike, player[currentPlayer], bar);

                                        //Removes checker from Bar
                                        if (Objects.equals(player[currentPlayer].playerColour, "red")) {
                                            bar.removeRedChecker();
                                        } else {
                                            bar.removeBlueChecker();
                                        }

                                        if (dieUsed == USING_TWO_DIE) {
                                            nextPlayerTurn = true;
                                            Board.blotsInPlayerPath(barMoves.get(moveChoice - 2).get(TO_SPIKE),player[opposingPlayer], bar);
                                        } else { // one die used
                                            // all moves need to be recalculated as the checkers have changed positions
                                            allMoves = ValidMoves.allMoves(dice, player[currentPlayer].playerDirection(), spikes, player[currentPlayer].getPlayerColour(), tray, dieUsed);

                                            ValidMoves.removeDie(allMoves, dieUsed);
                                            ValidMoves.removeDie(allMoves, USING_TWO_DIE);
                                            ValidMoves.removeDie(barMoves, dieUsed);
                                            ValidMoves.removeDie(barMoves, USING_TWO_DIE);


                                            if (movesMade == 1) { // removing the dice you just used
                                                ValidMoves.removeDie(allMoves, ++dieUsed % 2);
                                            }
                                        }

                                        if (barMoves.isEmpty() && (allMoves.isEmpty() || movesMade == 2)) {
                                            nextPlayerTurn = true;
                                            System.out.println("No more moves. Ending " + player[currentPlayer].getName() + "'s turn.");
                                        } else if (barMoves.isEmpty() && !allMoves.isEmpty()) {
                                            Board.displayBoard(spikes, tray, player[currentPlayer], bar);
                                            break;
                                        } else {
                                            Board.displayBoard(spikes, tray, player[currentPlayer], bar);
                                        }
                                        movesMade++;
                                    }

                                    while (!nextPlayerTurn) { // once player has no moves on bar and still has moves left they can make normal moves on the board


                                        ValidMoves.printMoves(allMoves);

                                        if (allMoves.isEmpty()) {
                                            System.out.println("No available moves. Ending " + player[currentPlayer].getName() + "'s turn.");
                                            nextPlayerTurn = true;
                                        } else {
                                            moveChoice = getUserMoveChoice(allMoves);
                                            ArrayList<Integer> selectedMove = allMoves.get(moveChoice - 1);
                                            int fromSpike = selectedMove.get(FROM_SPIKE);
                                            int toSpike = selectedMove.get(TO_SPIKE);
                                            int dieUsed = selectedMove.get(DICE_USED);

                                            Board.movePiece(spikes, fromSpike, toSpike, bar);

                                            if (toSpike == 0) {
                                                tray[currentPlayer].addChecker();
                                            }

                                            if (dieUsed == USING_TWO_DIE) {
                                                nextPlayerTurn = true;
                                                Board.blotsInPlayerPath(allMoves.get(moveChoice - 2).get(TO_SPIKE),player[opposingPlayer], bar);

                                                if (allowedTurns == 2)
                                                    Board.displayBoard(spikes, tray, player[currentPlayer], bar);
                                            } else {
                                                allMoves = ValidMoves.allMoves(dice, player[currentPlayer].playerDirection(), spikes, player[currentPlayer].getPlayerColour(), tray, dieUsed);

                                                ValidMoves.removeDie(allMoves, dieUsed);
                                                ValidMoves.removeDie(allMoves, USING_TWO_DIE);

                                                if (movesMade == 1) { // removing the moves of the dice you just used
                                                    ValidMoves.removeDie(allMoves, ++dieUsed % 2);
                                                }

                                                if (allMoves.isEmpty() && (turnsUsed == allowedTurns)) {
                                                    nextPlayerTurn = true;
                                                    System.out.println("No more moves. Ending " + player[currentPlayer].getName() + "'s turn.");
                                                } else if (allMoves.isEmpty() && turnsUsed < allowedTurns) {
                                                    nextPlayerTurn = true;
                                                    System.out.println("This is your second turn");
                                                    Board.displayBoard(spikes, tray, player[currentPlayer], bar);
                                                } else {
                                                    Board.displayBoard(spikes, tray, player[currentPlayer], bar);
                                                }
                                            }
                                        }

                                        if (tray[currentPlayer].getNumCheckers() == MAX_CHECKERS) {
                                            Board.displayBoard(spikes, tray, player[currentPlayer], bar);

                                            if (tray[opposingPlayer].getNumCheckers() == 0 && bar.hasCheckersOfColor(player[opposingPlayer].getPlayerColour())) {
                                                System.out.println(player[currentPlayer].getName() + " Won this Match, Backgammon");
                                                roundNum++;
                                                player[currentPlayer].updategameScore(3 * doublingCube);
                                                System.out.println(player[currentPlayer].getName() + " is on " + player[currentPlayer].getgameScore() + " game points");
                                                System.out.println(player[opposingPlayer].getName() + " is on " + player[opposingPlayer].getgameScore() + " game points");

                                            } else if (tray[opposingPlayer].getNumCheckers() == 0) {
                                                System.out.println(player[currentPlayer].getName() + " Won this Match, gammon");
                                                roundNum++;
                                                player[currentPlayer].updategameScore(2 * doublingCube);
                                                System.out.println(player[currentPlayer].getName() + " is on " + player[currentPlayer].getgameScore() + " game points");
                                                System.out.println(player[opposingPlayer].getName() + " is on " + player[opposingPlayer].getgameScore() + " game points");

                                            } else {
                                                System.out.println(player[currentPlayer].getName() + " Won this Match, Single");
                                                roundNum++;
                                                player[currentPlayer].updategameScore(doublingCube);
                                                System.out.println(player[currentPlayer].getName() + " is on " + player[currentPlayer].getgameScore() + " game points");
                                                System.out.println(player[opposingPlayer].getName() + " is on " + player[((currentPlayer + 1) % 2)].getgameScore() + " game points");

                                            }


                                            if (player[currentPlayer].getgameScore() >= pointsToPlay) {
                                                System.out.println(player[currentPlayer].getName() + " Won The whole Game with " + player[currentPlayer].getgameScore() + " Points");
                                                newRound = false;
                                            }
                                        }
                                        movesMade++;
                                    }
                                    turnsUsed++;
                                }
                                opposingPlayer = currentPlayer;
                                currentPlayer = ++currentPlayer % 2;

                            }
                            case "P" -> {
                                // Update and display both players' pip scores
                                player[0].calculatePipScore(spikes);
                                player[1].calculatePipScore(spikes);
                                System.out.println(player[0].getName() + "'s Pip Score: " + player[0].getPipScore());
                                System.out.println(player[1].getName() + "'s Pip Score: " + player[1].getPipScore());
                            }
                            case "H" -> {
                                System.out.println("Lists of Commands are:");
                                System.out.println("M = Move");
                                System.out.println("P = Calculate Pip Scores");
                                System.out.println("R = Change Dice Rolls");
                                System.out.println("D = Offer a Double");
                                System.out.println("S = Score");
                                System.out.println("B = Display Board");
                            }
                            case "D" -> {
                                if (player[currentPlayer].getdoubleOwnership()) {
                                    doublingCube *= 2; // Double the value of the doubling cube
                                    System.out.println(player[currentPlayer].getName() + " offers a double. The game is now worth " + doublingCube + " points.");

                                    // Prompt the opponent to accept (A) or decline (D)
                                    String opponentChoice;
                                    do {
                                        System.out.println(player[opposingPlayer].getName() + " Do you Accept (A) or Decline (D) " + player[currentPlayer].getName() + " offer?:");
                                        opponentChoice = scanner.nextLine().toUpperCase();
                                    } while (!opponentChoice.equals("A") && !opponentChoice.equals("D"));

                                    if (opponentChoice.equals("A")) {
                                        // Accept the double
                                        System.out.println(player[opposingPlayer].getName() + " accepted the double. Match is now worth " + doublingCube);
                                        // Passes ownership of double die
                                        player[opposingPlayer].setdoubleOwnership(true);
                                        player[currentPlayer].setdoubleOwnership(false);
                                    } else {
                                        // Decline the double
                                        doublingCube = (doublingCube/2);
                                        System.out.println(player[opposingPlayer].getName() + " declined the double. " + player[currentPlayer].getName() + " wins " + doublingCube + " points.");
                                        roundNum++;
                                        player[currentPlayer].updategameScore(doublingCube);
                                        doublingCube = 1; // Reset doubling cube for the next game
                                        // Sets ownership to true for new game
                                        player[opposingPlayer].setdoubleOwnership(true);
                                        player[currentPlayer].setdoubleOwnership(true);
                                        if (player[currentPlayer].getgameScore() >= pointsToPlay) {
                                                System.out.println(player[currentPlayer].getName() + " Won The whole Game with " + player[currentPlayer].getgameScore() + " Points");
                                                newRound = false;
                                            }
                                    }
                                } else {
                                    System.out.println("You do not possess ownership of the double die");
                                }
                            }
                            case "Q" -> {
                                System.out.println("Quitting the game...");
                                System.exit(0);
                            }
                            case "R" -> {
                                // Allow the user to manually change the dice roll
                                String newDice1 = "-1";
                                String newDice2 = "-1";
                                while(!String.valueOf(newDice1).matches("[1-6]") || !String.valueOf(newDice2).matches("[1-6]")) {
                                    System.out.println("Enter the new dice values (e.g., 3 4):");

                                    newDice1 = scanner.next();
                                    newDice2 = scanner.next();
                                    if (String.valueOf(newDice1).matches("[1-6]") && String.valueOf(newDice2).matches("[1-6]")){
                                        dice[0] = Integer.parseInt(newDice1);
                                        dice[1] = Integer.parseInt(newDice2);
                                        scanner.nextLine();
                                        System.out.println("Dice values changed to: " + dice[0] + ", " + dice[1]);
                                    } else System.out.println("Invalid Command");
                                }
                            }
                            case "S" -> {
                                System.out.println("———————————————————————————");
                                System.out.println("Game Score:");
                                System.out.println("Playing to " + pointsToPlay);
                                System.out.println(player[currentPlayer].getName() + ": is on " + player[currentPlayer].getgameScore() + " points.");
                                System.out.println(player[opposingPlayer].getName() + ": is on " + player[opposingPlayer].getgameScore() + " points.");
                                System.out.println("———————————————————————————");
                            }
                            case "T" -> {
                                try {
                                    System.out.println("Enter the name of the commands file (including extension): ");
                                    String fileName = scanner.nextLine();
                                    commands = readCommandsFromFile(fileName); 
                                    for (String fileCommand : commands) {
                                        fileCommand = fileCommand.toUpperCase();
                                        switch (fileCommand) {
                                            case "R" -> {
                                                // Allow the user to manually change the dice roll
                                                String newDice1 = "-1";
                                                String newDice2 = "-1";
                                                while(!String.valueOf(newDice1).matches("[1-6]") || !String.valueOf(newDice2).matches("[1-6]")) {
                                                    System.out.println("Enter the new dice values (e.g., 3 4):");
                
                                                    newDice1 = scanner.next();
                                                    newDice2 = scanner.next();
                                                    if (String.valueOf(newDice1).matches("[1-6]") && String.valueOf(newDice2).matches("[1-6]")){
                                                        dice[0] = Integer.parseInt(newDice1);
                                                        dice[1] = Integer.parseInt(newDice2);
                                                        scanner.nextLine();
                                                        System.out.println("Dice values changed to: " + dice[0] + ", " + dice[1]);
                                                    } else System.out.println("Invalid Command");
                                                }
                                            }
                                            case "P" -> {
                                                // Update and display both players' pip scores
                                                player[0].calculatePipScore(spikes);
                                                player[1].calculatePipScore(spikes);
                                                System.out.println(player[0].getName() + "'s Pip Score: " + player[0].getPipScore());
                                                System.out.println(player[1].getName() + "'s Pip Score: " + player[1].getPipScore());
                                            }
                                            case "H" -> {
                                                System.out.println("Lists of Commands are:");
                                                System.out.println("M = Move");
                                                System.out.println("P = Calculate Pip Scores");
                                                System.out.println("R = Change Dice Rolls");
                                                System.out.println("D = Offer a Double");
                                                System.out.println("S = Score");
                                                System.out.println("B = Display Board");
                                            }
                                            case "Q" -> {
                                                System.out.println("Quitting the game...");
                                                System.exit(0);
                                            }
                                            case "S" -> {
                                                System.out.println("———————————————————————————");
                                                System.out.println("Game Score:");
                                                System.out.println("Playing to " + pointsToPlay);
                                                System.out.println(player[currentPlayer].getName() + ": is on " + player[currentPlayer].getgameScore() + " points.");
                                                System.out.println(player[opposingPlayer].getName() + ": is on " + player[opposingPlayer].getgameScore() + " points.");
                                                System.out.println("———————————————————————————");
                                            }
                                            case "B" -> Board.displayBoard(spikes, tray, player[currentPlayer], bar);
                                            default -> System.out.println("Command entered is invalid");
                                        }
                                    }
                                } catch (IOException e) {
                                    System.out.println("Error reading commands from file: " + e.getMessage());
                                }
                            }
                            case "B" -> Board.displayBoard(spikes, tray, player[currentPlayer], bar);
                            default -> System.out.println("Command entered is invalid");
                        }
                    }
                }
            }
            System.out.println("Would you like to play another match? ");
            System.out.println("Yes (Y)  No (N)");
            command = scanner.nextLine().toUpperCase();
            boolean validCommand = false;
            while(!validCommand){
                if(command.equals("Y")) {
                    validCommand = true;
                } else if(command.equals("N")){
                    validCommand = true;
                    newMatch = false;
                    System.out.println("Thanks for playing!");
                } else {
                    System.out.println("Invalid Command.");
                    command = scanner.nextLine().toUpperCase();
                }
            }
        }
    }
}