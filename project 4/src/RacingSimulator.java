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
                        racecar.setPosition(row, column);
                        startingPosition = new int[]{row, column};
                    }
                    countStartStates--;
                }
            }
        }
    }

    public void moveCar(int xAcceleration, int yAcceleration) {
        racecar.updateAccelerate(xAcceleration, yAcceleration);
        int[] speed = racecar.getSpeed();
        for (int rows = 0; rows < speed[1]; rows++) {
            for (int columns = 0; columns < speed[2]; columns++) {
                if (racetrack[rows][columns] == 'F') {
                    raceDone = true;
                }
                if (racetrack[rows][columns] == '#') {
                    if (!typeOfReset) {
                        racecar.setSpeed(new int[]{0, 0});
                    } else {
                        racecar.setPosition(startingPosition);
                    }
                }
            }
        }
    }

    //TODO: Value Iteration algorithm
    //Call updateAcceleration not setAcceleration
    public void ValueIteration() {

        //This is here for testing purpose and needs to be removed
        for (int i = 0; i < 10000; i++) {
            racecar.updateAccelerate(1, 1);
        }
    }

    //TODO: Model free learning algorithm
    public void ModelFree() {

    }

}
