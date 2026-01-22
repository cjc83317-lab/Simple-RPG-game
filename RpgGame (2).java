import java.util.Scanner;
import java.util.Random;

abstract class Weapon {
    private String name;
    private int damage;

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public abstract void useWeapon();

    public String toString() {
        return "Weapon: " + name + ", Damage: " + damage;
    }
}

class ModernWeapon extends Weapon {
    private String type;
    private int ammo;

    public ModernWeapon(String name, int damage, String type, int ammo) {
        super(name, damage);
        this.type = type;
        this.ammo = ammo;
    }

    public String getType() {
        return type;
    }

    public int getAmmo() {
        return ammo;
    }

    
    @Override
    public void useWeapon() {
        if (ammo > 0) {
            System.out.println("Using " + getName() + " of type " + type + "!");
            ammo--;
            System.out.println("Remaining ammo: " + ammo);
        } else {
            System.out.println("Out of ammo!");
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Type: " + type + ", Ammo: " + ammo;
    }
}


class MagicWeapon extends Weapon {
    private String element;
    private int manaCost;

    public MagicWeapon(String name, int damage, String element, int manaCost) {
        super(name, damage);
        this.element = element;
        this.manaCost = manaCost;
    }

    public String getElement() {
        return element;
    }

    public int getManaCost() {
        return manaCost;
    }

    @Override
    public void useWeapon() {
        if (manaCost > 0) {
            System.out.println("Using " + getName() + " with " + element + "!");
            manaCost -= 5;
            System.out.println("Remaining mana: " + manaCost);
        } else {
            System.out.println("Out of Mana!");
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Element: " + element + ", Mana Cost: " + manaCost;
    }
}

abstract class Character {
    private String name;
    private int health;
    private boolean isEnemy;

    public Character(String name, int health, boolean isEnemy) {
        this.name = name;
        this.health = health;
        this.isEnemy = isEnemy;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void describe() {
        System.out.println("This is a character named " + name + " with " + health + " health.");
    }

    public void setEnemy(boolean isEnemy) {
        this.isEnemy = isEnemy;
    }

    public abstract void performAction();
}

class Villager extends Character {
    private String relation;
    private boolean isTraitor;

    public Villager(String name, int health, String relation, boolean isTraitor) {
        super(name, health, false);
        this.relation = relation;
        this.isTraitor = isTraitor;
    }

    public String getRelation() {
        return relation;
    }

    public boolean isTraitor() {
        return isTraitor;
    }

    public void betray() {
        isTraitor = true;
        setEnemy(true);
        System.out.println(getName() + " has betrayed you and joined the goblins!");
        System.out.println("Their connection to the goblins: " + relation);
    }

    @Override
    public void describe() {
        super.describe();
        System.out.println("They are a villager with " + relation + ".");
    }

    
    @Override
    public void performAction() {
        System.out.println(getName() + " is helping the village.");
    }
}

class Goblin extends Character {
    private String tribe;
    private String weaponType;
    private boolean isDisguised;

    public Goblin(String name, int health, String tribe, String weaponType, boolean isDisguised) {
        super(name, health, true); 
        this.tribe = tribe;
        this.weaponType = weaponType;
        this.isDisguised = isDisguised;
    }

    public String getTribe() {
        return tribe;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public boolean isDisguised() {
        return isDisguised;
    }

    @Override
    public void describe() {
        super.describe();
        if (isDisguised) {
            System.out.println("This goblin from " + tribe + " tribe is disguised as a villager, wielding a " + weaponType + ".");
        } else {
            System.out.println("This goblin is from the " + tribe + " tribe and wields a " + weaponType + ".");
        }
    }

    @Override
    public void performAction() {
        if (isDisguised()) {
            System.out.println(getName() + " secretly sabotages the village.");
        } else {
            System.out.println(getName() + " attacks the village with a " + weaponType + "!");
        }
    }
}

class RpgGames {
    private String gun;
    private int bullets;
    private Weapon[] weaponInventory; 
    private int inventorySize;
    private Villager[] villagers;
    private boolean villagerBetrayal;
    private Random random;
    private boolean gameOver;
    private int investigationProgress;

