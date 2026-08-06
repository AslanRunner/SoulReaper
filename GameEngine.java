import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controls the main game flow, map creation, combat phases, shop phases, and save output.
 */
public class GameEngine implements Saveable {
    private static final int MAX_PLAY_COUNT = 2;

    private String lastResult;
    private Hero player;
    private GameMap map;
    private Shop shop;
    private CombatSystem combatSystem;
    private ArrayList<Shinigami> shinigamies;
    private boolean isRunning;
    private boolean finalSupportApplied;
    private int playCount;
    private Scanner scanner;
    private int defeatedEnemies;
    private int escapedEnemies;
    private int collectedGifts;
    private boolean karakuraStoryShown;
    private boolean huecoStoryShown;

    /**
     * Creates a game engine and prepares the initial game state.
     */
    public GameEngine() {
        this.scanner = new Scanner(System.in);
        this.playCount = 0;
        this.lastResult = "Not started";
        resetGameState();
    }

    /**
     * Program entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args){
        GameEngine gameEngine = new GameEngine();
        gameEngine.start();
    }

    /**
     * Resets the player, shop, combat system, support characters, and run counters.
     */
    private void resetGameState() {
        this.player = new Hero("Ichigo", "SwordMan", 10, 150, 50, new Position(0));
        if (playCount == 2) {
            this.player.setMaxHp(155);
            this.player.setCurrentHp(155);
        }
        this.shop = new Shop();
        this.combatSystem = new CombatSystem();
        this.shinigamies = new ArrayList<>();
        this.isRunning = false;
        this.finalSupportApplied = false;
        this.defeatedEnemies = 0;
        this.escapedEnemies = 0;
        this.collectedGifts = 0;
        this.karakuraStoryShown = false;
        this.huecoStoryShown = false;
        initSupportShinigamies();
    }

    /**
     * Adds support Shinigami characters used before the final battle.
     */
    private void initSupportShinigamies() {
        this.shinigamies.add(new Healer("Melisa", "Healer", 80, 100, new Position(7)));
        this.shinigamies.add(new Booster("Aslan", "Booster", 35, 100, new Position(6)));
    }
    /**
     * Welcomes the player and controls the replay loop with a maximum of two playthroughs.
     */
    public void start() {
        System.out.println("====== WELCOME TO SOUL REAPER:THE HERO'S LAST STAND RPG ======");
        boolean wantsToPlay = true;
        while (wantsToPlay) {
            try {
                if (playCount >= MAX_PLAY_COUNT) {
                    throw new OverLoopException("You can play a maximum of 2 times. A third attempt is not allowed.");
                }

                playCount++;
                resetGameState();
                playSingleGame();
                prepareAndSaveSummary();

                int replayChoice = InputHelper.readIntInRange(scanner,
                        "\nDo you want to play again? (1: Yes, 2: No): ", 1, 2);

                if (replayChoice == 1) {
                    if (playCount >= MAX_PLAY_COUNT) {
                        throw new OverLoopException("You can play a maximum of 2 times. A third attempt is not allowed.");
                    }
                    System.out.println("\nA new game is being prepared...");
                    pause(800);
                } else {
                    wantsToPlay = false;
                }
            } catch (OverLoopException e) {
                System.out.println("ERROR: " + e.getMessage());
                wantsToPlay = false;
            }
        }
    }

    /**
     * Runs one full game after difficulty selection.
     */
    private void playSingleGame() {
        this.isRunning = true;
        System.out.println("\nSelect the difficulty mode:");
        System.out.println("1. Normal");
        System.out.println("2. Hard");

        int choice = InputHelper.readIntInRange(scanner, "Your choice: ", 1, 2);

        this.map = createMap(choice);
        initShopItems();
        showIntroStory(choice);
        gameLoop();
    }

