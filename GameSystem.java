import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class GameSystem {
    
    private ArrayList<Boost> boosts;
    private Player humanPlayer;
    private Player computerPlayer;
    private Display displayMessages;
    private int[] captureCoordinates;
    public final char X_COORDS = 'x';
    public final char Y_COORDS = 'y';  

    public GameSystem()
    {
        this.boosts = new ArrayList<>();
        this.displayMessages = new Display();
        this.captureCoordinates = new int[]{-1,-1};
    }

    public GameSystem(ArrayList<Boost> boosts, Player humanPlayer, Player computerPlayer)
    {
        this.boosts = boosts;
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
    }

    public GameSystem(Player humanPlayer, Player computerPlayer)
    {
        this.boosts = new ArrayList<>();
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
        this.displayMessages = new Display();
        this.captureCoordinates = new int[]{-1,-1};
    }

    /**
     * Display/write to screen the current boost captured from the file
     */
    public void displayBoosts()
    {
        for(Boost curBoost: boosts)
        {
            System.out.println(curBoost.display());
        }
    }

    /**
     * Display a single boost at a specific index
     * @param index
     * @return String value of boost at the current index value
     * @throws IndexOutOfBoundsException
     */
    public String displayBoostsAt(int index) throws IndexOutOfBoundsException
    {
        return boosts.get(index).display();
    }

    public ArrayList<Boost> getBoosts()
    {
        return this.boosts;
    }

    public Player getHumanPlayer()
    {
        return this.humanPlayer;
    }

    public Player getComputPlayer()
    {
        return this.computerPlayer;
    }
    
    public Display getDisplay()
    {
        return this.displayMessages;
    }

    public int[] getCaptureCoordinates()
    {
        return this.captureCoordinates;
    }

    /**
     * Return a specific boost at an index
     * @param index
     * @return Object boost at that index
     */
    public Boost getBoostAt(int index) throws IndexOutOfBoundsException
    {
        return boosts.get(index);
    }
    
    /**
     * Read file method, used to read the boosts file, will be used to store
     * the values of the file into an arraylist.
     * @param filename - the name of the file to be read in
     * @return fileContents - the contents of the file, each line is an element in the array
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ArrayList<String> readFile(String filename) throws FileNotFoundException, IOException
    {
        ArrayList<String> fileOutput = new ArrayList<>();
        FileReader reader = new FileReader(filename);
        Scanner fileContents = new Scanner(reader);
        while(fileContents.hasNextLine())
        {
            fileOutput.add(fileContents.nextLine());
        }
        fileContents.close();

        return fileOutput;
    }

    /**
     * Mutator method to set boosts
     * @param boosts
     */
    public void setBoosts(ArrayList<Boost> boosts)
    {
        this.boosts = boosts;
    }

    public void setCaptureCoordinates(int index, int value)
    {
        this.captureCoordinates[index] = value;
    }

    public String getUserInput(String message) throws IllegalArgumentException
    {
        Scanner console = new Scanner(System.in);        
        System.out.println(message);
        String userInput = console.nextLine();
        if (userInput == null)
        {
            throw new IllegalArgumentException("Name cannot be null");
        }            
        if (userInput.trim().length() == 0)
        {
            throw new IllegalArgumentException("Name cannot be empty or all spaces");
        }
        return userInput;
    }

    public String waitForEnter(String message)
    {
        Scanner console = new Scanner(System.in);        
        System.out.println(message);
        String userInput = console.nextLine();
        return userInput;
    }

    public void rollDice(Player player, int numOfTimes, int minNum, int maxNum)
    {
        int total = 0;
        for(int counter = 0; counter < numOfTimes; counter++)
        {
            int number = randomNumGen(minNum, maxNum);        
            total = total + number;
            updatePlayerStatsRolls(number,counter,player);
        }
        player.getStats().setRollValue(total); 
    }

    private void updatePlayerStatsRolls(int rollNumber, int switchCase, Player player)
    {
        switch(switchCase)
        {
            case 0:
                player.getStats().setRollOne(rollNumber);
                break;
            case 1:
                player.getStats().setRollTwo(rollNumber);
                break;
            case 2:
                player.getStats().setRollThree(rollNumber);
                break;
        }
    }

    public int randomNumGen(int min, int max)
    {
        int num = min + (int)(Math.random() * ((max - min) + 1));
        //max = max + 1;
        //return (int) ((Math.random() * (max - min)) + min);
        return num;
    }
    
    public int randomSabotageSelector()
    {
        int[] choiceCollection = new int[10];
        boolean flag = true;
        int counterOne = 0;
        int counterTwo = 0;
        int counterThree = 0;
        int index = 0;
        while (flag)
        {
            int randomNum = randomNumGen(1, 3);
            if ((randomNum == 1) && (counterOne != 4))
            {
                choiceCollection[index] = randomNum;
                counterOne++;
                index++;
            }
            if ((randomNum == 2) && (counterTwo != 4))
            {
                choiceCollection[index] = randomNum;
                counterTwo++;
                index++;
            }
            if ((randomNum == 3) && (counterThree != 2))
            {
                choiceCollection[index] = randomNum;
                counterThree++;
                index++;
            }
            if (index > 9)
            {
                flag = false;
            }
        }

        int randomIndex = randomNumGen(0, 9);
        return choiceCollection[randomIndex];
    }
}
