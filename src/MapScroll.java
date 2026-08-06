/**
 * Represents an item that reveals the full game map.
 */
public class MapScroll extends Item {
    private Region[] regions;
    /**
     * Creates a map scroll linked to the current game regions.
     *
     * @param itemName the scroll name
     * @param price the scroll price
     * @param regions the regions shown by the scroll
     */
    MapScroll(String itemName, int price, Region[] regions) {
        super(itemName, price);
        this.regions = regions;
    }
    /**
     * Prints the full map, enemy status, and the hero's current location.
     *
     * @param target the soul whose current location is highlighted
     */
    @Override
    public void use(BaseSoul target) {
        System.out.println("\n--- MAP ---");
        int currentX = target.getCurrentPosition().getX();

        for (int i = 0; i < regions.length; i++) {
            Region region = regions[i];
            Enemy enemy = region.getEnemyInRegion();
            String marker = i == currentX ? " <- Current location" : "";

            System.out.print((i + 1) + ". Region: " + region.getName()
                    + " | Danger: " + region.getDangerLevel());

            if (enemy != null) {
                String status = enemy.isAlive() ? "Alive" : "Defeated";
                System.out.print(" | Enemy: " + enemy.getName()
                        + " | HP: " + enemy.getCurrentHp() + "/" + enemy.getMaxHp()
                        + " | Status: " + status);
            } else {
                System.out.print(" | Enemy: None");
            }

            System.out.println(marker);
        }
        System.out.println("-------------\n");
    }
    /**
     * Returns the regions assigned to this map scroll.
     *
     * @return the regions shown by the scroll
     */
    public Region[] getRegions() {
        return regions;
    }
    /**
     * Returns the item name, map-reveal effect, and price.
     *
     * @return a readable map scroll summary
     */
    @Override
    public String toString() {
        return getItemName() + " | Reveals the full map | Price: " + getPrice();
    }
}
