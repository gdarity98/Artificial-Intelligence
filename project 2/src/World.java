import java.util.Random;

public class World {
    private String[][] filledWorld;
    private int[] playerStartingPosition; // row , column
    private int startingArrows = 0;
    private int playerStartingDirection = 1; //0 north, 1 east, 2 south, 3 west

    public World(int size, double[] prob){
        Random random = new Random();

        // create world in filledWorld based on size
        filledWorld = new String[size][size];

        // get the probabilities
        double pPit = (double) Math.round((prob[0])*100) / 100;
        double pObstacle = (double) Math.round((prob[0] + prob[1])*100) / 100;
        double pWumpus = (double) Math.round((prob[0] + prob[1] + prob[2])*100) / 100;

        //keep track of number of empty spaces, so we can randomly place gold and explorer
        int numEmpty = 0;

        //setting the spaces to empty, pit, obstacle, or wumpus based on probability
        for(int i = 0; i < filledWorld.length; i++){
            for(int j = 0; j < filledWorld[0].length; j++){
                double rDouble = random.nextDouble();
                if(rDouble < pPit){
                    //space is a pit
                    filledWorld[i][j] = "P";
                }else if(rDouble < pObstacle){
                    //space is an obstacle
                    filledWorld[i][j] = "O";
                }else if(rDouble < pWumpus){
                    //space is a Wumpus
                    filledWorld[i][j] = "W";
                    startingArrows++;
                }else{
                    //space is empty
                    filledWorld[i][j] = "_";
                    numEmpty++;
                }
            }
        }

        // randomly pick two unique empty spaces for gold and player
        int rEmptyGold = 0;
        int rEmptyPlayer = 0;
        while(rEmptyGold == rEmptyPlayer){
            rEmptyGold = random.nextInt(numEmpty) + 1;
            rEmptyPlayer = random.nextInt(numEmpty) + 1;
        }

        // go through the spaces and find the selected empty spaces
        // update the world to have the gold
        // update where the player is positioned
        // also update that the starting space is safe
        numEmpty = 0;
        boolean goldPlaced = false;
        boolean playerPlaced = false;
        int rowGP = 0; //row counter for gold and player placing
        int colGP = 0; //col counter for gold and player placing
        while(!(playerPlaced && goldPlaced)){
            if(filledWorld[rowGP][colGP].equals("_")){
                numEmpty++;
                if(numEmpty == rEmptyGold){
                    filledWorld[rowGP][colGP] = "G";
                    goldPlaced = true;
                }else if(numEmpty == rEmptyPlayer){
                    playerStartingPosition = new int[2];
                    playerStartingPosition[0] = rowGP;
                    playerStartingPosition[1] = colGP;
                    playerPlaced = true;
                }
            }
            colGP++;
            if(colGP == filledWorld[0].length){
                colGP = 0;
                rowGP++;
            }
        }
    }

    public String[][] getFilledWorld() {
        return filledWorld;
    }

    public int[] getPlayerStartingPosition() {
        return playerStartingPosition;
    }

    public int getPlayerStartingDirection() {
        return playerStartingDirection;
    }

    public int getStartingArrows() {
        return startingArrows;
    }

    public void setStartingArrows(int startingArrows) {
        this.startingArrows = startingArrows;
    }

    public void setPlayerStartingPosition(int[] playerStartingPosition) {
        this.playerStartingPosition = playerStartingPosition;
    }

    public void setPlayerStartingDirection(int playerStartingDirection) {
        this.playerStartingDirection = playerStartingDirection;
    }

    public void setFilledWorld(String[][] filledWorld) {
        this.filledWorld = filledWorld;
    }
}
