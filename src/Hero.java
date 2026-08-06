import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the main player character with inventory, budget, sword, and armour.
 */
public class Hero extends Shinigami implements Attack {
    private int budget;
    private ArrayList<Item> inventory;
    private Sword sword;
    private Armour armour;

    /**
     * Creates the hero with starting stats, budget, position, and default sword.
     *
     * @param name the hero name
     * @param duty the hero duty
     * @param soulPower the hero soul power
     * @param maxHp the hero maximum HP
     * @param budget the starting coin budget
     * @param currentPosition the starting map position
     */
    Hero(String name, String duty, int soulPower, int maxHp, int budget, Position currentPosition) {
        super(name, duty, soulPower, maxHp, currentPosition);
        this.budget = budget;
        this.inventory = new ArrayList<>();
        this.sword = new Sword("Normal Shinigami Sword", 0, 25);
        this.sword.setEquipped(true);
        this.armour = null;
        this.inventory.add(this.sword);
    }
    /**
     * Increases the hero's coin budget.
     *
     * @param amount the amount of coins to add
     */
    public void addBudget(int amount) {
        this.budget += amount;
    }
    /**
     * Deducts coins from the hero's budget and throws an exception if funds are insufficient.
     *
     * @param price the amount of coins to spend
     * @throws InsufficientBudgetException if the hero does not have enough coins
     */
    public void spendBudget(int price) throws InsufficientBudgetException {
        int newBudget = this.budget - price;
        if (newBudget < 0) {
            throw new InsufficientBudgetException("You don't have enough money!");
        }
        this.budget = newBudget;
    }
    /**
     * Returns the hero's equipped sword.
     *
     * @return the equipped sword
     */
    public Sword getSword() {
        return this.sword;
    }

    /**
     * Returns the hero's equipped armour.
     *
     * @return the equipped armour, or null if no armour is equipped
     */
    public Armour getArmour() {
        return armour;
    }
    /**
     * Returns the hero's basic text label.
     *
     * @return the hero label
     */
    @Override
    public String toString() {
        return "Hero";
    }
    /**
     * Deals damage with the equipped sword and has a 20% chance to land a critical hit.
     *
     * @param target the soul that receives the attack
     */
    @Override
    public void attack(BaseSoul target) {
        int dmg;
        if (this.sword != null) {
            if (Math.random() < 0.20) {
                dmg = sword.getDamage() * 2;
                System.out.println("CRITIC DAMAGE!!");
            } else {
                dmg = sword.getDamage();
            }
        } else {
            dmg = this.getSoulPower() / 5;
        }
        target.takeDamage(dmg);
    }
    /**
     * Reduces incoming damage with armour defense and clamps HP to zero when needed.
     *
     * @param dmg the incoming damage amount
     */
    @Override
    public void takeDamage(int dmg) {
        if (currentHp <= 0) {
            throw new InsufficientHealthException("Hero is already unable to fight.");
        }

        int reducedDamage = dmg - this.getDefense();
        if (reducedDamage < 0) {
            reducedDamage = 0;
        }

        this.currentHp -= reducedDamage;

        try {
            if (this.currentHp < 0) {
                throw new InsufficientHealthException("Hero HP cannot fall below 0.");
            }
        } catch (InsufficientHealthException e) {
            System.out.println("Error: " + e.getMessage());
            this.currentHp = 0;
        }
    }
    /**
     * Uses the selected item and removes it from inventory if it is consumable.
     *
     * @param item the item selected from the inventory
     * @return true when the item use action finishes
     */
    public boolean useItem(Item item) {
        if (!this.inventory.contains(item)) {
            throw new InvalidSelectionException("This item is not in your inventory!");
        }

        try {
            item.use(this);
        } catch (MaxHpExceededException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (item instanceof HealPotion || item instanceof Tip) {
                this.inventory.remove(item);
            }
        }
        return true;
    }
    /**
     * Displays the inventory and processes the player's selected item action.
     *
     * @param scanner the scanner used to read the player's selection
     * @return true if an item is used successfully, otherwise false
     */
    public boolean chooseAndUseItem(Scanner scanner) {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return false;
        }

