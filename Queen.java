package chess;

public class Queen extends Piece {
    public Queen(int color) {
        super(color);
    }

    private String type = "Queen";
    private String symbol = "Q";

    public String toString() {
        return symbol;
    }

    public String getType() {
        return type;
    }
}
