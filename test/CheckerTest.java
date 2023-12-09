import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CheckerTest {

    @Test
    public void testGetColour() {
        Checker checker = new Checker("red", 5);
        assertEquals("red", checker.getColour());
    }

    @Test
    public void testGetNumCheckers() {
        Checker checker = new Checker("blue", 5);
        assertEquals(5, checker.getNumCheckers());
    }

    @Test
    public void testAddChecker() {
        Checker checker = new Checker("blue", 5);
        checker.addChecker();
        assertEquals(6, checker.getNumCheckers());
    }

    @Test
    public void testSetNumCheckers() {
        Checker checker = new Checker("blue", 5);
        checker.setNumCheckers(7);
        assertEquals(7, checker.getNumCheckers());
    }

    @Test
    public void testChangeColour() {
        Checker checker = new Checker("blue", 5);
        checker.changeColour();
        assertEquals("red", checker.getColour());
    }

    @Test
    public void testGetColourCode() {
        Checker checker = new Checker("blue", 5);
        assertEquals("\u001B[34m", checker.getColourCode());
    }

    @Test
    public void testToString() {
        Checker checker = new Checker("blue", 5);
        assertEquals("blue", checker.toString());
    }



}