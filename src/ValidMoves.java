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

                        int moveto = spikenumber + (dice[0] + dice[1])*direction;

                        if (i < 2) moveto = spikenumber + dice[i]*direction;

                        if (moveto == 25) moveto = 0;

                        if(moveto >= 0 && moveto <= 24) {
                            allmovesrow.add(allmovesindex++);
                            allmovesrow.add(spikenumber);
                            allmovesrow.add(moveto);

                            allMoves.add(allmovesrow);
                        }
                    }
                }
            }
        }

        return allMoves;
    }
}
