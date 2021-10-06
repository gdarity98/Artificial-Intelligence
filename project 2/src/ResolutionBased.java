import java.util.Arrays;

public class ResolutionBased {
    private String updatedRules = "";
    private String rules = "";
    private int[][] safe; //is this the same as visited?: 1 is safe 0 is unsafe
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

        //set up game
        safe = new int[world.getFilledWorld().length][world.getFilledWorld()[0].length];
        frontier = new int[world.getFilledWorld().length][world.getFilledWorld()[0].length];
        Arrays.fill(frontier, 1);

        //where explorer is immediately add that to safe and remove from frontier
        int[] startingPos = world.getPlayerPosition();
        safe[startingPos[0]][startingPos[1]] = 1;
        frontier[startingPos[0]][startingPos[1]] = 0;
        playerPosition = startingPos;

        //call functions to smell, hear, look, etc.
        // get a rule based on that

        // call resolution based search method
        // update rules
            // within resolution going to need unification and stuff like that
        // make choice based on rules
        // if one safe then choose safe, if several safe choose randomly,
        //   if no safe then choose randomly

        //if death
        //   make new world of same size and run game again on new world
        //   reset updatedRules to be ""
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
