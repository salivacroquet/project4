//Import Section
import java.util.Scanner;


public class Main{
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
        
        //create minefield
        Minefield minefield = null;
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
            //indicates user wants to place a flag
            if (choice==1) {
                minefield.guess(x, y, true);
            }
            //indicates user wants to reveal a cell
            else if (choice==0) {
                minefield.guess(x, y, false);
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
}
