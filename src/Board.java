public class Board {

    private String[][] spikes;

    public Board() {
        // Initialize the board with empty spikes
        spikes = new String[2][12];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 12; j++) {
                spikes[i][j] = "";
            }
        }

        // Starting Spikes
        spikes[0][0] = "5O";  // Spike 13
        spikes[0][4] = "3X";  // Spike 17
        spikes[0][6] = "5X";  // Spike 19
        spikes[0][11] = "2O"; // Spike 24
        spikes[1][0] = "5X";  // Spike 12
        spikes[1][4] = "3O";  // Spike 8
        spikes[1][6] = "5O";  // Spike 6
        spikes[1][11] = "2X"; // Spike 1
    }
    // Displays board to user
    public void displayBoard() {

        // Print Top of Board (12-24)
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
                System.out.printf(" %-3s|", spikes[i][j]); // Spike content with 4-character width
            }
            System.out.println();
        }

        // Print bottom half of board
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

    public static void main(String[] args) {

        Board board = new Board();
        board.displayBoard();
    }
}