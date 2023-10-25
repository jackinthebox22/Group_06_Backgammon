import java.util.Random
public class Role{

    public int[] roleDice(){

        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;

        int[] dice = new int[2];
        dice = {die1, die2};

        return dice;
    }
}