        displayInventory();
        System.out.println("Enter 0 if you do not want to use an item.");
        try {
            int selection = InputHelper.readInt(scanner, "Item number to use: ");
            if (selection == 0) {
                return false;
            }

            int index = selection - 1;
            if (index < 0 || index >= inventory.size()) {
                throw new InvalidSelectionException("Invalid inventory selection!");
            }

            return useItem(inventory.get(index));

        } catch (InvalidSelectionException e) {
            System.out.println("Selection error: " + e.getMessage());
            return false;

        } catch (SwordAlreadyEquippedException e) {
            System.out.println("Sword error: " + e.getMessage());
            return false;

        } catch (ArmourAlreadyEquippedException e) {
            System.out.println("Armour error: " + e.getMessage());
            return false;

        } finally {
            System.out.println("Inventory action finished.");
        }
    }
    /**
     * Adds an item to inventory and prevents duplicate gear or map scrolls.
     *
     * @param item the item to add
     * @return true if the item is added, otherwise false
     */
    public boolean addItemToInventory(Item item) {
        if (item == null) return false;

        if (item instanceof Sword || item instanceof Armour || item instanceof MapScroll) {
            for (Item inventoryItem : inventory) {
                if (inventoryItem.equals(item)) {
                    System.out.println(item.getItemName() + " is already in your inventory.");
                    return false;
                }
            }
        }

        this.inventory.add(item);
        System.out.println(item.getItemName() + " was added to your inventory.");
        return true;
    }
    /**
     * Adds a dropped reward to inventory while preventing duplicate map scrolls.
     *
     * @param item the dropped reward item
     */
    public void addGiftToInventory(Item item) {
        if (item == null) return;

        if (item instanceof MapScroll) {
            for (Item inventoryItem : inventory) {
                if (inventoryItem instanceof MapScroll) {
                    System.out.println("The map is already in your inventory.");
                    return;
                }
            }
        }

        this.inventory.add(item);
        System.out.println("Your gift was added to your inventory.");
    }
    /**
     * Returns the hero's current coin budget.
     *
     * @return the current budget
     */
    public int getBudget() {
        return this.budget;
    }

    /**
     * Updates the hero's coin budget.
     *
     * @param budget the new budget
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }
    /**
     * Equips a new sword and marks the previous sword as unequipped.
     *
     * @param sword the sword to equip
     */
    public void setSword(Sword sword) {
        if (this.sword == sword && sword != null && sword.isEquipped()) {
            throw new SwordAlreadyEquippedException(sword.getItemName() + " is already your active sword.");
        }

        if (this.sword != null) {
            this.sword.setEquipped(false);
        }
        this.sword = sword;
    }
    /**
     * Equips new armour and marks the previous armour as unequipped.
     *
     * @param armour the armour to equip
     */
    public void setArmour(Armour armour) {
        if (this.armour == armour && armour != null) {
            throw new ArmourAlreadyEquippedException(armour.getItemName() + " is already your active armour.");
        }
        if (this.armour != null) {
            this.armour.setEquipped(false);
        }
        this.armour = armour;
    }

    /**
     * Returns the hero's current defense value from equipped armour.
     *
     * @return the defense value, or zero when no armour is equipped
     */
    public int getDefense() {
        if(this.armour != null){
            return this.armour.getDefense();
        } else{
            return 0;
        }
    }
    /**
     * Displays all inventory items and the currently equipped gear.
     */
    public void displayInventory() {
        System.out.println("\n--- INVENTORY ---");
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            System.out.println((i + 1) + ". " + item);
        }
        System.out.println("Active sword: " + (sword != null ? sword.getItemName() + " (" + sword.getDamage() + " damage)" : "None"));
        System.out.println("Active armour: " + (armour != null ? armour.getItemName() + " (" + armour.getDefense() + " defense)" : "None"));
    }
}
