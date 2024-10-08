/**
 * Game of Backgammon
 * @version 1.00 9-12-23
 * @author Jack Caldwell & Patrick Moxom
 * GitHub Names: jackinthebox & Patrick-Moxom
 */

public class Checker {

    private String colour;
    private int numCheckers;

    public Checker(String colour, int numCheckers) {
        this.colour = colour;
        this.numCheckers = numCheckers;
    }

    public String getColour() {
        return colour;
    }

    public String getColourCode(){
        String colourCode;

        if (getColour().equals("blue")) colourCode = "\u001B[34m";
        else colourCode = "\u001B[31m";

        return colourCode;
    }

    public int getNumCheckers() {
        return numCheckers;
    }

    public void addChecker() {
        this.numCheckers++;
    }

    public void setNumCheckers(int numCheckers) {
        this.numCheckers = numCheckers;
    }

    public void changeColour() {
        if (getColour().equals("blue")) {
            this.colour = "red";
        } else {
            this.colour = "blue";
        }
    }

    @Override
    public String toString() {
        return colour;
    }
}