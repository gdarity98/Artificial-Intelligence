import java.io.File;
import java.io.FileNotFoundException;
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

    //TODO: Value Iteration algorithm
    //Call updateAcceleration not setAcceleration
    //Call moveCar after updateAcceleration
    public void ValueIteration() {

        //This is here for testing purpose and needs to be removed
        for (int i = 0; i < 10000; i++) {
            racecar.updateAccelerate(1, 1);
            moveCar();
        }
    }

    //TODO: Model free learning algorithm
    public void ModelFree() {

    }

}
