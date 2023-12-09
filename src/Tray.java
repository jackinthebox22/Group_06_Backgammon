public class Tray extends Checker{
    private final String colour;

    public Tray(String colour, int numCheckers) {
        super(colour, numCheckers);
        this.colour = colour;

    }

    public static void handleWinningMatch(int numCheckers, PlayerData[] player, int currentPlayer, int opposingPlayer, int doublingCube, Bar bar, int opositionCheckers) {
        if (opositionCheckers == 0 && bar.hasCheckersOfColor(player[opposingPlayer].getPlayerColour())) { //Backgammon
            System.out.println(player[currentPlayer].getName() + " Won this Match, Backgammon");
            player[currentPlayer].updategameScore(3 * doublingCube);
            System.out.println(player[currentPlayer].getName() + " is on " + player[currentPlayer].getgameScore() + " game points");
            System.out.println(player[opposingPlayer].getName() + " is on " + player[opposingPlayer].getgameScore() + " game points");
        } else if (opositionCheckers == 0) { //Gammon
            System.out.println(player[currentPlayer].getName() + " Won this Match, Gammon");
            player[currentPlayer].updategameScore(2 * doublingCube);
            System.out.println(player[currentPlayer].getName() + " is on " + player[currentPlayer].getgameScore() + " game points");
            System.out.println(player[opposingPlayer].getName() + " is on " + player[opposingPlayer].getgameScore() + " game points");
        } else { // Single
            System.out.println(player[currentPlayer].getName() + " Won this Match, Single");
            player[currentPlayer].updategameScore(doublingCube);
            System.out.println(player[currentPlayer].getName() + " is on " + player[currentPlayer].getgameScore() + " game points");
            System.out.println(player[opposingPlayer].getName() + " is on " + player[((currentPlayer + 1) % 2)].getgameScore() + " game points");
        }
    }
}



