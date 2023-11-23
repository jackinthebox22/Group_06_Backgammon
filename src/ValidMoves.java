import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;


public class ValidMoves {



    public static ArrayList<ArrayList<Integer>> allMoves(int[] dice, int direction, Checker[][] spikes, String colour, Tray[] tray) {

        ArrayList<ArrayList<Integer>> allMoves = new ArrayList<>();
        int allmovesindex = 0;
        int spikenumber;
        int moveto;

        int biggerdie = dice[0];
        int smallerdie = dice[1];
        if(dice[1] > dice[0]) {
            biggerdie = dice[1]; smallerdie = dice[0];
        }
        int samesizedie = 0;

        for (int row = 1; row >= 0; row--){
            for (int col = 11; col >= 0; col--){

                if(spikes[row][col] != null && spikes[row][col].getColour().equals(colour)){

                    for(int i = 0; i <= 2; i++){
                        spikenumber = Board.convertIndicesToSpike(row, col);

                        ArrayList<Integer> allmovesrow = new ArrayList<>();

                        if(extractValue(allMoves, allmovesindex - 1, 1) == spikenumber || extractValue(allMoves, allmovesindex - 2, 1) == spikenumber)  moveto = spikenumber + (dice[0] + dice[1])*direction;
                        else moveto = -1;

                        if (i < 2) moveto = spikenumber + dice[i]*direction;

                        if (moveto == 25) moveto = 0;

                        int[] indices = Board.convertSpikeToIndices(moveto);
                        if(moveto >= 0 && moveto <= 24 &&
                                (moveto == 0 ||spikes[indices[0]][indices[1]] == null || spikes[indices[0]][indices[1]].getNumCheckers() == 1 || spikes[indices[0]][indices[1]].getColour().equals(colour))
                                    && !(moveto == 0 && !bearOffAllowed(spikes,direction, colour, tray))) {

                            allmovesrow.add(allmovesindex++);
                            allmovesrow.add(spikenumber);
                            allmovesrow.add(moveto);

                            if(biggerdie == smallerdie && Math.abs(spikenumber-moveto) != 2*biggerdie){
                                allmovesrow.add(samesizedie++ % 2);
                            }
                            else if(Math.abs(spikenumber-moveto) == biggerdie) allmovesrow.add(1); //well need the bigger die smaller die values for calculations later
                            else if (Math.abs(spikenumber-moveto) == smallerdie) allmovesrow.add(0);
                            else allmovesrow.add(2);

                            allMoves.add(allmovesrow);
                        }
                    }
                }
            }
        }

        int spikeindex = 0;

        if(!checkNumberInColumn(allMoves, 2, 3) && checkNumberExactlyOnceInColumn(allMoves, 1, 3)){
            for(ArrayList<Integer> row : allMoves){
                if(row.get(3) == 1) spikeindex = row.get(1);

            }
            int positionOfSpike = allMoves.get(spikeindex).get(1);
            int[] indices = Board.convertSpikeToIndices(positionOfSpike);

            if(spikes[indices[0]][indices[1]].getNumCheckers() == 1)
                removeRowsWithConditions(allMoves, spikeindex, 1, 0, 3);
        }

        return allMoves;
    }

    public static ArrayList<ArrayList<Integer>> barMoves(int[] dice, int direction, Checker[][] spikes, String colour) {
        ArrayList<ArrayList<Integer>> barMoves = new ArrayList<>();
        int bar = (direction == 1) ? 0 : 25; // Bar position based on direction

        int biggerdie = dice[0];
        int smallerdie = dice[1];
        if(dice[1] > dice[0]) {
            biggerdie = dice[1]; smallerdie = dice[0];
        }
        int samesizedie = 0;
        int moveTo;
        for (int i = 0; i <= 2; i++) {

            if(extractValue(barMoves, i - 1, 1) == bar || extractValue(barMoves, i - 2, 1) == bar)  moveTo = bar + (dice[0] + dice[1])*direction;
            else moveTo = -1;

            if(i < 2) moveTo = bar + dice[i] * direction;
                // Valid moves only onto the opponent's home board
            int[] indices = Board.convertSpikeToIndices(moveTo);
            if(moveTo >= 0 && moveTo <= 24 &&
                    (moveTo == 0 ||spikes[indices[0]][indices[1]] == null || spikes[indices[0]][indices[1]].getNumCheckers() == 1 || spikes[indices[0]][indices[1]].getColour().equals(colour))) {
                ArrayList<Integer> barMoveRow = new ArrayList<>();
                barMoveRow.add(i); // Index number
                barMoveRow.add(bar); // Move from
                barMoveRow.add(moveTo); // Move to

                if (biggerdie == smallerdie && Math.abs(bar - moveTo) != 2 * biggerdie) {
                    barMoveRow.add(samesizedie++ % 2);
                } else if (Math.abs(bar - moveTo) == biggerdie)
                    barMoveRow.add(1); //well need the bigger die smaller die values for calculations later
                else if (Math.abs(bar - moveTo) == smallerdie) barMoveRow.add(0);
                else barMoveRow.add(2);
                barMoves.add(barMoveRow);
            }
        }
        return barMoves;
    }

    

