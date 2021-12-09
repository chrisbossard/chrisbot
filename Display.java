public class Display {
    
    private String playerInputMenu;
    private String computerInputMenu;
    private String sabotageMenu;
    private String playerInputXCoords;
    private String playerInputYCoords;
    private String welcomeMsg;
    public final int BLANK_MESSAGE_LEN = 173;

    public Display()
    {
        this.playerInputMenu = playerInputMenu("","","");
        this.sabotageMenu = sabotageMenu("","","");
        this.playerInputXCoords = playerInputXCoords();
        this.playerInputYCoords = playerInputYCoords();
        this.computerInputMenu = computersTurn("","","");
        this.welcomeMsg = createWelcomeMsg();
    }

    public void setPlayerInputMenu(String messageOne, String messageTwo, String messageThree)
    {
        this.playerInputMenu = playerInputMenu(messageOne,messageTwo,messageThree);
    }

    public void setSabotageMenu(String messageOne, String messageTwo, String messageThree)
    {
        this.sabotageMenu = sabotageMenu(messageOne,messageTwo,messageThree);
    }

    public void setComputerTurnMsg(String messageOne, String messageTwo, String messageThree)
    {
        this.computerInputMenu = computersTurn(messageOne,messageTwo,messageThree);
    }

    public void setCostAcceptedMsg(String messageOne, String messageTwo, String messageThree)
    {
        this.sabotageMenu = costSabotageMenuAccepted(messageOne, messageTwo, messageThree);
    }

    public void setCostRejectedMsg(String messageOne, String messageTwo, String messageThree)
    {
        this.sabotageMenu = costSabotageMenuRejected(messageOne, messageTwo, messageThree);
    }

    public void setPlayerInputXCoords(String inputXCoords)
    {
        this.playerInputXCoords = inputXCoords;
    }

    public void setPlayerInputYCoords(String inputYCoords)
    {
        this.playerInputYCoords = inputYCoords;
    }

    public String getPlayerInputMenu()
    {
        return this.playerInputMenu;
    }

    public String getComputerInputMenu()
    {
        return this.computerInputMenu;
    }

    public String getSabotageMenu()
    {
        return this.sabotageMenu;
    }

    public String getPlayerInputXCoords()
    {
        return this.playerInputXCoords;
    }

    public String getPlayerInputYCoords()
    {
        return this.playerInputYCoords;
    }

    public String getWelcomeMsg()
    {
        return this.welcomeMsg;
    }

    private String playerInputMenu(String messageOne, String messageTwo, String messageThree)
    {
        String output = String.format( 
          "\rPress 1 to capture a grid spot                  \t\t|\t%s" 
        + "\nPress 2 to sabotage the enemy                   \t\t|\t%s"
        + "\nPress 3 to direct strike a heart                \t\t|\t%s",messageOne, messageTwo, messageThree);

        return output;
    }



  public String createWelcomeMsg() 
  {
      String output = String.format(
          "\r     ___                   ______ _      _     _      "
        + "\n    |_  |                  |  ___(_)    | |   | |     " 
        + "\n      | | __ ___   ____ _  | |_   _  ___| | __| |___  "
        + "\n      | |/ _` \\ \\ / / _` | |  _| | |/ _ \\ |/ _` / __| "
        + "\n  /\\__/ / (_| |\\ V / (_| | | |   | |  __/ | (_| \\__ \\ "
        + "\n  \\____/ \\__,_| \\_/ \\__,_| \\_|   |_|\\___|_|\\__,_|___/ "
        + "\n%s", "Welcome to Java Fields the game!");
      
        return output;
  }

    public String computersTurn(String messageOne, String messageTwo, String messageThree)
    {
        String output = String.format(               
            "\rComputer is choosing what to do                 \t\t|\t%s" 
          + "\nnext!                                           \t\t|\t%s"
          + "\nPress Enter to continue...                      \t\t|\t%s",messageOne, messageTwo, messageThree);
  
          return output;
    }

    public String sabotageMenu(String messageOne, String messageTwo, String messageThree)
    {
        String output = String.format( 
            "\rPress 1 to decrement the opponents attack by 2. \t\t|\t%s" 
          + "\nPress 2 to decrement the opponents defence by 2.\t\t|\t%s"
          + "\nPress 3 to sabotage an opponent’s grid square   \t\t|\t%s",messageOne, messageTwo, messageThree);
  
          return output;
    }
    
    public String costSabotageMenuAccepted(String messageOne, String messageTwo, String messageThree)
    {
        String output = String.format( 
            "\rPress 1 to accept the cost                      \t\t|\t%s" 
          + "\nPress 2 to reject the cost                      \t\t|\t%s"
          + "\n                                                \t\t|\t%s",messageOne, messageTwo, messageThree);
  
          return output;
    }

    public String costSabotageMenuRejected(String messageOne, String messageTwo, String messageThree)
    {
        String output = String.format( 
            "\rSorry you do not have enough                    \t\t|\t%s" 
          + "\ncoins to cover the cost of this sabotage        \t\t|\t%s"
          + "\nPress enter to continue...                      \t\t|\t%s",messageOne, messageTwo, messageThree);
  
          return output;
    }

    private String playerInputXCoords()
    {
        String output =
        "Enter the X coordinate you want to capture:";

        return output;
    }

    private String playerInputYCoords()
    {
        String output =
        "Enter the Y coordinate you want to capture:";

        return output;
    }
}
