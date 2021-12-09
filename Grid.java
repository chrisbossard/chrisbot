import java.util.ArrayList;


public class Grid {

    public final int MIN_GRID_SIZE = 3;    
    public final int MAX_GRID_SIZE = 10;   
    private GameSystem gameSystem;
    private int rows;
    private int columns;
    private int gridSize;
    private Boost[] gridBoosts;
    private int[] boostLocationsX;
    private int[] boostLocationsY;    
    private ArrayList<Cell> cellList;
    private String[] playerSideBar;
    private String[] computerSideBar;
    private boolean hasPath;  
   
    
    public Grid()
    {
        this.gameSystem = new GameSystem();
        this.rows = 3;
        this.columns = 3;         
    }

    /**
     * Non default constructor
     * @param gameSystem - The state of the current game
     */
    public Grid(GameSystem gameSystem)
    {
        this.gameSystem = gameSystem;
    }

    public void display()
    {
        this.gameSystem.displayBoosts();
    }

    private void printLegend(String[] gridLegend)
    {
        System.out.printf("%18s  %20s  %20s  %25s  %20s  %20s  %20s  %20s  %20s  %25s %n", gridLegend[0], gridLegend[1], gridLegend[2], gridLegend[3], gridLegend[4], gridLegend[5], gridLegend[6], gridLegend[7], gridLegend[8], gridLegend[9]);
    }

    private void printSideBar(String[] sideBar)
    {
        System.out.println();
        System.out.printf("%20s  %20s  %20s  %17s  %17s  %17s  %17s  %17s  %17s  %17s %n", sideBar[0], sideBar[1], sideBar[2], sideBar[3], sideBar[4], sideBar[5], sideBar[6], sideBar[7], sideBar[8], sideBar[9]);
        System.out.println();
    }

    private String[] generateLegend()
    {
        String[] gridLegend = new String[MAX_GRID_SIZE];
        for(int index = 0; index < gridLegend.length; index++)
        {
            if(index < this.columns)
            {
                gridLegend[index] = String.valueOf(index+1);
            }
            else
            {
                gridLegend[index] = "";
            }
        }

        return gridLegend;
    }
    
    private void generatePlayerSides()
    {
        this.playerSideBar = new String[MAX_GRID_SIZE];
        this.computerSideBar = new String[MAX_GRID_SIZE];
        Player humanPlayer = this.gameSystem.getHumanPlayer();
        Player computerPlayer = this.gameSystem.getComputPlayer();
        for(int index = 0; index < this.playerSideBar.length; index++)
        {
            if(index == 0)
            {
                this.playerSideBar[index] = humanPlayer.getName().toUpperCase();
                this.computerSideBar[index] = computerPlayer.getName().toUpperCase();
            }
            else if(index == 1)
            {
                this.playerSideBar[index] = "[Attack Power: " + humanPlayer.getStats().getAttack() + "]";
                this.computerSideBar[index] = "[Attack Power: " + computerPlayer.getStats().getAttack() + "]";
            }
            else if(index == 2)
            {
                this.playerSideBar[index] = "[Defence Power: " + humanPlayer.getStats().getDefence() + "] [Coins: " + humanPlayer.getStats().getCoins() + "] [Lives: "+ humanPlayer.getStats().livesToHearts() + "]";
                this.computerSideBar[index] = "[Defence Power: " + computerPlayer.getStats().getDefence() + "] [Coins: " + computerPlayer.getStats().getCoins() + "] [Lives: "+ computerPlayer.getStats().livesToHearts() + "]";
            }
            else
            {
                if(index < this.columns)
                {
                    playerSideBar[index] = "-";
                    computerSideBar[index] = "-";
                }
                else
                {
                    playerSideBar[index] = "";
                    computerSideBar[index] = "";
                }                
            }
        }
    } 

    private Boost[] generateRandomBoosts()
    {
        int numberOfBoosts = this.columns < 9 ? (int) (Math.random() * (9 - this.columns)) + this.columns : (int) (Math.random() * 9) + 1;
        
        ArrayList<Boost> boosts = gameSystem.getBoosts();       
        Boost[] randomBoosts = new Boost[numberOfBoosts];
        for(int i = 1; i <= numberOfBoosts; i++)
        {
            int index = (int) (Math.random() * boosts.size());
            Boost curBoost = gameSystem.getBoostAt(index);
            randomBoosts[i-1] = curBoost; // Loop starts at one (as number of boost shouldnt be one) hence the need the for the -1
        }
        return randomBoosts;
    }

