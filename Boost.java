/**
 * Class used to read boosts.txt which loads a set of benefits/drawbacks
 * will be placed on the playing grid
 * @author Chris Bossard
 * @category Server class
 * @version v1.0 * 
 */

public class Boost {    
    
    private int damage;
    private int defence;
    private int coins;    

    /**
     * Default constructor - initialising values to defauls
     */
    public Boost()
    {
        this.damage = -1;
        this.defence = -1;
        this.coins = -1;
    }

    /**
     * Non default constructor
     * @param damage - int type - the boost to the player’s damage stats
     * @param defence - int type - the boost to the player’s defence stats
     * @param coins - int type - the coins a player can find on the game grid
     */
    public Boost(int damage, int defence, int coins)
    {
        this.damage = damage;
        this.defence = defence;
        this.coins = coins;
    }

    /**
     * Display method to return the boost fields
     * @return String containing the values of the boost fields
     */
    public String display()
    {
        return "Damage: " + this.damage + " Defence: " + this.defence + " Coins: " + this.coins;
    }

    /**
     * Display method to return only the damage value
     * @return String value of the damage field
     */
    public String displayDamage()
    {
        return String.valueOf(this.damage);
    }

    /**
     * Display method to return only defence value
     * @return string value of the defence field
     */
    public String displayDefence()
    {
        return String.valueOf(this.defence);
    }

    /**
     * Display method to return only coins value
     * @return string value of the coins field
     */
    public String displayCoins()
    {
        return String.valueOf(this.coins);
    }

    /**
     * Accessor method to get damage field
     * @return damage - the players damage boost stats
     */
    public int getDamage()
    {
        return this.damage;
    }

    /**
     * Accessor method to get defence field
     * @return defence - the players defence boost stats
     */
    public int getDefence()
    {
        return this.defence;
    }

    /**
     * Accessor method to get the players coins field
     * @return coins - the players coins stats from the boost stats
     */
    public int getCoins()
    {
        return this.coins;
    }

    /**
     * Mutator method to set Damage field
     * @param damage - damage amount from the boost file
     */
    public void setDamage(int damage)
    {
        this.damage = damage;
    }

    /**
     * Mutator method to set Defence field
     * @param defence - defence amount from the boost file
     */
    public void setDefence(int defence)
    {
        this.defence = defence;
    }

    /**
     * Mutator method to set Coins field
     * @param coins - coins amount from the boost file
     */
    public void setCoins(int coins)
    {
        this.coins = coins;
    }

}
