import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
	@Test public void testCheckNumberExactlyOnceInColumn(){
		int[][] matrix = {{0, 2, 3, 1}, {1, 2, 4, 2},{2 ,3 ,1 ,2}};
		ArrayList<ArrayList<Integer>> arrayList = new ArrayList<>();
		for (int[] row : matrix) {
			ArrayList<Integer> rowList = new ArrayList<>();
			for (int value : row) {
				rowList.add(value);
			}
			arrayList.add(rowList);
		}

		assertTrue(ValidMoves.checkNumberExactlyOnceInColumn(arrayList,1,3));
	}
	//  printMoves, checkNumberInColumn, checkNumberExactlyOnceInColumn, 
	// removeRowsWithConditions, extractValue, removeDie, and bearOffAllowed
	// Difficult to test in isolation

	@Test public void testRemoveRowsWithConditions(){
		int[][] matrix = {{0, 2, 3, 1}, {1, 2, 4, 2},{2 ,1 ,1 ,2}};
		ArrayList<ArrayList<Integer>> arrayList = new ArrayList<>();
		for (int[] row : matrix) {
			ArrayList<Integer> rowList = new ArrayList<>();
			for (int value : row) {
				rowList.add(value);
			}
			arrayList.add(rowList);
		}
		ValidMoves.removeRowsWithConditions(arrayList,2, 1, 1, 3);
		assertEquals(2, arrayList.size());
		}
	@Test public void testRemoveDie(){
		int[][] matrix = {{0, 2, 3, 1}, {1, 2, 4, 2},{2 ,1 ,1 ,2}};
		ArrayList<ArrayList<Integer>> arrayList = new ArrayList<>();
		for (int[] row : matrix) {
			ArrayList<Integer> rowList = new ArrayList<>();
			for (int value : row) {
				rowList.add(value);
			}
			arrayList.add(rowList);
		}
		ValidMoves.removeDie(arrayList, 2);
		assertEquals(1, arrayList.size());
		}
		@Test public void testPrintMoves(){
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			System.setOut(new PrintStream(outputStreamCaptor));
			int[][] matrix = {{0, 2, 3, 1}};
			ArrayList<ArrayList<Integer>> arrayList = new ArrayList<>();
			for (int[] row : matrix) {
				ArrayList<Integer> rowList = new ArrayList<>();
				for (int value : row) {
					rowList.add(value);
				}
				arrayList.add(rowList);
			}
			ValidMoves.printMoves(arrayList);
			assertEquals(1, arrayList.size());

		}
		@Test public void onlyUseBiggerDie(){
		Checker[][]	spikes = new Checker[2][12];
			spikes[1][6] = new Checker("blue", 1);  // Spike 6
			spikes[1][11] = new Checker("red", 1);  // Spike 1
			spikes[1][7] = new Checker("blue", 1);  // Spike 5
			int[] dice = {5, 2};
			Tray[] tray = Board.Tray();
			ArrayList<ArrayList<Integer>> allMoves=ValidMoves.allMoves(dice, -1, spikes, "blue", tray, -1);
			assertEquals(2, allMoves.size());
		}

}
