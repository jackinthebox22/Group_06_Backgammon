import java.util.Scanner;

public class PlayerData {
    public final String name;
    public String playerColour;

    public PlayerData(String name, String playerColour){
        this.name = name;
        this.playerColour = playerColour;
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
}