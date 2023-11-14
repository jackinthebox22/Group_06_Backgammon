import java.util.ArrayList;
public class ValidMoves {

    public static ArrayList<ArrayList<Integer>> allMoves(int[] dice, int direction, Checker[][] spikes, String colour) {
        ArrayList<ArrayList<Integer>> allMoves = new ArrayList<>();
        int allmovesindex = 0;
        int spikenumber = 0;

        int biggerdie = dice[0];
        int smallerdie = dice[1];
        if(dice[1] > dice[0]) {
            biggerdie = dice[1]; smallerdie = dice[0];
        }

        for (int row = 1; row >= 0; row--){
            for (int col = 11; col >= 0; col--){

                if(spikes[row][col] != null && spikes[row][col].getColour().equals(colour)){

                    for(int i = 0; i <= 2; i++){
                        spikenumber = Board.convertIndicesToSpike(row, col);

                        ArrayList<Integer> allmovesrow = new ArrayList<>();

                        int moveto = spikenumber + (dice[0] + dice[1])*direction;

                        if (i < 2) moveto = spikenumber + dice[i]*direction;

                        if (moveto == 25) moveto = 0;

                        int[] indices = Board.convertSpikeToIndices(moveto);
                        if(moveto >= 0 && moveto <= 24 && (moveto == 0 ||spikes[indices[0]][indices[1]] == null || spikes[indices[0]][indices[1]].getNumCheckers() == 1 || spikes[indices[0]][indices[1]].getColour().equals(colour))  ) {

                            allmovesrow.add(allmovesindex++);
                            allmovesrow.add(spikenumber);
                            allmovesrow.add(moveto);

                            if(Math.abs(spikenumber-moveto) == biggerdie) allmovesrow.add(1); //well need the bigger die smaller die values for calculations later
                            else if (Math.abs(spikenumber-moveto) == smallerdie) allmovesrow.add(0);
                            else allmovesrow.add(2);

                            allMoves.add(allmovesrow);
                        }
                    }
                }
            }
        }
        return allMoves;
    }

}
