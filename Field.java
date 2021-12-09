import java.io.IOError;
import java.util.ArrayList;
import java.io.FileWriter;

public class Field {
       
    public static final String FILE_NAME = "boosts.txt";
    private Grid grid;
    private GameSystem gameSystem;
    private ArrayList<Boost> readInBoosts;
    private Player playerComputer;
    private Player playerHuman;    

    public Field()
    {
        this.readInBoosts = new ArrayList<>();
        this.playerComputer = new Player(false);
        this.playerHuman = new Player(true);
        this.gameSystem = new GameSystem(playerHuman, playerComputer);
        this.grid = new Grid(gameSystem);
    }

    public Field(Grid grid, GameSystem gameSystem,ArrayList<Boost> readInBoosts, Player playerComputer, Player playerHuman)
    {
        this.grid = grid;
        this.gameSystem = gameSystem;
        this.readInBoosts = readInBoosts;
        this.playerComputer = playerComputer;
        this.playerHuman = playerHuman;
    }
    public static void main(String[] args)
    {
        Field gameField = new Field();
        gameField.gameSystem.getDisplay().getWelcomeMsg();
        System.out.println(gameField.gameSystem.getDisplay().createWelcomeMsg());
        gameField.readBoosts();
        String playerName = gameField.getPlayerName();
        gameField.playerHuman.setName(playerName);
        gameField.playerComputer.setName("The Robots");
        int gridSize = gameField.getGridSize();       
        gameField.grid.setGridSize(gridSize);
        gameField.grid.generateCells();        
        gameField.grid.makeGrid();
        gameField.getPlayerMenuInput();
               
    }

    private void readBoosts()
    {
        try
        {            
            ArrayList<String> outputBoostFile = gameSystem.readFile(FILE_NAME);
            for(String currentBoost: outputBoostFile)
            {
                String[] boostValues = currentBoost.split(",");
                int damage = Integer.parseInt(boostValues[0]);
                int defence = Integer.parseInt(boostValues[1]);
                int coins = Integer.parseInt(boostValues[2]);
                readInBoosts.add(new Boost(damage, defence, coins));
            }
            gameSystem.setBoosts(readInBoosts);
        }
        catch(Exception | IOError  e)
        {
            System.out.println(e);
        }
    }
    
    private String getPlayerName()
    {
        String playerName = "";
        String message = "Enter your player name:";
        boolean flag = true;
        while(flag)
        {
            try
            {
                playerName = gameSystem.getUserInput(message);                
                if((playerName.length() >= 3) && (playerName.length() <= 12))
                {
                    flag = false;
                    System.out.println("Player name accepted! Hello " + playerName +"!");
                }
                else
                {
                    System.out.println("Try again. Please enter a name between 3 and 12 characters long");
                }
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Please enter a name between 3 and 12 characters long..");
            }
            catch(Exception e)
            {
                System.out.println("Please try again, invalid input detected");
            }
        }
        return playerName;
    }

    private int getGridSize()
    {
        int gridSize = -1;
        String message = "Enter the number of rows you would like on the grid (R x R Grid):";
        boolean flag = true;
        while(flag)
        {
            try
            {
                gridSize = Integer.parseInt(gameSystem.getUserInput(message));            
                if((gridSize >= grid.MIN_GRID_SIZE) && (gridSize <= grid.MAX_GRID_SIZE))
                {
                    flag = false;                    
                }
                else
                {
                    System.out.println("Try again. Please enter a size between 3 and 10");
                }
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Try again. Please enter a size between 3 and 10");
            }
            catch(Exception e)
            {
                System.out.println("Please try again, invalid input detected");
            }
        }
        return gridSize;
    }

    private void getPlayerMenuInput()
    {
        boolean flag = true;
        if ((gameSystem.getComputPlayer().getStats().getLives() == 0) || (gameSystem.getHumanPlayer().getStats().getLives() == 0))
        {
            flag = false;
            endGame();
        }
        if (gameSystem.getHumanPlayer().getStats().getTurnsPlayed() > 0) grid.updateGrid();
        String message =  gameSystem.getDisplay().getPlayerInputMenu();      
        while(flag)
        {
            String playerInput = this.gameSystem.getUserInput(message);
            clearMenuMessage();
            try
            {
                int playerSelection = Integer.parseInt(playerInput);                
                switch(playerSelection)
                {
                    case 1:
                        flag = false;
                        capture(gameSystem.getHumanPlayer());
                        break;
                    case 2:                        
                        flag = false;
                        sabotage(gameSystem.getHumanPlayer());
                        break;
                    case 3:
                        flag = false;
                        strike(gameSystem.getHumanPlayer());
                        break;
                    default:
                        System.out.println("Please enter a valid input");
                        break;
                }
            }
            catch(Exception e)
            {
                System.out.println("Invalid input please make a valid selection "); 
            }
        }
    }

