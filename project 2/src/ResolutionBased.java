import java.util.Arrays;

public class ResolutionBased {
    private String updatedRules = "";
    private String rules = "";
    private int[][] safe; //is this the same as visited?: 1 is safe 0 is unsafe (set as 2 for discovered obstacle?)
    private int[][] frontier; //unexplored:  1 is unexplored 0 is explored
    private int[] playerPosition = new int[2];
    private int playerDirection;
    private int points = 0;

    //world information
    private World world;

    //stats of the game
    private int cellsExplored = 0;
    private int wumpusKilled = 0;
    private int goldFound = 0;
    private int pitDeath = 0;
    private int wumpusDeath = 0;

    public ResolutionBased(String rules) {
        this.rules = rules;
    }

    public void runGame(int size, double[] prob) {
        world = new World(size, prob);

        //Reasoning System Explorer
        //set up game
        safe = new int[world.getFilledWorld().length][world.getFilledWorld()[0].length];
        frontier = new int[world.getFilledWorld().length][world.getFilledWorld()[0].length];
        for (int[] row : frontier) {
            Arrays.fill(row, 1);
        }

        //where explorer is immediately add that to safe and remove from frontier
        int[] startingPos = world.getPlayerStartingPosition();
        safe[startingPos[0]][startingPos[1]] = 1;
        frontier[startingPos[0]][startingPos[1]] = 0;
        playerPosition = startingPos;
        playerDirection = world.getPlayerStartingDirection();

        //index 0 = north, 1 = east, 2 = south, 3 = west
        int[][] surroundingSpaces = getSurrounding();
        boolean notDead = true;
        while (notDead) {
            //need to smell wumpus, feel wind, see shimmer
            boolean[] senses = sense(surroundingSpaces);
            System.out.println(senses);
           
             
            if (senses[2]) { //gold
                System.out.println(2);
           
            setGoldFound(1);
            }
            if(senses[1]){ //pit
                System.out.println(3);
            
            }
            if (senses[0] && senses[1]){ //pit and wumpus
                System.out.println(4);
          
            }
            if (senses[0]){ //wumpus
                System.out.println(5);
           
            }
            if (senses[0] && senses[2]){ //wumpus and gold
            System.out.println(6);
               
            }
            if (senses[1] && senses[2]){ //pit and gold
                System.out.println(7);
           
            }
            if (senses[0] && senses[1] && senses[2]){ //all three
            System.out.println(8);
            
            }
            if (!senses[2]) { //not gold
                System.out.println(21);
            
           
            }
            if(!senses[1]){ //not pit
                System.out.println(31);
        
            }
            if (!senses[0] && !senses[1]){ //not pit or wumpus
                System.out.println(41);
            
            }
            if (!senses[0]){ //not wumpus
                System.out.println(51);
          
            }
            if (!senses[0] && !senses[2]){ //not gold or wumpus
            System.out.println(61);
               
            }
            if (!senses[1] && !senses[2]){ //not gold or pit
                System.out.println(71);
            
            }
            if (!senses[0] && !senses[1] && !senses[2]){ //none of them
            System.out.println(81);
                
            }
            //TODO get a rule based on the senses to send into reasoning? Brock
            //if smell, then there exists a wumpus in the surroundingSpaces
            //if feel, then there exists a pit in the surroundingSpaces
            //if shimmer, then there exists a gold in the surroundingSpaces

            //if not smell, then there does not exists a wumpus in the surroundingSpaces
            //if not feel, then there does not exists a pit in the surroundingSpaces
            //if not shimmer, then there does not exists a gold in the surroundingSpaces
            //if not(shimmer and smell and feel), then the surroundingSpaces are safe

            //TODO call resolution based search method (unification) Kyler

            // should be able to find existing rules that would make things safe?
            // update rules
            // within resolution going to need unification and stuff like that

            //IDK about this part
            // if one safe then choose safe, if several safe choose randomly,
            //   if no safe then choose randomly
            //if smell wumpus chose unexplored cell and shoot to make safe, if you hear no scream go that way
            //    if you do hear a scream go other way?

            String holderX = "";
            String holderY = "";
            String subst = unify(holderX, holderY, "");


            //TODO make choice based on rules (resolution) Brock
            //move to safe space, if multiple we move to one of them randomly
            //if we can't find a safe space or no rule gives safe space then move randomly

            // move choice
            int[] destSpace = new int[2];
            //example movement choice for testing
            destSpace = surroundingSpaces[0];
            playerPosition = move(destSpace, 0);
            // (you won't move to a new space if destSpace was an obstacle or dead wumpus)

            //after moving check if the space you are on is safe
            // if it is a pit or wumpus you are dead, if it is gold teleport out
            if (world.getFilledWorld()[playerPosition[0]][playerPosition[1]].equals("W")) {
                points -= 10000;
                notDead = false;
                continue;
            } else if (world.getFilledWorld()[playerPosition[0]][playerPosition[1]].equals("P")) {
                points -= 10000;
                notDead = false;
                continue;
            } else if (world.getFilledWorld()[playerPosition[0]][playerPosition[1]].equals("G")) {
                points += 1000;
                //teleport to safety
                break;
            } else {
                safe[playerPosition[0]][playerPosition[1]] = 1;
                frontier[playerPosition[0]][playerPosition[1]] = 0;
            }

            //TODO shoot function Gabe
            //have outline in function
            boolean scream = shoot(playerPosition, playerDirection);
            //unification

            //TODO Keep track of stats (add [stat]++ where needed) Kyler

        }

        //TODO death = new game Gabe?
        //if died from the above game then need to
        //   make new world of same size
        //   reset updatedRules to be ""
        //   run game again on new world (call function again so that stats stay)

        //TODO reactive explorer Gabe
        //NEED TO FIGURE OUT WHERE TO DO THIS AT THE SAME TIME
        // (Have another explorer updating separately in the above while loop and has its own death
        // and gold found booleans?)
        //while not dead
        //Reactive Explorer
        //  we need to also make this!!!
        //  should run on same world
        //  stats contained separately for comparison

    }

