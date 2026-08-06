/**
 * Represents a hostile soul that can attack the hero and drop rewards.
 */
public abstract  class Enemy extends BaseSoul implements Attack, Lootable{
    /**
     * The enemy's difficulty level.
     */
    protected int difficultyLevel;
    /**
     * The name of the region where the enemy appears.
     */
    protected String regionName;

    /**
     * Creates an enemy with a name, health, position, and difficulty level.
     *
     * @param name the enemy's name
     * @param maxHp the enemy's maximum health
     * @param currentPosition the enemy's map position
     * @param difficultyLevel the enemy's difficulty level
     */
    Enemy(String name, int maxHp, Position currentPosition, int difficultyLevel){
        super(name, maxHp, currentPosition);
        this.difficultyLevel = difficultyLevel;
        this.regionName = "Unknown";
    }

    /**
     * Returns the enemy's difficulty level.
     *
     * @return the difficulty level
     */
    public int getDifficultyLevel(){
        return difficultyLevel;
    }
    /**
     * Updates the enemy's difficulty level.
     *
     * @param difficultyLevel the new difficulty level
     */
    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * Returns the name of the region where this enemy appears.
     *
     * @return the region name
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Updates the name of the region where this enemy appears.
     *
     * @param regionName the new region name
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    /**
     * Forces each enemy type to define its own attack behavior.
     *
     * @param target the soul that receives the attack
     */
    @Override
    public abstract void attack(BaseSoul target);
    /**
     * Forces each enemy type to define the item it may drop when defeated.
     *
     * @return the dropped item, or null if no item is dropped
     */
    public abstract Item drop();
    /**
     * Calculates the coin reward for defeating this enemy.
     *
     * @return the reward amount
     */
    public  int getReward(){
        return difficultyLevel * 10;
    }
    /**
     * Builds a readable summary of the enemy's health, difficulty, and region.
     *
     * @return a readable enemy summary
     */
    @Override
    public String toString(){
        return "HP:" + getCurrentHp() + "/" + getMaxHp()
                + " | Difficulty: " + getDifficultyLevel()
                + " | Region: " + getRegionName();
    }

}
