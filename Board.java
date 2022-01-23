package chess;
public class Board {
    public final static int BOARD_SIZE = 8;
    
    private Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];

    public Board() {

        //fill the board up with pieces
        for (int i=0; i < 2; i ++) {
            board[i*7][0] = new Rook(1-i);
            board[i*7][1] = new Knight(1-i);
            board[i*7][2] = new Bishop(1-i);
            board[i*7][4] = new King(1-i);
            board[i*7][3] = new Queen(1-i);
            board[i*7][5] = new Bishop(1-i);
            board[i*7][6] = new Knight(1-i);
            board[i*7][7] = new Rook(1-i);
        }
        
        for (int i=0; i < BOARD_SIZE; i ++) {
            board[1][i] = new Pawn(1);
        }
        for (int i=0; i < BOARD_SIZE; i ++) {
            board[6][i] = new Pawn(0);
        }


        
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void delPiece(int x, int y) {
        board[x][y] = null;
    }

    public void setPiece(int x, int y, Piece p) {
        board[x][y] = p;
        p.setX(x);
        p.setY(y);
    }

    public Piece getPos(int x, int y) {
        return board[x][y];
    }

    public boolean hasPiece(int x, int y) {
        return board[x][y] != null;
    }

    //splits all the move checks into smaller comprehensible parts
    public boolean canMove(int x, int y, int targetx, int targety, int turn) {
        //Maks sure there is actually a piece there to move, and make sure its the player's piece and not their opponents
        if (!hasPiece(x, y) || getPos(x, y).getColor() != turn) {
            return false;
        }

        System.out.println("Checking moves for a " + board[x][y].getType());
        if (board[x][y].getType().equals("Rook")) {
            return rookMove(x, y, targetx, targety);
        } else if (board[x][y].getType().equals("Pawn")) {
            return pawnMove(x, y, targetx, targety);
        } else if (board[x][y].getType().equals("Bishop")) {
            return bishopMove(x, y, targetx, targety);
        } else if (board[x][y].getType().equals("Queen")) {
            return (bishopMove(x, y, targetx, targety) || rookMove(x, y, targetx, targety));
        } else if (board[x][y].getType().equals("Knight")) {
            return knightMove(x, y, targetx, targety);
        }
        else if (board[x][y].getType().equals("King")) {
            return kingMove(x,y,targetx, targety);
        }
        return false;
    }

    public boolean kingMove(int x, int y, int targetx, int targety) {
        for (int searchx = -1; searchx < 2; searchx++) {
            for (int searchy = -1; searchy < 2; searchy++) {
                if (searchx == targetx && searchy == targety && (!hasPiece(targetx, targety) || (hasPiece(targetx, targety) && getPos(targetx,targety).getColor() != getPos(x,y).getColor()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pawnMove(int x, int y, int targetx, int targety) {
        int xsearch = 0;
        for (int i = 1; i < 3; i++) {

            //if the pawns are not on their starting row, it shouldnt be able to move 2 spaces.
            if (i==2 && ((getPos(x,y).getColor() == 0 && x!=6) || (getPos(x, y).getColor() == 1 && x != 1))) {
                break;
            }
            xsearch = x + i * ((getPos(x,y).getColor() == 1) ? 1 : -1); // if black, move one way, if white move the other
            
            //if there is an oppenent piece to the top left or right of pawn, its fair game
            if (i==1 && (y+1 == targety || y-1 == targety) && xsearch == targetx && hasPiece(xsearch,targety) && getPos(xsearch, targety).getColor() != getPos(x,y).getColor()) {
                return true;
            }

            if (xsearch < BOARD_SIZE && xsearch >= 0) {
                //If there is a piece in front of the pawn, stop searching
                if (hasPiece(xsearch,y)) {
                    break;
                } else if (!hasPiece(xsearch, y) && xsearch == targetx && y == targety) { //if there is no piece, its fair game
                    return true;
                }
            }
        }
        return false;
    }

    public boolean rookMove(int x, int y, int targetx, int targety) {
        int xsearch = -1;
        int ysearch = -1;

        for (int i=0; i < 4; i++) {
            if (i==0) {
                xsearch = x+1;
                ysearch = y;
            } else if (i==1) {
                xsearch = x-1;
                ysearch = y;
            } else if (i==2) {
                xsearch = x;
                ysearch = y+1;
            } else if (i==3) {
                xsearch = x;
                ysearch = y-1;
            }

            while ((xsearch < BOARD_SIZE && xsearch >= 0) && (ysearch < BOARD_SIZE && ysearch >= 0)) {
                //stop searching down this line if there is a same colored piece
                if (hasPiece(xsearch,ysearch) && getPos(xsearch,ysearch).getColor() == getPos(x,y).getColor()) {
                    break;
                } else if (!hasPiece(xsearch, ysearch) && xsearch == targetx && ysearch == targety) { //if there is no piece, its fair game
                    return true;
                } else if (hasPiece(xsearch, ysearch) && getPos(xsearch,ysearch).getColor() != getPos(x,y).getColor()) {//if there is an enemy piece, its also fair game if it wants to move to that space, but everything past it is not possible
                    if (xsearch == targetx && ysearch == targety) {
                        return true;
                    } 
                    break;
                }


                if (i==0) {
                    xsearch++;
                } else if (i==1) {
                    xsearch--;
                } else if (i==2) {
                    ysearch++;
                } else if (i==3) {
                    ysearch--;
                }
            }


        }
        return false;
    }

    public boolean bishopMove(int x, int y, int targetx, int targety) {

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

            while ((xsearch < BOARD_SIZE && xsearch >= 0) && (ysearch < BOARD_SIZE && ysearch >= 0)) {
                //stop searching down this line if there is a same colored piece
                if (hasPiece(xsearch,ysearch) && getPos(xsearch,ysearch).getColor() == getPos(x,y).getColor()) {
                    break;
                } else if (!hasPiece(xsearch, ysearch) && xsearch == targetx && ysearch == targety) { //if there is no piece, its fair game
                    return true;
                } else if (hasPiece(xsearch, ysearch) && getPos(xsearch,ysearch).getColor() != getPos(x,y).getColor()) {//if there is an enemy piece, its also fair game if it wants to move to that space, but everything past it is not possible
                    if (xsearch == targetx && ysearch == targety) {
                        return true;
                    } 
                    break;
                }


                if (i==0) {
                    xsearch++;
                    ysearch++;
                } else if (i==1) {
                    xsearch--;
                    ysearch++;
                } else if (i==2) {
                    xsearch++;
                    ysearch--;
                } else if (i==3) {
                    ysearch--;
                    xsearch--;
                }
            }


        }
        return false;
    }

    public boolean knightMove(int x, int y, int targetx, int targety) {
        //if the move follows how a knight can move
        System.out.println(x + " to " + (x+2) + " to " + targetx);
        System.out.println(y + " to " + (y-1) + " to " + targety);
        if ((x + 2 == targetx && (y+1 == targety || y-1 == targety))
        || (x - 2 == targetx && (y+1 == targety || y-1 == targety))
        || (y + 2 == targety && (x+1 == targetx || x-1 == targetx))
        || (y - 2 == targety && (x+1 == targetx || x-1 == targetx))) {
            //if the landing square has nothing or an enemy piece, it can move there
            if ((hasPiece(targetx, targety) && getPos(targetx, targety).getColor() != getPos(x,y).getColor()) || !hasPiece(targetx, targety)) {
                System.out.println("NEIGHHH");
                return true;
            }
        }
        return false;
    }
    //Prints out the board
    public String toString() {
        String output = "";

        

        //prints the pieces on board using their toString methods
        for (int i = BOARD_SIZE-1; i >= 0; i--) {

            output += (i+1) + " ";

            for (int j = 0; j < BOARD_SIZE; j++) {
                //if there is no piece, add " - "
                if (hasPiece(i,j)) {
                    output += (getPos(i,j).getColor() == 1) ?  " " + getPos(i,j) + " " : " " + getPos(i,j) + "*";
                } else {
                    output += " - ";
                }
            }
            output += "\n";
        }

        //prints the numbers across top
        output += "  ";
        for (int i = 0; i < BOARD_SIZE; i++) {
            output += " " + "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(i) + " "; //add letters on side
        }
        output += "\n";

        return output;
    }
}
