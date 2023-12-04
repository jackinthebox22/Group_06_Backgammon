public class Tray extends Checker{
    private final String colour;

    public Tray(String colour, int numCheckers) {
        super(colour, numCheckers);
        int numCheckers1 = 0;
        this.colour = colour;
    }

    public void handleWinningMatch(PlayerData[] player, int currentPlayer, int opposingPlayer, int doublingCube, int roundNum, Bar bar) {
        if (this.getNumCheckers() == 0 && bar.hasCheckersOfColor(player[opposingPlayer].getPlayerColour())) {
            System.out.println(player[currentPlayer].getName() + " Won this Match, Backgammon");
            roundNum++;
            player[currentPlayer].updategameScore(3 * doublingCube);
            System.out.println(player[currentPlayer].getName() + " is on " + player[currentPlayer].getgameScore() + " game points");
            System.out.println(player[opposingPlayer].getName() + " is on " + player[opposingPlayer].getgameScore() + " game points");
        } else if (this.getNumCheckers() == 0) {
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
    }
}



