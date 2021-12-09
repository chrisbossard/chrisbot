# Java Fields
![image](https://user-images.githubusercontent.com/30018218/145399401-cb8ac56b-c5c2-4ca0-95aa-821bec995f2e.png)

Java Field is an Assignment for Monash University ITO4131 Java programming

## Game Play
* When the game begins the system will read the file called ‘boosts.txt’. This file contains a list of boosts and their benefit/drawback to the player which have to be placed on the playing grid. The minimum playing grid for the game is 3x3 and the maximum is 10x10. The following details are to be read from the file:
  * Damage – the boost to the player’s damage stats
  * Defence – the boost to the player’s defence stats
  * Coins – the coins a player can find on the game grid.
* When the game beings, both players are given an attack stat of 5 and defence stat of 7. However the human player starts with a total of 3000 coins but the computer player starts with a total of 10,000 coins.
* Next, the game prompts the user to enter their name which must be between 3 and 12 characters respectively. Should the user enter a name which does not meet this criteria, the user is then asked to re-enter until the criteria has been met.
* Once the user has successfully entered their name, the game will ask the player to input the playing grid size. The input must be validated to ensure it doesn’t fall below the minimum grid size or above the maximum grid size mentioned above.
* Once this has been entered the game will then randomly place the boosts read from the file onto the playing grid and display the grid to the player along with the following menu options:
  * Press 1 to capture a grid spot
  * Press 2 to sabotage the enemy
  * Press 3 to direct strike a heart
  * Note: Multiple boosts can be placed on the same grid space.
* The user must make a choice from one of the above options. The game should perform basic validation to ensure correctness here.
For each of the above options, this is the required functionality:
### Capturing a grid spot
* When the human player chooses this option, the player is asked to enter the x and y coordinate on the grid they would like to capture. The game must accept these inputs and validate them for the grid size the game is being played upon.
* The game will then roll three dice having values between 1 and 6 for the player and two separate dice having values between 1 and 6 for the computer.
* Since the player is attacking their total attack is the sum of the dice face value + their attack stat. The defending player calculates their defence stat as the total of the sum of the dice face value + their defence stat.
* Only if the attacking player’s total attack is greater than the opponent’s defence, the grid spot is captured.
* A player can capture a grid spot which is already captured by their opponent. The same process described above must be done.
* Once captured, if a grid spot has benefits or drawbacks these must be applied to the player’s stats. If an opponent captures this spot from the player, the stats should be reversed. Coins can only be captured once by the first player to capture the grid space. During the game, coins can go negative if landing on a grid space that has a negative coin value.
### Sabotage the enemy
* The human player can choose to sabotage the enemy by choosing from one of the following options:
  * Press 1 to decrement the opponents attack by 2.
  * Press 2 to decrement the opponents defence by 2.
  * Press 3 to sabotage an opponent’s grid square
* The functionality for each of the above options are as follows:
  * Decrease attack -
    The game will prompt the user a random amount of coins between 500 and 1500 which is the cost. If the player accepts, their coins are reduced and the opponent’s attack is reduced by 2.
  * Decrease defense -
The game will prompt the user a random amount of coins between 500 and 1500 which is the cost. If the player accepts, their coins are reduced and the opponent’s defence is reduced by 2.
  * Sabotage a grid square -
The game will prompt the user a random amount of coins between 1000 and 2500 which is the cost. If the player accepts, their coins are reduced and the player must specify the coordinates of the grid position they would like to sabotage. In order to sabotage, the grid position MUST be captured by the opponent.
  * Direct strike at heart -
This option is only available if, at the time of the strike, the player has captured a complete path from their end of the field all the way to the opponent’s end of the field. For simplicity, a path is considered to be all grid spaces in a straight line that have been captured.
Should a player have a complete path, only then they can use this option. This reduces the number of hearts the opponents has by 1.
* The player can only choose a single choice in any given turn.
* Once the player has performed their action, the computer player’s turn begins as follows:
* Similar to the player, the computer can randomly select from one of the following three options:
* Capture a grid spot
  * If this choice is randomly selected, the game should randomly select a grid space and attempt to capture it.
The capture process is the same as mentioned above for the human player.
  * On turn one, this must be the default choice for the computer.
* Sabotage the enemy
If this choice is randomly selected, the game should randomly select one of the following choice:
 * Decrement attack by 2
   * The process is the same as mentioned above for the human playe
   * The probability of this choice is 40%
 * Decrement defence by 2
   * The process is the same as mentioned above for the human playeR
   * The probability of this choice is 40%
* Sabotage an opponent’s grid square
  * The process is the same as mentioned above for the human player
  * The probability of this choice is 20% (the programmer can choose how to implement this provided they justify their reasoning).
* If a player has a complete path, the computer must always select this choice irrespective of the defined probabilities.
Direct strike at heart
Again, this option is only available if, at the time of the strike, the computer player has captured a complete path from their end of the field all the way to the opponent’s end of the field. For simplicity, a path is considered to be all grid spaces in a straight line that have been captured.
* Should a player have a complete path, only then they can use this option. This reduces the number of hearts the opponents has by 1.
* The game continues until either player has lost all three hearts.
* When the game ends, the game should write the final outcome to the file, outcome.txt, which includes the player name, the number of turns played, the attack and defence stats, the number of grid squares captured, the number of grid squares lost, and the final outcome.

## The following are the requirements for your program:

* You must use the workspace environment in the Ed platform to code all parts of your program. You must not copy paste huge chunks of code from somewhere else.
* You must ensure that your program code meets the coding standard requirements outlined in the course.
* You must ensure to use appropriate collections within your program and be able to justify your choice.
* You must appropriate good design within your program by correctly applying abstraction and modularization techniques.
* Your program must only read and write to the file one time (at the start of the program to read and at the end of the program to write)
* Your program must ensure to use exception handling correctly. Your program must not crash no matter what the user enters.
* Your program must use arrays or arraylists only. Two dimensional arrays must NOT be used.
* The main class in your program MUST be called Field.java and it should contain the main() method to start the program.
