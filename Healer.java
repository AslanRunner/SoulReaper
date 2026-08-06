/**
 * Represents a support Shinigami that restores HP.
 */
public class Healer extends Shinigami {
    /**
     * Creates a healer support character.
     *
     * @param name the healer name
     * @param duty the healer duty
     * @param soulPower the healer soul power
     * @param maxHp the healer maximum HP
     * @param currentPosition the healer position
     */
    Healer(String name, String duty, int soulPower, int maxHp, Position currentPosition) {
        super(name, duty, soulPower, maxHp, currentPosition);
    }
    /**
     * Uses the healer's soul power to restore the target's HP without exceeding max HP.
     *
     * @param target the soul that receives healing
     */
    @Override
    public void use(BaseSoul target) {
        int healValue = this.soulPower * 2;
        int oldHp = target.getCurrentHp();
        int newHp = oldHp + healValue;
        if (newHp > target.getMaxHp()) {
            newHp = target.getMaxHp();
        }
        target.setCurrentHp(newHp);
        System.out.println(getName() + " supported you: " + healValue
                + " healing was attempted. HP " + oldHp + " -> "
                + target.getCurrentHp() + "/" + target.getMaxHp());
    }
    /**
     * Returns the healer's name, role, and soul power.
     *
     * @return a readable healer summary
     */
    @Override
    public String toString() {
        return getName() + " | Healer | Soul Power: " + getSoulPower();
    }
}
