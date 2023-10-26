import java.util.Random;

public class Roll{

    public static int[] rollDice(){
        int[] dice = new int[2];
        Random random = new Random();

        for(int i = 0; i < 2; i++) {
            dice[i] = random.nextInt(6) + 1;
        }

        System.out.println("Dice roll: <" + dice[0] + "> <" + dice[1] + ">");
        return dice;
    }
}