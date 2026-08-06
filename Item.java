/**
 * Represents a usable item with a name and optional price.
 */
public abstract class Item implements Usable {
    /**
     * The item's display name.
     */
    protected String itemName;
    /**
     * The item's shop price.
     */
    protected int price;
    /**
     * Creates a priced item.
     *
     * @param itemName the item's name
     * @param price the item's shop price
     */
    Item(String itemName, int price) {
        this.itemName = itemName;
        this.price = price;
    }

    /**
     * Creates a reward-only item with no explicit price.
     *
     * @param itemName the item's name
     */
    Item(String itemName) {
        this.itemName = itemName;
    }
    /**
     * Returns a readable summary of the item's name and price.
     *
     * @return a readable item summary
     */
    @Override
    public String toString() {
        return itemName + " | Price: " + price;
    }
    /**
     * Compares items by name and price instead of memory address.
     *
     * @param obj the object to compare with this item
     * @return true if both objects represent the same item name and price
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Item)) return false;
        Item other = (Item) obj;
        if (this.itemName == null) {
            return other.itemName == null && this.price == other.price;
        }
        return this.itemName.equals(other.itemName) && this.price == other.price;
    }
    /**
     * Forces each item type to implement its own effect.
     *
     * @param target the soul that receives the item's effect
     */
    @Override
    public abstract void use(BaseSoul target);

    /**
     * Returns the item's name.
     *
     * @return the item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Updates the item's name.
     *
     * @param itemName the new item name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Returns the item's price.
     *
     * @return the item price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Updates the item's price.
     *
     * @param price the new item price
     */
    public void setPrice(int price) {
        this.price = price;
    }
}
