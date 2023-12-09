import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTest {

	// 1
	@Test
	public void testSpikes() {
	    Checker[][] spikes = Board.Spikes();
	    assertNotNull(spikes, "Spikes should not be null");
	    assertEquals(2, spikes.length, "Spikes should have 2 rows");
	    assertEquals(12, spikes[0].length, "Each row in Spikes should have 12 columns");
	}

	// 2
	@Test
	public void testTray() {
	    Tray[] tray = Board.Tray();
	    assertNotNull(tray, "Tray should not be null");
	    assertEquals(2, tray.length, "Tray should have 2 elements");
	}

	// 3
	@Test
	public void testConvertSpikeToIndicesAndConvertIndicesToSpikeTopRow() {
	    int spike = 15;
	    int[] indices = Board.convertSpikeToIndices(spike);
	    int convertedSpike = Board.convertIndicesToSpike(indices[0], indices[1]);
	    assertEquals(spike, convertedSpike, "Conversion between spike and indices should be reversible");
	}
	@Test
	public void testConvertSpikeToIndicesAndConvertIndicesToSpikeBottomRow(){
		int spike = 5;
		int[] indices = Board.convertSpikeToIndices(spike);
		int convertedSpike = Board.convertIndicesToSpike(indices[0], indices[1]);
		assertEquals(spike, convertedSpike, "Conversion between spike and indices should be reversible");
	}
	@Test

	public void testConvertIndicesToSpikeInvaildInput(){
	int spikeNumber = Board.convertIndicesToSpike(-1, -1);
		assertEquals(-1, spikeNumber, "Conversion between spike and indices should be reversible");

	}
	@Test public void testAddCheckerNoBar(){
		Checker[][] spikes = new Checker[2][12];

		spikes[1][6] = new Checker("red", 1);  // Spike 6
		PlayerData player = new PlayerData("void", "red");
		Bar bar = new Bar();
		Board.addCheckerToSpike(spikes, 8, player, bar);

		assertEquals(1, spikes[1][4].getNumCheckers());
	}
	@Test public void testAddCheckerWithBarRed(){
		Checker[][] spikes = new Checker[2][12];
		spikes[1][4] = new Checker("blue", 1);  // Spike 8
		spikes[1][6] = new Checker("red", 1);  // Spike 6
		PlayerData player = new PlayerData("void", "red");
		Bar bar = new Bar();
		Board.addCheckerToSpike(spikes, 8, player, bar);

		assertEquals(1, bar.getBlueCheckers());

	}
	@Test public void testAddCheckerWithBarBlue(){
		Checker[][] spikes = new Checker[2][12];
		spikes[1][4] = new Checker("Blue", 1);  // Spike 8
		spikes[1][6] = new Checker("Red", 1);  // Spike 6
		PlayerData player = new PlayerData("void", "blue");
		Bar bar = new Bar();
		Board.addCheckerToSpike(spikes, 6, player, bar);

		assertEquals(1, bar.getRedCheckers());

	}
	@Test public void testMovePieceWithoutBar(){
		Checker[][] spikes = new Checker[2][12];
		spikes[1][4] = new Checker("Blue", 1);  // Spike 8
		spikes[1][6] = new Checker("Blue", 1);  // Spike 6
		int fromSpike = Board.convertIndicesToSpike(1,4);
		int toSpike = Board.convertIndicesToSpike(1,6);
		Bar bar = new Bar();
		Board.movePiece(spikes, fromSpike, toSpike,bar);
		assertEquals(2,spikes[1][6].getNumCheckers());
	}
	@Test public void testMovePieceWithBar(){
		Checker[][] spikes = new Checker[2][12];
		spikes[1][4] = new Checker("Blue", 1);  // Spike 8
		spikes[1][6] = new Checker("Red", 1);  // Spike 6
		int fromSpike = Board.convertIndicesToSpike(1,4);
		int toSpike = Board.convertIndicesToSpike(1,6);
		Bar bar = new Bar();
		Board.movePiece(spikes, fromSpike, toSpike,bar);
		assertEquals(1,bar.getRedCheckers());
	}

	@Test public void testMovePiecePiecesRemainingOnSpike(){
		Checker[][] spikes = new Checker[2][12];
		spikes[1][4] = new Checker("Blue", 2);  // Spike 8
		spikes[1][6] = new Checker("Blue", 1);  // Spike 6
		int fromSpike = Board.convertIndicesToSpike(1,4);
		int toSpike = Board.convertIndicesToSpike(1,6);
		Bar bar = new Bar();
		Board.movePiece(spikes, fromSpike, toSpike,bar);
		assertEquals(1,spikes[1][4].getNumCheckers());
	}
	@Test public void testDisplayBoard(){
		Checker[][] spikes = Board.Spikes();
		Tray[] tray = Board.Tray();
		Bar bar = new Bar();
		PlayerData player = new PlayerData("jack", "blue");
		Board.displayBoard(spikes, tray, player, bar);
		assertEquals("blue",player.getPlayerColour() );
	}
}