    public static void printMoves(ArrayList<ArrayList<Integer>> moves) {
        int choiceNumber = 1;

        System.out.println("Move Options:");

        for (ArrayList<Integer> move : moves) {
            int moveFrom = move.get(1);
            int moveTo = move.get(2);
            int diceUsed = move.get(3);

            String diceUsedStr;
            if (diceUsed == 0) {
                diceUsedStr = "smaller";
            } else if (diceUsed == 1) {
                diceUsedStr = "bigger";
            } else {
                diceUsedStr = "both";
            }

            if (moveTo == 0){
                System.out.println(choiceNumber++ + ") Bear off from " + moveFrom + " using " + diceUsedStr + " Die");
            } else {
                System.out.println(choiceNumber++ + ") " + moveFrom + " to " + moveTo + " using " + diceUsedStr + " Die");
            }
        }
    }
    public static boolean checkNumberInColumn(ArrayList<ArrayList<Integer>> allMoves, int targetNumber, int columnIndex) {
        for (ArrayList<Integer> row : allMoves) {
            // Check if the target number appears in the specified column
            if (row.get(columnIndex) == targetNumber) {
                return true;
            }
        }
        // If the number is not found in any row of the specified column
        return false;
    }

    public static boolean checkNumberExactlyOnceInColumn(ArrayList<ArrayList<Integer>> allMoves, int targetNumber, int columnIndex) {
        int count = 0;

        for (ArrayList<Integer> row : allMoves) {
            // Check if the target number appears in the specified column
            if (row.get(columnIndex) == targetNumber) {
                count++;

                // If the number appears more than once, return false
                if (count > 1) {
                    return false;
                }
            }
        }

        // If the number appears exactly once in the specified column
        return count == 1;
    }

    public static void removeRowsWithConditions(ArrayList<ArrayList<Integer>> matrix, int value1, int column1, int value2, int column2) {

        // Check if the conditions are met for the specified columns
        // Remove the row if the conditions are met
        matrix.removeIf(row -> row.get(column1) == value1 && row.get(column2) == value2);
    }

    public static int extractValue(ArrayList<ArrayList<Integer>> matrix, int rowIndex, int columnIndex) {
        // Check if the indices are within the bounds of the matrix
        if (rowIndex >= 0 && rowIndex < matrix.size() && columnIndex >= 0 && columnIndex < matrix.get(0).size()) {
            return matrix.get(rowIndex).get(columnIndex);
        } else {
            // Handle the case where the indices are out of bounds

            return -1; // You can return a specific value or throw an exception as needed
        }
    }

    public static ArrayList<ArrayList<Integer>> removeDie(ArrayList<ArrayList<Integer>> allMoves, int dieChoice){
        allMoves.removeIf(row -> row.get(3) == dieChoice);
        return allMoves;
    }

    public static boolean bearOffAllowed(Checker[][] spike,int direction, String colour, Tray[] tray){
        boolean bearOffAllowed = false;
        int firstSpike;
        int numberOfCheckers = 0;
        if(direction == 1) firstSpike = 24;
        else firstSpike = 6;

        for(int i = firstSpike; i > firstSpike - 6; i--){
            int[] indices = Board.convertSpikeToIndices(i);
            if(spike[indices[0]][indices[1]] != null && spike[indices[0]][indices[1]].getColour().equals(colour))
                numberOfCheckers += spike[indices[0]][indices[1]].getNumCheckers();
        }
        if(tray[0].getColour().equals(colour)) numberOfCheckers += tray[0].getNumCheckers();
        else numberOfCheckers += tray[1].getNumCheckers();

        if(numberOfCheckers == 15) bearOffAllowed = true;

        return bearOffAllowed;
    }
}
