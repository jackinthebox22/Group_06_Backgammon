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
	public void testConvertSpikeToIndicesAndConvertIndicesToSpike() {
	    int spike = 15;
	    int[] indices = Board.convertSpikeToIndices(spike);
	    int convertedSpike = Board.convertIndicesToSpike(indices[0], indices[1]);
	    assertEquals(spike, convertedSpike, "Conversion between spike and indices should be reversible");
	}

	// The displayBoard, addCheckerToSpike and movePiece difficult to test in isolation

}