    private void endGame()
    {   
        String message =  gameSystem.getDisplay().getComputerInputMenu();
        System.out.println(message);
        System.out.println(" ---------------- GAME OVER THANK YOU FOR PLAYING!! ---------------- ");  
        Player winner;
        int counterCellsWon = 0;
        int counterCellsLost = 0;
        // When the game ends, the game should write the final outcome to the file, outcome.txt, 
        // which includes the player name, the number of turns played, the attack and defence stats, the number of grid squares captured, 
        // the number of grid squares lost, and the final outcome.
        if (gameSystem.getHumanPlayer().getStats().getLives() > gameSystem.getComputPlayer().getStats().getLives())
        {
            winner = gameSystem.getHumanPlayer();
        }
        else
        {
            winner = gameSystem.getComputPlayer();
        }
        ArrayList<Cell> cellList = grid.getCellList();
        for(Cell cell: cellList)
        {
            if ((cell.getCellOwner() != null) && (cell.getCellOwner().equals(winner)))
            {
                if ((cell.getCapturedBy() != null) && (cell.getCapturedBy().equals(winner)))
                {
                    counterCellsWon++;
                }
                if ((cell.getLostBy() != null) && (cell.getLostBy().equals(winner)))
                {
                    counterCellsLost++;
                }
            }
        }
        counterCellsWon = counterCellsWon/2; 
        counterCellsLost = counterCellsLost/2; // Two cells per grid space
        String output = String.format( 
            "\rPlayer Name:                  \t\t|\t%s" 
          + "\nNumber of turns played        \t\t|\t%s"
          + "\nAttack Strength               \t\t|\t%s"
          + "\nDefence Strength              \t\t|\t%s"
          + "\nGrid spaces won               \t\t|\t%s"
          + "\nGrid spaces lost              \t\t|\t%s"
          + "\nOutcome                       \t\t|\t%s",winner.getName(), winner.getStats().getTurnsPlayed(), winner.getStats().getAttack(), winner.getStats().getDefence(),counterCellsWon,counterCellsLost, "WINNER!!!");

        try (FileWriter writer = new FileWriter("outcome.txt"))
        {
            writer.write(output);
        }
        catch (Exception e)
        {
            System.out.println("There was a problem writing to the file");
        }
        System.exit(0);
    }

    private void strike(Player player)
    {
        // This option is only available if, at the time of the strike, the player has captured a complete path from their end of the field all the way to the opponent’s end of the field. For simplicity, a path is considered to be all grid spaces in a straight line that have been captured.
        // Should a player have a complete path, only then they can use this option. This reduces the number of hearts the opponents has by 1.
        // check if the player has a direct line
        // for all each rows the column number is same and owner is the player [Straight line] or the index are all owned 1,1 2,2 3,3 4,4 5,5 etc
        int posX = 0;
        int posY = 0;     
        int counter = 0;
        int diagonalLeft = 0;
        int diagonalRight = 0;
        boolean isLine = false;
        ArrayList<Cell> cells = grid.getCellList();
        Player attacker;
        Player defender;
        String messageOne = "";
        String messageTwo = "";
        String messageThree = "";
        if(player.getIsHuman())
        {
            //Human player selection
            attacker = gameSystem.getHumanPlayer();
            defender = gameSystem.getComputPlayer();
        }
        else
        {
            //Computer player selection
            attacker = gameSystem.getComputPlayer();
            defender = gameSystem.getHumanPlayer();
        }
        for(posX = 1; posX <= grid.getGridSize(); posX++)
        {
            for(posY = 1; posY <= grid.getGridSize(); posY++)
            {
                if (posY == posX)
                {
                    Cell cell = Cell.queryCellbyXYLayer(cells, posX, posY, 1);
                    
                    if ((cell.getCellOwner() != null)  && (cell.getCellOwner().equals(attacker)))
                    {
                        diagonalLeft++;
                    }                  
                }
                // Element indexes from diagonals have one rule - their sum is constant on one diagonal:
                if ((posY + posX) == (grid.getGridSize() + 1))
                {
                    Cell cell = Cell.queryCellbyXYLayer(cells, posX, posY, 1);
                   
                    if ((cell.getCellOwner() != null)  && (cell.getCellOwner().equals(attacker)))
                    {
                        if (posY == posX)
                        {
                            diagonalLeft++;
                        }                        
                        diagonalRight++;
                    }
                }
                
                Cell cell = Cell.queryCellbyXYLayer(cells, posX, posY, 1);
                
                // If all the rows for a column have the same owner is a line
                if ((cell.getCellOwner() != null)  && (cell.getCellOwner().equals(attacker)))
                {
                    counter++;
                }
            }
            if (counter == grid.getGridSize() || diagonalLeft == grid.getGridSize() || diagonalRight == grid.getGridSize())
            {
                isLine = true;
            }
            
            
            counter = 0;
        }

        if(isLine)
            {
                grid.setHasPath(true);
                counter=0;
                diagonalLeft=0;
                diagonalRight=0;
                // Player has made a line
                defender.getStats().decreaseLife();
                attacker.getStats().updateTurnsPlayed();            

                if (defender.getStats().getLives() > 0) 
                {
                    messageOne = attacker.getName() + " just struck a deadly blow to " + defender.getName() + "!";
                    messageTwo = "Keep it up! Only " + defender.getStats().getLives() + " more left!";
                    messageThree = "";
                }
                else if (defender.getStats().getLives() == 0)
                {
                    messageOne = attacker.getName() + " HAS DEFEATED " + defender.getName() + "!";
                    messageTwo = "Great battle and well played!";
                    messageThree = "Remember to check out the output.txt for a breakdown of your stats!";
                    endGame();
                }


                if(attacker.getIsHuman())
                {
                    
                    updateMessage(attacker,messageOne,messageTwo,messageThree);
                    computersTurn();
                }
                else
                {
                    updateMessage(attacker,messageOne,messageTwo,messageThree); 
                    getPlayerMenuInput();
                }
            }
            else
            {
                messageOne = attacker.getName() + " needs to create a path to " + defender.getName() + " side of the map!";
                messageTwo = "Remember a path is from top to bottom or via the diaganols..";
                messageThree = "Good luck!";
                if(attacker.getIsHuman())
                {
                    updateMessage(attacker,messageOne,messageTwo,messageThree);
                    computersTurn();
                }
                else
                {
                    // To-Do computer hasnt made a path and has selected this option
                    updateMessage(attacker,"Computer played strike","there wasnt a path","...");
                    getPlayerMenuInput();
                }
            }
    }

