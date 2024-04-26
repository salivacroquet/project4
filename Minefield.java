//Created by Sylvia Croatt, croat038 and Hajar Ahmed, ahme0635

import java.util.Queue;
import java.util.Random;

public class Minefield {
    /**
     * Global Section
     */
    public static final String ANSI_YELLOW_BRIGHT = "\u001B[33;1m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_PURPLE = "\u001b[35m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001b[47m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001b[45m";
    public static final String ANSI_GREY_BACKGROUND = "\u001b[0m";

    /*
     * Class Variable Section
     *
     */

    /*Things to Note:
     * Please review ALL files given before attempting to write these functions.
     * Understand the Cell.java class to know what object our array contains and what methods you can utilize
     * Understand the StackGen.java class to know what type of stack you will be working with and methods you can utilize
     * Understand the QGen.java class to know what type of queue you will be working with and methods you can utilize
     */


    private int rows; //number of rows
    private int cols; //number of columns
    private Cell[][] field; //2D array representing the minefield
    private int flagsLeft; //keeps track of number of flags available for use
    private int totalFlags; //Total number of flags (equal to number of mines)

    private boolean valid;// a boolean that is true if a move made is valid


    private boolean win; //keeps track if player wins or loses

    /**
     * Minefield
     * <p>
     * Build a 2-d Cell array representing your minefield.
     * Constructor
     *
     * @param rows    Number of rows.
     * @param columns Number of columns.
     * @param flags   Number of flags, should be equal to mines
     */
    public Minefield(int rows, int columns, int flags) {
        this.rows = rows;
        this.cols = columns;
        //Create the board according to the given rows and columns
        this.field = new Cell[rows][columns];
        this.flagsLeft = flags;
        this.totalFlags = flags;
        //Set every cell to status 0 and revealed=false
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                field[i][j] = new Cell(false, "0");
            }
        }
    }

    /**
     * evaluateField
     *
     * @function: Evaluate entire array.
     * When a mine is found check the surrounding adjacent tiles. If another mine is found during this check, increment adjacent cells status by 1.
     */
    public void evaluateField() {
        //loop through the entire minefield to find each mine
        for(int i=0; i<rows;i++){
            for(int j=0; j<rows;j++){
                if(field[i][j].getStatus().equals("M")){//if the cell is a mine:
                    String oldStatus="";
                    int adjacentMines=0;
                    //Check all 8 adjacent tiles and increment by one if the adjacent cell is NOT a mine
                    if(i+1<rows && !field[i+1][j].getStatus().equals("M")){ //bottom cell
                        //get its status
                        oldStatus=field[i+1][j].getStatus();
                        //convert it to an int
                        adjacentMines= Integer.parseInt(oldStatus);
                        //increment its status
                        adjacentMines++;
                        //convert the status back to a String and set status to be the new status
                        field[i+1][j].setStatus(Integer.toString(adjacentMines));
                    }if(i-1>=0 && !field[i-1][j].getStatus().equals("M")){//top cell
                        oldStatus=field[i-1][j].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i-1][j].setStatus(Integer.toString(adjacentMines));
                    }if(j+1<cols && !field[i][j+1].getStatus().equals("M")){//right cell
                        oldStatus=field[i][j+1].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i][j+1].setStatus(Integer.toString(adjacentMines));
                    }if(j-1>=0 && !field[i][j-1].getStatus().equals("M")){//left cell
                        oldStatus=field[i][j-1].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i][j-1].setStatus(Integer.toString(adjacentMines));
                    }if(j-1>=0 && i-1>=0 && !field[i-1][j-1].getStatus().equals("M")){//upper left cell
                        oldStatus=field[i-1][j-1].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i-1][j-1].setStatus(Integer.toString(adjacentMines));
                    }if(j+1<cols && i-1>=0 && !field[i-1][j+1].getStatus().equals("M")){//upper right cell
                        oldStatus=field[i-1][j+1].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i-1][j+1].setStatus(Integer.toString(adjacentMines));
                    }if(j-1>=0 && i+1<rows && !field[i+1][j-1].getStatus().equals("M")){//lower left cell
                        oldStatus=field[i+1][j-1].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i+1][j-1].setStatus(Integer.toString(adjacentMines));
                    }if(j+1<cols && i+1<rows && !field[i+1][j+1].getStatus().equals("M")){//lower right cell
                        oldStatus=field[i+1][j+1].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i+1][j+1].setStatus(Integer.toString(adjacentMines));
                    }
                }
            }
        }
    }

        /**
         * createMines
         *
         * Randomly generate coordinates for possible mine locations.
         * If the coordinate has not already been generated and is not equal to the starting cell set the cell to be a mine.
         * utilize rand.nextInt()
         *
         * @param x       Start x, avoid placing on this square.
         * @param y        Start y, avoid placing on this square.
         * @param mines      Number of mines to place.
         */
        public void createMines ( int x, int y, int mines){
            Random random = new Random();
            int counter = 0; //counts how many mines have been created
            //create the designated amount of random mines
            while (counter < mines) {
                //create random coordinates for the new mine
                int randomX = random.nextInt(rows);
                int randomY = random.nextInt(cols);
                //if there is no mine in the random position and the random position is not  on (x,y):
                if (!field[randomX][randomY].getStatus().equals("M") && randomX!=x && randomY!=y) {
                    //make that postion a mine
                    field[randomX][randomY].setStatus("M");
                    counter++;
                }
            }
        }

        /**
         * guess
         *
         * Check if the guessed cell is inbounds (if not done in the Main class).
         * Either place a flag on the designated cell if the flag boolean is true or clear it.
         * If the cell has a 0 call the revealZeroes() method or if the cell has a mine end the game.
         * At the end reveal the cell to the user.
         *
         *
         * @param x       The x value the user entered.
         * @param y       The y value the user entered.
         * @param flag    A boolean value that allows the user to place a flag on the corresponding square.
         * @return boolean Return false if guess did not hit mine or if flag was placed, true if mine found.
         */
        public boolean guess ( int x, int y, boolean flag){
            valid=true;//assume the move is valid until proven otherwise
            //if there are flags left and that the guessed cell is not revealed:
            if (flagsLeft > 0 && flag && !field[x][y].getRevealed() &&
                    !field[x][y].getStatus().equals("F")) {
                //place a flag there
                field[x][y].setStatus("F");
                //subtract a flag from flagsLeft
                flagsLeft--;
                return false;
            }
            if (flag != true) {//if they are not placing a flag
                if (field[x][y].getStatus().equals("0")) {//check if the postion entered is 0:
                    //if so reveal the surrounding zeros
                    revealZeroes(x, y);
                    return false;
                }else if ((field[x][y].getStatus().equals("M"))) {//check if the positon is a mine
                    //if so then reveal the mine and end the game
                    field[x][y].setRevealed(true);
                    gameOver();
                    return true;//since a mine was found we return true
                }else if(field[x][y].getStatus().equals("F")){//if the position has a flag on it
                    //the move is invalid
                    valid=false;
                    return false;
                }else if(!field[x][y].getRevealed()){//if the position is unrevealed and does NOT have a flag
                    //reveal the cell
                    field[x][y].setRevealed(true);
                    return false;
                }else{//otherwise the move is invalid
                    valid=false;
                    return false;
                }
            }
            return false;
        }


        /**
         * gameOver
         *
         * Ways a game of Minesweeper ends:
         * 1. player guesses a cell with a mine: game over -> player loses
         * 2. player has revealed the last cell without revealing any mines -> player wins
         *
         * @return boolean Return false if game is not over and squares have yet to be revealed, otheriwse return true.
         */
        //FIX
        public boolean gameOver () {
            //Loop through the array and check if there is a mine with status=revealed
            int unRevealed=0; //keeps track of the number of unrevealed cells
            int mines=0; //keeps track of the number of revealed mines
            for(int i=0; i<rows;i++){
                for(int j=0;j<rows;j++){
                    //if there is a mine that has been revealed:
                    if (field[i][j].getRevealed() && field[i][j].getStatus().equals("M")) {
                        mines++;//increment number of revealed mines
                        if (mines > 1) {
                            //if there is at least 2 mines that are revealed they lose
                            //(keep in mind that the first mine is revealed for free to the player)
                            win = false;
                            return true;
                        }
                    }
                    //count the number of unrevealed cells
                    if(!field[i][j].getRevealed())
                        unRevealed++;

                }
            }
            
            
            if(unRevealed == totalFlags-1) {//if unrevealed is same as number of mines
                //they won the game
                win = true;
                return true;
            }

            //otherwise the game is not over
            return false;
        }

        /**
         * Reveal the cells that contain zeroes that surround the inputted cell.
         * Continue revealing 0-cells in every direction until no more 0-cells are found in any direction.
         * Utilize a STACK to accomplish this.
         *
         * This method should follow the psuedocode given in the lab writeup.
         * Why might a stack be useful here rather than a queue?
         *
         * @param x      The x value the user entered.
         * @param y      The y value the user entered.
         */
        public void revealZeroes ( int x, int y){ 
            //initialize a generic stack and add the first position
            Stack1Gen<int[]> zeros = new Stack1Gen<>();
            int[] coordinates = new int[]{x,y};
            zeros.push(coordinates);

            while(!zeros.isEmpty()){//while the stack has something in it
                //push the top item in the stack
                int[] newCoordinates= zeros.pop();
                int currentX= newCoordinates[0];
                int currentY=newCoordinates[1];
                
                //reveal the current cell
                field[currentX][currentY].setRevealed(true);

                //check if adjacent cells to the stack are in bounds, unrevealed, and if they are "0",
                //if so put that cells  position into the stack
                if(currentX+1< rows && !field[currentX+1][currentY].getRevealed() &&
                        field[currentX+1][currentY].getStatus().equals("0")){//bottom cell
                    //create an int array for the position of the cell and add it to the stack
                    int[] bottomCell = new int[]{currentX+1,currentY};
                    zeros.push(bottomCell);
                }if(currentX-1>= 0 && !field[currentX-1][currentY].getRevealed() &&
                        field[currentX-1][currentY].getStatus().equals("0")){//top cell
                    //create an int array for the position of the cell and add it to the stack
                    int[] topCell = new int[]{currentX-1,currentY};
                    zeros.push(topCell);
                }if(currentY+1< cols && !field[currentX][currentY+1].getRevealed() &&
                        field[currentX][currentY+1].getStatus().equals("0")){//right cell
                    //create an int array for the position of the cell and add it to the stack
                    int[] rightCell = new int[]{currentX,currentY+1};
                    zeros.push(rightCell);
                }if(currentY-1>=0 && !field[currentX][currentY-1].getRevealed() &&
                        field[currentX][currentY-1].getStatus().equals("0")){//left cell
                    //create an int array for the position of the cell and add it to the stack
                    int[] leftCell = new int[]{currentX,currentY-1};
                    zeros.push(leftCell);
                }
            }
        }

        /**
         * revealStartingArea
         *
         * On the starting move only reveal the neighboring cells of the inital cell and continue revealing the surrounding concealed cells until a mine is found.
         * Utilize a QUEUE to accomplish this.
         *
         * This method should follow the psuedocode given in the lab writeup.
         * Why might a queue be useful for this function?
         *
         * @param x     The x value the user entered.
         * @param y     The y value the user entered.
         */
        public void revealStartingArea ( int x, int y){
            //Initialize a generic queue and add the first position
            Q1Gen<int[]> startArea = new Q1Gen<>();
            int[] coordinates = new int[]{x,y};
            startArea.add(coordinates);

            //while the queue has something in it
            while(startArea.length() != 0){
                //remove the first position in the queue
                int[] newCoordinates= startArea.remove();
                int currentX= newCoordinates[0];
                int currentY=newCoordinates[1];

                //reveal the current cell
                Cell currentCell = field[currentX][currentY];
                currentCell.setRevealed(true);
                
                //if the current cell is a mine exit the loop
                if(currentCell.getStatus().equals("M")) {
                    //remove one flag since one mine has been revealed for free
                    flagsLeft--;
                    break;
                }else{//otherwise check adjacent cells 
                    // if they are in bounds and unrevealed add them to the queue
                    if(currentX+1< rows && !field[currentX+1][currentY].getRevealed()){//bottom cell
                        //create an int array of the position of the adjacent cell and add it to the queue
                        int[] bottomCell = new int[]{currentX+1,currentY};
                        startArea.add(bottomCell);
                    }if(currentX-1>= 0 && !field[currentX-1][currentY].getRevealed()){//top cell
                        //create an int array of the position of the adjacent cell and add it to the queue
                        int[] topCell = new int[]{currentX-1,currentY};
                        startArea.add(topCell);
                    }if(currentY+1< cols && !field[currentX][currentY+1].getRevealed()){//right cell
                        //create an int array of the position of the adjacent cell and add it to the queue
                        int[] rightCell = new int[]{currentX,currentY+1};
                        startArea.add(rightCell);
                    }if(currentY-1>=0 && !field[currentX][currentY-1].getRevealed()){//left cell
                        //create an int array of the position of the adjacent cell and add it to the queue
                        int[] leftCell = new int[]{currentX,currentY-1};
                        startArea.add(leftCell);
                    }
                }
            }

        }

        /**
         * For both printing methods utilize the ANSI colour codes provided!
         *
         *
         *
         *
         *
         * debug
         *
         * @function This method should print the entire minefield, regardless if the user has guessed a square.
         * *This method should print out when debug mode has been selected.
         */
        public void debug() {
            //create a string representing the entire board
            String board = "   ";
            //for the first row, add the column numbers from 0-cols
            for(int i=0;i<cols;i++){
                board=board+"  "+i;
            }
            board = board+"\n";//add a new line after the first row
            
            //loop through the entire board
            for(int i=0;i<rows;i++){
                for(int j=0;j<cols;j++){
                    //for the first column, add the row numbers from 0-rows
                    if(j==0)
                        board = board+"  "+i;
                    //if it is NOT the first row or first column check the status of the cell
                    //and print it in a specific color
                    switch(field[i][j].getStatus()){
                        case "M":
                            board = board+"  "+ ANSI_RED_BRIGHT+ "M"+ANSI_GREY_BACKGROUND;
                            break;
                        case "1":
                            board = board+"  "+ ANSI_BLUE_BRIGHT+ "1"+ANSI_GREY_BACKGROUND;
                            break;
                        case "2":
                            board = board+"  "+ ANSI_BLUE+ "2"+ANSI_GREY_BACKGROUND;
                            break;
                        case "3":
                            board = board+"  "+ ANSI_CYAN+ "3"+ANSI_GREY_BACKGROUND;
                            break;
                        case "4":
                            board = board+"  "+ ANSI_PURPLE+ "4"+ANSI_GREY_BACKGROUND;
                            break;
                        case "5":
                            board = board+"  "+ ANSI_YELLOW+ "5"+ANSI_GREY_BACKGROUND;
                            break;
                        case "6":
                            board = board+"  "+ ANSI_YELLOW_BRIGHT+ "6"+ANSI_GREY_BACKGROUND;
                            break;
                        case "7":
                            board = board+"  "+ ANSI_YELLOW_BRIGHT+ "7"+ANSI_GREY_BACKGROUND;
                            break;
                        case "8":
                            board = board+"  "+ ANSI_RED+ "8"+ANSI_GREY_BACKGROUND;
                            break;
                        case "F":
                            board = board+"  F";
                            break;
                        default://if the board is empty the entire board will be filled with green zeros
                            board = board+"  "+ ANSI_GREEN+ "0"+ANSI_GREY_BACKGROUND;
                    }
                }
                //add a newline after each row
                board=board+"\n";
            }
            
            //print the board
            System.out.print(board);
        }

        /**
         * toString
         *
         * @return String The string that is returned only has the squares that has been revealed to the user or that the user has guessed.
         */
        public String toString(){
            //create a string representing the entire board
            String board = "   ";
            //for the first row, add the column numbers from 0-cols
            for(int i=0;i<cols;i++){
                board=board+"  "+i;
            }
            board=board+"\n";//add a new line after the first row

            //loop through the entire board
            for(int i=0;i<rows;i++){
                for(int j=0;j<cols;j++){
                    //for the first column, add the row numbers from 0-rows
                    if(j==0)
                        board = board+"  "+i;

                    //if it is NOT the first row or first column AND the cell is revealed OR the cell is flagged
                    //print the cell's status in a specific color
                    if (field[i][j].getRevealed()|| field[i][j].getStatus()=="F"){
                        switch(field[i][j].getStatus()){
                            case "M":
                                board = board+"  "+ ANSI_RED_BRIGHT+ "M"+ANSI_GREY_BACKGROUND;
                                break;
                            case "1":
                                board = board+"  "+ ANSI_BLUE_BRIGHT+ "1"+ANSI_GREY_BACKGROUND;
                                break;
                            case "2":
                                board = board+"  "+ ANSI_BLUE+ "2"+ANSI_GREY_BACKGROUND;
                                break;
                            case "3":
                                board = board+"  "+ ANSI_CYAN+ "3"+ANSI_GREY_BACKGROUND;
                                break;
                            case "4":
                                board = board+"  "+ ANSI_PURPLE+ "4"+ANSI_GREY_BACKGROUND;
                                break;
                            case "5":
                                board = board+"  "+ ANSI_YELLOW+ "5"+ANSI_GREY_BACKGROUND;
                                break;
                            case "6":
                                board = board+"  "+ ANSI_YELLOW_BRIGHT+ "6"+ANSI_GREY_BACKGROUND;
                                break;
                            case "7":
                                board = board+"  "+ ANSI_YELLOW_BRIGHT+ "7"+ANSI_GREY_BACKGROUND;
                                break;
                            case "8":
                                board = board+"  "+ ANSI_RED+ "8"+ANSI_GREY_BACKGROUND;
                                break;
                            case "F":
                                board = board+"  "+ ANSI_GREEN +"F"+ANSI_GREY_BACKGROUND;
                                break;
                            default:
                                board = board+"  "+ ANSI_GREEN+ "0"+ANSI_GREY_BACKGROUND;
                        }
                    } else{//if the cell is unrevealed use the placeholder "-"
                        board = board +"  -";
                    }
                }
                //add a new line at the end of each row
                board=board+"\n";
            }
            return board;
        }

        //A getter that returns the variable win
        public boolean getWin(){
            return win;
        }
        
        //A getter that returns the variable valid
        public boolean getValid(){
            return valid;
        }

}
