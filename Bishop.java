package chess;

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
}
