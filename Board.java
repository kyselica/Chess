import java.security.spec.ECFieldF2m;

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

    // will return boolean if the square is attacked by a piece of the same color as inputted
    public boolean isAttacked(int x, int y, int color) {
        //get next piece above the cords
        int searchx;
        int searchy;
        Piece piece = null;
        for (int i = 0; i < 8; i++) {
            searchx = x;
            searchy = y;
            if (i==0) {
                searchx = x + 1; // will check above
            } else if (i == 1) {
                searchx = x - 1; //checks below
            }  else if (i == 2) {
                searchy = y + 1; //checks right
            } else if (i==3) {
                searchy = y - 1; //checks left
            } else if (i==4) {
                searchy = y + 1; //checls up right diag
                searchx = x + 1;
            } else if (i==5) {
                searchy = y + 1; //checks down right diag
                searchx = x - 1;
            } else if (i==6) {
                searchy = y - 1; //checks up left diag
                searchx = x + 1;
            } else if (i==7) {
                searchy = y - 1; //checks down left diag
                searchx = x - 1;
            }

            //gets a piece in each direction
            while (searchx < BOARD_SIZE && searchx > 0 && searchy > 0 && searchy < BOARD_SIZE) {
                if (hasPiece(searchx, searchy)) {
                    piece = getPos(searchx, searchy);
                    break;
                }

                //progress all the checks in the direction they need to go
                if (searchx - x > 0) {
                    searchx++;
                } else if (searchx - x < 0) {
                    searchx--;
                }
                if (searchy - y > 0) {
                    searchy++;
                } else if (searchy - y < 0) {
                    searchy--;
                }
            }

            //checks if a unblocked correctly colored rook or queen is on the horizontal
            if (piece != null && piece.getColor() == color) {
                if (i <= 3 && (piece.getType().equals("Rook") || piece.getType().equals("Queen"))) {
                    return true;
                } else if (i > 3 && i <= 7 && (piece.getType().equals("Bishop") || piece.getType().equals("Queen"))) { //this checks if there is a correctly colored bishop or queen on the diag
                    return true;
                }
            }
        }

        //now we have to check if there is a pawn, knight, or king covering those squares.

        //check if there is a knight at an attacking square
        for (searchx = x + 2; searchx >= 1; searchx--) {
            for (searchy = y + 1; searchy <=2; searchy++) {
                for (int i = 0; i < 4; i++) {
                    if (i==1) {
                        searchx = -searchx;
                    } else if (i == 2) {
                        searchy = -searchy;
                    } else if (i==3) {
                        searchx = -searchx;
                        searchy = -searchy;
                    }
                    if (hasPiece(searchx, searchy) && getPos(searchx, searchy).getType().equals("Knight") && getPos(searchx, searchy).getColor() == color) {
                        return true;
                    }
                }
            }
        }

        //check if there is a pawn in an attacking square. Have to take into account that different oclored pawns attack in different directions
        if (color == 1) {
            if (hasPiece(x-1, y+1) && getPos(x-1, y+1).getType().equals("Pawn") && getPos(x-1, y+1).getColor() == 1) {
                return true;
            } else if (hasPiece(x-1, y-1) && getPos(x-1, y-1).getType().equals("Pawn") && getPos(x-1, y-1).getColor() == 1) {
                return true;
            }
        } else if (color == 0) {
            if (hasPiece(x+1, y+1) && getPos(x+1, y+1).getType().equals("Pawn") && getPos(x+1, y+1).getColor() == 0) {
                return true;
            } else if (hasPiece(x+1, y-1) && getPos(x+1, y-1).getType().equals("Pawn") && getPos(x+1, y-1).getColor() == 0) {
                return true;
            }
        }

        //finally, check if a king is attacking the square
        for (searchx = -1; searchx <=1; searchx++) {
            for (searchy = -1; searchy <= 1; searchy++) {
                //make sure the king isnt on the square
                if (searchx != x && searchy != y) {
                    if (hasPiece(searchx, searchy) && getPos(searchx, searchy).getType().equals("King") && getPos(searchx, searchy).getColor() == color){
                        return true;
                    } 
                }
            }
        }
        //if none of those return true triggers, then the square is not attacked.
        return false;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void delPiece(int x, int y) {
        board[x][y] = null;
    }

    public void setPiece(int x, int y, Piece p) {
        System.out.println(x + " " + y);
        board[x][y] = p;
        p.setX(x);
        p.setY(y);
    }

    public Piece getPos(int x, int y) {
        return board[x][y];
    }

    // will return true if the square the king of color is on is attacked by the opposite color
    public boolean isChecked(int color) {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (hasPiece(x, y) && getPos(x, y).getType().equals("King") && getPos(x, y).getColor() == color) {
                    return isAttacked(x, y, 1 - color);
                }
            }
        }
        return false;
    }

    public boolean hasPiece(int x, int y) {
        if (x < BOARD_SIZE && x >= 0 && y < BOARD_SIZE && y >=0 ) {
            return board[x][y] != null;
        }
        return false;
    }

    public void movePiece(int x, int y, int targetx, int targety) {
        System.out.println(x + " " + y + " " + targetx + " " + targety);
        setPiece(targetx, targety, getPos(x, y));
        delPiece(x,y);
    }

    public void setEqual(Piece[][] x, Piece[][] y) {
        for (int i =0; i < BOARD_SIZE;i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                x[i][j] = y[i][j];
            }
        }
    }

    public boolean legalMove(int x, int y, int targetx, int targety, int turn) {
        Piece[][] testBoard = new Piece[BOARD_SIZE][BOARD_SIZE];
        setEqual(testBoard, board);

        //if the piece can move, move it...
        if (canMove(x, y, targetx, targety, turn)) {
            movePiece(x, y, targetx, targety);
        }
        //but if that move left your king in check, then return false
        if (isChecked(turn)) {
            setEqual(board, testBoard);
            return false;
        }
        if (board == testBoard) {
            System.out.println("Crap");
        }
        setEqual(board, testBoard); // make sure we return board to previous state before moving on
        return true;
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