    private void generateBoostCordinates()
    {
        this.boostLocationsX = new int[this.gridBoosts.length];
        this.boostLocationsY = new int[this.gridBoosts.length];
        for(int index = 0; index < gridBoosts.length; index++)
        {
            int randomY = (int) (Math.random()*this.columns) + 1;
            int randomX = (int) (Math.random()*this.columns) + 1;            
            this.boostLocationsX[index] = randomX;
            this.boostLocationsY[index] = randomY;
        }
    }

    public void setGridSize(int gridSize)
    {
        this.rows = gridSize;
        this.columns = gridSize;
        this.gridSize = gridSize;
    }

    public int getGridSize()
    {
        return this.gridSize;
    }

    public void generateCells() throws IllegalArgumentException 
    {       
        this.cellList = new ArrayList<>();
        if((this.rows >= MIN_GRID_SIZE) && (this.rows <= MAX_GRID_SIZE) && (this.columns >= MIN_GRID_SIZE) && (this.columns <= MAX_GRID_SIZE))
        {            
            for(int indexRow=0; indexRow < MAX_GRID_SIZE; indexRow++)
            {
                for(int indexCol=0; indexCol < MAX_GRID_SIZE; indexCol++)
                {                    
                    int posX = indexCol + 1;
                    int posY = indexRow + 1;
                    this.cellList.add(new Cell(1,posX, posY));
                    this.cellList.add(new Cell(2,posX, posY));
                }                             
            }
        }
        else
        {
            throw new IllegalArgumentException("Rows or collumn values incorrect for generating a game grid, unable to generate the cells to populate the grid"); 
        }
    }

    public void makeGrid()
    {
        this.gridBoosts = generateRandomBoosts();
        generateBoostCordinates();
        populateRandomBoost();
        populateGridCells();
        String[] gridLegend = generateLegend();
        generatePlayerSides();
        printSideBar(this.playerSideBar);
        printLegend(gridLegend);
        printGrid();      
        printLegend(gridLegend);
        printSideBar(this.computerSideBar);        
    }

    public void updateGrid()
    {
        String[] gridLegend = generateLegend();
        generatePlayerSides();
        printSideBar(this.playerSideBar);
        printLegend(gridLegend);
        printGrid();      
        printLegend(gridLegend);
        printSideBar(this.computerSideBar);
    }

    private void printGrid()
    {
        Cell cellLayerOne = null;
        Cell cellLayerTwo = null;
        String[] gridCellLayerOne = new String[MAX_GRID_SIZE];
        String[] gridCellLayerTwo = new String[MAX_GRID_SIZE];
        for(int indexRow=0; indexRow < rows; indexRow++)
        {
            for(int indexCol=0; indexCol < MAX_GRID_SIZE; indexCol++)
            {
                int posX = indexCol + 1;
                int posY = indexRow + 1;
                cellLayerOne = Cell.queryCellbyXYLayer(this.cellList, posX, posY, 1);
                cellLayerTwo = Cell.queryCellbyXYLayer(this.cellList, posX, posY, 2);           
                // Cells have the content inside of them already
                gridCellLayerOne[indexCol] = cellLayerOne.getCellContents();
                gridCellLayerTwo[indexCol] = cellLayerTwo.getCellContents();
            }
            formattedGridRow(indexRow, gridCellLayerOne, gridCellLayerTwo); // Prints the formatted grid to the screen with the cell contents
        }
    }

    private void formattedGridRow(int index, String[] gridCellLayerOne, String[] gridCellLayerTwo)
    {
        if(index+1 == 10)
        {
            System.out.printf("\r|%d%-3s  %s  %s  %s  %s  %s  %s  %s  %s  %s  %s %d|",index+1,"", gridCellLayerOne[0], gridCellLayerOne[1], gridCellLayerOne[2], gridCellLayerOne[3], gridCellLayerOne[4], gridCellLayerOne[5], gridCellLayerOne[6], gridCellLayerOne[7], gridCellLayerOne[8], gridCellLayerOne[9], index+1);
            System.out.printf("\r\n| %-4s  %s  %s  %s  %s  %s  %s  %s  %s  %s  %s   |","", gridCellLayerTwo[0], gridCellLayerTwo[1], gridCellLayerTwo[2], gridCellLayerTwo[3], gridCellLayerTwo[4], gridCellLayerTwo[5], gridCellLayerTwo[6], gridCellLayerTwo[7], gridCellLayerTwo[8], gridCellLayerTwo[9]);
        }
        else
        {
            System.out.printf("\r|%d%-4s  %s  %s  %s  %s  %s  %s  %s  %s  %s  %s  %d|",index+1,"", gridCellLayerOne[0], gridCellLayerOne[1], gridCellLayerOne[2], gridCellLayerOne[3], gridCellLayerOne[4], gridCellLayerOne[5], gridCellLayerOne[6], gridCellLayerOne[7], gridCellLayerOne[8], gridCellLayerOne[9], index+1);
            System.out.printf("\r\n|%-5s  %s  %s  %s  %s  %s  %s  %s  %s  %s  %s   |","", gridCellLayerTwo[0], gridCellLayerTwo[1], gridCellLayerTwo[2], gridCellLayerTwo[3], gridCellLayerTwo[4], gridCellLayerTwo[5], gridCellLayerTwo[6], gridCellLayerTwo[7], gridCellLayerTwo[8], gridCellLayerTwo[9]);
        }            
        System.out.println();
    }

