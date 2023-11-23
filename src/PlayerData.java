import java.util.Scanner;

public class PlayerData {
    public final String name;
    public String playerColour;
    public int pipScore;
    public int gameScore;

    public PlayerData(String name, String playerColour){
        this.name = name;
        this.playerColour = playerColour;
        this.pipScore = 167;  // Initial pip count for each player
        this.gameScore = 0;
    }

    public String getName() {return name;}


    public String getPlayerColour(){return playerColour;}

    // gets the player names from the user
    public static String[] getNamesFromUser() {
        Scanner scanner = new Scanner(System.in);

        String[] playerNames = new String[2];

        System.out.println("Player 1, enter your name: ");
        playerNames[0] = scanner.nextLine();

        System.out.println("Player 2, enter your name: ");
        playerNames[1] = scanner.nextLine();

        return playerNames;
    }


    public int playerDirection() {
        int direction = 1;

        if(playerColour.equals("blue")) direction = -1;

        return direction;
    }

    public int getPipScore() {
        return pipScore;
    }

    public int getgameScore() {
        return gameScore;
    }

    public void updategameScore(int points) {
        this.gameScore = gameScore + points;
    }


    public void updatePipScore(int newPipScore) {
        this.pipScore = newPipScore;
    }

    public void calculatePipScore(Checker[][] spikes) {
        int pipScore = 0;
    
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 12; j++) {
                if (spikes[i][j] != null && spikes[i][j].getColour().equals(playerColour)) {
                    int spikeNumber;
                    if (playerColour.equals("blue")) {
                        spikeNumber = Board.convertIndicesToSpike(i, j);
                    } else {
                        // For red, reverse the spike numbering
                        spikeNumber = 25 - Board.convertIndicesToSpike(i, j);
                    }
                    pipScore += spikeNumber * spikes[i][j].getNumCheckers();
                }
            }
        }
    
        this.pipScore = pipScore;
    }
}