    private void clearMenuMessage()
    {
        if(gameSystem.getDisplay().getPlayerInputMenu().length() > gameSystem.getDisplay().BLANK_MESSAGE_LEN)
        {
            gameSystem.getDisplay().setPlayerInputMenu("", "", "");           
        }  
    }

    private void capture(Player attacker)
    {
        int posX = 0;
        int posY = 0;
        Player defender = null;
        boolean attackerHasWon = false;
        String boostMessage = "";
        
        // Set the attacker and defender status
        if (attacker.getIsHuman())
        {
            // Attacker is human - need to get cell coords
            defender = gameSystem.getComputPlayer();
            posX = getInput(gameSystem.X_COORDS);
            posY = getInput(gameSystem.Y_COORDS);
            gameSystem.rollDice(attacker,3,1,6); 
            gameSystem.rollDice(defender,2,1,6);
            Cell attemptedCell = Cell.queryCellbyXYLayer(grid.getCellList(), posX, posY, 1);
            if ((attemptedCell.getCellOwner() != null) && (attemptedCell.getCellOwner().equals(attacker)))
            {
                // Player has already captured this cell
                // Let the user know they have captured this cell already and try again
                updateMessage(attacker, attemptedCell);
                getPlayerMenuInput(); // Let the player make another selection
            }
        }
        else
        {
            // Attacker is not human - randomly generate coords
            defender = gameSystem.getHumanPlayer();
            posX = gameSystem.randomNumGen(1, grid.getGridSize());
            posY = gameSystem.randomNumGen(1, grid.getGridSize());
            gameSystem.rollDice(attacker,3,1,6); 
            gameSystem.rollDice(defender,2,1,6);
        }

        defender.getStats().setRollThree(0); // UI clean up as the defender only has two dice rolls.
        Cell cellOne = Cell.queryCellbyXYLayer(grid.getCellList(), posX, posY, 1);
        Cell cellTwo = Cell.queryCellbyXYLayer(grid.getCellList(), posX, posY, 2);

        if(attacker.getTotalAttack() > defender.getTotalDefence())
        {
            // attacker wins the cell
            attackerHasWon = true;      
            cellOne.setLostBy(defender);
            cellTwo.setLostBy(defender);
            
            if(cellOne.getHasBoost())
            {
                Boost cellBoost = cellOne.getBoost();
                boostMessage = generateBoostMessage(cellBoost,boostMessage);
                applyBoost(cellOne,attacker);
                if(cellTwo.getHasBoost())
                {
                    Boost cellBoostTwo = cellTwo.getBoost();
                    boostMessage = generateBoostMessage(cellBoostTwo,boostMessage);
                    applyBoost(cellTwo,attacker);
                    cellCaptured(attacker,cellOne,cellTwo,boostMessage,attackerHasWon);
                }
                else
                {
                    // Only cell one had a boost
                    cellCaptured(attacker,cellOne,cellTwo,boostMessage,attackerHasWon);                  
                }
            }
            else
            {
                // Cell has no boost
                cellCaptured(attacker,cellOne,cellTwo,boostMessage,attackerHasWon);
            }
        }            
        else
        {
            // attacker loses the cell
            attackerHasWon = false;
            attacker.getStats().updateTurnsPlayed();
            attacker.getStats().updateCellsLost();
            updateMessage(attacker,attackerHasWon,cellOne,boostMessage);
            if(attacker.getIsHuman())
            {
                computersTurn();
            }
            else
            {
                getPlayerMenuInput();
            }     
        }
    }

