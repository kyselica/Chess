import java.util.*;

public class Chess {
    static Scanner input = new Scanner(System.in);

    public static int[] getCords() {
        int[] output = new int[2];
        String a;

        while (true) {
            try {
                a = input.nextLine().toUpperCase();
                if (a.equals("O-O-O")) {
                    output[0] = 10;
                    break;
                } else if (a.equals("O-O")) {
                    output[0] = 20;
                    break;
                } else {
                    output[1] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(a.charAt(0));
                    output[0] = 8 - (Integer.parseInt((a.charAt(1) + "").toUpperCase()));

                    if (output[1] != -1 && output[0] != -1) {
                        break;
                    }
                }
                
            } catch (Exception e) {}
        }
        return output;
    }

    public static void main (String[] args) {
        Board game = new Board();
        game.setup();
        boolean gamegoing = true;
        int[] movepiecefrom;
        int[] movepieceto;
        int turn = 1;

        while (gamegoing) {
            
            System.out.println(game);
            System.out.println(((turn == 1) ? "White" : "Black") + "\'s Turn! Enter cords of piece you want to move. (O-O or O-O-O to castle)");
            movepiecefrom = getCords();
            if (movepiecefrom[0] == 10 && movepiecefrom[1] == -1) {
                if (!game.castleQueenside(turn)) {
                    System.out.println("That isnt a legal move!");
                } else {
                    turn = 1 - turn;
                }
            } else if (movepiecefrom[0] == 20 && movepiecefrom[1] == -1) {
                if (!game.castleKingside(turn)) {
                    System.out.println("That isnt a legal move!");
                } else {
                    turn = 1 - turn;
                }
            }  else {
                System.out.println("Enter cords of where you would like to move it.");
                movepieceto = getCords();

                if (game.legalMove(movepiecefrom[0], movepiecefrom[1], movepieceto[0], movepieceto[1], turn)) {
                    game.movePiece(movepiecefrom[0], movepiecefrom[1], movepieceto[0], movepieceto[1]);
                    turn = 1 - turn;
                } else {
                    System.out.println("That move is not legal.");
                }
    
                if (!game.hasLegalMoves(turn)) {
                    if (game.isChecked(turn)) {
                        System.out.println("Checkmate! " + ((turn == 1)  ? "Black" : "White") + " has won!");
                    } else {
                        System.out.println("Stalemate!");
                    }  
                    System.out.println(game);
                    gamegoing = false;
                }
            }
            

            
        }


    }
    
}
