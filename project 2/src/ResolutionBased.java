import java.util.Arrays;
import java.util.Random;

public class ResolutionBased {
    private String updatedRules = "";
    private String rules = "";
    private int[][] safe; //is this the same as visited?: 1 is safe 0 is unsafe (set as 2 for discovered obstacle?)
    private int[][] frontier; //unexplored:  1 is unexplored 0 is
    private int[][] reactiveSafe; //is this the same as visited?: 1 is safe 0 is unsafe (set as 2 for discovered obstacle?)
    private int[][] reactiveFrontier; //unexplored:  1 is unexplored 0 is explored
    private int[] playerPosition = new int[2];
    private int[] playerReactivePosition = new int[2];
    private int playerDirection;
    private int playerReactiveDirection;
    private int points = 0;
    private int reactivePoints = 0;
    private int numArrows = 0;
    private int reactiveNumArrows = 0;

    //world information
    private World world;
    private World reactiveWorld;

    //stats of the game
    private int cellsExplored = 0;
    private int wumpusKilled = 0;
    private int goldFound = 0;
    private int pitDeath = 0;
    private int wumpusDeath = 0;

    private int reactiveCellsExplored = 0;
    private int reactiveWumpusKilled = 0;
    private int reactiveGoldFound = 0;
    private int reactivePitDeath = 0;
    private int reactiveWumpusDeath = 0;

    public ResolutionBased(String rules) {
        this.rules = rules;
        this.updatedRules = this.rules;
    }

