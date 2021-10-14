import java.io.FileNotFoundException;

public class MainProject2 {
    public static void main(String[] args){
        //Setting up worldSizes and putting them in an array for easy access
        // P is the probabilities of pit, obstacle, and wumpus
        // p empty would be 1 - the sum of the other probabilities

        int[] worldSizes = new int[10];
        for(int i = 1; i < 11; i++){
            worldSizes[i-1] = 5*i;
        }

        double[] p = new double[3];
        p[0] = 0.15;
        p[1] = 0.15;
        p[2] = 0.15;

        //creating rules and game
        //TODO NEED TO MAKE BASE RULES
        String rules = "Rule1, Rule2, Rule3, Wumpus(X,Y)";
        ResolutionBased game = new ResolutionBased(rules);


        //running the game on each worldSize
        // run until gold found on one of the worlds, returns stats?
        //    need to add it returning stats (build a string of the stats and return the string?)
        for(int worldSize : worldSizes){
            game.runGame(worldSize, p, true, true);
            //get and print game stats
            game.printStatsFull();
        }
    }
}