    private void sabotage(Player player)
    {
        Player attacker = null;
        Player defender = null;
        int selection = 0;
        if(player.getIsHuman())
        {
            //Human player selection
            attacker = gameSystem.getHumanPlayer();
            defender = gameSystem.getComputPlayer();
            selection = sabotageMenu();
        }
        else
        {
            //Computer player selection
            attacker = gameSystem.getComputPlayer();
            defender = gameSystem.getHumanPlayer();
            if (grid.getHasPath())
            {
                selection = 3;
                // Toggle off
                grid.setHasPath(false);
            }
            else
            {
                selection = gameSystem.randomSabotageSelector();
            }               
            // To-Do add logic for overriding selection once player has created a line
        }

        switch(selection)
        {
            case 1:
                //Decrease attack
                //The game will prompt the user a random amount of coins between 500 and 1500 which is the cost. If the player accepts, their coins are reduced and the opponent’s attack is reduced by 2.
                int cost = gameSystem.randomNumGen(500, 1500);
                int coins = attacker.getStats().getCoins();
                Boolean attackStat = true;
                sabotageAttackOrDefence(attacker, defender, coins, cost, attackStat);                
                break;
            case 2:
                // To do case 2 decrement the opponents defence by 2.
                cost = gameSystem.randomNumGen(500, 1500);
                coins = attacker.getStats().getCoins();
                attackStat = false;
                sabotageAttackOrDefence(attacker, defender, coins, cost, attackStat);
                break;
            case 3:
                // To do case 3 sabotage an opponent?s grid square
                cost = gameSystem.randomNumGen(1000, 2500);
                coins = attacker.getStats().getCoins();
                sabotageCaptureCell(attacker, defender, coins, cost);                
                
        }
    }

