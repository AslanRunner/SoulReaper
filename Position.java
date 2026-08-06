/**
 * Represents a one-dimensional position on the game map.
 */
public class Position {
    private int x;
    /**
     * Creates a default position.
     */
    Position() {
    }

    /**
     * Creates a position with the given x coordinate.
     *
     * @param x the x coordinate
     */
    Position(int x) {
        this.x = x;
    }
    /**
     * Moves the position one step forward on the x axis.
     */
    public void moveX() {
        this.x += 1;
    }

    /**
     * Updates the x coordinate.
     *
     * @param x the new x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the x coordinate.
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }
    /**
     * Returns the position as readable text.
     *
     * @return a readable position summary
     */
    @Override
    public String toString() {
        return "X: " + x;
    }
}
