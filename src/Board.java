import javax.xml.namespace.QName;
import java.util.Objects;

public class Board {

    public static Checker[][] spikes;
    public static Tray[] tray;

    public static Checker[][] Spikes() {
        // Creates Starting Board
        spikes = new Checker[2][12];

        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 12; j++) {
                spikes[i][j] = null;
            }
        }

       // Starting Spikes
       spikes[0][0] = new Checker("blue", 5);  // Spike 13
       spikes[0][4] = new Checker("red", 3);   // Spike 17
       spikes[0][6] = new Checker("red", 5);   // Spike 19
       spikes[0][11] = new Checker("blue", 2); // Spike 24
       spikes[1][0] = new Checker("red", 5);   // Spike 12
       spikes[1][4] = new Checker("blue", 3);  // Spike 8
       spikes[1][6] = new Checker("blue", 5);  // Spike 6
       spikes[1][11] = new Checker("red", 2);  // Spike 1


        return spikes;
    }

    public static Tray[] Tray(){
        tray = new Tray[2];

        tray[0] = new Tray("red", 10);
        tray[1] = new Tray("blue", 10);

        return tray;
    }

    // Displays board to user
    public static void displayBoard(Checker[][] spikes, Tray[] tray, PlayerData currentPlayer, Bar bar) {
        // Initializing reset string to reset the colour output to the terminal
        String reset = "\u001B[0m";

        // Print the players game score
        System.out.println(currentPlayer.getName() + "'s game Score: " + currentPlayer.getgameScore());

        // Print the current player's pip score
        currentPlayer.calculatePipScore(spikes); 
        System.out.print(currentPlayer.getName() + "'s Pip Score: " + currentPlayer.getPipScore());

        // Print top of board (12-24)
        System.out.println(" \n————————————————————————————————————————————————————————————————");
        System.out.print("|");

        for (int i = 12; i <= 17; i++) {
            System.out.printf(" %2s |", i + 1); 
        }
        System.out.print("  |");

        for (int i = 18; i <= 23; i++) {
            System.out.printf(" %2s |", i + 1); 
        }
        System.out.print("\u001B[31m" + " red tray" + reset);

        // Print 2D array of checkers
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                System.out.print("\n");
            }
            else {
                System.out.println("————————————————————————————————————————————————————————————————");
            }
            System.out.print("|");
            for (int j = 0; j <= 11; j++) {
                if (j == 6) {
                    System.out.print("  |");
                }
                if (spikes[i][j] != null) {
                    int numCheckers = spikes[i][j].getNumCheckers();
                    System.out.printf(spikes[i][j].getColourCode() + " %2s " + reset + "|", numCheckers); // Print number of checkers and checker symbol
                } else {
                    System.out.print("    |"); // Print only number when there is no checker
                }

            }
            if(tray[i].getNumCheckers() != 0 )
                System.out.print(" " + tray[i].getColourCode() + tray[i].getNumCheckers() + reset);

            System.out.println();
        }

        // Print bottom half of board (12-1)
        System.out.print("|");
        for (int i = 11; i >= 6; i--) {
            System.out.printf(" %2s |", i + 1); // Print spike numbers with 2-character width
        }
        System.out.print("  |");
        for (int i = 5; i >= 0; i--) {
            System.out.printf(" %2s |", i + 1); // Print spike numbers with 2-character width
        }
        System.out.print("\u001B[34m" + " blue tray" + reset);

        System.out.println("\n————————————————————————————————————————————————————————————————");
        // Print the bar with separate counts for red and blue checkers
        System.out.println("Bar: Red(" +  "\u001B[31m" + bar.getRedCheckers() + reset + ") | Blue(" + "\u001B[34m" + bar.getBlueCheckers() + reset + ")");


    }

    public static int[] convertSpikeToIndices(int spikeNumber) {
        int[] indices = new int[2];
        if (spikeNumber >= 1 && spikeNumber <= 12) {
            // Bottom row (spikes 1-12)
            indices[0] = 1;
            indices[1] = 12 - spikeNumber;
        } else if (spikeNumber >= 13 && spikeNumber <= 24) {
            // Top row (spikes 13-24)
            indices[1] = spikeNumber - 13;
        }
        // For debugging
        //System.out.println("Spike " + spikeNumber + " -> Row: " + indices[0] + ", Col: " + indices[1]);
        return indices;
    }

    public static int convertIndicesToSpike(int row, int col) {
        if (row == 1) {
            // Bottom row (spikes 1-12)
            return 12 - col;
        } else if (row == 0) {
            // Top row (spikes 13-24)
            return 13 + col;
        } else return -1;
    }

    public static void addCheckerToSpike(Checker[][] spikes, int toSpike, PlayerData currentPlayer, Bar bar) {
        int[] toIndices = convertSpikeToIndices(toSpike);
        if(spikes[toIndices[0]][toIndices[1]] != null && spikes[toIndices[0]][toIndices[1]].getNumCheckers() == 1 && !currentPlayer.playerColour.equals(spikes[toIndices[0]][toIndices[1]].getColour())) {
            spikes[toIndices[0]][toIndices[1]].changeColour();
            spikes[toIndices[0]][toIndices[1]].setNumCheckers(0);

            if(currentPlayer.playerColour.equals("red")) bar.addBlueChecker();
            else bar.addRedChecker();

        }
        // Add one checker to the specified spike with the color of the current player
        if (spikes[toIndices[0]][toIndices[1]] != null) {
            // Spike is not empty
            spikes[toIndices[0]][toIndices[1]].setNumCheckers(spikes[toIndices[0]][toIndices[1]].getNumCheckers() + 1);
            System.out.println("Added checker to Spike " + toSpike + " with color " + currentPlayer.getPlayerColour());
        } else {
            // If the spike is empty, create a new checker with the color of the current player and one checker
            spikes[toIndices[0]][toIndices[1]] = new Checker(currentPlayer.getPlayerColour(), 1);
            System.out.println("Added checker to Spike " + toSpike + " with color " + currentPlayer.getPlayerColour());
        }
    }

    public static void movePiece(Checker[][] spikes, int fromSpike, int toSpike, Bar bar) {
        int[] fromIndices = convertSpikeToIndices(fromSpike);
        int[] toIndices = convertSpikeToIndices(toSpike);

        // Move one checker at a time
        Checker pieceToMove = spikes[fromIndices[0]][fromIndices[1]];
        if (pieceToMove != null && pieceToMove.getNumCheckers() > 0) {
            int numCheckersToMove = 1;  // Change this value based on the number of checkers you want to move
            int remainingCheckers = pieceToMove.getNumCheckers() - numCheckersToMove;

            // Adjust the number of checkers in the source spike
            if (remainingCheckers > 0) {
                spikes[fromIndices[0]][fromIndices[1]].setNumCheckers(remainingCheckers);
            } else {
                spikes[fromIndices[0]][fromIndices[1]] = null;  // If no checkers remain, remove the spike
            }

            if (toSpike != 0) {
                if (spikes[toIndices[0]][toIndices[1]] != null) {
                    // Destination spike is not empty
                    if (!Objects.equals(spikes[toIndices[0]][toIndices[1]].getColour(), pieceToMove.getColour()) && spikes[toIndices[0]][toIndices[1]].getNumCheckers() == 1) {
                        // Replace the existing checker and move the other to the bar
                        System.out.println("Move successful. Replaced checker at Spike " + toSpike + " and moved to the bar.");
                        spikes[toIndices[0]][toIndices[1]].changeColour();
    
                        // Update the bar counts based on the color of the moved checker
                        if (pieceToMove.getColour().equals("red")) {
                            bar.addBlueChecker(); // add one checker of opposite color to the Bar
                        } else {
                            bar.addRedChecker(); // add one checker of opposite color to the Bar
                        }
    
                        System.out.println("Moved checker of color " + pieceToMove.getColour() + " to the bar.");
                    } else {
                        // Increment the number of checkers in the destination spike
                        spikes[toIndices[0]][toIndices[1]].setNumCheckers(spikes[toIndices[0]][toIndices[1]].getNumCheckers() + 1);
                        System.out.println("Move successful. From Spike " + fromSpike + " to Spike " + toSpike);
                    }
                } else {
                    // If the destination spike is empty, create a new checker with one checker
                    spikes[toIndices[0]][toIndices[1]] = new Checker(pieceToMove.getColour(), numCheckersToMove);
                }

            }   

        } else {
            System.out.println("Invalid move: Source spike is empty.");
        }
    }
    public static void blotsInPlayerPath(int spikeToCheck,PlayerData player, Bar bar){
        int[] indices = convertSpikeToIndices(spikeToCheck);
        if(spikes[indices[0]][indices[1]] != null && spikes[indices[0]][indices[1]].getColour().equals(player.playerColour)) {
            spikes[indices[0]][indices[1]] = null;

            if (player.playerColour.equals("red")) bar.addRedChecker();
            else bar.addBlueChecker();
        }
    }
}