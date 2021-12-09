import java.util.ArrayList;

public class Cell {
    
    private int posX;
    private int posY;
    private int layer;
    private boolean isCaptured;
    private Player owner;
    private Player capturedBy;
    private Player lostBy;
    private boolean hasBoost;
    private Boost boost;
    private String contents;
    private int cellWidth;

    public Cell()
    {
        this.posX = 0;
        this.posY = 0;
        this.layer = 1;
        this.isCaptured = false;
        this.hasBoost = false;
        this.contents = "";
        this.cellWidth = 19;
    }

    public Cell(int posX, int posY, int layer, boolean isCaptured, boolean hasBoost, String contents)
    {
        this.posX = posX;
        this.posY = posY;
        this.layer = layer;
        this.isCaptured = isCaptured;
        this.hasBoost = hasBoost;
        this.contents = contents;
    }

    public Cell(int layer, int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
        this.layer = layer;
        this.isCaptured = false;
        this.hasBoost = false;
        this.contents = "";
        this.cellWidth = 19;
    }

    public String display()
    {
        return "posX: "+this.posX+" posY: "+this.posY+" layer: "+this.layer+" isCaptured: "+this.isCaptured+" owner: "+this.owner+" capturedBy: "+this.capturedBy+" lostBy: "+this.lostBy+" hasBoost: "+this.hasBoost+" contents: "+this.contents+" cellWidth: "+this.cellWidth;
    }

    public int getPosX()
    {
        return this.posX;
    }

    public int getPosY()
    {
        return this.posY;
    }

    public int getLayer()
    {
        return this.layer;
    }

    public boolean getCapturedStatus()
    {
        return this.isCaptured;
    }

    public Player getCellOwner()
    {
        return this.owner;
    }

    public Player getCapturedBy()
    {
        return this.capturedBy;
    }

    public Player getLostBy()
    {
        return this.lostBy;
    }

    public boolean getHasBoost()
    {
        return this.hasBoost;
    }

    public Boost getBoost()
    {
        return this.boost;
    }

    public String getCellContents()
    {
        return this.contents;
    }

    public int getCellWidth()
    {
        return this.cellWidth;
    }

    public void setPosX(int posX)
    {
        this.posX = posX;
    }

    public void setPosY(int posY)
    {
        this.posY = posY;
    }

    public void setLayer(int layer)
    {
        this.layer = layer;
    }

    public void setCapturedStatus(boolean isCaptured)
    {
        this.isCaptured = isCaptured;
    }

    public void setOwner(Player player)
    {
        this.owner = player;
    }

    public void setCapturedBy(Player capturedBy)
    {
        this.capturedBy = capturedBy;
    }

    public void setLostBy(Player lostBy)
    {
        this.lostBy = lostBy;
    }

    public void setHasBoost(boolean hasBoost)
    {
        this.hasBoost = hasBoost;
    }

    public void setBoost(Boost boost)
    {
        this.boost = boost;
    }

    public void setContents(String contents) throws IllegalArgumentException
    {
        if(contents.length() <= this.cellWidth+2)
        {
            this.contents = contents;
        }
        else
        {
            throw new IllegalArgumentException("Cell contents have a maximum number of characters of " + this.cellWidth);
        }   
    }

    public void setCellWidth(int cellWidth)
    {
        this.cellWidth = cellWidth;
    }

    public String genertateCellContent(String cellContent) {
        String output = "";
        if(this.cellWidth > 1)
        {
            output = String.format("[%1$"+this.cellWidth+ "s]", cellContent);
        }
        else
        {
            output = "";
        }        
        return output;
    }

    public static Cell queryCellbyXYLayer(ArrayList<Cell> cellList, int posX, int posY, int layer)
    {
        Cell cell = null;
        for(Cell cellItem: cellList)
        {
            if((cellItem.posX == posX) && (cellItem.posY == posY) && (cellItem.layer == layer))
            {
                cell = cellItem;
                break;
            }
        }
        return cell;
    }

    public String formatBoostCell(int damage, int defence, int coins)
    {
        return "A:"+damage+" D:"+defence+" C:"+coins;
    }

}
