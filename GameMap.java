/**
 * Stores the regions and enemies that make up the game map.
 */
public class GameMap {
    private Region[] regions;
    private Enemy[] enemies;
    /**
     * Creates a map with regions and matching enemies.
     *
     * @param regions the regions in the map
     * @param enemies the enemies placed in the map
     */
    public GameMap(Region[] regions, Enemy[] enemies) {
        this.regions = regions;
        this.enemies = enemies;
    }
    /**
     * Returns the region at the given index.
     *
     * @param index the region index
     * @return the region at the index
     */
    public Region getRegionAt(int index) {
        return regions[index];
    }

    /**
     * Returns the enemy at the given index.
     *
     * @param index the enemy index
     * @return the enemy at the index
     */
    public Enemy getEnemyAt(int index) {
        return enemies[index];
    }

    /**
     * Returns the number of regions in the map.
     *
     * @return the map size
     */
    public int getSize() {
        return regions.length;
    }

    /**
     * Returns the number of enemies in the map.
     *
     * @return the enemy count
     */
    public int getEnemyCount() {
        return enemies.length;
    }

    /**
     * Returns all regions in the map.
     *
     * @return the region array
     */
    public Region[] getRegions() {
        return regions;
    }
}
