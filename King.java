public class King extends Piece {
    public King(int color) {
        super(color);
    }

    private String type = "King";
    private String symbol = "K";

    public String toString() {
        return symbol;
    }

    public String getType() {
        return type;
    }

    public boolean canMove(Board board, int targetx, int targety) {
        int x = getX();
        int y = getY();
        for (int searchx = -1; searchx < 2; searchx++) {
            for (int searchy = -1; searchy < 2; searchy++) {
                if (searchx == targetx && searchy == targety && (!board.hasPiece(targetx, targety) || (board.hasPiece(targetx, targety) && board.getPos(targetx,targety).getColor() != board.getPos(x,y).getColor()))) {
                    return true;
                }
            }
        }
        return false;
    }
}