    private void sabotageCaptureCell(Player attacker, Player defender, int coins, int cost)
    {
        int posX = 0;
        int posY = 0;
        if(coins > cost)
        {
            // they have enought coins for the choice
            // ask them to accept or reject
            String messageOne = "Sabotage it will take over an opponents captured GRID SPACE!!";
            String messageTwo = "It will cost you: " + cost + " coins";
            String messageThree = "You have " + coins + " coins (You will have " + (coins-cost) + " left if you accept)";
            gameSystem.getDisplay().setCostAcceptedMsg(messageOne, messageTwo, messageThree);
            int selection;
            if(attacker.getIsHuman())
            {
                selection = sabotageMenuAcceptReject();
            }
            else
            {
                selection = 1;
            }
            
            switch(selection)
            {
                case 1:
                    // Player accepted the cost
                    // Check and see if any cells are owned by the defender if there are no cells owned by the defender quit process
                    ArrayList<Cell> cellList = grid.getCellList();
                    boolean defenderHasCells = false;                            
                    for(Cell cell: cellList)
                    {
                        if ((cell.getCellOwner() != null) && (cell.getCellOwner().equals(defender)))
                        {
                            defenderHasCells = true;
                            break;      
                        }                              
                    }
                    if(!defenderHasCells)
                    {
                        // no cells have been captured by the defence
                        String name = defender.getName();
                        messageOne = "Cannot use this Sabotage as " + name + " have not captured any";
                        messageTwo = "grid positions!";
                        messageThree = "Can only use once " + name + " have captured a grid position!!";
                        cancelSabotageGridSquare(defender, messageOne, messageTwo, messageThree);
                    }                            
                    // input x and y co-ordinates
                    if(attacker.getIsHuman())
                    {
                        posX = getInput(gameSystem.X_COORDS);
                        posY = getInput(gameSystem.Y_COORDS);
                    }
                    else
                    {
                        posX = gameSystem.randomNumGen(1, grid.getGridSize());
                        posY = gameSystem.randomNumGen(1, grid.getGridSize());
                    }
                    // Check if the cell is owned by the defender, for computer loop until we find a cell, human cancel
                    boolean found = false;
                    for(Cell cell: cellList)
                    {
                        if ((cell.getPosX() == posX) && (cell.getPosY() == posY) && (cell.getLayer() == 1) && (cell.getCellOwner() != null) && (cell.getCellOwner().equals(defender))) // only search layer 1 cells because layer two will have the same owner
                        {
                            // found the cell that the player inputted
                            cell.setOwner(attacker);
                            cell.setLostBy(defender);
                            cell.setCapturedBy(attacker);
                            String contents = cell.genertateCellContent("CAPTURED/SABOTAGE");
                            cell.setContents(contents);
                            attacker.getStats().updateCellsWon();
                            defender.getStats().updateCellsLost();
                            attacker.getStats().updateTurnsPlayed();
                            Cell cellSecond = Cell.queryCellbyXYLayer(cellList,posX,posY,2); // Get the second layer cell
                            cellSecond.setOwner(attacker);
                            cellSecond.setLostBy(defender);
                            cellSecond.setCapturedBy(attacker);
                            contents = cellSecond.genertateCellContent("BY: " + attacker.getName());
                            cellSecond.setContents(contents);
                            messageThree = "";
                            if (cell.getHasBoost())
                            {
                                applyBoost(cell, attacker);
                                removeBoost(cell, defender);
                                int damage = cell.getBoost().getDamage();
                                int defence = cell.getBoost().getDefence();
                                int coinsBoost = cell.getBoost().getCoins();                                        
                                messageThree = "BOOST - A:"+damage+" D:"+defence+" C:"+coinsBoost+" ";
                            }
                            if (cellSecond.getHasBoost())
                            {
                                applyBoost(cellSecond, attacker);
                                removeBoost(cellSecond, defender);
                                int damage = cellSecond.getBoost().getDamage();
                                int defence = cellSecond.getBoost().getDefence();
                                int coinsBoost = cellSecond.getBoost().getCoins();
                                messageThree = messageThree + "BOOST - A:"+damage+" D:"+defence+" C:"+coinsBoost+" ";
                            }
                            messageOne = attacker.getName() + " just Sabotaged " + defender.getName() + "!";
                            messageTwo = "They stole the square (X:" + posX + " Y:" + posY + ")";
                            updateMessage(attacker, messageOne, messageTwo, messageThree);
                            found = true; // Update the flag to indicate found the cells and updated them
                            break;                                  
                            }
                        }
                        if (!found)
                        {
                            // Cell not found
                            if (attacker.getIsHuman())
                            {
                                messageOne = defender.getName() + " has not captured that space!";
                                messageTwo = "Invalid coordinates (X:" + posX + " Y:" + posY + ")";
                                messageThree = "Try again with a valid input";
                                updateMessage(attacker,messageOne,messageTwo,messageThree);
                                //getPlayerMenuInput();
                                computersTurn();
                            }
                            else
                            {
                                // TO-DO computer makes a search for first cell that has 
                                //System.out.println("Need to add logic to find a cell for the computer as its random choice didnt find anything");
                                // Computer is attacker
                                for(Cell cell: cellList)
                                {
                                    
                                    if ((cell.getCellOwner() != null) && (cell.getCellOwner().equals(defender)))
                                    {
                                        cell.setOwner(attacker);
                                        cell.setLostBy(defender);
                                        cell.setCapturedBy(attacker);
                                        String contents = cell.genertateCellContent("CAPTURED/SABOTAGE");
                                        cell.setContents(contents);
                                        attacker.getStats().updateCellsWon();
                                        defender.getStats().updateCellsLost();
                                        attacker.getStats().updateTurnsPlayed();
                                        Cell cellSecond = Cell.queryCellbyXYLayer(cellList,cell.getPosX(),cell.getPosY(),2); // Get the second layer cell
                                        cellSecond.setOwner(attacker);
                                        cellSecond.setLostBy(defender);
                                        cellSecond.setCapturedBy(attacker);
                                        contents = cellSecond.genertateCellContent("BY: " + attacker.getName());
                                        cellSecond.setContents(contents);
                                        messageThree = "";
                                        if (cell.getHasBoost())
                                        {
                                            applyBoost(cell, attacker);
                                            removeBoost(cell, defender);
                                            int damage = cell.getBoost().getDamage();
                                            int defence = cell.getBoost().getDefence();
                                            int coinsBoost = cell.getBoost().getCoins();                                        
                                            messageThree = "BOOST - A:"+damage+" D:"+defence+" C:"+coinsBoost+" ";
                                        }
                                        if (cellSecond.getHasBoost())
                                        {
                                            applyBoost(cellSecond, attacker);
                                            removeBoost(cellSecond, defender);
                                            int damage = cellSecond.getBoost().getDamage();
                                            int defence = cellSecond.getBoost().getDefence();
                                            int coinsBoost = cellSecond.getBoost().getCoins();
                                            messageThree = messageThree + "BOOST - A:"+damage+" D:"+defence+" C:"+coinsBoost+" ";
                                        }
                                        messageOne = attacker.getName() + " just Sabotaged " + defender.getName() + "!";
                                        messageTwo = "They stole the square (X:" + cell.getPosX() + " Y:" + cell.getPosY() + ")";
                                        updateMessage(attacker, messageOne, messageTwo, messageThree);
                                        found = true; // Update the flag to indicate found the cells and updated them
                                    }
                                    if (found)
                                    {
                                        getPlayerMenuInput();
                                    }
                                    else
                                    {
                                        // Could not find any cells
                                        updateMessage(attacker,"Computer was looking for a cell of yours","to take over","but they couldnt find any!");
                                        getPlayerMenuInput();
                                    }
                                }
                            } 
                        } else if (found)
                        {
                            if (attacker.getIsHuman())
                            {
                                updateMessage(attacker, messageOne, messageTwo, messageThree);
                                computersTurn();
                            }
                            else
                            {
                                updateMessage(attacker, messageOne, messageTwo, messageThree);
                                getPlayerMenuInput();
                            }
                        }
                        break;
                    case 2:
                        // Player rejected the cost
                        getPlayerMenuInput();
                        break;
            }

        }
        else
        {
            // Player does not have enought coins
            String messageOne = "Sorry but this Sabotage would have costed you " + cost + "!";
            String messageTwo = "But you only had " + coins;
            String messageThree = "Unable to pull of player Sabotage!";
            
            if(attacker.getIsHuman())
            {
                updateMessage(attacker,messageOne,messageTwo,messageThree);
                computersTurn();
            }
            else
            {
                messageOne = "Computer tried to play Sabotage but it would have costed " + cost + "!";
                messageTwo = "and the computer had " + coins;
                messageThree = "They were unable to pull of player Sabotage! Lucky!";
                updateMessage(attacker,messageOne,messageTwo,messageThree);
                getPlayerMenuInput();
            }
        }
    }

