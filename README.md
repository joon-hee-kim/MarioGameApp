# Mobile Programming with Java
This is the result of Mario game app that I created.


## Motivation
I was interested in the Nintendo Mario game, so I created a Mario game app using Java in Android Studio by referring to YouTube and Google on my own.


## Project Structure
<img width="897" alt="스크린샷 2024-06-18 오전 11 44 29" src="https://github.com/joon-hee-kim/MarioGameApp/assets/121689436/643e6acd-5675-4cf4-99d8-e3e5ece96968"> </br>

1. MainActivity.java: </br>
- Serves as the main entry point to the application. (Two-player play has not been implemented yet) </br>
 
2. SelActivity.java: </br>
- Allow users to select a character (Mario, Luiji) before starting the game. </br> 

3. WldActivity.java: </br>
- Allows users to select which world (level) they want to play in the game. World = Cupa Junior, Cupa. “Cupa” == “Bowser” </br>
 
4. PlayActivity.java: </br>
- This is the main activity where actual game play takes place. </br>
- Handles game logic such as game loops, user input, collisions, score updates, game over conditions, etc. </br>
 
5. Game.java: </br>
- This file contains the core game logic and mechanics. </br>
- Manages game state, updates game elements (e.g. Mario and enemies), and renders game graphics. </br>
- Handles interactions between game objects and maintains game rules. </br>

6. EndActivity.java: </br>
- The game end screen is displayed with some reference to actual Nintendo Mario game footage and shows the final score or result. </br>
- When finished, go back to MainActivity.java. </br>
 
7. DeadActivity.java: </br>
- Handles scenarios when a player loses a life. </br>
- When finished, go back to MainActivity.java. </br>

## Output
* **Video** of "Mario vs Bowser" (**A little loading!**) </br>
![mario_vs_bowser](https://github.com/joon-hee-kim/MarioGameApp/assets/121689436/024d3365-4935-451a-9b5a-57a70d94c2bf) </br></br>

* **Photo** of ""Mario vs Bowser Junior" </br>
<a href="https://github.com/joon-hee-kim/MarioGameApp">
    &nbsp;&nbsp;&nbsp;&nbsp;<img src="https://github.com/joon-hee-kim/MarioGameApp/assets/121689436/8855a495-363d-4008-b617-315b74f8b710" width="55%" height="55%">
</a> </br></br>

* **Photo** of ""Luiji vs Bowser Junior" </br>
<a href="https://github.com/joon-hee-kim/MarioGameApp">
    &nbsp;&nbsp;&nbsp;&nbsp;<img src="https://github.com/joon-hee-kim/MarioGameApp/assets/121689436/bee199d3-1a55-4b38-ae17-619385191e1d" width="55%" height="55%">
</a> </br></br>

* **Photo** of "Luiji vs Bowser" </br>
<a href="https://github.com/joon-hee-kim/MarioGameApp">
    &nbsp;&nbsp;&nbsp;&nbsp;<img src="https://github.com/joon-hee-kim/MarioGameApp/assets/121689436/e0d2d2b3-1f4e-4873-ade8-140fbaf59192" width="55%" height="55%">
</a> </br></br>

 
## ✔️ Source
* Youtube video you referenced: [Reference Link](https://www.youtube.com/watch?v=i-Wg6oc8X04&t=1s) </br>
* GitHub source of the referenced YouTuber: [Reference Link](https://github.com/jgs901221/Android-basic-game-making-source) </br>
