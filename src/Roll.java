import java.util.Random;

public class Roll{

    public int[] rollDice(){ //Roll not Role u dummy

        Random rand = new Random();
        int die1 =rand.nextInt(6) + 1;
        int die2 = rand.nextInt(6) + 1;
        System.out.println(die1 + "" + die2);
        int[] dice = {1, 2};

        return dice;
    }
}