    public void runGame(int size, double[] prob, boolean runReasoning) {
        world = new World(size, prob);
        //set up copy of world for reactive agent to run on
        reactiveWorld = new World(size, prob);
        reactiveWorld.setFilledWorld(world.getFilledWorld());
        reactiveWorld.setPlayerStartingDirection(world.getPlayerStartingDirection());
        reactiveWorld.setPlayerStartingPosition(world.getPlayerStartingPosition());
        reactiveWorld.setStartingArrows(world.getStartingArrows());

        //Reasoning System Explorer
        //set up game for both reasoning
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
        numArrows = world.getStartingArrows();


        //index 0 = north, 1 = east, 2 = south, 3 = west
        int[][] surroundingSpaces = getSurrounding(playerPosition);
        boolean notDead = true;
        while (notDead) {
            //need to smell wumpus, feel wind, see shimmer
            boolean[] senses = sense(surroundingSpaces);
            //System.out.println(senses);
           
             
          if (senses[2]) { //gold
                System.out.println(2);
           
            goldFound++;
            }
            if(senses[1]){ //pit
                System.out.println(3);
             safe[playerPosition.length][playerPosition.length] = 0;
            }
            if (senses[0] && senses[1]){ //pit and wumpus
                System.out.println(4);
           
           safe[playerPosition.length][playerPosition.length] = 0;
            }
            if (senses[0]){ //wumpus
                System.out.println(5);
            
            safe[playerPosition.length][playerPosition.length] = 0;
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
             safe[surroundingSpaces.length][surroundingSpaces.length] = 0;
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
                safe[surroundingSpaces.length][surroundingSpaces.length] = 0;
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


            /*Senses key:
            0 = Wumpus (W)
            1 = Pit    (P)
            2 = Gold   (G)
            3 = SAFE?  (S)
             */
            String holderX = "";
            String holderY = "";
            String subst = unify(holderX, holderY, "");

            if (senses[3]) {
                rules = rules + "; " + unify(rules, ("Safe(" + playerPosition[0] + "," + playerPosition[1] + ")"), "");
            } else if (senses[2]) {
                //Move to find Gold???
                rules = rules + "; " + unify(rules, ("Glitter(" + playerPosition[0] + "," + playerPosition[1] + ")"), "");
            } else if (senses[1]) {
                rules = rules + "; " + unify(rules, ("Breeze(" + playerPosition[0] + "," + playerPosition[1] + ")"), "");
            } else if (senses[0]) {
                rules = rules + "; " + unify(rules, ("Smell(" + playerPosition[0] + "," + playerPosition[1] + ")"), "");
            }


            //TODO make choice based on rules (resolution) Brock
            //move to safe space, if multiple we move to one of them randomly
            //if we can't find a safe space or no rule gives safe space then move randomly

            // move choice
            int[] destSpace = new int[2];
            //example movement choice for testing
            destSpace = surroundingSpaces[0];
            playerPosition = moveReasoning(destSpace, 0);
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

            //shoot function
            boolean scream = shoot(playerPosition, playerDirection, numArrows);
            numArrows--;
            //unification

            //TODO Keep track of stats (add [stat]++ where needed) Kyler
            //this break is just so that I can test reactive without sitting in a repeating loop
            break;
        }

        //Reactive Explorer
        //it makes a decision which cell to enter at random based on
        //whether or not it believes the neighboring cell is safe.
        //So it selects first from safe neighboring cells, next
        //from unsafe neighboring cells. Record the same statistics for this reactive explorer.
        // run reactive until death or gold found
        reactiveSafe = new int[reactiveWorld.getFilledWorld().length][reactiveWorld.getFilledWorld()[0].length];
        reactiveFrontier = new int[reactiveWorld.getFilledWorld().length][reactiveWorld.getFilledWorld()[0].length];
        for(int[] row : reactiveFrontier){
            Arrays.fill(row, 1);
        }

        int[] reactiveStartingPos = reactiveWorld.getPlayerStartingPosition();
        reactiveSafe[reactiveStartingPos[0]][reactiveStartingPos[1]] = 1;
        reactiveFrontier[reactiveStartingPos[0]][reactiveStartingPos[1]] = 0;
        playerReactivePosition = reactiveStartingPos;
        playerReactiveDirection = reactiveWorld.getPlayerStartingDirection();
        reactiveNumArrows = reactiveWorld.getStartingArrows();

        runReactive();

        //Game keeps going until reasoning agent wins
        //death = new game
        //winning means stop running
        //check only if we are still running one of the games
        if(runReasoning != false){
            if(notDead == true){
                runReasoning = false;
            }
        }

        // but don't run a new game when you get to a game that successfully teleports out
        // for both reactive and reasoning
        if(runReasoning == true){
            updatedRules = rules;
            runGame(size,prob, true);
        }
        return;
    }

    private void runReactive() {
        boolean notDeadReactive = true;
        while(notDeadReactive){
            int[][] surroundingSpaces = getSurrounding(playerReactivePosition);
            //[smell, feel, shimmer]
            boolean[] senses = sense(surroundingSpaces);
            Random random = new Random();
            int prob = random.nextInt(2);

            //prioritize unexploredSpaces
            int[] exploredSpaces = new int[4];
            Arrays.fill(exploredSpaces, 100);
            for(int i = 0; i < surroundingSpaces.length; i++){
                int[] aSpace = surroundingSpaces[i];
                if(aSpace[0] != 100){
                    if(reactiveFrontier[aSpace[0]][aSpace[1]] == 0){
                        exploredSpaces[i] = i;
                    }
                }
            }

            //to move back to if necessary
            int[] startingPos = new int[2];
            // true, false, false
            if(senses[0] && !senses[1] && !senses[2]) {
                if(prob == 0){ // prob of shooting
                    //if hear scream move in any direction but that one
                    boolean scream = shoot(playerReactivePosition, playerReactiveDirection,reactiveNumArrows);
                    reactiveNumArrows--;
                    if(scream){
                        reactiveWumpusKilled++;
                        moveRandomReactive(surroundingSpaces, random, playerReactiveDirection, exploredSpaces);
                        // else move in that direction
                    }else{
                        playerReactivePosition = moveReactive(surroundingSpaces[playerReactiveDirection], playerReactiveDirection);
                    }
                }else{ // prob of moving at random
                    moveRandomReactive(surroundingSpaces, random, 100, exploredSpaces);
                }
            }
            // true, true, false
            if(senses[0] && senses[1] && !senses[2]) {
                if(prob == 0){ // prob of shooting
                    //if hear scream move in any direction but that one
                    boolean scream = shoot(playerReactivePosition, playerReactiveDirection,reactiveNumArrows);
                    reactiveNumArrows--;
                    if(scream){
                        reactiveWumpusKilled++;
                        moveRandomReactive(surroundingSpaces, random, playerReactiveDirection, exploredSpaces);
                    // else move in that direction
                    }else{
                        playerReactivePosition = moveReactive(surroundingSpaces[playerReactiveDirection], playerReactiveDirection);
                    }
                }else{ // prob of moving at random
                    moveRandomReactive(surroundingSpaces, random, 100, exploredSpaces);
                }
            }
            // true, false, true
            if(senses[0] && !senses[1] && senses[2]) {
                if(prob == 0){ // prob of shooting
                    //if hear scream move in any direction but that one
                    boolean scream = shoot(playerReactivePosition, playerReactiveDirection, reactiveNumArrows);
                    reactiveNumArrows--;
                    if(scream){
                        reactiveWumpusKilled++;
                        moveRandomReactive(surroundingSpaces, random, playerReactiveDirection, exploredSpaces);
                        // else move in that direction
                    }else{
                        playerReactivePosition = moveReactive(surroundingSpaces[playerReactiveDirection], playerReactiveDirection);
                    }
                }else{ // prob of moving at random
                    moveRandomReactive(surroundingSpaces, random, 100, exploredSpaces);
                }
            }
            // true, true, true
            if(senses[0] && senses[1] && senses[2]) {
                if(prob == 0){ // prob of shooting
                    //if hear scream move in any direction but that one
                    boolean scream = shoot(playerReactivePosition, playerReactiveDirection,reactiveNumArrows);
                    reactiveNumArrows--;
                    if(scream){
                        reactiveWumpusKilled++;
                        moveRandomReactive(surroundingSpaces, random, playerReactiveDirection, exploredSpaces);
                        // else move in that direction
                    }else{
                        playerReactivePosition = moveReactive(surroundingSpaces[playerReactiveDirection], playerReactiveDirection);
                    }
                }else{ // prob of moving at random
                    moveRandomReactive(surroundingSpaces, random, 100, exploredSpaces);
                }
            }
            // false, false, false
            if(!senses[0] && !senses[1] && !senses[2]) {
                // safe randomly pick any and move
                //prob of moving to random space
                moveRandomReactive(surroundingSpaces, random, 100, exploredSpaces);
            }
            // false, true, false
            if(!senses[0] && senses[1] && !senses[2]) {
                // prob of moving at random
                moveRandomReactive(surroundingSpaces, random, 100, exploredSpaces);
            }
            // false, true, true
            if(!senses[0] && senses[1] && senses[2]) {
                // prob of moving at random
                startingPos[0] = playerReactivePosition[0];
                startingPos[1] = playerReactivePosition[1];
                moveRandomReactive(surroundingSpaces, random, 100, exploredSpaces);
            }
            // false, false, true
            if(!senses[0] && !senses[1] && senses[2]) {
                // prob of moving at random
                startingPos[0] = playerReactivePosition[0];
                startingPos[1] = playerReactivePosition[1];
                moveRandomReactive(surroundingSpaces, random, 100, exploredSpaces);
            }

            // check to see if we died for all senses except for the ones that can see a shimmer of gold
            notDeadReactive = checkForAliveReactive();
            boolean gold = checkForGoldReactive();
            if(!notDeadReactive){
                continue;
            }else if(gold){
                reactiveGoldFound++;
                break;
            }else{
                reactiveSafe[playerReactivePosition[0]][playerReactivePosition[1]] = 1;
                reactiveFrontier[playerReactivePosition[0]][playerReactivePosition[1]] = 0;
                reactiveCellsExplored++;
                if(senses[2]){
                    // if we did sense gold, but we moved away then go back
                    if(playerReactiveDirection == 0){
                        playerReactivePosition = moveReactive(startingPos,2);
                    }else if(playerReactiveDirection == 1){
                        playerReactivePosition = moveReactive(startingPos,3);
                    }else if(playerReactiveDirection == 2){
                        playerReactivePosition = moveReactive(startingPos,0);
                    }else if(playerReactiveDirection == 3){
                        playerReactivePosition = moveReactive(startingPos,1);
                    }
                }
            }
            System.out.println("RUNNING");
            //TODO Keep track of stats for Reactive (add [stat]++ where needed) Gabe
        }
    }

    private boolean checkForGoldReactive() {
        if(world.getFilledWorld()[playerReactivePosition[0]][playerReactivePosition[1]].equals("G")) {
            reactivePoints += 1000;
            //teleport to safety
            return true;
        }else{
            return false;
        }
    }

    private boolean checkForAliveReactive() {
        if (reactiveWorld.getFilledWorld()[playerReactivePosition[0]][playerReactivePosition[1]].equals("W")) {
            reactiveWumpusDeath++;
            reactivePoints -= 10000;
            return false;
        } else if (reactiveWorld.getFilledWorld()[playerReactivePosition[0]][playerReactivePosition[1]].equals("P")) {
            reactivePitDeath++;
            reactivePoints -= 10000;
            return false;
        } else{
            return true;
        }
    }

    private void moveRandomReactive(int[][] surroundingSpaces, Random random, int notAllowedDirection, int[] notAllowedSpace) {
        int highIndexOfViableMoves = 0;
        for(int i = 0; i < surroundingSpaces.length; i++){
            int[] space = surroundingSpaces[i];
            if(space[0] != 100){
                highIndexOfViableMoves = i;
            }
        }

        int randomMove = 0;
        boolean notViableMove = true;
        while(notViableMove) {
            boolean viableSpace = true;
            randomMove = random.nextInt(highIndexOfViableMoves+1);
            for(int i = 0; i < notAllowedSpace.length; i++){
                if(randomMove == notAllowedSpace[i]) {
                    viableSpace = false;
                    break;
                }
            }
            int numNotAllowed = 0;
            int uniqueNotAllowedDirection = 0;
            for(int i = 0; i < notAllowedSpace.length; i++){
                if(notAllowedSpace[i] != 100){
                    numNotAllowed++;
                }
                if(surroundingSpaces[i][0] == 100){
                    numNotAllowed++;
                }
                if(notAllowedSpace[i] != notAllowedDirection){
                    uniqueNotAllowedDirection++;
                }
            }
            if(uniqueNotAllowedDirection == 4){
                numNotAllowed++;
            }

            //if we have explored all the surrounding spaces this lets us go back to them and choose randomly
            if(numNotAllowed == 4){
                viableSpace = true;
            }

            if (surroundingSpaces[randomMove][0] != 100 && randomMove != notAllowedDirection && viableSpace) {
                notViableMove = false;
            }
        }
        playerReactivePosition = moveReactive(surroundingSpaces[randomMove], randomMove);
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
//        Find out if x and y are ???compounds??? and return
//        } else if (false /* COMPOUND?(x) && COMPOUND?(y) */) {
//            return unify(x, y, unify(x, y, substList)); //pseudo: UNIFY(ARGS[x], ARGS[y], UNIFY(OP[x], OP[y], THETA)) THETA = substList
//        } else if (false/* LIST?(x) && LIST?(y) */) {
//            return unify(x, y, unify(x, y, substList)); //pseudo: UNIFY(REST[x], REST[y], UNIFY(FIRST[x], FIRST[y], THETA)) THETA = substList
        } else {
            return "FAILURE";
        }
    }

