/**
 * Represents the second enemy type in the game.
 */
public class Arrancar extends Enemy{
    /**
     * Creates an Arrancar enemy.
     *
     * @param name the Arrancar's name
     * @param maxHp the Arrancar's maximum HP
     * @param currentPosition the Arrancar's position
     * @param difficultyLevel the Arrancar's difficulty level
     */
    Arrancar(String name, int maxHp, Position currentPosition, int difficultyLevel){
        super(name, maxHp, currentPosition, difficultyLevel);
    }
    /**
     * Calculates damage from the enemy's difficulty level and applies it to the target.
     *
     * @param target the soul that receives the attack
     */
    @Override
    public void attack(BaseSoul target) {
        int damage=difficultyLevel*5;
        target.takeDamage(damage);
    }
    /**
     * Randomly decides whether the defeated enemy drops a blade or potion.
     *
     * @return a dropped item, or null when no reward drops
     */
    @Override
    public Item drop(){
        if (Math.random() < 0.7) {
            if (Math.random() < 0.6) {
                return new Sword("Arrancar Blade", 0, 50);
            } else {
                return new HealPotion("Arrancar Potion", 0, 60);
            }
        }

        return null;
    }
    /**
     * Returns the Arrancar label with its basic enemy stats.
     *
     * @return a readable Arrancar summary
     */
    @Override
    public String toString(){
        return "Arrancar\n"+ super.toString();
    }
    /**
     * Doubles the base coin reward for defeating an Arrancar.
     *
     * @return the Arrancar reward amount
     */
    @Override
    public int getReward(){
        return super.getReward() * 2;
    }

}