    private void cancelSabotageGridSquare(Player defender, String messageOne, String messageTwo, String messageThree)
    {
        gameSystem.getDisplay().setPlayerInputMenu(messageOne, messageTwo, messageThree);
        getPlayerMenuInput();
    }

    private void sabotageAttackOrDefence(Player attacker, Player defender, int coins, int cost, boolean attackStat)
    {
        if(coins > cost)
        {
            String messageOne = "Sabotage it will decrement the opponents " + (attackStat ? "attack" : "defence") + " by 2!";
            String messageTwo = "It will cost you: " + cost + " coins";
            String messageThree = "You have " + coins + " coins (You will have " + (coins-cost) + " left if you accept)";
            gameSystem.getDisplay().setCostAcceptedMsg(messageOne, messageTwo, messageThree);
            int result;
            if (attacker.getIsHuman())
            {
                result = sabotageMenuAcceptReject();
            } 
            else
            {
                result = 1;
            }
            switch(result)
            {
                case 1:
                    // Accepted the cost, deduct the coins from the player and take 2 from the defenders attack stats, update players move count
                    int newCoins = coins - cost;
                    attacker.getStats().setCoins(newCoins);
                    int newStat = attackStat ? defender.getStats().getAttack() - 2 : defender.getStats().getDefence() - 2;
                    if(newStat < 0) newStat = 0;
                    if(attackStat)
                    {
                        defender.getStats().setAttack(newStat);
                    }
                    else
                    {
                        defender.getStats().setDefence(newStat);
                    }                    
                    attacker.getStats().updateTurnsPlayed();
                    if(attacker.getIsHuman())
                    {
                        updateMessage(attacker,defender,attackStat);
                        computersTurn();
                    }
                    else
                    {
                        updateMessage(attacker,defender,attackStat);
                        getPlayerMenuInput();
                    }
                    break;
                case 2:
                    // Rejected the cost return to menu
                    getPlayerMenuInput();
                    break;
                }
        }
        else
        {
            // Player didnt have enought coins
            String messageOne = "Sorry but this Sabotage would have costed you " + cost + "!";
            String messageTwo = "But you only had " + coins;
            String messageThree = "Unable to pull of player Sabotage!";
            gameSystem.getDisplay().setCostRejectedMsg(messageOne, messageTwo, messageThree);
            //String message =  gameSystem.getDisplay().getSabotageMenu();
            //this.gameSystem.waitForEnter(message);
            if(attacker.getIsHuman())
            {
                updateMessage(attacker,messageOne,messageTwo,messageThree);
                computersTurn();
            }
            else
            {
                getPlayerMenuInput();
            }
        }
    }

