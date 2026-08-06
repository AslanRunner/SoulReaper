/**
 * Defines enemies that can drop an item reward after being defeated.
 */
public interface Lootable {
    /**
     * Defines enemies that can drop an item reward after being defeated.
     *
     * @return the item dropped by the defeated enemy, or null when no item is dropped
     */
    public Item drop();
}
