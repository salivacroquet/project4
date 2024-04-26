Group members’ names and x500s:
Sylvia Croatt croat038
Hajar Ahmed ahme0635

Contributions of each partner (if working with a partner):
Sylvia: 
  - Minefield:
      -Constructor
      -evaluateField
      -createMines
      -guess
      -revealZeros
  -main:
      -main
      
Hajar:
  -Minefield:
      -gameOver
      -debug
      -toString
      -revealStartingArea
      -reveal zeros
  -main:
    -main
    -checkInput
      

How to compile and run your program:
The program can compile and run in the terminal or through inteliJ. 

Any assumptions/Notes:
-Note that we DO NOT allow the player to remove flags, once they have flagged a space it
 will stay flagged for the entire game.

Any known bugs or defects in the program:
-sometimes revealZeros does NOT reveal all the zeros that should be revealed
-formatting is strange for Hard mode

Additional features that you implemented (if applicable):
-Added a Helper function in main checkInput() that checks if an input is valid (i.e. it is inbounds, a number, and the correct format).
-Added getters for two variables in minefield, win:a boolean that is true if the player wins, valid: a boolean that is true if the move made is valid.

Any outside sources (aside from course resources) consulted for ideas used in the project:
-NumberFormatException: https://stackoverflow.com/questions/19149784/java-java-lang-numberformatexception

I certify that the information contained in this README
file is complete and accurate. I have both read and followed the course policies
in the ‘Academic Integrity - Course Policy’ section of the course syllabus.

- Hajar Ahmed
-Sylvia Croatt
