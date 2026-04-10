/* Functions Needed:
 * Grid Generator
 *      -Take in width and Length and bomb amount   *
 *      -Generate Tiles                             *
 *      -Randomly Place bombs                       *
 *      -if not bomb, check how many bombs surround *
 *
 * Check if bomb
 *      -When tile is selected                      *
 *      -check if bomb, if not, reveal number       *
 *      -if bomb, end game                          ~ kinda
 * 
 * Draw Game Table
 *      -start with printing                        *
 *      -Use buttons to display game board          *
 *      -Display timer that tracks how long current
 *       game has gone on in Seconds
 * 
 * Flagging
 *      -mark bombs with flags by right-clicking
 *      -right click flag to replace it with ?
 *      -keep track of flags as user places them
 * 
 * Difficulty Selection
 *      -Allow User to select difficulty            *
 *      -Offer Easy, Medium, Hard, & Custom         ~ need custom
 *      -Allow user to define custom difficulty;
 *       let them choose dimensions and amount of bombs
 */

public class App
{
    public static void main(String[] args) throws Exception 
    {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() 
            {
                SweeperUI ui = new SweeperUI();
                ui.showDifficultyWindow();
            }
        });
    }
}