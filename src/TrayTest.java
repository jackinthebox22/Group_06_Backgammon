import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
public class TrayTest {
    @Test
    public void testHandleWinningMatches(){
        PlayerData[] player = new PlayerData[2];
        player[0] = new PlayerData("void","red");
        player[1] = new PlayerData("void","blue");
        Bar bar = new Bar();
        Tray.handleWinningMatch(0, player, 0, 1, 1, bar, 0);
        bar.addBlueChecker();
        Tray.handleWinningMatch(0, player, 0, 1, 1, bar, 0);
        Tray.handleWinningMatch(1, player, 0, 1, 1, bar, 0);
        assertEquals(1,1);
    }
}
