public class Board {

    public static Checker[][] spikes;
    public static Bar[] bar;

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
    public static Bar[] Bar(){
        bar = new Bar[2];

        bar[0] = new Bar("red", 11);
        bar[1] = new Bar("blue", 0);

        return bar;
    }

    // Displays board to user
    public static void displayBoard(Checker[][] spikes, Bar[] bar) {
        // Initializing reset string to reset the colour output to the terminal
        String reset = "\u001B[0m";

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
                    String checkerSymbol = spikes[i][j].getColourCode();
                    int numCheckers = spikes[i][j].getNumCheckers();
                    System.out.printf(spikes[i][j].getColourCode() + " %2s " + reset + "|", numCheckers); // Print number of checkers and checker symbol
                } else {
                    System.out.print("    |"); // Print only number when there is no checker
                }

            }
            if(bar[i].getNumCheckers() != 0 )
                System.out.print(" " + bar[i].getColourCode() + bar[i].getNumCheckers() + reset);

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
        System.out.println("Bar: \n");   
    }

    public static int[] convertSpikeToIndices(int spikeNumber) {
        int[] indices = new int[2];
        if (spikeNumber >= 1 && spikeNumber <= 12) {
            // Bottom row (spikes 1-12)
            indices[0] = 1;
            indices[1] = 12 - spikeNumber;
        } else if (spikeNumber >= 13 && spikeNumber <= 24) {
            // Top row (spikes 13-24)
            indices[0] = 0;
            indices[1] = spikeNumber - 13;
        } else {
            System.out.println("Invalid spike number.");
        }

        // For debugging
        System.out.println("Spike " + spikeNumber + " -> Row: " + indices[0] + ", Col: " + indices[1]);
    
        return indices;
    }

    public static void movePiece(Checker[][] spikes, int fromSpike, int toSpike) {
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
            
            if (spikes[toIndices[0]][toIndices[1]] != null) {
                // Increment the number of checkers in the destination spike
                spikes[toIndices[0]][toIndices[1]].setNumCheckers(spikes[toIndices[0]][toIndices[1]].getNumCheckers() + 1);
            } else {
                // If the destination spike is empty, create a new checker with one checker
                spikes[toIndices[0]][toIndices[1]] = new Checker(pieceToMove.getColour(), numCheckersToMove);
            }

            System.out.println("Move successful.");
        } else {
            System.out.println("Invalid move: Source spike is empty.");
        }
    }

}