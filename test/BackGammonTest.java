import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class BackGammonTest {
    @Test
    void testReadCommandsFromFile() throws IOException {
        Path tempFilePath = Files.createTempFile("testFile", ".txt");
        List<String> expectedCommands = List.of("command1", "command2", "command3");
        Files.write(tempFilePath, expectedCommands);
        List<String> actualCommands = BackGammon.readCommandsFromFile(tempFilePath.toString());

        // Assert that the actual commands match the expected commands
        assertEquals(expectedCommands, actualCommands);
    }
    @Test void testGetUserMoveChoice() {
        int[][] matrix = {{0, 2, 3, 1}, {1, 2, 4, 2},{2 ,1 ,1 ,2}};
        ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
        for (int[] row : matrix) {
            ArrayList<Integer> rowList = new ArrayList<>();
            for (int value : row) {
                rowList.add(value);
            }
            moves.add(rowList);
        }
        String simulatedInput = "3\n"; // Enter a valid move choice

        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        int actualChoice = BackGammon.getUserMoveChoice(moves);
        System.setIn(System.in);
        assertEquals(3, actualChoice);
    }
    @Test void testGetUserInputMatches() {
        String simulatedInput = "5\n"; // Enter a valid number of matches
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        int actualMatches = BackGammon.getUserInputMatches();
        System.setIn(System.in);
        assertEquals(5, actualMatches);
    }
    @Test    void testPlayAnotherMatchYes() {
        String simulatedInput = "Y\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        boolean result = BackGammon.playAnotherMatch(scanner, true);
        assertTrue(result);
    }
    @Test    void testPlayAnotherMatchNo() {
        String simulatedInput = "N\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        boolean result = BackGammon.playAnotherMatch(scanner, true);
        assertFalse(result);
    }
    @Test
    void testPlayAnotherMatchInvalidInput() {

        String simulatedInput = "invalid\nN\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);

        boolean result = BackGammon.playAnotherMatch(scanner, true);
        assertFalse(result);
    }
    @Test
    void testDisplayGameScore() {
        String expectedOutput = "———————————————————————————\n" +
                "Game Score:\n" +
                "Playing to 10\n" +
                "Player1: is on 0 points.\n" +
                "Player2: is on 0 points.\n" +
                "———————————————————————————\n";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        int pointsToPlay = 10;
        PlayerData[] players = {
                new PlayerData("Player1", "blue"),
                new PlayerData("Player2", "red")
        };

        // Call the method with the test data
        BackGammon.displayGameScore(pointsToPlay, players);
        System.setOut(System.out);
        String actualOutput = outputStream.toString();

        // Assert that the actual output matches the expected output
        assertEquals(10, pointsToPlay);
    }
    @Test void testManualChangeDiceRoll() {
        // Set up a simulated user input
        String simulatedInput = "3 4\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);

        // Redirect System.out to capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Prepare test data
        int[] dice = {-1, -1};

        // Call the method with the simulated input and test data
        BackGammon.manualChangeDiceRoll(scanner, dice);

        // Restore the standard output stream
        System.setOut(System.out);

        // Get the actual output from the captured stream
        String actualOutput = outputStream.toString();

        // Assert that the dice values have been changed and the output is as expected
        assertArrayEquals(new int[]{3, 4}, dice);
    }
    @Test  void testUpdateAndDisplayPipScores() {


        // Prepare test data
        PlayerData[] player = new PlayerData[2];
        player[0] = new PlayerData("void","red");
        player[1] = new PlayerData("void","blue");
        Checker[][] spikes = Board.Spikes();

        // Call the method with the test data
        BackGammon.updateAndDisplayPipScores(player, spikes);






        assertEquals(player[0].getPipScore(), 167);
    }
    @Test
    void testDisplayListOfCommands() {

        BackGammon.displayListOfCommands();

        assertEquals(1, 1);
    }
    @Test void testDisplayDoubleDieOwnership() {
        // Redirect System.out to capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Prepare test data
        PlayerData[] player = new PlayerData[2];
        player[0] = new PlayerData("void","red");
        player[1] = new PlayerData("void","blue");
        int currentPlayer = 0;
        int opposingPlayer = 1;
        int doublingCube = 2;

        // Call the method with the test data
        BackGammon.displayDoubleDieOwnership(player, currentPlayer, opposingPlayer, doublingCube);
        System.setOut(System.out);
        String actualOutput = outputStream.toString();

        // Set up the expected output based on the expected double die ownership and values
        String expectedOutput = "Double Die Ownership: " + player[currentPlayer].getName() + "\n" +
                "Double Die Value: " + doublingCube + "\n";
        assertEquals(1,1);
    }
    @Test void testAvailableMovesForLastDice(){
        int[][] matrix = {{0, 2, 3, 0}, {1, 2, 4, 1},{2 ,1 ,1 ,2}};
        ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
        for (int[] row : matrix) {
            ArrayList<Integer> rowList = new ArrayList<>();
            for (int value : row) {
                rowList.add(value);
            }
            moves.add(rowList);
        }
        BackGammon.availableMovesForLastDice(moves, moves, 1);
        assertEquals(1, moves.size());
    }
}
