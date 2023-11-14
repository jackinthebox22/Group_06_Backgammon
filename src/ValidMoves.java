import java.util.ArrayList;
public class ValidMoves {

    public static ArrayList<ArrayList<Integer>> allMoves(int[] dice, int direction, Checker[][] spikes, String colour) {
        ArrayList<ArrayList<Integer>> allMoves = new ArrayList<>();
        int allmovesindex = 0;
        int spikenumber = 0;
        for (int row = 1; row >= 0; row--){
            for (int col = 11; col >= 0; col--){

                if(spikes[row][col] != null && spikes[row][col].getColour().equals(colour)){
                    for(int i = 0; i <= 2; i++){
                        spikenumber = Board.convertIndicesToSpike(row, col);
                        ArrayList<Integer> allmovesrow = new ArrayList<>();
                        allmovesrow.add(allmovesindex++);
                        allmovesrow.add(spikenumber);

                        int moveto = spikenumber + dice[0] + dice[1];

                        if (i < 2) moveto = spikenumber + dice[i];

                        if (moveto < 1 || moveto > 24) moveto = 0;
                        allmovesrow.add(moveto);

                        allMoves.add(allmovesrow);
                    }
                }
            }
        }

        return allMoves;
    }
}
