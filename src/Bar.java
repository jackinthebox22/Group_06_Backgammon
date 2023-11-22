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

    public void removeRedChecker() {
        if (this.redCheckers > 0) {
            this.redCheckers--;
        }
    }


    public void removeBlueChecker() {
        if (this.blueCheckers > 0) {
            this.blueCheckers--;
        }
    }

    public boolean hasChecker(String colour) {
        return (colour.equals("red") && this.redCheckers > 0) || (colour.equals("blue") && this.blueCheckers > 0);
    }

    public boolean hasCheckersOfColor(String colour) {
        return (colour.equals("red") && this.redCheckers > 0) || (colour.equals("blue") && this.blueCheckers > 0);
    }
}
