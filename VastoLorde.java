/**
 * Represents the final boss enemy.
 */
public class VastoLorde extends Enemy {

    /**
     * Creates the final boss enemy.
     *
     * @param name the boss name
     * @param maxHp the boss maximum HP
     * @param currentPosition the boss position
     * @param difficultyLevel the boss difficulty level
     */
    VastoLorde(String name, int maxHp, Position currentPosition, int difficultyLevel) {
        super(name, maxHp, currentPosition, difficultyLevel);
    }
    /**
     * Deals heavy damage by multiplying the difficulty level by 11.
     *
     * @param target the soul that receives the attack
     */
    @Override
    public void attack(BaseSoul target) {
        int damage = difficultyLevel * 11;
        target.takeDamage(damage);
    }
    /**
     * Guarantees a high-tier sword reward when defeated.
     *
     * @return the final boss sword reward
     */
    @Override
    public Item drop() {
        return new Sword("The Lord's Sword", 0, 200);
    }
    /**
     * Returns the Vasto Lorde label with its basic enemy stats.
     *
     * @return a readable Vasto Lorde summary
     */
    @Override
    public String toString() {
        return "Vasto Lorde\n" +super.toString();
    }
    /**
     * Quadruples the base coin reward for defeating the final boss.
     *
     * @return the final boss reward amount
     */
    @Override
    public int getReward() {
        return super.getReward()*4;
    }
}
