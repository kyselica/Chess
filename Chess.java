package chess;
import java.util.*;

public class Chess {
    static Scanner input = new Scanner(System.in);

    public static int[] getCords() {
        int[] output = new int[2];
        String a;

        while (true) {
            try {
                a = input.nextLine();
                output[1] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(a.charAt(0));
                output[0] = Integer.parseInt((a.charAt(1) + "").toUpperCase()) - 1;

                if (output[1] != -1 && output[0] != -1) {
                    break;
                }
            } catch (Exception e) {}
        }
        return output;
    }

    public static void main(String[] args) {
        Board game = new Board();
        boolean gamegoing = true;
        int[] movepiecefrom;
        int[] movepieceto;
        int turn = 1;

        while (gamegoing) {
            System.out.println(game);
            System.out.println(((turn == 1) ? "White" : "Black") + "\'s Turn! Enter cords of piece you want to move.");
            movepiecefrom = getCords();
            System.out.println("Enter cords of where you would like to move it.");
            movepieceto = getCords();

            if (game.canMove(movepiecefrom[0], movepiecefrom[1], movepieceto[0], movepieceto[1], turn)) {
                game.setPiece(movepieceto[0], movepieceto[1], game.getPos(movepiecefrom[0], movepiecefrom[1]));
                game.delPiece(movepiecefrom[0],movepiecefrom[1]);
                turn = 1-turn;
            } else {
                System.out.println("Please enter a valid move.");
            }
        }


    }
    
}
