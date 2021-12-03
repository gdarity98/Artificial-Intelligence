import java.util.Random;

public class Racecar {
    int[] position = new int[2];
    int[] speed = new int[2];
    int xAcceleration;
    int yAcceleration;

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public void setPosition(int xPosition, int yPosition) {
        this.position[0] = xPosition;
        this.position[1] = yPosition;
    }

    public int[] getSpeed() {
        return speed;
    }

    public static int[] getStartingPosition() {
        return new int[2];
    }

    public Racecar() {
        position = getStartingPosition();
    }

    public Racecar(int[] startPosition) {
        this.position = startPosition;
    }

    public void setSpeed(int[] speed) {
        if (speed[0] < -5) {
            speed[0] = -5;
        }
        if (speed[0] > 5) {
            speed[0] = 5;
        }
        if (speed[1] < -5) {
            speed[1] = -5;
        }
        if (speed[1] > 5) {
            speed[1] = 5;
        }
        this.speed = speed;
    }

    public void updateSpeed(int xAcceleration, int yAcceleration) {
        speed[0] = speed[0] + xAcceleration;
        speed[1] = speed[1] + yAcceleration;
        setSpeed(speed);
    }

    public void setAcceleration(int xAcceleration, int yAcceleration) {
        this.xAcceleration = xAcceleration;
        this.yAcceleration = yAcceleration;
    }

    public void updateAccelerate(int xAcceleration, int yAcceleration) {
        if (xAcceleration < -1) {
            xAcceleration = -1;
        }
        if (xAcceleration > 1) {
            xAcceleration = 1;
        }
        if (yAcceleration < -1) {
            yAcceleration = -1;
        }
        if (yAcceleration > 1) {
            yAcceleration = 1;
        }

        int spill = 0;
        if (!checkOilSpill()) {
            this.setAcceleration(xAcceleration, yAcceleration);
            this.updateSpeed(xAcceleration, yAcceleration);
        } else {
            this.setAcceleration(0, 0);
        }
    }

    public boolean checkOilSpill() {
        Random random = new Random();
        int spillProb = random.nextInt(100);
        return spillProb <= 20;
    }

//This is here as legacy code but may be useful, DELETE BEFORE TURN IN
//    public void moveCar(int xAcceleration, int yAcceleration) {
//        updateAccelerate(xAcceleration, yAcceleration);
//        for (int rows = 0; rows < speed[1]; rows++) {
//            for (int columns = 0; columns < speed[2]; columns++) {
//                if (true) {
//
//                }
//            }
//        }
//    }

}
