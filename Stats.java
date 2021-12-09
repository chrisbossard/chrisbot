/**
 * Stats class used to store the state of the player statistics
 * @author Chris Bossard
 */
public class Stats {

    private int attack;
    private int defence;
    private int coins;
    private int lives;
    private char heart;
    private int turnsPlayed;
    private int rollValue;
    private int rollOne;
    private int rollTwo;
    private int rollThree;
    private int countCellsWon;
    private int countCellsLost;    

    /**
     * Default constructor
     */
    public Stats()
    {
        this.attack = 5;
        this.defence = 7;                
        this.coins = 10000;
        this.lives = 3;
        this.heart = '\u2661';
        this.turnsPlayed = 0;
        this.rollValue = 0;
        this.countCellsLost = 0;
        this.countCellsWon = 0;
    }

    /**
     * Non default constructor
     * @param name - name of the player
     * @param isHuman - flag to indicate if the stats are for the computer or the player
     */
    public Stats(int attack, int defence, int coins, int lives)
    {
        this.attack = attack;
        this.defence = defence;  
        this.coins = coins;
        this.lives = lives;
        this.heart = '\u2661';
        this.turnsPlayed = 0;
        this.rollValue = 0;
    }

    public Stats(boolean isHuman)
    {
        this.attack = 5;
        this.defence = 7;  
        this.coins = isHuman ? 3000 : 10000;
        this.lives = 3;
        this.heart = '\u2764';
        this.turnsPlayed = 0;
        this.rollValue = 0;
        this.countCellsLost = 0;
        this.countCellsWon = 0;
    }

    public String display()
    {
        return "Attack: " + this.attack + " Defence: " + this.defence + " Coins: " + this.coins;
    }

    public String livesToHearts()
    {
        String lives = "";
        
        switch(this.lives)
        {
            case 0:
                this.heart = '\u2763';
                lives = String.valueOf(this.heart);
                break;
            case 1:
                lives = String.valueOf(this.heart);
                break;
            case 2:
                lives = String.valueOf(this.heart) + String.valueOf(this.heart);
                break;
            case 3:
                lives = String.valueOf(this.heart) + String.valueOf(this.heart) + String.valueOf(this.heart);
                break;
        }

        return lives;
    }

    public int getTurnsPlayed()
    {
        return this.turnsPlayed;
    }

    public int getAttack()
    {
        return this.attack;
    }

    public int getCoins()
    {
        return this.coins;
    }

    public int getCountCellsLost()
    {
        return this.countCellsLost;
    }

    public int getCountCellsWon()
    {
        return this.countCellsWon;
    }

    public int getDefence()
    {
        return this.defence;
    }

    public int getDiceRollValue()
    {
        return this.rollValue;
    }

    public int getRollOne()
    {
        return this.rollOne;
    }

    public int getRollTwo()
    {
        return this.rollTwo;
    }

    public int getRollThree()
    {
        return this.rollThree;
    }

    public int getLives()
    {
        return this.lives;
    }

    public void setAttack(int attack)
    {
        this.attack = attack;
    }

    public void setDefence(int defence)
    {
        this.defence = defence;
    }

    public void setCoins(int coins)
    {
        this.coins = coins;
    }

    public void setCountCellsLost(int cellsLost)
    {
        this.countCellsLost = cellsLost;
    }

    public void setCountCellsWon(int cellsWon)
    {
        this.countCellsWon = cellsWon;
    }

    public void setTurnsPlayed(int turnsPlayed)
    {
        this.turnsPlayed = turnsPlayed;
    }

    public void setRollValue(int rollValue)
    {
        this.rollValue = rollValue;
    }

    public void setRollOne(int rollOne)
    {
        this.rollOne = rollOne;
    }

    public void setRollTwo(int rollTwo)
    {
        this.rollTwo = rollTwo;
    }

    public void setRollThree(int rollThree)
    {
        this.rollThree = rollThree;
    }

    public void setLives(int lives)
    {
        this.lives = lives;
    }

    public void updateTurnsPlayed()
    {
        this.turnsPlayed++;
    }

    public void updateCellsLost()
    {
        this.countCellsLost++;
    }

    public void updateCellsWon()
    {
        this.countCellsWon++;
    }

    public void decreaseLife()
    {
        this.lives--;
    }
}