    //TODO Figure out what the hell this algorithm is supposed to do
    private String unifyVariables(String var, String x, String subRules) {
        return "THIS DOES NOT WORK YET";
    }

    //returns if you heard a scream or not
    private boolean shoot(int[] playerPosition, int playerDirection, int numArrows) {
        //check each space to direction facing for a wumpus or obstacle or border(end of array)
        if(numArrows > 0){
            if(playerDirection == 0){//if player direction is 0 then need to decrease playerPosition[0] until <=/==/idk 0 lol
                for(int i = playerPosition[0]; i >= 0; i--){
                    //if find obstacle first then return false
                    //if find wumpus first then return true
                    //if find end of array first then return false
                    if(world.getFilledWorld()[i][playerPosition[1]].equals("O")){
                        return false;
                    }else if(world.getFilledWorld()[i][playerPosition[1]].equals("W")){
                        return true;
                    }
                }
                return false;
            }else if(playerDirection == 2){//if player direction is 2 then need to increase playPos[0] until 4
                for(int i = playerPosition[0]; i <= 4; i++){
                    //if find obstacle first then return false
                    //if find wumpus first then return true
                    //if find end of array first then return false
                    if(world.getFilledWorld()[i][playerPosition[1]].equals("O")){
                        return false;
                    }else if(world.getFilledWorld()[i][playerPosition[1]].equals("W")){
                        return true;
                    }
                }
                return false;
            }else if(playerDirection == 3){//if player direction is 3 then need to decrease playPos[1] until 0
                for(int i = playerPosition[1]; i >= 0; i--){
                    //if find obstacle first then return false
                    //if find wumpus first then return true
                    //if find end of array first then return false
                    if(world.getFilledWorld()[playerPosition[0]][i].equals("O")){
                        return false;
                    }else if(world.getFilledWorld()[playerPosition[0]][i].equals("W")){
                        return true;
                    }
                }
                return false;
            }else{ //if player direction is 1 then need to increase playPos[1] until 4
                for(int i = playerPosition[1]; i <= 4; i++){
                    //if find obstacle first then return false
                    //if find wumpus first then return true
                    //if find end of array first then return false
                    if(world.getFilledWorld()[playerPosition[0]][i].equals("O")){
                        return false;
                    }else if(world.getFilledWorld()[playerPosition[0]][i].equals("W")){
                        return true;
                    }
                }
                return false;
            }
        }else{
            return false;
        }
    }

