/**
 * Represents the first enemy type in the game.
 */
public class Hollow extends Enemy{
    /**
     * Creates a Hollow enemy.
     *
     * @param name the Hollow's name
     * @param maxHp the Hollow's maximum HP
     * @param currentPosition the Hollow's position
     * @param difficultyLevel the Hollow's difficulty level
     */
    Hollow(String name, int maxHp, Position currentPosition, int difficultyLevel){
        super(name, maxHp, currentPosition, difficultyLevel);
    }
    /**
     * Calculates damage by multiplying the difficulty level by 3.
     *
     * @param target the soul that receives the attack
     */
    @Override
    public void attack(BaseSoul target){
        int damage=difficultyLevel*3;
        target.takeDamage(damage);
    }
    /**
     * Returns the Hollow's guaranteed potion drop.
     *
     * @return a healing potion reward
     */
    @Override
    public Item drop(){
        return new HealPotion("Hollow Potion", 0, 35);
    }
    /**
     * Returns the Hollow label with its basic enemy stats.
     *
     * @return a readable Hollow summary
     */
    @Override
    public String toString(){
        return "Hollow\n"+super.toString();
    }
    /**
     * Returns the coin reward for defeating a Hollow.
     *
     * @return the Hollow reward amount
     */
    @Override
    public int getReward() {
        return super.getReward();
    }
    
}