    public RpgGames(String rpgGamesGun, int rpgGamesBullets) {
        gun = rpgGamesGun;
        bullets = rpgGamesBullets;

        weaponInventory = new Weapon[5]; 
        inventorySize = 0;

        villagers = new Villager[4];
        villagers[0] = new Villager("Elder Tomas", 60, "Ancient blood pact between his ancestors and goblin tribes", false);
        villagers[1] = new Villager("Merchant Lila", 45, "Trading illegal weapons to goblins for profit", false);
        villagers[2] = new Villager("Guard Bruno", 70, "Threatened by goblin chief to cooperate or die", false);
        villagers[3] = new Villager("Healer Mira", 40, "Believes goblins are misunderstood and can be peaceful", false);

        villagerBetrayal = false;
        random = new Random();
        gameOver = false;
        investigationProgress = 0;
    }

    public void attackGoblin() {
        if (bullets > 0) {
            System.out.println("You are drawing your " + gun + " and fire at the goblin without hesitation!");
            bullets--;
            System.out.println("The goblin is shocked and runs away in fear. Remaining bullets: " + bullets);
            System.out.println("\nWhat do you want to do next?");
            System.out.println("1. Chase the fleeing goblin");
            System.out.println("2. Search the goblin's body");
            System.out.println("3. Return to defend the village");

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

            if (choice == 1) {
                chaseGoblin();
            } else if (choice == 2) {
                searchGoblin();
            } else if (choice == 3) {
                defendVillage();
            }
        } else {
            System.out.println("Your " + gun + " clicks empty. You have to reload fast!");
            System.out.println("The goblin pulls out a crude handmade gun! They've learned from watching you!");
            System.out.println("You quickly dodge behind cover as they fire wildly!");
        }
    }

    public void reload(int newBullets) {
        bullets += newBullets;
        System.out.println("You calmly pick up some bullets to reload the " + gun + ". The total bullets: " + bullets);
        System.out.println("\nWhile reloading, you notice:");
        System.out.println("1. Smoke signals in the distance");
        System.out.println("2. Goblin footprints leading to a cave");
        System.out.println("3. A wounded villager calling for help");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        if (choice == 1) {
            investigateSmoke();
        } else if (choice == 2) {
            exploreCave();
        } else if (choice == 3) {
            helpVillager();
        }
    }

    public void respondToEnemyComment(int choice) {
        System.out.println("\nChoose what to say to the goblin:");
        System.out.println("1. (Shout) 'Prepare to meet your doom, goblin!'");
        System.out.println("2. 'You're no match for my modern gun, little goblin!' (Smiling very Aggressively)");
        System.out.println("3. 'How dare you attack the village!'");

        if (choice == 1) {
            System.out.println("(You are shouting), 'Prepare to meet your doom, goblin!'");
            System.out.println("'We evolve! We adapt! We take your boom-stick magic!' the goblin leader shouts back");
        } else if (choice == 2) {
            System.out.println("You declare, 'You're no match for my modern gun, little goblin! (Smiling very Aggressively)!'");
            System.out.println("'Not anymore!' the goblin cackles, revealing their own crude firearms");
        } else if (choice == 3) {
            System.out.println("'How dare you attack the village!'");
            System.out.println("'We evolve! We adapt! We take your Bomb loud weapon!' the goblin leader shouts back");
        } else {
            System.out.println("Confused, you mutter something incoherent. Try again.");
        }
    }

    public void chaseGoblin() {
        System.out.println("You chase after the fleeing goblin through the forest...");
        System.out.println("You discover the goblin was running back to their hidden workshop!");
        System.out.println("Shocked, you see goblins attempting to replicate your rifle design!");
        System.out.println("You quickly sabotage their workshop and grab some bullets. (+5 bullets)");
        bullets += 5;

        findWeapon();
    }

