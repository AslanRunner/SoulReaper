/**
 * Represents a map region with a name, danger level, and optional enemy.
 */
public class Region {
    private String name;
    private String dangerLevel;
    private Enemy enemyInRegion;
    /**
     * Creates a region without an assigned enemy.
     *
     * @param name the region name
     * @param dangerLevel the region danger level
     */
    public Region(String name, String dangerLevel) {
        this.name = name;
        this.dangerLevel = dangerLevel;
        this.enemyInRegion = null;
    }

    /**
     * Creates a region with an assigned enemy.
     *
     * @param name the region name
     * @param dangerLevel the region danger level
     * @param enemyInRegion the enemy assigned to the region
     */
    public Region(String name, String dangerLevel, Enemy enemyInRegion) {
        this.name = name;
        this.dangerLevel = dangerLevel;
        this.enemyInRegion = enemyInRegion;
    }
    /**
     * Returns the enemy assigned to this region.
     *
     * @return the enemy assigned to this region
     */
    public Enemy getEnemyInRegion() {
        return this.enemyInRegion;
    }
    /**
     * Assigns or updates the enemy occupying this region.
     *
     * @param enemyInRegion the enemy to assign
     */
    public void setEnemyInRegion(Enemy enemyInRegion) {
        this.enemyInRegion = enemyInRegion;
    }
    /**
     * Prints the region name and danger level when the player enters.
     */
    public void enter() {
        System.out.println("\n[REGION ENTRY] Entered " + this.name + " successfully.");
        System.out.println("Danger Level: " + this.dangerLevel);
    }
    /**
     * Updates the region name.
     *
     * @param name the new region name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the region name.
     *
     * @return the region name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the region danger level.
     *
     * @return the danger level
     */
    public String getDangerLevel() {
        return this.dangerLevel;
    }

    /**
     * Updates the region danger level.
     *
     * @param dangerLevel the new danger level
     */
    public void setDangerLevel(String dangerLevel) {
        this.dangerLevel = dangerLevel;
    }
    /**
     * Returns the region name and danger level as readable text.
     *
     * @return a readable region summary
     */
    @Override
    public String toString() {
        return "Region Name: " + this.name + " (" + this.dangerLevel + " Danger)";
    }
}
