public class Board {

    private Checker[][] spikes;

    public Board() {
        // Creates Starting Board
        spikes = new Checker[2][12];

        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 12; j++) {
                spikes[i][j] = null;
            }
        }

        // Starting Spikes
        spikes[0][0] = new Checker("O", 5);  // Spike 13
        spikes[0][4] = new Checker("X", 3);  // Spike 17
        spikes[0][6] = new Checker("X", 5);  // Spike 19
        spikes[0][11] = new Checker("O", 2); // Spike 24
        spikes[1][0] = new Checker("X", 5);  // Spike 12
        spikes[1][4] = new Checker("O", 3);  // Spike 8
        spikes[1][6] = new Checker("O", 5);  // Spike 6
        spikes[1][11] = new Checker("X", 2); // Spike 1
    }
    // Displays board to user
    public void displayBoard() {

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

        // Print 2D array of checkers
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                System.out.print("\n");
            }
            if (i == 1) {
                System.out.println("————————————————————————————————————————————————————————————————");
            }
            System.out.print("|");
            for (int j = 0; j <= 11; j++) {
                if (j == 6) {
                    System.out.print("  |");
                }
                if (spikes[i][j] != null) {
                    String checkerSymbol = spikes[i][j].getColor();
                    int numCheckers = spikes[i][j].getNumCheckers();
                    System.out.printf("%2s%s |", numCheckers, checkerSymbol); // Print number of checkers and checker symbol
                } else {
                    System.out.printf("    |"); // Print only number when there is no checker
                }
            }
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

        System.out.println("\n————————————————————————————————————————————————————————————————\n");
    }
}