    private String unify(String x, String y, String substList) {
        if (substList.equals("FAILURE")) {
            return "";
        } else if (x.equals(y)) {
            return substList;
        } else if (!rules.contains(x)) {
            return unifyVariables(x, y, substList);
        } else if (!rules.contains(y)) {
            return unifyVariables(y, x, substList);
        //Find out if x and y are ???compounds??? and return
        } else if (false /* COMPOUND?(x) && COMPOUND?(y) */) {
            return unify(x, y, unify(x, y, substList)); //pseudo: UNIFY(ARGS[x], ARGS[y], UNIFY(OP[x], OP[y], THETA)) THETA = substList
        } else if (false/* LIST?(x) && LIST?(y) */) {
            return unify(x, y, unify(x, y, substList)); //pseudo: UNIFY(REST[x], REST[y], UNIFY(FIRST[x], FIRST[y], THETA)) THETA = substList
        } else {
            return "FAILURE";
        }
    }

    //TODO Figure out what the hell this algorithm is supposed to do
    private String unifyVariables(String var, String x, String subRules) {
        return "THIS DOES NOT WORK YET";
    }

    //returns if you heard a scream or not
    private boolean shoot(int[] playerPosition, int playerDirection) {
        //check each space to direction facing for a wumpus or obstacle or border(end of array)
        if (playerDirection == 0) {//if player direction is 0 then need to decrease playerPosition[0] until <=/==/idk 0 lol
            //if find obstacle first then return false
            //if find wumpus first then return true change W to O
            //if find end of array first then return false
            return true;
        } else if (playerDirection == 2) {//if player direction is 2 then need to increase playPos[0] until 4
            //if find obstacle first then return false
            //if find wumpus first then return true W to O
            //if find end of array first then return false
            return true;
        } else if (playerDirection == 3) {//if player direction is 3 then need to decrease playPos[1] until 0
            //if find obstacle first then return false
            //if find wumpus first then return true W to O
            //if find end of array first then return false
            return true;
        } else { //if player direction is 1 then need to increase playPos[1] until 4
            //if find obstacle first then return false
            //if find wumpus first then return true W to O
            //if find end of array first then return false
            return true;
        }
    }

