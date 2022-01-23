package chess;

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
}
