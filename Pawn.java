public class Pawn extends Piece {

    public Pawn(int color) {
        super(color);
    }

    private String type = "Pawn";
    private String symbol = "P";

    public String toString() {
        return symbol;
    }

    public String getType() {
        return type;
    }

    public boolean canMove(Board board, int targetx, int targety) {
        int x = getX();
        int y = getY();

        int xsearch = 0;
        for (int i = 1; i < 3; i++) {

            //if the pawns are not on their starting row, it shouldnt be able to move 2 spaces.
            if (i==2 && ((board.getPos(x,y).getColor() == 0 && x!=6) || (board.getPos(x, y).getColor() == 1 && x != 1))) {
                break;
            }
            xsearch = x + i * ((board.getPos(x,y).getColor() == 1) ? 1 : -1); // if black, move one way, if white move the other
            
            //if there is an oppenent piece to the top left or right of pawn, its fair game
            if (i==1 && (y+1 == targety || y-1 == targety) && xsearch == targetx && board.hasPiece(xsearch,targety) && board.getPos(xsearch, targety).getColor() != board.getPos(x,y).getColor()) {
                return true;
            }

            if (xsearch < board.getBoardSize() && xsearch >= 0) {
                //If there is a piece in front of the pawn, stop searching
                if (board.hasPiece(xsearch,y)) {
                    break;
                } else if (!board.hasPiece(xsearch, y) && xsearch == targetx && y == targety) { //if there is no piece, its fair game
                    return true;
                }
            }
        }
        return false;
    }
}