    /**
     * Shows the introduction story for the selected difficulty.
     *
     * @param choice the selected difficulty choice
     */
    private void showIntroStory(int choice) {
        System.out.println("\nBefore all of this, the hero was only human.");
        pause(700);
        System.out.println("He lived in Karakura Town, a quiet place that never truly felt ordinary.");
        pause(700);
        System.out.println("One day, after crossing the border between life and death, he became a Shinigami.");
        pause(700);
        System.out.println("No ancient blessing was given to him. No legendary power awakened inside his soul.");
        pause(700);
        System.out.println("He had only his will, his blade, and the duty placed before him.");
        pause(700);
        System.out.println("\nKarakura Town is now drowning in restless spirits.");
        pause(700);
        System.out.println("Hollows have begun to appear across the streets, blocking pure souls from reaching the afterlife.");
        pause(700);
        System.out.println("These Hollows were not always monsters. Many were once gentle spirits, twisted by a darker force.");
        pause(700);
        System.out.println("To save them, the hero must defeat them. Not out of hatred, but to end their suffering.");
        pause(700);
        System.out.println("\nBut Karakura Town is only the beginning...");
        pause(700);
    }
    /**
     * Creates the game map with regions and enemies based on the selected difficulty.
     *
     * @param choice the selected difficulty choice
     * @return the generated game map
     */
    public GameMap createMap(int choice) {
        if (choice == 1) {
            Region[] regions = {
                    new Region("Karakura Town", "Low"),
                    new Region("Karakura Town", "Low"),
                    new Region("Karakura Town", "Low"),
                    new Region("Hueco Mundo", "Medium"),
                    new Region("Hueco Mundo", "Medium"),
                    new Region("Soul Society", "High")
            };
            Enemy[] enemies = {
                    new Hollow("Hollow-1", 80, new Position(0), 2),
                    new Hollow("Hollow-2", 100, new Position(1), 4),
                    new Hollow("Hollow-3", 120, new Position(2), 5),
                    new Arrancar("Arrancar-1", 180, new Position(3), 7),
                    new Arrancar("Arrancar-2", 210, new Position(4), 8),
                    new VastoLorde("VastoLorde", 500, new Position(5), 10)
            };
            linkEnemiesToRegions(regions, enemies);
            return new GameMap(regions, enemies);
        }

        Region[] regions = {
                new Region("Karakura Town", "Low"),
                new Region("Karakura Town", "Low"),
                new Region("Hueco Mundo", "Medium"),
                new Region("Hueco Mundo", "Medium"),
                new Region("Hueco Mundo", "Medium"),
                new Region("Soul Society", "High")
        };
        Enemy[] enemies = {
                new Hollow("Hollow-1", 140, new Position(2), 5),
                new Hollow("Hollow-2", 160, new Position(3), 5),
                new Arrancar("Arrancar-1", 180, new Position(0), 6),
                new Arrancar("Arrancar-2", 200, new Position(1), 7),
                new Arrancar("Arrancar-3", 220, new Position(4), 9),
                new VastoLorde("VastoLorde", 600, new Position(5), 12)
        };
        linkEnemiesToRegions(regions, enemies);
        return new GameMap(regions, enemies);
    }

    /**
     * Connects enemies to their matching regions and updates enemy region names.
     *
     * @param regions the regions in the map
     * @param enemies the enemies to place in the regions
     */
    private void linkEnemiesToRegions(Region[] regions, Enemy[] enemies) {
        int limit = Math.min(regions.length, enemies.length);
        for (int i = 0; i < limit; i++) {
            regions[i].setEnemyInRegion(enemies[i]);
            enemies[i].setRegionName(regions[i].getName());
        }
    }

    /**
     * Adds all purchasable items to the shop.
     */
    private void initShopItems() {
        shop.addItem(new HealPotion("Small Potion", 20, 25));
        shop.addItem(new HealPotion("Big Potion", 50, 65));
        shop.addItem(new Sword("Training Sword", 30, 35));
        shop.addItem(new Sword("Zangetsu Sword", 60, 45));
        shop.addItem(new Sword("Soul Cutter", 90, 60));
        shop.addItem(new Sword("Bankai Sword", 120, 75));
        shop.addItem(new Armour("Light Armour", 40, 10));
        shop.addItem(new Armour("Heavy Armour", 75, 17));
        shop.addItem(new Tip("Enemy Tip", 15, map.getRegions()));
        shop.addItem(new MapScroll("Map Scroll", 45, map.getRegions()));
    }

