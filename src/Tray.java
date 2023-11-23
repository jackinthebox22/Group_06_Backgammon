public class Tray extends Checker{
    private final String colour;

    public Tray(String colour, int numCheckers) {
        super(colour, numCheckers);
        int numCheckers1 = 0;
        this.colour = colour;
    }

    public void checkWinner() {

        if (getNumCheckers() == 15) {
            System.out.println("Congratulations! The tray with color " + colour + " has 15 checkers and is the winner!");
            System.exit(0);
        }
    }

}
