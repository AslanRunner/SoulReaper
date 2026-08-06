/**
 * Represents defensive equipment that can be worn by the hero.
 */
public class Armour extends Item {
    private int defense;
    private boolean equipped;

    Armour(String itemName, int price, int defense) {
        super(itemName, price);
        this.defense = defense;
        this.equipped = false;
    }
    /**
     * Equips this armour on the hero and unequips the previous armour if needed.
     *
     * @param target the soul that should equip the armour
     */
    @Override
    public void use(BaseSoul target) {
        if (target instanceof Hero) {
            Hero hero = (Hero) target;

            if (hero.getArmour() != null && hero.getArmour().equals(this)) {
                throw new ArmourAlreadyEquippedException(getItemName() + " is already equipped.");
            }

            if (hero.getArmour() != null) {
                hero.getArmour().setEquipped(false);
            }

            hero.setArmour(this);
            this.equipped = true;
            System.out.println(getItemName() + " equipped! Defense: " + defense);
        }
    }
    /**
     * Returns the defense value used to reduce incoming damage.
     *
     * @return the defense value
     */
    public int getDefense() {
        return defense;
    }
    /**
     * Updates the armour's defense value.
     *
     * @param defense the new defense value
     */
    public void setDefense(int defense){this.defense =defense;}
    /**
     * Checks whether this armour is currently equipped.
     *
     * @return true if this armour is equipped
     */
    public boolean isEquipped() {
        return equipped;
    }
    /**
     * Marks this armour as equipped or unequipped.
     *
     * @param equipped true to mark the armour as equipped, false otherwise
     */
    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
    /**
     * Builds a readable summary of the armour's name, price, defense, and status.
     *
     * @return a readable armour summary
     */
    @Override
    public String toString() {
        String status = equipped ? " | Equipped" : "";
        return super.toString() + " | Defense: " + defense + status;
    }
}
