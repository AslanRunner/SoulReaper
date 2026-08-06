/**
 * Represents a consumable item that restores health.
 */
public class HealPotion extends Item {
    private int healValue;

    /**
     * Creates a priced healing potion.
     *
     * @param itemName the potion name
     * @param price the potion price
     * @param healValue the amount of HP restored
     */
    HealPotion(String itemName, int price, int healValue) {
        super(itemName, price);
        this.healValue = healValue;
    }

    /**
     * Creates a reward healing potion without an explicit price.
     *
     * @param itemName the potion name
     * @param healValue the amount of HP restored
     */
    HealPotion(String itemName, int healValue) {
        super(itemName);
        this.healValue = healValue;
    }
    /**
     * Restores the target's HP and caps the result at the target's maximum HP.
     *
     * @param target the soul whose HP will be restored
     */
    @Override
    public void use(BaseSoul target) {
        int currentHp = target.getCurrentHp();
        int newHp = currentHp + healValue;

        if (newHp > target.getMaxHp()) {
            target.setCurrentHp(target.getMaxHp());
            throw new MaxHpExceededException(getItemName()
                    + " exceeded max HP. Your HP was capped at: "
                    + target.getCurrentHp() + "/" + target.getMaxHp());
        }

        target.setCurrentHp(newHp);
        System.out.println(getItemName() + " used! HP: " + target.getCurrentHp() + "/" + target.getMaxHp());
    }
    /**
     * Returns the potion's healing value.
     *
     * @return the healing value
     */
    public int getHealValue() {
        return healValue;
    }

    /**
     * Updates the potion's healing value.
     *
     * @param healValue the new healing value
     */
    public void setHealValue(int healValue) {
        this.healValue = healValue;
    }
    /**
     * Returns a readable summary of the potion's name, healing value, and price.
     *
     * @return a readable potion summary
     */
    @Override
    public String toString() {
        return getItemName() + " | Heal: " + healValue + " | Price: " + getPrice();
    }
}