    /**
     * Runs the main stage loop until the player wins, loses, or the game stops.
     */
    private void gameLoop() {
        while (this.isRunning && player.isAlive()) {
            int currentX = player.getCurrentPosition().getX();

            if (currentX >= map.getEnemyCount()) {
                lastResult = "Victory";
                System.out.println("\n*** CONGRATULATIONS! You defeated every enemy and saved the world! ***");
                this.isRunning = false;
                break;
            }

            if (currentX == map.getEnemyCount() - 1 && !finalSupportApplied) {
                applyFinalSupport();
                finalSupportApplied = true;
            }

            movement();
        }

        if (!player.isAlive()) {
            lastResult = "Defeat";
            System.out.println("\n[GAME OVER] Ichigo lost his soul... You were defeated.");
            this.isRunning = false;
        }
    }
    /**
     * Prints the current stage information and sends the encounter to the combat system.
     */
    public void movement() {
        int x = player.getCurrentPosition().getX();
        Enemy currentEnemy = map.getEnemyAt(x);
        Region currentRegion = map.getRegionAt(x);

        System.out.println("\n------------------------------------------------");
        System.out.println("Location: " + currentRegion.getName()
                + " | Danger: " + currentRegion.getDangerLevel()
                + " | Stage: " + (x + 1));
        System.out.println("Enemy: " + currentEnemy);
        System.out.println("------------------------------------------------");

        combatSystem.startFight(player, currentEnemy, scanner);

        if (combatSystem.didPlayerEscape()) {
            escapedEnemies++;
            player.getCurrentPosition().moveX();
            return;
        }

        if (!currentEnemy.isAlive()) {
            handleVictory(currentEnemy, x);
            player.getCurrentPosition().moveX();
        }
    }

    /**
     * Handles rewards, story progress, shop access, and next movement after a victory.
     *
     * @param currentEnemy the defeated enemy
     * @param x the current stage index
     */
    private void handleVictory(Enemy currentEnemy, int x) {
        defeatedEnemies++;
        System.out.println("\n[VICTORY] " + currentEnemy.getName() + " was defeated!");

        int earned = currentEnemy.getReward();
        player.addBudget(earned);
        System.out.println("+" + earned + " coins earned. Budget: " + player.getBudget());

        Item dropped = currentEnemy.drop();
        if (dropped != null) {
            collectedGifts++;
            player.addGiftToInventory(dropped);
            System.out.println("You earned a gift for defeating this enemy.");
            int revealChoice = InputHelper.readAllowedInt(scanner,
                    "Enter 1 to reveal your gift, or 0 to skip: ", 0, 1);
            if (revealChoice == 1) {
                System.out.println("Your gift: " + dropped);
            }
        } else {
            System.out.println("This enemy did not drop a gift.");
        }

        showStoryAfterVictory(currentEnemy, x);

        if (x < map.getEnemyCount() - 1) {
            askForShop();
            prepareNextMove();
        }
    }

    /**
     * Shows story scenes when the next enemy type changes.
     *
     * @param currentEnemy the enemy that was just defeated
     * @param x the current stage index
     */
    private void showStoryAfterVictory(Enemy currentEnemy, int x) {
        Enemy nextEnemy = x + 1 < map.getEnemyCount() ? map.getEnemyAt(x + 1) : null;

        if (!karakuraStoryShown && currentEnemy instanceof Hollow && nextEnemy instanceof Arrancar) {
            karakuraStoryShown = true;
            showAfterKarakuraStory();
        }

        if (!huecoStoryShown && currentEnemy instanceof Arrancar && nextEnemy instanceof VastoLorde) {
            huecoStoryShown = true;
            showAfterHuecoMundoStory();
        }
    }

    /**
     * Shows the story after Karakura Town is cleared.
     */
    private void showAfterKarakuraStory() {
        System.out.println("\nAs the last Hollow fades, Karakura Town grows silent for the first time in days.");
        pause(800);
        System.out.println("But before Ichigo can rest, a message reaches him from Soul Society:");
        pause(800);
        System.out.println("Heaven itself is in danger.");
        pause(800);
        System.out.println("\nTo reach Soul Society, Ichigo must cross Hueco Mundo, a dead world between despair and darkness.");
        pause(800);
        System.out.println("Hueco Mundo is not a place for the living, nor a place for peaceful souls.");
        pause(800);
        System.out.println("Its sky never brightens. Its ground is pale like bone.");
        pause(800);
        System.out.println("Every step echoes with the hunger of corrupted spirits.");
        pause(800);
        System.out.println("Here, the Arrancars wait.");
        pause(800);
    }

    /**
     * Shows the story after Hueco Mundo is cleared.
     */
    private void showAfterHuecoMundoStory() {
        System.out.println("\nWith the Arrancars defeated, the path to Soul Society opens.");
        pause(800);
        System.out.println("But their final words reveal the truth:");
        pause(800);
        System.out.println("They were not the source of this nightmare. They were only servants.");
        pause(800);
        System.out.println("The one behind everything is Vasto Lorde.");
        pause(800);
        System.out.println("\nSoul Society should have been a place of peace.");
        pause(800);
        System.out.println("But now its gates tremble under a pressure older than memory.");
        pause(800);
        System.out.println("Vasto Lorde has waited since the beginning of time to claim every soul beyond death.");
        pause(800);
        System.out.println("If he wins, no spirit will ever find peace again.");
        pause(800);
    }

