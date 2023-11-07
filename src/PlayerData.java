import java.util.Scanner;

public class PlayerData {
    public final String name;
    public int playerNumber;

    public PlayerData(String name, int playerNumber){
        this.name = name;
        this.playerNumber = playerNumber;
    }

    public String getName() {return name;}


    public int getPlayerNumber(){return playerNumber;}

    public int assignPlayer1(){playerNumber = 1;
    return playerNumber;}

    public int assignPlayer2(){playerNumber = 2;
    return playerNumber;}

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