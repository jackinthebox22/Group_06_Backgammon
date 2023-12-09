import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
    private static final InputStream originalSystemIn = System.in;

    @BeforeAll
    public static void setUp() {
        // Redirect System.in to provide specific input for the tests
        String simulatedInput = "Patrick\nJack\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
    }

    @AfterAll
    public static void tearDown() {
        // Reset System.in to its original state after all tests
        System.setIn(originalSystemIn);
    }

    @Test
    public void testGetNamesFromUser() {
        // Execute the method that reads user names
        String[] result = PlayerData.getNamesFromUser();

        // Reset System.in to its original state
        System.setIn(originalSystemIn);

        // Assert that the result matches the simulated input
        String[] expected = {"Patrick", "Jack"};
        assertArrayEquals(expected, result);
    }
}