public class Bishop extends Piece {
    public Bishop(int color) {
        super(color);
    }

    private String type = "Bishop";
    private String symbol = "B";

    public String toString() {
        return symbol;
    }

    public String getType() {
        return type;
    }

    public boolean canMove(Board board, int targetx, int targety) {

        int x = getX();
        int y = getY();

        int xsearch = -1;
        int ysearch = -1;

        for (int i=0; i < 4; i++) {
            if (i==0) {
                xsearch = x+1;
                ysearch = y+1;
            } else if (i==1) {
                xsearch = x-1;
                ysearch = y+1;
            } else if (i==2) {
                xsearch = x+1;
                ysearch = y-1;
            } else if (i==3) {
                xsearch = x-1;
                ysearch = y-1;
            }

            while ((xsearch < board.getBoardSize() && xsearch >= 0) && (ysearch < board.getBoardSize() && ysearch >= 0)) {
                //stop searching down this line if there is a same colored piece
                if (board.hasPiece(xsearch,ysearch) && board.getPos(xsearch,ysearch).getColor() == board.getPos(x,y).getColor()) {
                    break;
                } else if (!board.hasPiece(xsearch, ysearch) && xsearch == targetx && ysearch == targety) { //if there is no piece, its fair game
                    return true;
                } else if (board.hasPiece(xsearch, ysearch) && board.getPos(xsearch,ysearch).getColor() != board.getPos(x,y).getColor()) {//if there is an enemy piece, its also fair game if it wants to move to that space, but everything past it is not possible
                    if (xsearch == targetx && ysearch == targety) {
                        return true;
                    } 
                    break;
                }


                if (xsearch - x > 0) {
                    xsearch++;
                } else if (xsearch - x < 0) {
                    xsearch--;
                }
                if (ysearch - y > 0) {
                    ysearch++;
                } else if (ysearch - y < 0) {
                    ysearch--;
                }
            }


        }
        return false;
    }

    public Bishop makeCopy() {
        Bishop output = new Bishop(getColor());
        output.setX(getX());
        output.setY(getY());
        return output;
    }
}
