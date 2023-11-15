public class Bar extends Checker {
    private int redCheckers;
    private int blueCheckers;

    public Bar() {
        super("black", 0); // assuming black is the color for the bar
        this.redCheckers = 0;
        this.blueCheckers = 0;
    }

    public int getRedCheckers() {
        return redCheckers;
    }

    public int getBlueCheckers() {
        return blueCheckers;
    }

    public void addRedChecker() {
        this.redCheckers++;
    }

    public void addBlueChecker() {
        this.blueCheckers++;
    }
}
