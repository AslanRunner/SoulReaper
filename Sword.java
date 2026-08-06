/**
 * Represents a weapon item that can be equipped by the hero.
 */
public class Sword extends Item {
    private int damage;
    private boolean equipped;
    /**
     * Creates a sword that can be sold in the shop.
     *
     * @param itemName the sword name
     * @param price the sword price
     * @param damage the sword damage value
     */
    Sword(String itemName, int price, int damage) {
        super(itemName, price);
        this.damage = damage;
        this.equipped = false;
    }

    /**
     * Creates a reward sword without an explicit price.
     *
     * @param itemName the sword name
     * @param damage the sword damage value
     */
    Sword(String itemName, int damage) {
        super(itemName);
        this.damage = damage;
        this.equipped = false;
    }
    /**
     * Returns the sword's stats and indicates whether it is currently equipped.
     *
     * @return a readable sword summary
     */
    @Override
    public String toString() {
        String status = this.equipped ? " | Equipped" : "";
        return super.toString() + " | Damage: " + this.damage + status;
    }
    /**
     * Equips this sword on the hero and deactivates the previously used sword.
     *
     * @param target the soul that should equip the sword
     */
    @Override
    public void use(BaseSoul target) {
        if (target instanceof Hero) {
            Hero hero = (Hero) target;

            if (hero.getSword() != null && hero.getSword().equals(this)) {
                throw new SwordAlreadyEquippedException(getItemName() + " is already equipped.");
            }

            if (hero.getSword() != null) {
                hero.getSword().setEquipped(false);
            }

            hero.setSword(this);
            this.equipped = true;
            System.out.println(getItemName() + " equipped!");
        }
    }
    /**
     * Returns the sword's damage value.
     *
     * @return the damage value
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Updates the sword's damage value.
     *
     * @param dmg the new damage value
     */
    public void setDamage(int dmg) {
        this.damage = dmg;
    }
    /**
     * Checks whether this sword is currently equipped.
     *
     * @return true if this sword is equipped
     */
    public boolean isEquipped() {
        return equipped;
    }
    /**
     * Marks this sword as equipped or unequipped.
     *
     * @param equipped true to mark the sword as equipped, false otherwise
     */
    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
}