    /**
     * Asks whether the player wants to visit the shop.
     */
    private void askForShop() {
        int shopChoice = InputHelper.readIntInRange(scanner,
                "\nDo you want to visit the shop before moving on? (1: Yes, 2: No): ", 1, 2);
        if (shopChoice == 1) {
            openShopPhase();
        } else {
            System.out.println("You decided to skip the shop for now.");
        }
    }

    /**
     * Runs the shop menu until the player leaves it.
     */
    private void openShopPhase() {
        while (true) {
            System.out.println("\n--- SHOP ---");
            shop.displayShop();
            System.out.println("Budget: " + player.getBudget());
            System.out.println("Enter a product number to buy it, or 0 to leave the shop.");

            try {
                int selection = InputHelper.readInt(scanner, "Your choice: ");
                if (selection == 0) {
                    System.out.println("You left the shop.");
                    return;
                }
                shop.sellItem(player, selection);
            } catch (InsufficientBudgetException | InvalidSelectionException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Lets the player move forward, use inventory, or inspect inventory before the next stage.
     */
    private void prepareNextMove() {
        while (true) {
            System.out.println("\nNext action:");
            System.out.println("1. Move forward");
            System.out.println("2. Use inventory");
            System.out.println("3. Show inventory");

            try {
                int selection = InputHelper.readInt(scanner, "Your choice: ");
                if (selection == 1) {
                    return;
                } else if (selection == 2) {
                    player.chooseAndUseItem(scanner);
                } else if (selection == 3) {
                    player.displayInventory();
                } else {
                    throw new InvalidSelectionException("Invalid next action choice!");
                }
            } catch (InvalidSelectionException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Applies final battle support from allied Shinigami characters.
     */
    private void applyFinalSupport() {
        System.out.println("\n=======================================================");
        System.out.println("[EPIC SUPPORT] Your allies arrive at the gate of Vasto Lorde!");
        pause(700);
        System.out.println("Melisa and Aslan transfer their power to you for the final battle!");
        pause(700);

        for (Shinigami support : this.shinigamies) {
            support.use(this.player);
            pause(500);
        }
        System.out.println("=======================================================\n");
    }

    /**
     * Prepares and saves the journey summary.
     */
    private void prepareAndSaveSummary() {
        System.out.println("\nYour journey summary is being prepared. Please wait...");
        pause(1000);
        save();
    }

    /**
     * Saves the current journey summary to a text file.
     */
    @Override
    public void save() {
        String activeSword = player.getSword() != null ? player.getSword().getItemName() : "None";
        String activeArmour = player.getArmour() != null ? player.getArmour().getItemName() : "None";
        String storyEnding = lastResult.equals("Victory")
                ? "In the end, Ichigo stood victorious. His friends arrived when the final pressure became almost unbearable, and their support helped him push beyond his limits."
                : "In the end, Ichigo fell before the journey could be completed. Still, his struggle left a mark, because he kept moving forward even when his strength was fading.";

        String summary = "SOUL REAPER RPG - JOURNEY REPORT\n"
                + "================================\n"
                + "Playthrough Number: " + playCount + "\n"
                + "Final Result: " + lastResult + "\n"
                + "Ichigo's Remaining HP: " + player.getCurrentHp() + "/" + player.getMaxHp() + "\n"
                + "Remaining Budget: " + player.getBudget() + "\n"
                + "Enemies Defeated: " + defeatedEnemies + "\n"
                + "Enemies Escaped From: " + escapedEnemies + "\n"
                + "Gifts Collected: " + collectedGifts + "\n"
                + "Active Sword: " + activeSword + "\n"
                + "Active Armour: " + activeArmour + "\n\n"
                + "Ichigo's Story\n"
                + "--------------\n"
                + "Ichigo stepped into the unknown with only his resolve, his blade, and the weight of the Soul Society on his shoulders.\n"
                + "Across dangerous regions, he faced Hollows, Arrancars, and the rising pressure of stronger enemies.\n"
                + "There were moments when his health dropped and the next strike could have ended everything, but he refused to give up.\n"
                + "He gathered gifts from defeated enemies, searched his inventory for anything that could keep him alive, and carried every scar into the next battle.\n"
                + storyEnding + "\n";

        String saveFileName = "your_history_" + playCount + ".txt";
        try (FileWriter writer = new FileWriter(saveFileName)) {
            writer.write(summary);
            System.out.println("[SYSTEM] Game summary was saved to " + saveFileName + ".");
        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    /**
     * Pauses story text briefly for pacing.
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