    private int sabotageMenu()
    {
        int selection = -1;
        gameSystem.getDisplay().setSabotageMenu("Will cost between 500 and 1500 coins!", "Will cost between 500 and 1500 coins!", "Will cost between 1000 and 2500 coins!");
        String message =  gameSystem.getDisplay().getSabotageMenu();        
        boolean flag = true;
        while(flag)
        {
            String playerInput = this.gameSystem.getUserInput(message);
            
            try
            {
                int playerSelection = Integer.parseInt(playerInput);                
                switch(playerSelection)
                {
                    case 1:
                        flag = false;
                        selection = 1;
                        return selection;
                    case 2:                        
                        flag = false;
                        selection = 2;
                        return selection;
                    case 3:
                        flag = false;
                        selection = 3;
                        return selection;
                    default:
                        System.out.println("Please enter a valid input");
                        break;
                }
            }
            catch(Exception e)
            {
                System.out.println("Invalid input please make a valid selection");
            }
        }
        return selection;
    }

    private int sabotageMenuAcceptReject()
    {
        int selection = -1;
        // Expected that the menu will be set before this method is called
        String message =  gameSystem.getDisplay().getSabotageMenu();        
        boolean flag = true;
        while(flag)
        {
            String playerInput = this.gameSystem.getUserInput(message);
            
            try
            {
                int playerSelection = Integer.parseInt(playerInput);                
                switch(playerSelection)
                {
                    case 1:
                        flag = false;
                        selection = 1;
                        return selection;
                    case 2:                        
                        flag = false;
                        selection = 2;
                        return selection;
                    default:
                        System.out.println("Please enter a valid input");
                        break;
                }
            }
            catch(Exception e)
            {
                System.out.println("Invalid input please make a valid selection");
            }
        }
        return selection;
    }

    private void computersTurn()
    {
        
        if ((gameSystem.getComputPlayer().getStats().getLives() == 0) || (gameSystem.getHumanPlayer().getStats().getLives() == 0))
        {
            endGame();
        }
        grid.updateGrid();
        String message = "";        
        int randomChoice;
        // Capture  - On turn one, this must be the default choice for the computer.        
        if (gameSystem.getComputPlayer().getStats().getTurnsPlayed() < 1)
        {
            message =  gameSystem.getDisplay().getComputerInputMenu();
            this.gameSystem.waitForEnter(message);              
            capture(gameSystem.getComputPlayer());
        }
        else
        {
            // Computer has already made a move
            message =  gameSystem.getDisplay().getComputerInputMenu();
            this.gameSystem.waitForEnter(message);
            randomChoice = gameSystem.randomNumGen(1, 3);                         
            switch(randomChoice)
            {
                case 1:       
                    capture(gameSystem.getComputPlayer());
                    break;
                case 2:             
                    sabotage(gameSystem.getComputPlayer());
                    break;
                case 3:          
                    strike(gameSystem.getComputPlayer());
                    break;
            }
        }      
    }

    private String generateBoostMessage(Boost cellBoost, String boostMessage)
    {
        int coins = cellBoost.getCoins();
        int damage = cellBoost.getDamage();
        int defence = cellBoost.getDefence();
        boostMessage = boostMessage + " BOOST APPLIED - A:" + damage + " D:" + defence + " C:" + coins;
        return boostMessage;
    }

    private void applyBoost(Cell capturedCellOne, Player player)
    {
        Boost cellBoost = capturedCellOne.getBoost();
        int coins = cellBoost.getCoins() + player.getStats().getCoins();
        int damage = cellBoost.getDamage() + player.getStats().getAttack();
        int defence = cellBoost.getDefence() + player.getStats().getDefence();
        damage = damage < 0 ? 0 : damage;
        defence = defence < 0 ? 0 : defence;
        player.getStats().setAttack(damage);
        player.getStats().setDefence(defence);
        player.getStats().setCoins(coins); 
    }

    private void removeBoost(Cell capturedCellOne, Player player)
    {
        Boost cellBoost = capturedCellOne.getBoost();        
        int damage = player.getStats().getAttack() - cellBoost.getDamage();
        int defence = player.getStats().getDefence() - cellBoost.getDefence();
        damage = damage < 0 ? 0 : damage;
        defence = defence < 0 ? 0 : defence;
        player.getStats().setAttack(damage);
        player.getStats().setDefence(defence);        
    }

