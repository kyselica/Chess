public class Piece {
    private int x = -1;
    private int y = -1;
    private int color;

    public Piece(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return "Unset Piece";
    }

    public boolean canMove(Board board, int targetx, int targety) {
        return false;
    }

    public Piece makeCopy() {
        Piece output = new Piece(getColor());
        output.setX(getX());
        output.setY(getY());
        return output;
    }
}
