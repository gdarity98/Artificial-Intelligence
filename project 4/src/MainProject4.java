import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;

public class MainProject4 {
    public static void main(String[] args) throws FileNotFoundException {
        File ltrack = new File("project 4/tracks/L-track.txt");
        File otrack = new File("project 4/tracks/O-track.txt");
        File rtrack = new File("project 4/tracks/R-track.txt");

        RacingSimulator lTrackSimulator = new RacingSimulator(ltrack);
        RacingSimulator oTrackSimulator = new RacingSimulator(otrack);
        RacingSimulator rTrackSimulator = new RacingSimulator(rtrack);

        Hashtable<String, int[]> lTrackPolicy = lTrackSimulator.ValueIteration();
        lTrackSimulator.ModelFree();
        Hashtable<String, int[]> oTrackPolicy = oTrackSimulator.ValueIteration();
        oTrackSimulator.ModelFree();
        rTrackSimulator.typeOfReset = true;
        Hashtable<String, int[]> rTrackPolicy = rTrackSimulator.ValueIteration();
        rTrackSimulator.ModelFree();

    }
}
