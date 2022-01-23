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
}
