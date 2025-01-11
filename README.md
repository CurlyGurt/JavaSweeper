## JavaSweeper
It may sound wierd, but I've recently been obsessed with playing MineSweeper on my phone. I used to play it as a kid on my family's Windows 95 computer, even though my child self had no idea how to play correctly. I think of the time I spent on that old, beige, box fondly, so I found an Android version that was true to the original. While I was playing one day, I randomly started to realize how simple the game logic was and started theorizing how various pieces function. 

It eventually dawned on me that it would be fun (and a nice programming challenge) to attempt to reverse-engineer MineSweeper and create my own, and thus this project was born. I've decided that for this project I will not use ANY tutorials, instead relying purely on my own intuition and official documentation.

After this project is finished up, I'd like to attempt to reverse-engineer another simple game from my childhood. Currently I'm thinking Galaga or Space Cadet Pinball!

## Reverse Engineering: Things I noticed
I started taking mental note of all of the systems that would be required for the game to function. First, I would need a simple grid system to place bombs onto. To create a grid, I decided to use a 2D array of "Tile" objects. The "Tile" object represents a single tile in MineSweeper. It tracks several things, such as whether the tile is revealed, if the tile is a bomb, or how many bombs surround the tile.

I then realized I would need a system that places bombs randomly. To do this, I simply 
go through grid and update tile number according to bombs