    public void searchGoblin() {
        System.out.println("You carefully search the fallen goblin...");
        System.out.println("To your horror, you find primitive blueprints for firearms!");
        System.out.println("You also find some stolen bullets they were studying. (+3 bullets)");
        System.out.println("This information is crucial - they're learning to make guns!");
        bullets += 3;

        int chance = random.nextInt(10);
        if (chance > 7 && !villagerBetrayal) {
            System.out.println("\nYou also find a crumpled note with human handwriting!");
            System.out.println("It contains instructions on how to build firearms, signed by one of the villagers!");
            System.out.println("This suggests someone from the village is helping the goblins!");
            villagerBetrayal = true;
        }
    }

    public void defendVillage() {
        System.out.println("You rush back to defend the village...");
        System.out.println("The villagers warn you that some goblins now carry crude firearms!");
        System.out.println("They give you extra bullets for the tough fight ahead. (+3 bullets)");
        bullets += 3;

        if (villagerBetrayal) {
            confrontVillagers();
        }
    }

    public void investigateSmoke() {
        System.out.println("You head towards the smoke signals...");
        System.out.println("You find survivors under attack by gun-wielding goblins!");
        System.out.println("After helping them, they share their bullets with you. (+4 bullets)");
        bullets += 4;
    }

    public void exploreCave() {
        System.out.println("You follow the footprints to a dark cave...");
        System.out.println("Inside, you find a goblin gunsmith workshop!");
        System.out.println("You steal their bullet supplies and sabotage their work. (+6 bullets)");
        bullets += 6;

        System.out.println("\nDeeper in the cave, you discover a hidden chamber!");
        System.out.println("To your surprise, it contains a cache of modern weapons!");
        findWeapon();
        findWeapon();
    }

    public void helpVillager() {
        System.out.println("You rush to help the wounded villager...");
        System.out.println("They were shot by a goblin's makeshift gun!");
        System.out.println("You find some dropped bullets nearby. (+5 bullets)");
        bullets += 5;

        int villagerIndex = random.nextInt(villagers.length);
        System.out.println("The wounded villager is " + villagers[villagerIndex].getName() + ".");

        if (!villagerBetrayal && random.nextInt(10) > 7) {
            System.out.println("\nAs you tend to their wounds, they whisper something disturbing:");
            System.out.println("\"Some of us... working with goblins... ancient alliance...\"");
            System.out.println("Before you can ask more, they pass out from the pain.");
            villagerBetrayal = true;
        }
    }

    public void findWeapon() {
        if (inventorySize < weaponInventory.length) {
            // Using polymorphism
            Weapon newWeapon;
            if (random.nextBoolean()) { 
                String[] modernWeaponTypes = {"Assault Rifle", "Shotgun", "Sniper Rifle", "Machine Gun", "Grenade Launcher"};
                String[] modernWeaponNames = {"Thunderbolt", "Devastator", "Precision", "Firestorm", "Boom Stick"};

                int typeIndex = random.nextInt(modernWeaponTypes.length);
                int nameIndex = random.nextInt(modernWeaponNames.length);
                int damage = random.nextInt(20) + 10;
                int ammo = random.nextInt(30) + 10;

                newWeapon = new ModernWeapon(modernWeaponNames[nameIndex], damage, modernWeaponTypes[typeIndex], ammo);
                System.out.println("\nYou found a Modern Weapon!");
            } else {
                String[] magicWeaponElements = {"Fire", "Water", "Earth", "Air", "Shadow"};
                String[] magicWeaponNames = {"Inferno Staff", "Tidal Blade", "Earth Hammer", "Windrazor", "Nightbane"};
                int elementIndex = random.nextInt(magicWeaponElements.length);
                int nameIndex = random.nextInt(magicWeaponNames.length);
                int damage = random.nextInt(25) + 15;
                int manaCost = random.nextInt(40) + 20;
                newWeapon = new MagicWeapon(magicWeaponNames[nameIndex], damage, magicWeaponElements[elementIndex], manaCost);
                System.out.println("\nYou found a Magic Weapon!");
            }

            weaponInventory[inventorySize] = newWeapon;
            inventorySize++;

            System.out.println(newWeapon); // This line will be toString() method

            System.out.println("\nWould you like to switch to this weapon? (1: Yes, 2: No)");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

            if (choice == 1) {
                gun = newWeapon.getName(); 
                
                System.out.println("You've equipped the " + gun + "!");
            }
        } else {
            System.out.println("Your inventory is full! You can't carry any more weapons.");
        }
    }

