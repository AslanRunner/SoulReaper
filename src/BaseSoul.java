/**
 * Represents the shared base data and behavior for souls in the game.
 */
public abstract class BaseSoul {
    /**
     * The soul's display name.
     */
    protected String name;
    /**
     * The soul's current health points.
     */
    protected int currentHp;
    /**
     * The soul's maximum health points.
     */
    protected int maxHp;
    /**
     * The soul's current map position.
     */
    protected Position currentPosition;
    /**
     * Creates a soul with a name, maximum HP, and starting position.
     *
     * @param name the soul's name
     * @param maxHp the maximum health points of the soul
     * @param currentPosition the starting position of the soul
     */
    public BaseSoul(String name, int maxHp, Position currentPosition) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.currentPosition = currentPosition;
    }
    /**
     * Checks whether the character still has health remaining.
     *
     * @return true if current HP is greater than zero, otherwise false
     */
    public boolean isAlive(){
        if(this.currentHp <= 0){
            return false;
        }
        return true;
    }
    /**
     * Reduces the character's current HP and clamps it to zero when damage is fatal.
     *
     * @param dmg the amount of damage to apply
     */
    public void takeDamage(int dmg){
        this.currentHp -= dmg;

        try {
            if (this.currentHp < 0) {
                throw new InsufficientHealthException(this.name + " HP cannot fall below 0.");
            }
        } catch (InsufficientHealthException e) {
            System.out.println("Error: " + e.getMessage());
            this.currentHp = 0;
        }
    }
    /**
     * Forces subclasses to provide their own text representation.
     *
     * @return a readable description of the soul
     */
    public abstract String toString();
    /**
     * Returns the character's name.
     *
     * @return the character's name
     */
    public String getName() {
        return this.name;
    }
    /**
     * Returns the character's current HP.
     *
     * @return the current health points
     */
    public int getCurrentHp() {
        return this.currentHp;
    }
    /**
     * Returns the character's maximum HP.
     *
     * @return the maximum health points
     */
    public int getMaxHp() {
        return this.maxHp;
    }
    /**
     * Returns the character's current position.
     *
     * @return the current position
     */
    public Position getCurrentPosition() {
        return this.currentPosition;
    }
    /**
     * Updates the character's name.
     *
     * @param name the new character name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Updates the character's current HP.
     *
     * @param currentHp the new current health value
     */
    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }
    /**
     * Updates the character's current position.
     *
     * @param currentPosition the new current position
     */
    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }
    /**
     * Updates the character's maximum HP.
     *
     * @param maxHp the new maximum health value
     */
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }


}
