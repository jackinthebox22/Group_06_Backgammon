public class Main{

    public static void main(String[] args) {

        Board board = new Board();
        board.displayBoard();

        int[] dice = Roll.rollDice(); // rolls dice

        String[] playerNames = PlayerData.getNameFromUser();

    }
}
