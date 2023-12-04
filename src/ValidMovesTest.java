import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ValidMovesTest {

	// 1
	@Test
	public void testAllMoves() {
	    int[] dice = {1, 2};
	    int direction = 1;
	    Checker[][] spikes = Board.Spikes();
	    String colour = "blue";
	    Tray[] tray = Board.Tray();
	    int dieUsed = 1;

	    ArrayList<ArrayList<Integer>> moves = ValidMoves.allMoves(dice, direction, spikes, colour, tray, dieUsed);

	    assertNotNull(moves, "Moves should not be null");
	    assertFalse(moves.isEmpty(), "Moves should not be empty");
	}
	
	// 2
	@Test
	public void testBarMoves() {
	    int[] dice = {1, 2};
	    int direction = 1;
	    Checker[][] spikes = Board.Spikes();
	    String colour = "blue";

	    ArrayList<ArrayList<Integer>> moves = ValidMoves.barMoves(dice, direction, spikes, colour);

	    assertNotNull(moves, "Moves should not be null");
	    assertFalse(moves.isEmpty(), "Moves should not be empty");
	}
	
	//  printMoves, checkNumberInColumn, checkNumberExactlyOnceInColumn, 
	// removeRowsWithConditions, extractValue, removeDie, and bearOffAllowed
	// Difficult to test in isolation



}
