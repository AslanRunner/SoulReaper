import java.util.ArrayList;

/**
 * Represents the shop where the hero can buy items.
 */
public class Shop {
    private ArrayList<Item> itemList;
    /**
     * Creates an empty shop item list.
     */
    Shop() {
        this.itemList = new ArrayList<>();
    }
    /**
     * Prints all items currently available in the shop.
     */
    public void displayShop() {
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            System.out.println((i + 1) + ". " + item);
        }
    }
    /**
     * Adds an item to the shop.
     *
     * @param item the item to add to the shop list
     */
    public void addItem(Item item) {
        itemList.add(item);
    }
    /**
     * Validates the selected product, handles payment, and adds a copy to the player's inventory.
     *
     * @param player the hero buying the item
     * @param selection the selected shop item number
     * @throws InsufficientBudgetException if the hero cannot pay for the item
     */
    public void sellItem(Hero player, int selection) throws InsufficientBudgetException {
        int index = selection - 1;

        if (index < 0 || index >= itemList.size()) {
            throw new InvalidSelectionException("Invalid item selection!");
        }

        Item selectedItem = itemList.get(index);
        Item itemToGive = null;

        if (selectedItem instanceof HealPotion) {
            HealPotion potion = (HealPotion) selectedItem;
            itemToGive = new HealPotion(potion.getItemName(), potion.getPrice(), potion.getHealValue());
        } else if (selectedItem instanceof Sword) {
            Sword sword = (Sword) selectedItem;
            itemToGive = new Sword(sword.getItemName(), sword.getPrice(), sword.getDamage());
        } else if (selectedItem instanceof Armour) {
            Armour armour = (Armour) selectedItem;
            itemToGive = new Armour(armour.getItemName(), armour.getPrice(), armour.getDefense());
        } else if (selectedItem instanceof Tip) {
            Tip tip = (Tip) selectedItem;
            itemToGive = new Tip(tip.getItemName(), tip.getPrice(), tip.getRegions());
        } else if (selectedItem instanceof MapScroll) {
            MapScroll mapScroll = (MapScroll) selectedItem;
            itemToGive = new MapScroll(mapScroll.getItemName(), mapScroll.getPrice(), mapScroll.getRegions());
        }

        if (itemToGive == null) {
            throw new InsufficientBudgetException("This item cannot be purchased right now!");
        }

        player.spendBudget(selectedItem.getPrice());
        boolean added = player.addItemToInventory(itemToGive);

        if (!added) {
            player.addBudget(selectedItem.getPrice());
            System.out.println(selectedItem.getItemName() + " could not be purchased. Your money was refunded.");
            return;
        }

        System.out.println(selectedItem.getItemName() + " bought successfully!");
    }
}
