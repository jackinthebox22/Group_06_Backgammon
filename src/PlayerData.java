import java.util.Scanner;

public class PlayerData {
    public final String name;
    public final int points;

    public PlayerData(String name, int points){
        this.name = name;
        this.points = points;
    }

    public String getName() {return name;}

    public int getPoints(){return points;}

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