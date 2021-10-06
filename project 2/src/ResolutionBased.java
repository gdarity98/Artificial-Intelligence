import java.util.Arrays;

public class ResolutionBased {
    private String updatedRules = "";
    private int[][] safe; //is this the same as visited?: 1 is safe 0 is unsafe
    private int[][] frontier; //unexplored:  1 is unexplored 0 is explored
    public ResolutionBased(){}

    public void runGame(World world, String rules){
        //set up game
        safe = new int[world.getFilledWorld().length][world.getFilledWorld()[0].length];
        frontier = new int[world.getFilledWorld().length][world.getFilledWorld()[0].length];
        Arrays.fill(frontier, 1);

        //where explorer is immediately add that to safe and remove from frontier
        int[] startingPos = world.getPlayerPosition();
        safe[startingPos[0]][startingPos[1]] = 1;
        frontier[startingPos[0]][startingPos[1]] = 0;

        //call functions to smell, hear, look, etc.
        // get a rule based on that

        // call resolution based search method
        // update rules
            // within resolution going to need unification and stuff like that
        // make choice based on rules
        // if one safe then choose safe, if several safe choose randomly,
        //   if no safe then choose randomly
    }
}
