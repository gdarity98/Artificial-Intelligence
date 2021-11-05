import java.io.FileNotFoundException;
import java.util.Arrays;

public class MainProject2 {
    public static void main(String[] args){
        //Setting up worldSizes and putting them in an array for easy access
        // P is the probabilities of pit, obstacle, and wumpus
        // p empty would be 1 - the sum of the other probabilities

        int[] worldSizes = new int[10];
        for(int i = 1; i < 11; i++){
            worldSizes[i-1] = 5*i;
        }

        double[][] p = new double[10][3];
        p[0][0] = 0.1;
        p[0][1] = 0.1;
        p[0][2] = 0.1;

        p[1][0] = 0.01;
        p[1][1] = 0.10;
        p[1][2] = 0.01;

        p[2][0] = 0.01;
        p[2][1] = 0.10;
        p[2][2] = 0.10;

        p[3][0] = 0.10;
        p[3][1] = 0.01;
        p[3][2] = 0.01;

        p[4][0] = 0.10;
        p[4][1] = 0.10;
        p[4][2] = 0.01;

        p[5][0] = 0.10;
        p[5][1] = 0.01;
        p[5][2] = 0.10;

        p[6][0] = 0.10;
        p[6][1] = 0.10;
        p[6][2] = 0.10;

        p[7][0] = 0.00;
        p[7][1] = 0.10;
        p[7][2] = 0.00;

        p[8][0] = 0.00;
        p[8][1] = 0.01;
        p[8][2] = 0.00;

        p[9][0] = 0.10;
        p[9][1] = 0.00;
        p[9][2] = 0.00;

        //creating rules and game
        String rules = "Wumpus(X,Y);Smell(X,Y);Breeze(X,Y);Glitter(X,Y);Safe(X,Y);notSafe(X,Y);";


        //running the game on each worldSize
        // run until gold found on one of the worlds, returns stats?
        //    need to add it returning stats (build a string of the stats and return the string?)
        int exp = 1;
        for(double[] prob : p){
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Experiment " + exp);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            ResolutionBased game = new ResolutionBased(rules);
            double[] averageStats = game.getAvgStatsFull(1);
            for(int worldSize : worldSizes){
                //System.out.println("Running on world size of: "+ worldSize);
                int i;
                for(i = 0; i < 10; i ++){
                    game.runGame(worldSize, prob, true);
                    //get and print game stats
                    //System.out.println("Times Run: " + (i+1));
                    //game.printStatsFull();
                }
                averageStats = game.getAvgStatsFull(i);
                for(double avgS : averageStats){

                }
            }
            int index = 0;
            for(double avgS : averageStats){
                averageStats[index] = avgS / 10;
                index++;
            }
            System.out.println("Average of all worlds after 10 runs on each:\n cellsExplored, goldFound, pitDeath, wumpusDeath, wumpusKill, Points");
            System.out.println(" " + Arrays.toString(averageStats));
            System.out.println("-----------------------------------------------");
            exp++;
        }
    }
}
