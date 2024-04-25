//Import Section
import java.util.Random;
import java.util.Scanner;

/*
 * Provided in this class is the neccessary code to get started with your game's implementation
 * You will find a while loop that should take your minefield's gameOver() method as its conditional
 * Then you will prompt the user with input and manipulate the data as before in project 2
 * 
 * Things to Note:
 * 1. Think back to project 1 when we asked our user to give a shape. In this project we will be asking the user to provide a mode. Then create a minefield accordingly
 * 2. You must implement a way to check if we are playing in debug mode or not.
 * 3. When working inside your while loop think about what happens each turn. We get input, user our methods, check their return values. repeat.
 * 4. Once while loop is complete figure out how to determine if the user won or lost. Print appropriate statement.
 */

public class main(String[] args) {
    int rows;
    int cols;
    int mines;
    int flags;
    Scanner scanner = new Scanner(System.in);
    System.out.println("Would you like to play Easy Mode: 1, Medium Mode: 2, or Hard Mode: 3?");
    int mode = scanner.nextInt();
    if (mode == 1) {
        rows = 5;
        cols = 5;
        mines = 5;
        flags = 5;
        Minefield minefield = new Minefield(rows, cols, flags );
        //place mines
        //evaluate
        //starting area

    }
    if (mode == 2) {
        rows = 9;
        cols = 9;
        mines = 12;
        flags = 12;
        Minefield minefield = new Minefield(rows, cols, flags );
    }
    if (mode == 3) {
        rows = 20;
        cols = 20;
        mines = 40;
        flags = 40;
        Minefield minefield = new Minefield(rows, cols, flags );
    }
    while(/* ! minefield.gameOver() */ ){
    }
    
}
