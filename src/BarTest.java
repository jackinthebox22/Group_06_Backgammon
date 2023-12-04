import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BarTest {
	
	// 1
	@Test
	public void testGetRedCheckers() {
	    Bar bar = new Bar();
	    assertEquals(0, bar.getRedCheckers());
	}
	
	// 2
	@Test
	public void testGetBlueCheckers() {
	    Bar bar = new Bar();
	    assertEquals(0, bar.getBlueCheckers());
	}

	// 3
	@Test
	public void testAddCheckers() {
	    Bar bar = new Bar();
	    bar.addRedChecker();
	    bar.addBlueChecker();
	    assertEquals(1, bar.getRedCheckers());
	    assertEquals(1, bar.getBlueCheckers());
	}

	// 4
	@Test
	public void testRemoveCheckers() {
	    Bar bar = new Bar();
	    bar.addRedChecker();
	    bar.addBlueChecker();
	    bar.removeRedChecker();
	    bar.removeBlueChecker();
	    assertEquals(0, bar.getRedCheckers());
	    assertEquals(0, bar.getBlueCheckers());
	}

	// 5
	@Test
	public void testHasCheckers() {
	    Bar bar = new Bar();
	    assertFalse(bar.hasChecker("red"));
	    assertFalse(bar.hasCheckersOfColor("blue"));
	    bar.addRedChecker();
	    bar.addBlueChecker();
	    assertTrue(bar.hasChecker("red"));
	    assertTrue(bar.hasCheckersOfColor("blue"));
	}

}