    private int[] moveReasoning(int[] destSpace, int destDirection) {
        // need to add cost
        // facing the direction we need to be
        if (playerDirection == destDirection) {
            //lose a point for moving forward
            //check if next space is obstacle
            if(destSpace[0] == 100){
                // moves forward into a border and explorer knows it cannot move there
                return playerPosition;
            }
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
            if(destSpace[0] == 100){
                points -= 1;
                return playerPosition;
            }
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
            if(destSpace[1] == 100){
                points -= 1;
                return playerPosition;
            }
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

    private int[] moveReactive(int[] destSpace, int destDirection) {
        // need to add cost
        // facing the direction we need to be
        if(playerReactiveDirection == destDirection){
            //lose a point for moving forward
            //check if next space is obstacle
            if(destSpace[0] == 100){
                // moves forward into a border and explorer knows it cannot move there
                return playerReactivePosition;
            }
            if (reactiveWorld.getFilledWorld()[destSpace[0]][destSpace[1]].equals("O")) {
                reactiveFrontier[destSpace[0]][destSpace[1]] = 0;
                reactivePoints -= 1;
                return playerReactivePosition;
            } else {
                reactivePoints -= 1;
                return destSpace;
            }
            // need to turn 180 degrees
        } else if (Math.abs(playerReactiveDirection - destDirection) == 2) {
            // lose two points for turning
            reactivePoints -= 2;
            if(destSpace[0] == 100){
                reactivePoints -= 1;
                return playerReactivePosition;
            }
            playerReactiveDirection = destDirection;
            //lose a point for moving forward
            if (reactiveWorld.getFilledWorld()[destSpace[0]][destSpace[1]].equals("O")) {
                reactiveFrontier[destSpace[0]][destSpace[1]] = 0;
                reactivePoints -= 1;
                return playerReactivePosition;
            } else {
                reactivePoints -= 1;
                return destSpace;
            }
            // need to turn 90 degrees
        } else {
            reactivePoints -= 1;
            if(destSpace[1] == 100){
                reactivePoints -= 1;
                return playerReactivePosition;
            }
            playerReactiveDirection = destDirection;
            if (reactiveWorld.getFilledWorld()[destSpace[0]][destSpace[1]].equals("O")) {
                reactiveFrontier[destSpace[0]][destSpace[1]] = 0;
                reactivePoints -= 1;
                return playerReactivePosition;
            } else {
                reactivePoints -= 1;
                return destSpace;
            }
        }
    }

    private int[][] getSurrounding(int[] pPosition) {
        int pRow = pPosition[0];
        int pCol = pPosition[1];

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
        if ((pRow == 0 || pRow == (world.getFilledWorld()[0].length - 1)) && (pCol == 0 || pCol == (world.getFilledWorld()[0].length - 1))) {
            if (pRow == 0 && pCol == 0) {
                east[0] = pRow;
                east[1] = pCol + 1;
                south[0] = pRow + 1;
                south[1] = pCol;
            } else if (pRow == 0 && pCol == (world.getFilledWorld()[0].length - 1)) {
                south[0] = pRow + 1;
                south[1] = pCol;
                west[0] = pRow;
                west[1] = pCol - 1;
            } else if (pRow == (world.getFilledWorld()[0].length - 1) && pCol == 0) {
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
        } else if (pRow == 0 || pRow == (world.getFilledWorld()[0].length - 1)) {
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
        } else if (pCol == 0 || pCol == (world.getFilledWorld()[0].length - 1)) {
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
        // idk why the fuck we are having a forth sense I swear to god
        boolean[] senses = new boolean[4];

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

    public void printStatsFull(){
        System.out.println("Reasoning:");
        printStatForType(cellsExplored, goldFound, pitDeath, wumpusDeath, wumpusKilled, points);

        System.out.println("Reactive:");
        printStatForType(reactiveCellsExplored, reactiveGoldFound, reactivePitDeath, reactiveWumpusDeath, reactiveWumpusKilled, reactivePoints);
    }

    private void printStatForType(int cellsExplored, int goldFound, int pitDeath, int wumpusDeath, int wumpusKilled, int points) {
        System.out.println("Cells Explored: " + cellsExplored);
        System.out.println("Gold Found: " + goldFound);
        System.out.println("Pit Deaths: " + pitDeath);
        System.out.println("Wumpus Deaths: " + wumpusDeath);
        System.out.println("Wumpus Killed: " + wumpusKilled);
        System.out.println("Points: " + points);
    }

    public int getCellsExplored() {
        return cellsExplored;
    }

    public int getGoldFound() {
        return goldFound;
    }

    public int getPitDeath() {
        return pitDeath;
    }

    public int getWumpusDeath() {
        return wumpusDeath;
    }

    public int getWumpusKilled() {
        return wumpusKilled;
    }

    public int getPoints() {
        return points;
    }

    public int getReactiveCellsExplored() {
        return reactiveCellsExplored;
    }

    public int getReactiveGoldFound() {
        return reactiveGoldFound;
    }

    public int getReactivePitDeath() {
        return reactivePitDeath;
    }

    public int getReactiveWumpusDeath() {
        return reactiveWumpusDeath;
    }

    public int getReactiveWumpusKilled() {
        return reactiveWumpusKilled;
    }

    public int getReactivePoints() {
        return reactivePoints;
    }
}
