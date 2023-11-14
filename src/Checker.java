public class Checker {
    private String colour;
    private int numCheckers;

    public Checker(String colour, int numCheckers) {
        this.colour = colour;
        this.numCheckers = numCheckers;
    }

    public String getColour() {
        return colour;
    } // if we have getColourCode, is this needed anymore?

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

    @Override
    public String toString() {
        return colour;
    }
}