    public void confrontVillagers() {
        System.out.println("\nNow that you know about the betrayal, you look at the villagers with suspicion.");
        System.out.println("Who would you like to confront?");

        for (int i = 0; i < villagers.length; i++) {
            System.out.println((i + 1) + ". " + villagers[i].getName());
        }

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt() - 1;

        if (choice >= 0 && choice < villagers.length) {
            Villager suspect = villagers[choice];

            if (suspect.isTraitor()) {
                System.out.println("\nYou confront " + suspect.getName() + " about their betrayal.");
                System.out.println("They panic and call for their goblin allies!");
                System.out.println("Suddenly, several goblins emerge from hiding spots in the village!");
                System.out.println("\"Yes, it's true!\" " + suspect.getName() + " shouts. \"We've been working with them all along!\"");
                System.out.println("\"" + suspect.getRelation() + "\"");

                System.out.println("\nA battle breaks out in the village! You must defend yourself!");
                startBattle(suspect);
            } else {
                System.out.println("\nYou confront " + suspect.getName() + ", but they seem genuinely shocked by your accusation.");
                System.out.println("\"I would never betray our village! We must find who is really working with the goblins!\"");

                for (Villager v : villagers) {
                    if (v.isTraitor() && random.nextInt(10) > 5) {
                        System.out.println(suspect.getName() + " lowers their voice. \"I've had suspicions about " + v.getName() + " for a while now...\"");
                        break;
                    }
                }
            }
        } else {
            System.out.println("Invalid villager choice.  Please try again.");
        }
    }

    public void startBattle(Villager traitor) {
        System.out.println("Battle Commences!");
        Scanner scanner = new Scanner(System.in);
        int playerHealth = 100;
        int traitorHealth = traitor.getHealth();
        System.out.println("Your Health: " + playerHealth + ". " + traitor.getName() + "'s Health: " + traitorHealth);

        while (playerHealth > 0 && traitorHealth > 0) {
            System.out.println("\nChoose your action:");
            System.out.println("1. Attack");
            System.out.println("2. Reload");
            System.out.print("Enter your choice: ");
            int action = scanner.nextInt();

            if (action == 1) {
                int playerDamage = 20;
                System.out.println("You attack " + traitor.getName() + " with your " + gun + " for " + playerDamage + " damage.");
                traitorHealth -= playerDamage;
                if (traitorHealth < 0) {
                    traitorHealth = 0;
                }
                System.out.println(traitor.getName() + "'s Health: " + traitorHealth);

                if (traitorHealth > 0) {
                    int traitorDamage = 15;
                    System.out.println(traitor.getName() + " attacks you for " + traitorDamage + " damage.");
                    playerHealth -= traitorDamage;
                    if (playerHealth < 0) {
                        playerHealth = 0;
                    }
                    System.out.println("Your Health: " + playerHealth);
                }
            } else if (action == 2) {
                reload(10);
            } else {
                System.out.println("Invalid action. Try again.");
            }
        }

        if (playerHealth <= 0) {
            System.out.println("You have been defeated!");
            System.out.println("GAME OVER");
            gameOver = true;
            System.exit(0);
        } else {
            System.out.println("Congratulations! You defeated " + traitor.getName() + "!");
            revealVillagers(); //this line will call the method
        }
    }

