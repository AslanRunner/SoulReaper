import java.util.ArrayList;
/**
 * Represents a Shinigami character with a duty and soul power.
 */
public class Shinigami extends BaseSoul implements Usable{
    /**
     * The Shinigami's duty or role.
     */
    protected String duty;
    /**
     * The Shinigami's soul power value.
     */
    protected int soulPower;

    /**
     * Creates a Shinigami with role-specific soul power.
     *
     * @param name the Shinigami name
     * @param duty the Shinigami duty
     * @param soulPower the Shinigami soul power
     * @param maxHp the Shinigami maximum HP
     * @param currentPosition the Shinigami position
     */
    Shinigami(String name, String duty, int soulPower, int maxHp, Position currentPosition) {
        super(name, maxHp, currentPosition);
        this.duty = duty;
        this.soulPower = soulPower;
    }

    /**
     * Provides a default support action that subclasses can override.
     *
     * @param target the soul that receives support
     */
    @Override
    public void use(BaseSoul target) {

    }
    
    /**
     * Returns a readable summary of this Shinigami's status.
     *
     * @return a readable Shinigami summary
     */
    @Override
    public String toString(){
        return getName()+"Shinigami\n"+"Duty:"+getDuty()+"HP:"+getCurrentHp()+"/"+getMaxHp()+" |Belonged Region:"+getCurrentPosition();
    }
    /**
     * Returns the Shinigami duty.
     *
     * @return the duty
     */
    public String getDuty() {
        return duty;
    }

    /**
     * Updates the Shinigami duty.
     *
     * @param duty the new duty
     */
    public void setDuty(String duty) {
        this.duty = duty;
    }

    /**
     * Returns the Shinigami soul power.
     *
     * @return the soul power
     */
    public int getSoulPower() {
        return soulPower;
    }

    /**
     * Updates the Shinigami soul power.
     *
     * @param soulPower the new soul power
     */
    public void setSoulPower(int soulPower) {
        this.soulPower = soulPower;
    }
}

