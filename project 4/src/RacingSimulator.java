import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

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
        startStateHolder = random.nextInt(countStartStates);
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
        racetrack[racecar.position[1]][racecar.position[0]] = 'C';
        bresenham(racecar.position[1], racecar.position[0], racecar.position[1] + racecar.speed[0],
                racecar.position[0] + racecar.speed[1]);
    }

    void bresenham(int x1, int y1, int x2, int y2) {
        int m_new = 2 * (y2 - y1);
        int slope_error_new = m_new - (x2 - x1);

        for (int x = x1, y = y1; x < x2; x++) {
            if (x > racetrack[y].length || y > racetrack.length) {
                //System.out.print("(" + x + "," + y + ")\n");
                if (racetrack[y][x] == '#') {
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

    //TODO: Value Iteration algorithm (finished MDP SETUP)
    //Call updateAcceleration not setAcceleration
    //Call moveCar after updateAcceleration
    public void ValueIteration() {

        //Set Up MDP
        // tunable paramaters
        double bellmanErrorMagnitude = 0.001;
        double gamma;
        double noise;

        // states and actions
        // defining all actions
        //a_x, a_y can be -1,0,1, the possible actions are we can currently take are accelerate, decelerate, and turn
        int[][] actions = new int[9][2];
        int[] action = new int[2];
        //[1,1] accelerate
        actions[0][0] = 1;
        actions[0][1] = 1;
        //[-1,-1] decelerate
        actions[1][0] = -1;
        actions[1][1] = -1;
        // [0,0] no change
        actions[2][0] = 0;
        actions[2][1] = 0;
        //turns
        actions[3][0] = 0;
        actions[3][1] = 1;
        //[[0,-1]
        actions[4][0] = 0;
        actions[4][1] = -1;
        //[1,0]
        actions[5][0] = 1;
        actions[5][1] = 0;
        //[1,-1]
        actions[6][0] = 1;
        actions[6][1] = -1;
        //[-1,0]
        actions[7][0] = -1;
        actions[7][1] = 0;
        //[-1,1]
        actions[8][0] = -1;
        actions[8][1] = 1;

        Hashtable<int[],int[][]> possibleStateActions = new Hashtable<int[],int[][]>();
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
                if(c != '#' && c != 'F' && c != 'C'){
                    possibleStateActions.put(state, actions);
                }
                stateCount++;
                setUpY++;
            }
            setUpX++;
        }

        // rewards
        int[][] rewards = new int[racetrack.length][racetrack[0].length];
        for(int rRow = 0; rRow < racetrack.length; rRow++){
            for(int rCol = 0; rCol < racetrack[0].length; rCol++){
                if(racetrack[rRow][rCol] == '#' || racetrack[rRow][rCol] == 'C'){
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
        Hashtable<int[],int[]> policy = new Hashtable<int[],int[]>();
        Enumeration<int[]> keys = possibleStateActions.keys();
        while(keys.hasMoreElements()){
            int[] key = keys.nextElement();
            int[][] stateAction = possibleStateActions.get(key);
            int randomAction = random.nextInt(8);
            int[] chosenAction = stateAction[randomAction];
            policy.put(key,chosenAction);
        }

        // initial value function (if it is a state that has actions then give it a value)
        Hashtable<int[], Integer> V = new Hashtable<int[], Integer>();
        for(int[] state : states){
            V.put(state,rewards[state[0]][state[1]]);
        }

        //TODO
        // Value Iteration

    }

    //TODO: Model free learning algorithm
    public void ModelFree() {

    }

}
