import java.util.Scanner;

/**
 * Controls turn-based combat between the hero and an enemy.
 */
public class CombatSystem {
    private static final int ACTION_ATTACK = 1;
    private static final int ACTION_DEFEND = 2;
    private static final int ACTION_INVENTORY = 3;
    private static final int ACTION_ESCAPE = 4;
    private static final double COUNTER_CHANCE = 0.35;

    private int turnCounter;
    private boolean isCombatActive;
    private boolean playerEscaped;

    /**
     * Creates a combat system for managing battle turns.
     */
    public CombatSystem() {
    }
    /**
     * Starts the turn-based battle loop until the player, enemy, or escape condition ends the fight.
     *
     * @param player the hero controlled by the player
     * @param target the enemy being fought
     * @param scanner the scanner used to read combat input
     */
    public void startFight(Hero player, Enemy target, Scanner scanner) {
        if (isCombatActive) {
            throw new CombatAlreadyActiveException("A fight is already active!");
        }

        this.turnCounter = 1;
        this.isCombatActive = true;
        this.playerEscaped = false;

        System.out.println("\n[FIGHT STARTED] " + player.getName() + " vs " + target.getName());

        while (!checkCombatEnd(player, target) && !this.playerEscaped) {
            System.out.println("\nTurn: " + this.turnCounter);
            System.out.println(player.getName() + " HP: " + player.getCurrentHp() + "/" + player.getMaxHp()
                    + " | " + target.getName() + " HP: " + target.getCurrentHp() + "/" + target.getMaxHp());

            int actionResult = processPlayerTurn(player, target, scanner);

            if (checkCombatEnd(player, target) || playerEscaped) {
                break;
            }

            processEnemyTurn(target, player, actionResult == ACTION_DEFEND);
            this.turnCounter++;
        }

        this.isCombatActive = false;
    }
    /**
     * Reads the player's combat choice and executes the selected action.
     *
     * @param player the hero taking the turn
     * @param target the enemy being targeted
     * @param scanner the scanner used to read combat input
     * @return the action constant representing the selected combat action
     */
    public int processPlayerTurn(Hero player, Enemy target, Scanner scanner) {
        while (true) {
            System.out.println("1. Attack");
            System.out.println("2. Defend");
            System.out.println("3. Use inventory");
            System.out.println("4. Escape");

            try {
                int actionChoice = InputHelper.readInt(scanner, "Your choice: ");

                if (actionChoice == ACTION_ATTACK) {
                    System.out.println(player.getName() + " grips the sword and rushes forward...");
                    pause(500);
                    player.attack(target);
                    System.out.println(player.getName() + " attacked. "
                            + target.getName() + " HP: " + target.getCurrentHp() + "/" + target.getMaxHp());
                    return ACTION_ATTACK;
                } else if (actionChoice == ACTION_DEFEND) {
                    System.out.println(player.getName() + " takes a defensive stance and waits for an opening...");
                    pause(500);
                    System.out.println("Incoming damage will be reduced. There is also a 35% chance to counterattack.");
                    return ACTION_DEFEND;
                } else if (actionChoice == ACTION_INVENTORY) {
                    if (player.chooseAndUseItem(scanner)) {
                        System.out.println("Inventory is now ready for you to use. Be careful and choose wisely...");
                    }
                } else if (actionChoice == ACTION_ESCAPE) {
                    if (tryToEscape(target, scanner)) {
                        return ACTION_ESCAPE;
                    }
                } else {
                    throw new InvalidSelectionException("Invalid combat choice!");
                }
            } catch (InvalidSelectionException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    /**
     * Runs the enemy attack and reduces damage when the player is defending.
     *
     * @param target the enemy taking the turn
     * @param player the hero receiving the attack
     * @param defending true if the hero chose to defend this turn
     */
    public void processEnemyTurn(Enemy target, Hero player, boolean defending) {
        try {
            System.out.println(target.getName() + " raises its spiritual pressure and prepares to strike...");
            pause(500);

            int hpBeforeAttack = player.getCurrentHp();
            int damage;

            if (defending) {
                player.setCurrentHp(player.getMaxHp());

                target.attack(player);

                int fullHpAfterAttack = player.getCurrentHp();
                int realDamage = player.getMaxHp() - fullHpAfterAttack;

                if (realDamage < 0) {
                    realDamage = 0;
                }

                damage = (realDamage + 1) / 2;

                int newHp = hpBeforeAttack - damage;

                if (newHp < 0) {
                    newHp = 0;
                }

                player.setCurrentHp(newHp);
            } else {
                target.attack(player);

                damage = hpBeforeAttack - player.getCurrentHp();

                if (damage < 0) {
                    damage = 0;
                }
            }

            System.out.println(target.getName() + " attacked. Damage taken: " + damage
                    + " | " + player.getName() + " HP: " + player.getCurrentHp() + "/" + player.getMaxHp());

            if (defending && player.getCurrentHp() > 0) {
                tryCounterAttack(player, target);
            }

        } catch (InsufficientHealthException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Attempts a counterattack after a successful defensive action.
     *
     * @param player the hero attempting the counterattack
     * @param target the enemy receiving the counterattack
     */
    private void tryCounterAttack(Hero player, Enemy target) {
        if (!player.isAlive() || !target.isAlive()) {
            return;
        }

        if (Math.random() <= COUNTER_CHANCE) {
            int counterDamage = player.getSword().getDamage();

            target.takeDamage(counterDamage);
            System.out.println("Perfect guard! " + player.getName()
                    + " counterattacked for " + counterDamage + " damage. "
                    + target.getName() + " HP: " + target.getCurrentHp() + "/" + target.getMaxHp());
        } else {
            System.out.println(player.getName() + " blocked the attack, but could not find a counter opening.");
        }
    }

    /**
     * Attempts to escape from the current enemy.
     *
     * @param target the enemy the player is trying to escape from
     * @param scanner the scanner used to confirm the escape attempt
     * @return true if the escape action is accepted, otherwise false
     */
    private boolean tryToEscape(Enemy target, Scanner scanner) {
        if (target instanceof VastoLorde) {
            System.out.println("You cannot escape from the final enemy!");
            return false;
        }

        System.out.println("[WARNING] Escape chance is low: 20%.");
        System.out.println("If escape fails, the enemy will still attack.");
        int confirmation = InputHelper.readIntInRange(scanner, "Do you still want to try escaping? (1: Yes, 2: No): ", 1, 2);
        if (confirmation != 1) {
            System.out.println("Escape cancelled. Returning to combat choices.");
            return false;
        }

        if (Math.random() < 0.2) {
            playerEscaped = true;
            System.out.println("You escaped successfully. You will not receive a reward from this enemy.");
        } else {
            System.out.println("Escape failed!");
        }
        return true;
    }
    /**
     * Checks whether the fight should end because either side has been defeated.
     *
     * @param player the hero in the fight
     * @param target the enemy in the fight
     * @return true if the fight should end, otherwise false
     */
    public boolean checkCombatEnd(Hero player, Enemy target) {
        if (!player.isAlive()) {
            return true;
        }

        if (!target.isAlive()) {
            return true;
        }
        return false;
    }
    /**
     * Returns whether the player successfully escaped from the current fight.
     *
     * @return true if the player escaped successfully
     */
    public boolean didPlayerEscape() {
        return this.playerEscaped;
    }

    /**
     * Pauses combat text briefly for pacing.
     *
     * @param ms the pause duration in milliseconds
     */
    private void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
