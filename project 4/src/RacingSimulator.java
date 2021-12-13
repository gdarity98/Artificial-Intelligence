import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class RacingSimulator {
    char[][] racetrack;
    Racecar racecar;
    int countStartStates;
    int startStateHolder;
    int[] startingPosition;
    int[][] lineDetection;
    boolean typeOfReset = false;
    boolean raceDone = false;
    int cost;
    int timeStep;
    double discountFactor;
    int iterations;
    int numSteps;

    public RacingSimulator(char[][] racetrack) {
        this.racetrack = racetrack;
        racecar = new Racecar();
    }

    public RacingSimulator(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String temp = scanner.nextLine();
        String[] breakApart = temp.split(",");
        int rowSize = Integer.parseInt(breakApart[0]);
        int columnSize = Integer.parseInt(breakApart[1]);
        racetrack = new char[rowSize][columnSize];

        for (int row = 0; row < rowSize; row++) {
            String holder = scanner.nextLine();
            for (int column = 0; column < columnSize; column++) {
                if (holder.charAt(column) == 'S') {
                    countStartStates++;
                }
                racetrack[row][column] = holder.charAt(column);
            }
        }

        Random random = new Random(System.currentTimeMillis());
        //changed to +1 because counting start states starting at 1 so need random int from 1-4 not 0-3
        startStateHolder = random.nextInt(countStartStates)+1;
//        if(startStateHolder == 0){
//            System.out.println("I think this is an error");
//        }
        racecar = new Racecar();

        for (int row = 0; row < rowSize; row++) {
            for (int column = 0; column < columnSize; column++) {
                if (racetrack[row][column] == 'S') {
                    if (countStartStates == startStateHolder) {
                        racecar.setPosition(column, row);
                        startingPosition = new int[]{column, row};
                    }
                    countStartStates--;
                }
            }
        }
    }

    public void moveCar() {
        //racetrack[racecar.position[1]][racecar.position[0]] = 'C';
        bresenham(racecar.position[1], racecar.position[0], racecar.position[1] + racecar.speed[0],
                racecar.position[0] + racecar.speed[1]);
        racetrack[racecar.position[1]][racecar.position[0]] = 'C';
    }

    void bresenham(int x1, int y1, int x2, int y2) {
        int m_new = 2 * (y2 - y1);
        int slope_error_new = m_new - (x2 - x1);

        for (int x = x1, y = y1; x <= x2; x++) {
            if (!(x >= racetrack.length) && !(y >= racetrack[0].length)) {
                //System.out.print("(" + x + "," + y + ")\n");
                if (racetrack[x][y] == '#') {
                    if (!typeOfReset) {
                        racecar.position = new int[]{y, x};
                        racecar.speed = new int[]{0, 0};
                    } else {
                        racecar.position = startingPosition;
                    }
                } else {
                    racecar.position[0] = y;
                    racecar.position[1] = x;
                }
                // Add slope to increment angle formed
                slope_error_new += m_new;


                // Slope error reached limit, time to
                // increment y and update slope error.
                if (slope_error_new >= 0) {
                    y++;
                    slope_error_new -= 2 * (x2 - x1);
                }
            }
        }
    }

    //Value Iteration algorithm
    //Call updateAcceleration not setAcceleration
    //Call moveCar after updateAcceleration
    public Hashtable<String, int[]> ValueIteration() {
        //racecar.setAcceleration(0,0);
        moveCar();

        //Set Up MDP
        // tunable paramaters
        double bellmanErrorMagnitude = 0.001;
        double gamma = 0.9;
        double noise = 0.1;

        // states and actions
        // defining all actions
        //a_x, a_y can be -1,0,1, the possible actions are we can currently take are accelerate, decelerate, and turn
        int[][] actions = new int[8][2];
        int[] action = new int[2];
        //[1,1] accelerate
        actions[0][0] = 1;
        actions[0][1] = 1;
        //[-1,-1] decelerate
        actions[1][0] = -1;
        actions[1][1] = -1;
        //turns
        actions[2][0] = 0;
        actions[2][1] = 1;
        //[[0,-1]
        actions[3][0] = 0;
        actions[3][1] = -1;
        //[1,0]
        actions[4][0] = 1;
        actions[4][1] = 0;
        //[1,-1]
        actions[5][0] = 1;
        actions[5][1] = -1;
        //[-1,0]
        actions[6][0] = -1;
        actions[6][1] = 0;
        //[-1,1]
        actions[7][0] = -1;
        actions[7][1] = 1;

        Hashtable<String,int[][]> possibleStateActions = new Hashtable<String,int[][]>();
        // creating states and action assignment to states that have actions
        int[][] states = new int[racetrack.length*racetrack[0].length][2];
        int setUpX  = 0;
        int stateCount = 0;
        for(char[] row : racetrack){
            int setUpY = 0;
            for(char c : row){
                int[] state = new int[2];
                state[0] = setUpX;
                state[1] = setUpY;
                states[stateCount] = state;
                if(c != '#'){
                    possibleStateActions.put(Arrays.toString(state), actions);
                }
                stateCount++;
                setUpY++;
            }
            setUpX++;
        }

        // rewards
        double[][] rewards = new double[racetrack.length][racetrack[0].length];
        for(int rRow = 0; rRow < racetrack.length; rRow++){
            for(int rCol = 0; rCol < racetrack[0].length; rCol++){
                if(racetrack[rRow][rCol] == '#'){
                    rewards[rRow][rCol] = -1;
                }else if(racetrack[rRow][rCol] == 'F'){
                    rewards[rRow][rCol] = 1;
                }else{
                    rewards[rRow][rCol] = 0;
                }
            }
        }

        // initial policy (pick a random action for each state)
        Random random = new Random();
        Hashtable<String,int[]> policy = new Hashtable<String,int[]>();
        Enumeration<String> keys = possibleStateActions.keys();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            int[][] stateAction = possibleStateActions.get(key);
            int randomAction = random.nextInt(7);
            int[] chosenAction = stateAction[randomAction];
            policy.put(key,chosenAction);
        }

        // initial value function (if it is a state that has actions then give it a value)
        Hashtable<String, Double> V = new Hashtable<String, Double>();
        for(int[] state : states){
            V.put(Arrays.toString(state),rewards[state[0]][state[1]]);
        }

        // Value Iteration
        int numIterations = 0;
        while(true){
            double largestChange = 0;
            for(int[] state : states){
                if(policy.containsKey(Arrays.toString(state))){
                    double oldV = V.get(Arrays.toString(state));
                    double newV = 0;

                    //pick the best action
                    int[][] actionsForState = possibleStateActions.get(Arrays.toString(state));
                    for(int[] actionForState : actionsForState){

                        //transition probability 20% do nothing 80% do something
                        int[] acceleration = new int[2];
                        boolean accelerationFail = false;
                        double prob = random.nextDouble();
                        if(prob <= 0.2){
                            accelerationFail = true;
                        }
                        if(accelerationFail){
                            acceleration[0] = 0;
                            acceleration[0] = 0;
                        }else{
                            acceleration = actionForState;
                        }
                        racecar.position[0] = state[1];
                        racecar.position[1] = state[0];
                        int[] oldAcceleration = new int[2];
                        oldAcceleration[0] = racecar.xAcceleration;
                        oldAcceleration[1] = racecar.yAcceleration;
                        racecar.updateAccelerate(acceleration[0], acceleration[1]);
                        int[] oldPosition = new int[2];
                        oldPosition[0] = racecar.position[0];
                        oldPosition[1] = racecar.position[1];
                        moveCar();
                        int[] newPosition = new int[2];
                        newPosition[0] = racecar.position[0];
                        newPosition[1] = racecar.position[1];

                        //need to get the state we are in if we take the action
                        int[] keyFromPosition = new int[2];
                        keyFromPosition[0] = newPosition[1];
                        keyFromPosition[1] = newPosition[0];
                        double v = rewards[state[0]][state[1]] + (gamma * ((1-noise)*V.get(Arrays.toString(keyFromPosition))));
                        if(v > newV){
                            newV = v;
                            policy.put(Arrays.toString(state),acceleration);
                        }
                        racecar.position = oldPosition;
                        racecar.setAcceleration(oldAcceleration[0], oldAcceleration[1]);
                    }
                    V.put(Arrays.toString(state), newV);
                    largestChange = Math.max(largestChange, Math.abs(oldV - V.get(Arrays.toString(state))));
                }
            }
            System.out.println("Iteration " + numIterations + ": " + largestChange);
            //System.out.println("Iteration " + numIterations);
            if(largestChange < bellmanErrorMagnitude){
                break;
            }
            numIterations++;
        }
        return policy;
    }

    //TODO: Model free learning algorithm
    public void ModelFree() {
        Random random = new Random();
    int length = racetrack.length;
    int goal ='F';
    double discount = 0.5;
    double learnRate = 0.5;
    int max = 1000;
    double[][] rewards = new double[racetrack.length][racetrack[0].length];
        for(int rRow = 0; rRow < racetrack.length; rRow++){
            for(int rCol = 0; rCol < racetrack[0].length; rCol++){
                if(racetrack[rRow][rCol] == '#'){
                    rewards[rRow][rCol] = -1;
                }else if(racetrack[rRow][rCol] == 'F'){
                    rewards[rRow][rCol] = 1;
                }else{
                    rewards[rRow][rCol] = 0;
                }
            }
        }
    double[][] quality = new double[length][];
    for (int i = 0; i < length; ++i)
    quality[i] = new double[length];
    }
   

    }

}
