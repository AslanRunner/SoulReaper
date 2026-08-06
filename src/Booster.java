/**
 * Represents a support Shinigami that increases sword damage.
 */
public class Booster extends Shinigami {
    /**
     * Creates a Booster support character.
     *
     * @param name the booster name
     * @param duty the booster duty
     * @param soulPower the booster soul power
     * @param maxHp the booster maximum HP
     * @param currentPosition the booster position
     */
    Booster(String name, String duty, int soulPower, int maxHp, Position currentPosition) {
        super(name, duty, soulPower, maxHp, currentPosition);
    }
    /**
     * Increases the hero's equipped sword damage using the booster's soul power.
     *
     * @param target the soul that receives the damage boost
     */
    @Override
    public void use(BaseSoul target) {
        if (target instanceof Hero) {
            Hero hero = (Hero) target;
            Sword sword = hero.getSword();
            if (sword != null) {
                int oldDamage = sword.getDamage();
                int boost = soulPower * 2;
                sword.setDamage(oldDamage + boost);
                System.out.println(getName() + " supported you: " + sword.getItemName()
                        + " damage increased by +" + boost + " (" + oldDamage + " -> "
                        + sword.getDamage() + ").");
            }
        }
    }
    /**
     * Returns the booster's name, role, and soul power.
     *
     * @return a readable booster summary
     */
    @Override
    public String toString() {
        return getName() + " | Booster | Soul Power: " + getSoulPower();
    }
}
