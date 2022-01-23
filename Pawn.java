package chess;

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
}
