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

public class main{
    public static void main(String[] args) {
        int rows = 0;
        int cols = 0;
        int mines = 0;
        int flags = 0;
        boolean debug=false;
        boolean win=false;
        Scanner scanner = new Scanner(System.in);
        Minefield minefield = null;
        System.out.println("Would you like to play Easy Mode: 1, Medium Mode: 2, or Hard Mode: 3?");
        int mode = scanner.nextInt();
        while(mode!=1 && mode!=2 && mode !=3) {
            System.out.println("Not a valid input.");
            System.out.println("Would you like to play Easy Mode: 1, Medium Mode: 2, or Hard Mode: 3?");
            mode = scanner.nextInt();
            //sets size for each mode
        }
        if (mode == 1) {//easy
            rows = 5;
            cols = 5;
            mines = 5;
            flags = 5;
        } else if (mode == 2) {//medium
            rows = 9;
            cols = 9;
            mines = 12;
            flags = 12;
        } else if (mode == 3) {//hard
            rows = 20;
            cols = 20;
            mines = 40;
            flags = 40;
        }

        minefield = new Minefield(rows, cols, flags);

        System.out.println("Would you like to play in debug mode: yes/no");
        String output = scanner.next();
        while(!output.equals("yes") && !output.equals("no")){
            System.out.println("Not a valid input.");
            System.out.println("Would you like to play in debug mode: yes/no");
            output = scanner.next();

        }if(output.equals("yes")){
            minefield.debug();
            System.out.println(" ");
            System.out.println(minefield);
            debug=true;
        }else if(output.equals("no")){
            System.out.println(minefield);
            debug=false;
        }

        System.out.println("What is your first move? [x],[y]:");
        String input = scanner.next();
        String[] firstMove = input.split(",");
        while(firstMove.length!=2 || Integer.parseInt(firstMove[0])<0 ||
                Integer.parseInt(firstMove[0])>rows ||
                Integer.parseInt(firstMove[1])<0 ||
                Integer.parseInt(firstMove[1])>cols){
            System.out.println("Not a valid input.");
            System.out.println("What is your first move? [x],[y] :");
            input = scanner.next();
            firstMove = input.split(",");
        }
        int firstX= Integer.parseInt(firstMove[0]);
        int firstY= Integer.parseInt(firstMove[1]);

        minefield.createMines(firstX,firstY,mines);
        minefield.evaluateField();
        minefield.revealStartingArea(firstX,firstY);
        System.out.println(" ");


        while (!minefield.gameOver()) {
            //print the game
            if(debug){
                minefield.debug();
                System.out.println(" ");
                System.out.println(minefield);
            }else{
                System.out.println(minefield);
            }

            //asks for input
            System.out.println("What is your move? [x],[y],[flag:1 OR reveal:0] ");
            input = scanner.next();
            String[] move = input.split(",");
            while(move.length!=3 || Integer.parseInt(move[0])<0 || Integer.parseInt(move[0])>rows ||
                    Integer.parseInt(move[1])<0 || Integer.parseInt(move[1])>cols ||
                    (Integer.parseInt(move[2]) != 0 && Integer.parseInt(move[2]) != 1)){

                System.out.println("Not a valid input.");
                System.out.println("What is your move? [x] [y] [flag:1 OR reveal:0] ");
                input = scanner.next();
                move = input.split(",");
            }

            int x= Integer.parseInt(move[0]);
            int y= Integer.parseInt(move[1]);
            int choice = Integer.parseInt(move[2]);

            if (choice==1) {
                minefield.guess(x, y, true);
            }
            else if (choice==0) {
                minefield.guess(x, y, false);
            }
        }

        System.out.println("Game Over");
        if(minefield.getWin()){
            System.out.println("You Won!");
        }else{
            System.out.println("You Lost!");
        }

    }

}