    private void cellCaptured(Player player, Cell capturedCellOne, Cell capturedCellTwo, String boostMessage, boolean playerWinsCell)
    {   
        capturedCellOne.setCapturedBy(player);
        capturedCellTwo.setCapturedBy(player);
        capturedCellOne.setCapturedStatus(true);
        capturedCellTwo.setCapturedStatus(true);
        capturedCellOne.setOwner(player);
        capturedCellTwo.setOwner(player);
        String cellContents = capturedCellOne.genertateCellContent("CAPTURED");
        capturedCellOne.setContents(cellContents);
        String message = "BY: " + player.getName();
        cellContents = capturedCellTwo.genertateCellContent(message);
        capturedCellTwo.setContents(cellContents);         
        player.getStats().updateTurnsPlayed();
        updateMessage(player,playerWinsCell,capturedCellOne,boostMessage);
        player.getStats().updateCellsWon();
        //grid.updateGrid();
        if(player.getIsHuman())
        {
            computersTurn();
        }
        else
        {
            getPlayerMenuInput();
        }            
    }

    private void updateMessage(Player player, boolean playerWinsCell, Cell capturedCellOne, String boostMessage)
    {
        String messageOne = player.getName().toUpperCase() + (playerWinsCell ? " CAPTURED THE POSITION" : " DID NOT CAPTURED THE POSITION") + " (X: " + capturedCellOne.getPosX() + " Y: " + capturedCellOne.getPosY() + ")" + " " + boostMessage;
        String messageTwo = "By rolling a total of: " + (player.getIsHuman() ? playerHuman.getTotalAttack() : playerComputer.getTotalAttack()) + " by rolling [" + (player.getIsHuman() ? playerHuman.getStats().getRollOne() : playerComputer.getStats().getRollOne()) + "," + (player.getIsHuman() ? playerHuman.getStats().getRollTwo() : playerComputer.getStats().getRollTwo()) + "," + (player.getIsHuman() ? playerHuman.getStats().getRollThree() : playerComputer.getStats().getRollThree())+"]";
        String messageThree = "The defence total was: " +  (player.getIsHuman() ? playerComputer.getTotalDefence() : playerHuman.getTotalDefence()) + " by rolling [" + (player.getIsHuman() ? playerComputer.getStats().getRollOne() : playerHuman.getStats().getRollOne()) + "," + (player.getIsHuman() ? playerComputer.getStats().getRollTwo() : playerHuman.getStats().getRollTwo()) + "," + (player.getIsHuman() ? playerComputer.getStats().getRollThree() : playerHuman.getStats().getRollThree())+"]";
        if(player.getIsHuman())
        {
            gameSystem.getDisplay().setComputerTurnMsg(messageOne, messageTwo, messageThree);            
        }
        else
        {
            gameSystem.getDisplay().setPlayerInputMenu(messageOne, messageTwo, messageThree);
        }  
    }

    private void updateMessage(Player player, Cell capturedCellOne)
    {
        String messageOne = "You have already captured this square (X: " + capturedCellOne.getPosX() + " Y: " + capturedCellOne.getPosY() + ")";
        String messageTwo = "Please select another grid location to capture";
        String messageThree = "";
        gameSystem.getDisplay().setPlayerInputMenu(messageOne, messageTwo, messageThree);  
    }

    private void updateMessage(Player attacker, Player defender, boolean attackStat)
    {
        String messageOne = attacker.getName() + " Sabotaged the " + defender.getName() + " " + (attackStat ? "attack strength by 2!" : "defence strength by 2!");
        String messageTwo = "";
        String messageThree = "";
        if(attacker.getIsHuman())
        {
            gameSystem.getDisplay().setComputerTurnMsg(messageOne, messageTwo, messageThree);
        }
        else
        {
            gameSystem.getDisplay().setPlayerInputMenu(messageOne, messageTwo, messageThree);
        }   
    }

    private void updateMessage(Player attacker, String messageOne, String messageTwo,  String messageThree)
    {
        if(attacker.getIsHuman())
        {
            gameSystem.getDisplay().setComputerTurnMsg(messageOne, messageTwo, messageThree);
        }
        else
        {
            gameSystem.getDisplay().setPlayerInputMenu(messageOne, messageTwo, messageThree);
        }   
    }

    private int getInput(char value)
    {
        String message = value == 'x' ? this.gameSystem.getDisplay().getPlayerInputXCoords() : this.gameSystem.getDisplay().getPlayerInputYCoords();
        int playerSelection = -1;
        boolean flag = true;
        while(flag)
        {
            String playerInput = this.gameSystem.getUserInput(message);
            try
            {
                playerSelection = Integer.parseInt(playerInput);
                if(!((playerSelection < 1) || (playerSelection > grid.getGridSize())))
                {
                    flag = false;
                }
                else
                {
                    System.out.println("Invalid input please enter a number that can fit on the gid!");
                }
            }
            catch(Exception e)
            {
                System.out.println("Invalid input please make a valid selection");
            }
        }
        return playerSelection;
    }
}