    private int[] move(int[] destSpace, int destDirection) {
        // need to add cost
        // facing the direction we need to be
        if (playerDirection == destDirection) {
            //lose a point for moving forward
            //check if next space is obstacle
            if (world.getFilledWorld()[destSpace[0]][destSpace[1]].equals("O")) {
                points -= 1;
                return playerPosition;
            } else {
                points -= 1;
                return destSpace;
            }
            // need to turn 180 degrees
        } else if (Math.abs(playerDirection - destDirection) == 2) {
            // lose two points for turning
            points -= 2;
            playerDirection = destDirection;
            //lose a point for moving forward
            if (world.getFilledWorld()[destSpace[0]][destSpace[1]].equals("O")) {
                points -= 1;
                return playerPosition;
            } else {
                points -= 1;
                return destSpace;
            }
            // need to turn 90 degrees
        } else {
            points -= 1;
            playerDirection = destDirection;
            if (world.getFilledWorld()[destSpace[0]][destSpace[1]].equals("O")) {
                points -= 1;
                return playerPosition;
            } else {
                points -= 1;
                return destSpace;
            }
        }
    }

    private int[][] getSurrounding() {
        int pRow = playerPosition[0];
        int pCol = playerPosition[1];

        int[] north = new int[2];
        int[] east = new int[2];
        int[] south = new int[2];
        int[] west = new int[2];

        //filling the arrays with 100, so that if they have 100 at the end I don't need to check them
        Arrays.fill(north, 100);
        Arrays.fill(east, 100);
        Arrays.fill(south, 100);
        Arrays.fill(west, 100);

        //checking position to avoid array out of bounds
        //if the player is in the corner
        if ((pRow == 0 || pRow == 4) && (pCol == 0 || pCol == 4)) {
            if (pRow == 0 && pCol == 0) {
                east[0] = pRow;
                east[1] = pCol + 1;
                south[0] = pRow + 1;
                south[1] = pCol;
            } else if (pRow == 0 && pCol == 4) {
                south[0] = pRow + 1;
                south[1] = pCol;
                west[0] = pRow;
                west[1] = pCol - 1;
            } else if (pRow == 4 && pCol == 0) {
                north[0] = pRow - 1;
                north[1] = pCol;
                east[0] = pRow;
                east[1] = pCol + 1;
            } else {
                north[0] = pRow - 1;
                north[1] = pCol;
                west[0] = pRow;
                west[1] = pCol - 1;
            }
            //if the player is top or bottom row
        } else if (pRow == 0 || pRow == 4) {
            if (pRow == 0) {
                south[0] = pRow + 1;
                south[1] = pCol;
            } else {
                north[0] = pRow - 1;
                north[1] = pCol;
            }
            east[0] = pRow;
            east[1] = pCol + 1;
            west[0] = pRow;
            west[1] = pCol - 1;
            //if the player is in the far left or far right col
        } else if (pCol == 0 || pCol == 4) {
            if (pCol == 0) {
                east[0] = pRow;
                east[1] = pCol + 1;
            } else {
                west[0] = pRow;
                west[1] = pCol - 1;
            }
            north[0] = pRow - 1;
            north[1] = pCol;
            south[0] = pRow + 1;
            south[1] = pCol;
            //if the player is anywhere else
        } else {
            north[0] = pRow - 1;
            north[1] = pCol;
            east[0] = pRow;
            east[1] = pCol + 1;
            south[0] = pRow + 1;
            south[1] = pCol;
            west[0] = pRow;
            west[1] = pCol - 1;
        }

        int[][] directions = new int[4][2];
        directions[0] = north;
        directions[1] = east;
        directions[2] = south;
        directions[3] = west;

        return directions;
    }

    private boolean[] sense(int[][] surroundingSpaces) {
        //Smell, Feel, Shimmer
        boolean[] senses = new boolean[3];

        //check each direction for wumpus, gold, pit
        for (int[] space : surroundingSpaces) {
            if (space[0] != 100) {
                String s = world.getFilledWorld()[space[0]][space[1]];
                if (s.equals("G")) {
                    senses[2] = true;
                } else if (s.equals("P")) {
                    senses[1] = true;
                } else if (s.equals("W")) {
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
