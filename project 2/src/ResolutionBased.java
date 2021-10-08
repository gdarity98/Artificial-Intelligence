import java.util.Arrays;

public class ResolutionBased {
    private String updatedRules = "";
    private String rules = "";
    private int[][] safe; //is this the same as visited?: 1 is safe 0 is unsafe (set as 2 for discovered obstacle?)
    private int[][] frontier; //unexplored:  1 is unexplored 0 is explored
    private int[] playerPosition = new int[2];

    //world information
    private World world;

    //stats of the game
    private int cellsExplored = 0;
    private int wumpusKilled = 0;
    private int goldFound = 0;
    private int pitDeath = 0;
    private int wumpusDeath = 0;

    public ResolutionBased(String rules){
        this.rules = rules;
    }

    public void runGame(int size, double[] prob){
        world = new World(size, prob);

        //Reasoning System Explorer
        //set up game
        safe = new int[world.getFilledWorld().length][world.getFilledWorld()[0].length];
        frontier = new int[world.getFilledWorld().length][world.getFilledWorld()[0].length];
        for(int[] row : frontier){
            Arrays.fill(row, 1);
        }

        //where explorer is immediately add that to safe and remove from frontier
        int[] startingPos = world.getPlayerPosition();
        safe[startingPos[0]][startingPos[1]] = 1;
        frontier[startingPos[0]][startingPos[1]] = 0;
        playerPosition = startingPos;

        int[][] surroundingSpaces = getSurrounding();
        boolean notDead = true;
        while(notDead){
            //need to smell wumpus, feel wind, see shimmer
            boolean[] senses = Sense(surroundingSpaces);
            System.out.println(senses);
            // get a rule based on that
            //if smell, then there exists a wumpus in the surroundingSpaces
            //if feel, then there exists a pit in the surroundingSpaces
            //if shimmer, then there exists a gold in the surroundingSpaces

            //if not smell, then there does not exists a wumpus in the surroundingSpaces
            //if not feel, then there does not exists a pit in the surroundingSpaces
            //if not shimmer, then there does not exists a gold in the surroundingSpaces
            //if not(shimmer and smell and feel), then the surroundingSpaces are safe

            // call resolution based search method
                // should be able to find existing rules that would make things safe?
                // update rules
                // within resolution going to need unification and stuff like that

            // make choice based on rules
                // move

            //IDK about this part
            // if one safe then choose safe, if several safe choose randomly,
            //   if no safe then choose randomly
            //if smell wumpus chose unexplored cell and shoot to make safe, if you hear no scream go that way
            //    if you do hear a scream go other way?
        }

        //while not dead
            //Reactive Explorer
            //  we need to also make this!!!
            //  should run on same world
            //  stats contained separately for comparison

        //   make new world of same size and run game again on new world
        //   reset updatedRules to be ""

    }

    private int[][] getSurrounding() {
        int pRow = playerPosition[0];
        int pCol = playerPosition[1];

        int[] north = new int[2];
        int[] east = new int[2];
        int[] south = new int[2];
        int[] west = new int[2];

        //filling the arrays with 100, so that if they have 100 at the end I don't need to check them
        Arrays.fill(north,100);
        Arrays.fill(east,100);
        Arrays.fill(south,100);
        Arrays.fill(west,100);

        //checking position to avoid array out of bounds
        //if the player is in the corner
        if((pRow == 0 || pRow == 4) && (pCol == 0 || pCol == 4)){
            if(pRow == 0 && pCol == 0){
                east[0] = pRow;
                east[1] = pCol+1;
                south[0] = pRow+1;
                south[1] = pCol;
            }else if(pRow ==0 && pCol == 4){
                south[0] = pRow+1;
                south[1] = pCol;
                west[0] = pRow;
                west[1] = pCol-1;
            }else if(pRow == 4 && pCol == 0){
                north[0] = pRow-1;
                north[1] = pCol;
                east[0] = pRow;
                east[1] = pCol+1;
            }else{
                north[0] = pRow-1;
                north[1] = pCol;
                west[0] = pRow;
                west[1] = pCol-1;
            }
            //if the player is top or bottom row
        }else if(pRow == 0 || pRow == 4){
            if(pRow == 0){
                south[0] = pRow+1;
                south[1] = pCol;
            }else{
                north[0] = pRow-1;
                north[1] = pCol;
            }
            east[0] = pRow;
            east[1] = pCol+1;
            west[0] = pRow;
            west[1] = pCol-1;
            //if the player is in the far left or far right col
        }else if(pCol == 0 || pCol == 4){
            if(pCol == 0){
                east[0] = pRow;
                east[1] = pCol+1;
            }else{
                west[0] = pRow;
                west[1] = pCol-1;
            }
            north[0] = pRow-1;
            north[1] = pCol;
            south[0] = pRow+1;
            south[1] = pCol;
            //if the player is anywhere else
        }else{
            north[0] = pRow-1;
            north[1] = pCol;
            east[0] = pRow;
            east[1] = pCol+1;
            south[0] = pRow+1;
            south[1] = pCol;
            west[0] = pRow;
            west[1] = pCol-1;
        }

        int[][] directions = new int[4][2];
        directions[0] = north;
        directions[1] = east;
        directions[2] = south;
        directions[3] = west;

        return directions;
    }

    private boolean[] Sense(int[][] surroundingSpaces) {
        //Smell, Feel, Shimmer
        boolean[] senses = new boolean[3];

        //check each direction for wumpus, gold, pit
        for(int[] space : surroundingSpaces){
            if(space[0] != 100){
                String s = world.getFilledWorld()[space[0]][space[1]];
                if(s.equals("G")){
                    senses[2] = true;
                }else if(s.equals("P")){
                    senses[1] = true;
                }else if(s.equals("W")){
                    senses[0] = true;
                }
            }
        }
        return senses;
    }

    public int getCellsExplored() {
        return cellsExplored;
    }

    public void setCellsExplored(int cellsExplored) {
        this.cellsExplored = cellsExplored;
    }

    public int getGoldFound() {
        return goldFound;
    }

    public void setGoldFound(int goldFound) {
        this.goldFound = goldFound;
    }

    public int getPitDeath() {
        return pitDeath;
    }

    public void setPitDeath(int pitDeath) {
        this.pitDeath = pitDeath;
    }

    public int getWumpusDeath() {
        return wumpusDeath;
    }

    public void setWumpusDeath(int wumpusDeath) {
        this.wumpusDeath = wumpusDeath;
    }

    public int getWumpusKilled() {
        return wumpusKilled;
    }

    public void setWumpusKilled(int wumpusKilled) {
        this.wumpusKilled = wumpusKilled;
    }
}
