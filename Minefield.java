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
    private int totalFlags; //number of flags (equal to number of mines)


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
    public Minefield(int rows, int columns, int flags) { //SYLVIA
        this.rows = rows;
        this.cols = columns;
        this.field = new Cell[rows][columns];
        this.flagsLeft = flags;
        this.totalFlags = flags;
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
    public void evaluateField() { //SYLVIA
        for(int i=0; i<rows;i++){
            for(int j=0; j<rows;j++){
                if(field[i][j].getStatus().equals("M")){
                    String oldStatus="";
                    int adjacentMines=0;
                    //Check all 8 adjacent tiles and increment by one if not a mine
                    if(i+1<rows && !field[i+1][j].getStatus().equals("M")){
                        oldStatus=field[i+1][j].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i+1][j].setStatus(Integer.toString(adjacentMines));
                    }if(i-1>0 && !field[i-1][j].getStatus().equals("M")){
                        oldStatus=field[i-1][j].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i-1][j].setStatus(Integer.toString(adjacentMines));
                    }if(j+1<cols && !field[i][j+1].getStatus().equals("M")){
                        oldStatus=field[i][j+1].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i][j+1].setStatus(Integer.toString(adjacentMines));
                    }if(j-1>0 && !field[i][j-1].getStatus().equals("M")){
                        oldStatus=field[i][j-1].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i][j-1].setStatus(Integer.toString(adjacentMines));
                    }if(j-1>0 && i-1>0 && !field[i-1][j-1].getStatus().equals("M")){
                        oldStatus=field[i-1][j-1].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i-1][j-1].setStatus(Integer.toString(adjacentMines));
                    }if(j+1<cols && i-1>0 && !field[i-1][j+1].getStatus().equals("M")){
                        oldStatus=field[i-1][j+1].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i-1][j+1].setStatus(Integer.toString(adjacentMines));
                    }if(j-1>0 && i+1<rows && !field[i+1][j-1].getStatus().equals("M")){
                        oldStatus=field[i+1][j-1].getStatus();
                        adjacentMines= Integer.parseInt(oldStatus);
                        adjacentMines++;
                        field[i+1][j-1].setStatus(Integer.toString(adjacentMines));
                    }if(j+1<cols && i+1<rows && !field[i+1][j+1].getStatus().equals("M")){
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
        public void createMines ( int x, int y, int mines){ //SYLVIA
            Random random = new Random();
            int counter = 0;
            while (counter < mines) {
                int randomX = random.nextInt(rows);
                int randomY = random.nextInt(cols);
                if (!field[randomX][randomY].getStatus().equals("M") && randomX!=x && randomY!=y) {
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
            public boolean guess ( int x, int y, boolean flag){ //SYLVIA
        if (x < rows && x>0 && y>0 && y < cols) {
            if (flag == true) {
                if (flagsLeft > 0) {
                    field[x][y].setStatus("F");
                    flagsLeft--;
                    return false;
                }
                if (field[x][y].getStatus().equals("F")) {
                    System.out.println("Already a flag there");
                    return false;
                }
                if (field[x][y].getRevealed()) {
                    System.out.println("Cell already revealed");
                    return false;
                } else {
                    System.out.println("No flags left");
                }

            } else {
                if (field[x][y].getRevealed()) {
                    System.out.println("Cell already revealed");
                    return false;
                }
                if (field[x][y].getStatus().equals("*")) {
                    gameOver();
                    return true;
                }
                else if (field[x][y].getStatus().equals("0")) {
                    revealZeroes(x, y);
                }
                else {
                    field[x][y].setRevealed(true);
                }
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
        public boolean gameOver () {//HAJAR
            //Loop through the array and check if there is a mine with status=revealed
            int unRevealed=0;
            int mines=0;
            for(int i=0; i<rows;i++){
                for(int j=0;j<rows;j++){
                    //if there is a mine that has been selected they lose
                    if (field[i][j].getRevealed() && field[i][j].getStatus().equals("M"))
                        mines++;
                        if(mines>1)
                            return true;

                    if(!field[i][j].getRevealed())
                        unRevealed++;

                }
            }

            if(unRevealed == totalFlags)//if unrevealed is same as number of mines
                return true;

            //Check the case where there is
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
        public void revealZeroes ( int x, int y){ //SYLVIA
            Stack1Gen<int[]> zeros = new Stack1Gen<>();
            int[] coordinates = new int[]{x,y};
            zeros.push(coordinates);

            while(!zeros.isEmpty()){
                int[] newCoordinates= zeros.pop();
                int currentX= newCoordinates[0];
                int currentY=newCoordinates[1];


                if(currentX+1< rows && !field[currentX+1][currentY].getRevealed() &&
                        field[currentX+1][currentY].getStatus().equals("0")){
                    int[] bottomCell = new int[]{currentX+1,currentY};
                    zeros.push(bottomCell);
                }if(currentX-1> 0 && !field[currentX-1][currentY].getRevealed() &&
                        field[currentX+1][currentY].getStatus().equals("0")){
                    int[] topCell = new int[]{currentX-1,currentY};
                    zeros.push(topCell);
                }if(currentY+1< cols && !field[currentX][currentY+1].getRevealed() &&
                        field[currentX+1][currentY].getStatus().equals("0")){
                    int[] rightCell = new int[]{currentX,currentY+1};
                    zeros.push(rightCell);
                }if(currentY-1>0 && !field[currentX][currentY-1].getRevealed() &&
                        field[currentX+1][currentY].getStatus().equals("0")){
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
            //Create queue
            Q1Gen<int[]> startArea = new Q1Gen<>();
            int[] coordinates = new int[]{x,y};
            startArea.add(coordinates);

            while(startArea.length() != 0){
                int[] newCoordinates= startArea.remove();
                int currentX= newCoordinates[0];
                int currentY=newCoordinates[1];

                Cell currentCell = field[currentX][currentY];
                currentCell.setRevealed(true);

                if(currentCell.getStatus().equals("M")) {
                    flagsLeft--;
                    break;
                }else{
                    if(currentX+1< rows && !field[currentX+1][currentY].getRevealed()){
                        int[] bottomCell = new int[]{currentX+1,currentY};
                        startArea.add(bottomCell);
                    }if(currentX-1> 0 && !field[currentX-1][currentY].getRevealed()){
                        int[] topCell = new int[]{currentX-1,currentY};
                        startArea.add(topCell);
                    }if(currentY+1< cols && !field[currentX][currentY+1].getRevealed()){
                        int[] rightCell = new int[]{currentX,currentY+1};
                        startArea.add(rightCell);
                    }if(currentY-1>0 && !field[currentX][currentY-1].getRevealed()){
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
        public void debug() {//HAJAR
            String board = "   ";
            for(int i=0;i<cols;i++){
                board=board+"  "+i;
            }
            board = board+"\n";
            for(int i=0;i<rows;i++){
                for(int j=0;j<cols;j++){
                    if(j==0)
                        board = board+"  "+i;
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
                        default:
                            board = board+"  "+ ANSI_GREEN+ "0"+ANSI_GREY_BACKGROUND;
                    }
                }
                board=board+"\n";
            }

            System.out.print(board);
        }

        /**
         * toString
         *
         * @return String The string that is returned only has the squares that has been revealed to the user or that the user has guessed.
         */
        public String toString(){//HAJAR
            String board = "   ";
            for(int i=0;i<cols;i++){
                board=board+"  "+i;
            }
            board=board+"\n";
            for(int i=0;i<rows;i++){
                for(int j=0;j<cols;j++){
                    if(j==0)
                        board = board+"  "+i;
                    if (field[i][j].getRevealed()){
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
                            default:
                                board = board+"  "+ ANSI_GREEN+ "0"+ANSI_GREY_BACKGROUND;
                        }
                    }else{
                        board = board +"  -";
                    }
                }
                board=board+"\n";
            }
            return board;
        }
}
