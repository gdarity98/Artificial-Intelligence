import java.util.Random;

public class World {
    private String[][] filledWorld;
    private int[] playerPosition; // row , column
    private int size = 0;
    private int cellsExplored = 0;
    private int wumpusKilled = 0;
    private int goldFound = 0;
    private int pitDeath = 0;
    private int wumpusDeath = 0;

    public World(int size, double[] prob){
        Random random = new Random();
        this.size = size;
        // create world in filledWorld based on size
        filledWorld = new String[size][size];

        // get the probabilities
        double pPit = prob[0];
        double pObstacle = prob[0] + prob[1];
        double pWumpus = prob[0] + prob[1] + prob[2];

        //keep track of number of empty spaces, so we can randomly place gold and explorer
        int numEmpty = 0;

        //
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
        for(int i = 0; i < filledWorld.length; i++) {
            for (int j = 0; j < filledWorld[0].length; j++) {
                if(filledWorld[i][j].equals("_")){
                    numEmpty++;
                    if(numEmpty == rEmptyGold){
                        filledWorld[i][j] = "G";
                    }else if(numEmpty == rEmptyPlayer){
                        playerPosition = new int[2];
                        playerPosition[0] = i;
                        playerPosition[1] = j;
                    }
                }
            }
        }


    }

    public String[][] getFilledWorld() {
        return filledWorld;
    }

    public int[] getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(int[] playerPosition) {
        this.playerPosition = playerPosition;
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

    // More methods
}
