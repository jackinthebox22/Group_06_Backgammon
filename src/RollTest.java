import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RollTest {

	@Test
	public void testRollDice() {
	    String player_name = "Player 1";
	    int[] dice = Roll.rollDice(player_name);

	    assertEquals(2, dice.length, "Dice array should have length of 2");
	    
	    for (int i = 0; i < dice.length; i++) {
	        assertTrue(dice[i] >= 1 && dice[i] <= 6, "Dice value should be between 1 and 6");
	    }
	}

}
