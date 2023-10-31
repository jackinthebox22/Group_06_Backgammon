public class Checker {
    private String color;
    private int numCheckers;

    public Checker(String color, int numCheckers) {
        this.color = color;
        this.numCheckers = numCheckers;
    }

    public String getColor() {
        return color;
    }

    public int getNumCheckers() {
        return numCheckers;
    }

    public void setNumCheckers(int numCheckers) {
        this.numCheckers = numCheckers;
    }

    @Override
    public String toString() {
        return color;
    }
}
