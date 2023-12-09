/**
 * Game of Backgammon
 * @version 1.00 9-12-23
 * @author Jack Caldwell & Patrick Moxom
 * GitHub Names: jackinthebox & Patrick-Moxom
 */

import java.util.Random;

public class Roll {

    //rolls two dice
    public static int[] rollDice(String player_name) {
        int[] dice = new int[2];
        Random random = new Random();

        for (int i = 0; i < 2; i++) {
            dice[i] = random.nextInt(6) + 1; // generates random integer from 1 - 6
        }

        System.out.println(player_name + " rolled: <" + dice[0] + "> <" + dice[1] + ">");
        return dice;
    }


}