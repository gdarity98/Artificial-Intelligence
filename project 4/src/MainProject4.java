import java.io.File;
import java.io.FileNotFoundException;

public class MainProject4 {
    public static void main(String[] args) throws FileNotFoundException {
        File ltrack = new File("project 4/tracks/L-track.txt");
        File otrack = new File("project 4/tracks/O-track.txt");
        File rtrack = new File("project 4/tracks/R-track.txt");

        RacingSimulator lTrackSimulator = new RacingSimulator(ltrack);
        RacingSimulator oTrackSimulator = new RacingSimulator(otrack);
        RacingSimulator rTrackSimulator = new RacingSimulator(rtrack);

        lTrackSimulator.ValueIteration();
        lTrackSimulator.ModelFree();
        oTrackSimulator.ValueIteration();
        oTrackSimulator.ModelFree();
        rTrackSimulator.typeOfReset = true;
        rTrackSimulator.ValueIteration();
        rTrackSimulator.ModelFree();

    }
}