    private void populateGridCells()
    {
        Cell cellLayerOne = null;
        Cell cellLayerTwo = null;
        String cellContentsOne = "";
        String cellContentsTwo = "";
        for(int indexRow=0; indexRow < MAX_GRID_SIZE; indexRow++)
        {
            for(int indexCol=0; indexCol < MAX_GRID_SIZE; indexCol++)
            {                    
                int posX = indexCol + 1;
                int posY = indexRow + 1;
                if((posX <= this.columns) && (posY <= this.rows))
                {
                    cellLayerOne = Cell.queryCellbyXYLayer(this.cellList, posX, posY, 1);
                    cellLayerTwo = Cell.queryCellbyXYLayer(this.cellList, posX, posY, 2);
                    if(!(cellLayerOne.getHasBoost()))
                    {
                        cellLayerOne.setCellWidth(19);
                        cellContentsOne = cellLayerOne.genertateCellContent(" ");
                        cellLayerOne.setContents(cellContentsOne);
                    }
                    if(!(cellLayerTwo.getHasBoost()))
                    {
                        cellLayerTwo.setCellWidth(19);                        
                        cellContentsTwo = cellLayerTwo.genertateCellContent(" ");                       
                        cellLayerTwo.setContents(cellContentsTwo);
                    }                    
                }
                else
                {
                    cellLayerOne = Cell.queryCellbyXYLayer(this.cellList, posX, posY, 1);
                    cellLayerTwo = Cell.queryCellbyXYLayer(this.cellList, posX, posY, 2);
                    String cellContents = "";
                    cellLayerOne.setCellWidth(1);
                    cellLayerTwo.setCellWidth(1);
                    cellContentsOne = cellLayerOne.genertateCellContent(cellContents);
                    cellContentsTwo = cellLayerTwo.genertateCellContent(cellContents);
                    cellLayerOne.setContents(cellContentsOne);
                    cellLayerTwo.setContents(cellContentsTwo);
                }
            }                             
        }
    }

    private Boost getRandomBoost(int index)
    {
        return this.gridBoosts[index];
    }

    private void updateGridBoostCell(Cell cellToUpdate, int index)
    {
        cellToUpdate.setHasBoost(true);
        Boost boost = getRandomBoost(index);
        int damage = boost.getDamage();
        int defence = boost.getDefence();
        int coins = boost.getCoins();
        String contents = cellToUpdate.formatBoostCell(damage, defence, coins);
        contents = cellToUpdate.genertateCellContent(contents);
        cellToUpdate.setContents(contents);
        cellToUpdate.setBoost(boost);
    }
    
    private void populateRandomBoost()
    {        
        Cell cellToUpdate = null;  
        for(int index = 0; index <= this.boostLocationsX.length - 1; index++)
        {
            int posX = this.boostLocationsX[index];
            int posY = this.boostLocationsY[index];
            cellToUpdate = Cell.queryCellbyXYLayer(this.cellList, posX, posY, 1);      
            if(cellToUpdate.getHasBoost())
            {
                // Add Boost to Layer 2 cell
                cellToUpdate = Cell.queryCellbyXYLayer(this.cellList, posX, posY, 2);
                updateGridBoostCell(cellToUpdate, index);
            }
            else
            {
                updateGridBoostCell(cellToUpdate, index);
            }
        }
    }

    public ArrayList<Cell> getCellList()
    {
        return this.cellList;
    }

    public boolean getHasPath()
    {
        return this.hasPath;
    }

    public void setHasPath(boolean hasPath)
    {
        this.hasPath = hasPath;
    }
}
