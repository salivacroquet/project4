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
    //main, runs through the game
    public static void main(String[] args) {
        //initialize variables
        int rows = 0; //number of rows
        int cols = 0; //number of columns
        int mines = 0; //total number of mines
        int flags = 0; //total number of flags
        boolean debug=false; //True if debug mode selected
        boolean win=false; //True if the player wins
        Scanner scanner = new Scanner(System.in);
        Minefield minefield = null; //the minefield


        //ask user for input on mode
        System.out.println("Would you like to play Easy Mode: 1, Medium Mode: 2, or Hard Mode: 3?");
        String mode = scanner.next();
        //accounts for incorrect user input
        while(!mode.equals("1") && !mode.equals("2") && !mode.equals("3") ) {
            System.out.println("Not a valid input.");
            System.out.println("Would you like to play Easy Mode: 1, Medium Mode: 2, or Hard Mode: 3?");
            mode= scanner.next();
            //sets size for each mode
        }
        //sets size of game and amount of flags and mines for each level
        if (mode.equals("1")) {//easy
            rows = 5;
            cols = 5;
            mines = 5;
            flags = 5;
        } else if (mode.equals("2")) {//medium
            rows = 9;
            cols = 9;
            mines = 12;
            flags = 12;
        } else if (mode.equals("3")) {//hard
            rows = 20;
            cols = 20;
            mines = 40;
            flags = 40;
        }

        //creates minefield with indicated size, flags, and mines
        minefield = new Minefield(rows, cols, flags);

        //asks user for input on debug mode
        System.out.println("Would you like to play in debug mode: yes/no");
        String output = scanner.next();
        //accounts for incorrect user input, spelling or capitalization
        while(!output.equals("yes") && !output.equals("no")){
            System.out.println("Not a valid input.");
            System.out.println("Would you like to play in debug mode: yes/no");
            output = scanner.next();
            //user indicated debug mode
        }if(output.equals("yes")){
            minefield.debug();
            System.out.println(" ");
            System.out.println(minefield);
            debug=true; //debug set to true
            //user indicated not to do debug mode
        }else if(output.equals("no")){
            System.out.println(minefield);
            debug=false; //debug set to false
        }

        //asks user for initial move
        System.out.println("What is your first move? [x],[y]:");
        String input = scanner.next();
        String[] firstMove = input.split(",");
        //accounts for incorrect user input, input out of bounds
        while(!checkInput(firstMove,rows, cols, 2)){
            System.out.println("Not a valid input.");
            scanner.nextLine();//go to next line
            System.out.println("What is your first move? [x],[y] :");
            input = scanner.next();
            firstMove = input.split(",");
        }
        int firstX= Integer.parseInt(firstMove[0]);
        int firstY= Integer.parseInt(firstMove[1]);
        //places mines
        minefield.createMines(firstX,firstY,mines);
        minefield.evaluateField();
        //reveals the starting area
        minefield.revealStartingArea(firstX,firstY);
        System.out.println(" ");

        //while the game is not over
        while (!minefield.gameOver()) {
            //print the game
            //if debug mode is true
            if(debug){
                //displays fully revealed board
                minefield.debug();
                System.out.println(" ");
                //displays player game board
                System.out.println(minefield);
            }else{
                //displays player game board
                System.out.println(minefield);
            }

            //asks for input
            System.out.println("What is your move? [x],[y],[flag:1 OR reveal:0] ");
            input = scanner.next();
            String[] move = input.split(",");
            //accounts for incorrect user input, input out of bounds
            while(!checkInput(move,rows, cols, 3) ||
                    (Integer.parseInt(move[2]) != 0 && Integer.parseInt(move[2]) != 1)){
                System.out.println("Not a valid input.");
                scanner.nextLine(); //go to next line
                System.out.println("What is your move? [x] [y] [flag:1 OR reveal:0] ");
                input = scanner.next();
                move = input.split(",");
            }

            int x= Integer.parseInt(move[0]);
            int y= Integer.parseInt(move[1]);
            int choice = Integer.parseInt(move[2]);
            //indicates user wants to place a flag
            if (choice==1) {
                minefield.guess(x, y, true);
            }
            //indicates user wants to reveal a cell
            else if (choice==0) {
                minefield.guess(x, y, false);
            }

            if(!minefield.getValid()){
                System.out.println("Invalid move, try again.");
            }
        }
        //if debug mode is true
        if(debug){
            //displays fully revealed board
            minefield.debug();
            System.out.println(" ");
            //displays player game board
            System.out.println(minefield);
        }else{
            //displays player game board
            System.out.println(minefield);
        }

        //end while loop, game is over
        System.out.println("Game Over");
        if(minefield.getWin()){
            //if getWin is true, user won
            System.out.println("You Won!");
        }else{
            //otherwise, user lost
            System.out.println("You Lost!");
        }
    }


    //A helper function: that checks if the input is valid
    private static boolean checkInput(String[] input, int rows, int cols, int splitNumber) {
        if (input.length == 0){//if the input does not have a comma it is invalid
            return false;
        }
        //if the input is not a number it is invalid
        for (int i = 0; i<input.length; i++){
            try {
                Integer.parseInt(input[i]);
            } catch(NumberFormatException e){
                return false;
            }
        }
        //if the input is out of bounds it is invalid
        if (input.length!=splitNumber || Integer.parseInt(input[0])<0 ||
                Integer.parseInt(input[0])>rows ||
                Integer.parseInt(input[1])<0 ||
                Integer.parseInt(input[1])>cols)
            return false;
        //otherwise it is a valid input
        return true;
    }
}

