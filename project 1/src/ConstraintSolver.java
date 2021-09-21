public abstract class ConstraintSolver {
    public PuzzleImporter puzzles[];
    public String variation = "";
    public int tracker = 0;

    public ConstraintSolver(PuzzleImporter array[], String variationName){
        puzzles = array;
        variation = variationName;
    }

    public void initializePuzzle(PuzzleImporter puzzle, String type){

    };

    public int getTracker() {
        return tracker;
    }

    public void setTracker(int tracker) {
        this.tracker = tracker;
    }
}
