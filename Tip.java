/**
 * Represents an item that reveals information about the next living enemy.
 */
public class Tip extends Item {
    private Region[] regions;
    /**
     * Creates a tip item linked to the current game regions.
     *
     * @param itemName the tip item name
     * @param price the tip price
     * @param regions the regions used to find the next enemy
     */
    Tip(String itemName, int price, Region[] regions) {
        super(itemName, price);
        this.regions = regions;
    }
    /**
     * Finds and describes the next living enemy ahead of the hero.
     *
     * @param target the soul whose position is used as the starting point
     * @return a report about the next living enemy, or a message when none is found
     */
    public String showOneEnemy(BaseSoul target) {
        if (regions == null || regions.length == 0) {
            return "[TIP] Map information is unavailable.";
        }

        int startIndex = target.getCurrentPosition().getX() + 1;
        for (int i = startIndex; i < regions.length; i++) {
            Region currentRegion = regions[i];
            Enemy enemy = currentRegion.getEnemyInRegion();

            if (enemy != null && enemy.isAlive()) {
                return "[INTELLIGENCE REPORT]\n"
                        + "Order: " + (i + 1) + "\n"
                        + "Region: " + currentRegion.getName() + "\n"
                        + "Danger: " + currentRegion.getDangerLevel() + "\n"
                        + "Enemy: " + enemy.getName() + "\n"
                        + "HP: " + enemy.getCurrentHp() + "/" + enemy.getMaxHp() + "\n"
                        + "Difficulty: " + enemy.getDifficultyLevel();
            }
        }

        return "[TIP] There are no living enemies ahead.";
    }
    /**
     * Displays information about the upcoming enemy threat.
     *
     * @param target the soul whose position is used for the report
     */
    @Override
    public void use(BaseSoul target) {
        System.out.println("\n--- " + getItemName() + " ---");
        System.out.println(showOneEnemy(target));
        System.out.println("------------------");
    }
    /**
     * Returns the regions used by this tip.
     *
     * @return the regions used for enemy information
     */
    public Region[] getRegions() {
        return this.regions;
    }
    /**
     * Returns the item name, purpose, and price.
     *
     * @return a readable tip summary
     */
    @Override
    public String toString() {
        return getItemName() + " | Reveals the next unknown enemy | Price: " + getPrice();
    }
}
