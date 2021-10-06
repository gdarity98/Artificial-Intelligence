public class ResolutionBased {
    private String updatedRules = "";
    private int[][] safe; //is this the same as visited?
    private int[][] frontier; //unexplored
    public ResolutionBased(){}

    public void runGame(World world, String rules){
        //set up game
        //where explorer is immediately add that to safe

        //call functions to smell, hear, look, etc.
        // get a rule based on that

        // call resolution based search method
        // update rules
        
        // make choice based on rules
        // if one safe then choose safe, if several safe choose randomly,
        //   if no safe then choose randomly
    }
}