    public void revealVillagers() {
        System.out.println("\nAs the dust settles, you survey the village...");
        for (Villager villager : villagers) {
            if (villager.isTraitor()) {
                System.out.println(villager.getName() + " was a goblin in disguise! Their mask fades away, revealing their true form.");
            } else {
                System.out.println(villager.getName() + " was a true villager, but their trust is shattered.");
            }
        }

        System.out.println("\nThe years of deception and betrayal weigh heavily on your soul.");
        System.out.println("The line between human and monster has blurred beyond recognition.");
        System.out.println("You can no longer find solace in this world, forever tainted by the goblins' trickery.");
        System.out.println("\nWith a heavy heart, you abandon the village, becoming a cold-blooded wanderer.");
        System.out.println("You walk alone, carrying the burden of your past, seeking a place where trust and peace might still exist.");
        gameOver = true;
    }

    public void investigateVillagers() {
        if (investigationProgress < villagers.length) {
            System.out.println("\nYou begin to investigate the villagers, one by one.");
            for (int i = 0; i < villagers.length; i++)
             {
                villagers[i].describe();
             }
            System.out.println("You are getting suspicious of them.");
            investigationProgress++;
            if (investigationProgress >= villagers.length)
            {
                nightEncounter();
            }
        }
         else {
            System.out.println("You have already investigated all the villagers.");
        }
    }

    public void nightEncounter()
    {
        System.out.println("\nOne night, you decide to stay vigilant and observe the villagers' activities.");
        System.out.println("As darkness engulfs the village, you witness a shocking scene...");
        for (Villager villager : villagers)
        {
            if (random.nextBoolean())
            {
                villager.betray();
            }
        }
        System.out.println("The villagers are meeting with the goblins, their faces twisted in wicked grins.");
        System.out.println("Your memories with them flood your mind - their laughter, their kindness, all a cruel facade.");
        revealVillagers();
    }
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.println("You have been summoned to another world where you are carrying your modern equipment. You encounter a village where you will stay");
        System.out.println("for a long time. Time passed, and you gained combat experience, produced bullets, and enhanced your rifle skill.");
        System.out.println("Then one day, goblins attacked you and the village, injuring the villagers.");
        System.out.println("You're very angry and desire revenge to kill them all.");
        System.out.println("To your horror, you notice some goblins carrying crude, handmade guns - they've been watching and learning from you!");

        RpgGames games = new RpgGames("Rifle", 10);

        while (!games.gameOver) {
            System.out.println("\nChoose an action");
            System.out.println("1. Revenge and attack the goblins");
            System.out.println("2. Reload");
            System.out.println("3. Respond to the enemy");
            System.out.println("4. Check weapon inventory (the weapons you found along the way)");
            System.out.println("5. Investigate villagers");
            System.out.println("6. Exit game");
            System.out.print("Pick your choice, my friend HAHAHAHA: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                games.attackGoblin();
            } else if (choice == 2) {
                games.reload(10);
            } else if (choice == 3) {
                System.out.println("\nChoose what to say to the goblin:");
                System.out.println("1. (Shout) 'Prepare to meet your doom, goblin!'");
                System.out.println("2. 'You're no match for my modern gun, little goblin!' (Smiling very Aggressively)");
                System.out.println("3. 'How dare you attack the village!'");
                System.out.print("\nChoose your response (1-3): ");
                int responseChoice = sc.nextInt();
                games.respondToEnemyComment(responseChoice);
            } else if (choice == 4) {
                System.out.println("\n===== YOUR WEAPONS =====");
                System.out.println("Currently equipped: " + games.gun);

                if (games.inventorySize == 0) {
                    System.out.println("You have no weapons yet, Sorry player HAHAHAH.");
                } else {
                    for (int i = 0; i < games.inventorySize; i++) {
                        Weapon weapon = games.weaponInventory[i];
                        System.out.println("\n" + (i + 1) + ". " + weapon); 
                    }
                }
            } else if (choice == 5) {
                games.investigateVillagers();
                 if (games.gameOver) { 
                    break; 
                }
            } else if (choice == 6) {
                System.out.println("Exiting game. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }
}