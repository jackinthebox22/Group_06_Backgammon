import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerDataTest {

	// 1
	@Test
	public void testGetName() {
	    PlayerData player = new PlayerData("John", "blue");
	    assertEquals("John", player.getName());
	}

	// 2
	@Test
	public void testDoubleOwnership() {
	    PlayerData player = new PlayerData("John", "blue");
	    player.setdoubleOwnership(false);
	    assertFalse(player.getdoubleOwnership());
	}

	// 3
	@Test
	public void testGetPlayerColour() {
	    PlayerData player = new PlayerData("John", "blue");
	    assertEquals("blue", player.getPlayerColour());
	}

	// 4
	@Test
	public void testPlayerDirection() {
	    PlayerData player = new PlayerData("John", "blue");
	    assertEquals(-1, player.playerDirection());
	    player = new PlayerData("John", "red");
	    assertEquals(1, player.playerDirection());
	}

	// 5
	@Test
	public void testPipScore() {
	    PlayerData player = new PlayerData("John", "blue");
	    player.updatePipScore(150);
	    assertEquals(150, player.getPipScore());
	}

	// 6 
	@Test
	public void testGameScore() {
	    PlayerData player = new PlayerData("John", "blue");
	    player.updategameScore(5);
	    assertEquals(5, player.getgameScore());
	}


}
