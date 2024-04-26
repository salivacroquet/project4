//Import Section
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

public class Main {
    public static void main(String[] args) {
        int rows = 0;
        int cols = 0;
        int mines = 0;
        int flags = 0;
        Scanner scanner = new Scanner(System.in);
        Minefield minefield = null;
        System.out.println("Would you like to play Easy Mode: 1, Medium Mode: 2, or Hard Mode: 3?");
        int mode = scanner.nextInt();
        //sets size for each mode
        if (mode == 1) {
            rows = 5;
            cols = 5;
            mines = 5;
            flags = 5;
        } else if (mode == 2) {
            rows = 9;
            cols = 9;
            mines = 12;
            flags = 12;
        } else if (mode == 3) {
            rows = 20;
            cols = 20;
            mines = 40;
            flags = 40;
        } else {
            System.out.println("Not a valid input.");
        }
        minefield = new Minefield(rows, cols, flags);

        System.out.println("What is your initial pick? (x y ): ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        minefield.revealStartingArea(x, y);
        System.out.println(minefield.toString());

        while (!minefield.gameOver()) {
            System.out.println("Would you like to play in debug mode?: ");
            String debugChoice = scanner.next();
            if (debugChoice.equals("yes") || debugChoice.equals("Yes")) {
                minefield.debug();
            } else {
                //prints game
                System.out.println(minefield.toString());
            }

            //asks for input
            System.out.println("What is your move? (x y flag/reveal): ");
            x = scanner.nextInt();
            y = scanner.nextInt();
            String choice = scanner.next();

            if (choice.equals("flag") || choice.equals("Flag")) {
                minefield.guess(x, y, true);
            }
            else if (choice.equals("reveal") || choice.equals("Reveal")) {
                minefield.guess(x, y, false);
            } else {
                System.out.println("Not a valid input.");
            }
        }

        System.out.println("Game Over.");
            minefield.gameOver();
    }
}

