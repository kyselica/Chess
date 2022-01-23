package chess;

public class Rook extends Piece {

    public Rook(int color) {
        super(color);
    }

    private String type = "Rook";
    private String symbol = "R";

    public String toString() {
        return symbol;
    }

    public String getType() {
        return type;
    }
}
