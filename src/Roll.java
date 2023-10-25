import java.util.Random

public class Roll{

    public int[] rollDice(){ //Roll not Role u dummy

        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;

        int[] dice = new int[2];
        dice = {die1, die2};

        return dice;
    }
}