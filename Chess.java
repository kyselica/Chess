import java.util.*;

public class Chess {
    static Scanner input = new Scanner(System.in);

    public static int[] getMove(Board game, int turn) {
        String a;
        int[] output = new int[4];
        int typeIndex = -1;
        int targetx = -1;
        int targety = -1;
        int x;
        int y;
        for (int i = 0; i < output.length; i++) {
            output[i] = -1;
        }



        while (true) {
            a = input.nextLine();
            while (a.indexOf("x") != -1) {
                a = a.substring(0, a.indexOf("x")) + a.substring(a.indexOf("x") + 1, a.length());
            }
            while (a.indexOf("=") != -1) {
                a = a.substring(0, a.indexOf("=")); //get rid of everything past the =, the game board will handle promotion
            }
            if (a.equals("O-O-O")) {
                output[0] = 10;
                break;
            } else if (a.equals("O-O")) {
                output[0] = 20;
                break;
            } else {

                if (a.length() == 2) {
                    typeIndex = 5;
                    targetx = 8 - Integer.parseInt(a.substring(1,2));
                    targety = "abcdefghijklmnop".indexOf(a.substring(0,1), turn);
                } else if (a.length() == 3) {
                    if (!a.substring(0,1).toLowerCase().equals(a.substring(0, 1))) { //if first letter is uppercase
                        typeIndex = "RNBQKP".indexOf(a.charAt(0) + ""); //treat it like a Piece
                        targetx = 8 - Integer.parseInt(a.substring(2,3));
                        targety = "abcdefghijklmnop".indexOf(a.substring(1,2));

                    } else if (!a.substring(0,1).toUpperCase().equals(a.substring(0, 1))) { // if the first letter is lowercase
                        typeIndex = 5; //its a pawn
                        y = "abcdefghijklmnop".indexOf(a.substring(0, 1));
                        targety = "abcdefghijklmnop".indexOf(a.substring(1,2));
                        targetx = 8 - Integer.parseInt(a.substring(2,3));
                        for (x = 0; x < game.getBoardSize(); x++) {
                            if (game.hasPiece(x, y) && game.getPos(x, y).toString().equals("RNBQKP".charAt(typeIndex) + "")) {
                                if (game.legalMove(x, y, targetx, targety, turn)) {
                                    output[0] = x;
                                    output[1] = y;
                                    output[2] = targetx;
                                    output[3] = targety;
                                    return output;
                                }
                            }
                        }
                    }
                } else if (a.length() == 4) {
                    typeIndex = "RNBQKP".indexOf(a.charAt(0) + ""); //treat it like a Piece
                    targety = "abcdefghijklmnop".indexOf(a.substring(2,3));
                    targetx = 8 - Integer.parseInt(a.substring(3,4));
                    if ("abcdefghijklmnop".indexOf(a.substring(1,2)) == -1) {
                        x = 8 - Integer.parseInt(a.substring(1,2));
                        for (y = 0; y < game.getBoardSize(); y++) {
                            if (game.hasPiece(x, y) && game.getPos(x, y).toString().equals("RNBQKP".charAt(typeIndex) + "")) {
                                if (game.legalMove(x, y, targetx, targety, turn)) {
                                    output[0] = x;
                                    output[1] = y;
                                    output[2] = targetx;
                                    output[3] = targety;
                                    return output;
                                }
                            }
                        }
                    }else {
                        y = "abcdefghijklmnop".indexOf(a.substring(1,2));
                        for (x = 0; x < game.getBoardSize(); x++) {
                            if (game.hasPiece(x, y) && game.getPos(x, y).toString().equals("RNBQKP".charAt(typeIndex) + "")) {
                                if (game.legalMove(x, y, targetx, targety, turn)) {
                                    output[0] = x;
                                    output[1] = y;
                                    output[2] = targetx;
                                    output[3] = targety;
                                    return output;
                                }
                            }
                        }
                    }

                }

                if (typeIndex != -1) {
                    for (x = 0; x < game.getBoardSize(); x++) {
                        for (y = 0; y < game.getBoardSize(); y++) {
                            if (game.hasPiece(x, y) && game.getPos(x, y).toString().equals("RNBQKP".charAt(typeIndex) + "")) {
                                if (game.legalMove(x, y, targetx, targety, turn)) {
                                   
                                    output[0] = x;
                                    output[1] = y;
                                    output[2] = targetx;
                                    output[3] = targety;
                                    return output;
                                }
                            }
                        }
                    }
                }
                
            }
        }

        return output;
    }

    public static void main (String[] args) {
        Board game = new Board();
        game.setup();
        boolean gamegoing = true;
        int[] output;
        int turn = 1;

        while (gamegoing) {
            
            System.out.println(game);
            System.out.println(((turn == 1) ? "White" : "Black") + "\'s Turn! Enter cords of piece you want to move. (O-O or O-O-O to castle)");
            output = getMove(game, turn);


            if (output[0] == 10 && output[1] == -1) {
                if (!game.castleQueenside(turn)) {
                    System.out.println("That isnt a legal move!");
                } else {
                    turn = 1 - turn;
                }
            } else if (output[0] == 20 && output[1] == -1) {
                if (!game.castleKingside(turn)) {
                    System.out.println("That isnt a legal move!");
                } else {
                    turn = 1 - turn;
                }
            }  else {
                if (game.legalMove(output[0], output[1], output[2], output[3], turn)) {
                    game.movePiece(output[0], output[1], output[2], output[3], true);
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
