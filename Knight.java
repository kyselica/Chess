public class Knight extends Piece {
    public Knight(int color) {
        super(color);
    }

    private String type = "Knight";
    private String symbol = "N";

    public String toString() {
        return symbol;
    }

    public String getType() {
        return type;
    }

    public boolean canMove(Board board, int targetx, int targety) {
        //if the move follows how a knight can move
        if ((getX() + 2 == targetx && (getY()+1 == targety || getY()-1 == targety))
        || (getX() - 2 == targetx && (getY()+1 == targety || getY()-1 == targety))
        || (getY() + 2 == targety && (getX()+1 == targetx || getX()-1 == targetx))
        || (getY() - 2 == targety && (getX()+1 == targetx || getX()-1 == targetx))) {
            //if the landing square has nothing or an enemy piece, it can move there
            if ((board.hasPiece(targetx, targety) && board.getPos(targetx, targety).getColor() != board.getPos(getX(),getY()).getColor()) || !board.hasPiece(targetx, targety)) {
                return true;
            }
        }
        return false;
    }
}
