/**
 * Player class to create a player
 * @author Chris Bossard
 * @version v1.1
 */
public class Player {
    
    private String name;
    private boolean isHuman;
    private Stats playerStats;    

    public Player()
    {
        this.isHuman = false;
        this.name = "Unassigned";
        this.playerStats = new Stats();
    }

    public Player(String name, boolean isHuman)
    {
        this.name = name;
        this.isHuman = isHuman;
        this.playerStats = new Stats();
    }

    public Player(boolean isHuman)
    {
        this.name = "Unassigned";
        this.isHuman = isHuman;
        this.playerStats = new Stats(isHuman);
    }

    /**
     * Display/write to screen the current boost captured from the file
     */
    public String display()
    {
        return "Player: " + this.name + " Attack Strength: " + this.playerStats.getAttack() + " Defence Strength: " + this.playerStats.getDefence() + " Amount of Coins: " + this.playerStats.getCoins() + " (" + (this.isHuman ? "Human Player" : "Computer Player") + ")"; 
    }

    public void displayPlayer()
    {
        System.out.println("Player: " + this.name + " Attack Strength: " + this.playerStats.getAttack() + " Defence Strength: " + this.playerStats.getDefence() + " Amount of Coins: " + this.playerStats.getCoins() + " (" + (this.isHuman ? "Human Player" : "Computer Player") + ")");
    }

    public boolean getIsHuman()
    {
        return this.isHuman;
    }

    public String getName()
    {
        return this.name;
    }

    public Stats getStats()
    {
        return this.playerStats;
    }

    public int getTotalAttack()
    {
        return this.playerStats.getAttack() + this.playerStats.getDiceRollValue();
    }

    public int getTotalDefence()
    {
        return this.playerStats.getDefence() + this.playerStats.getDiceRollValue();
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
