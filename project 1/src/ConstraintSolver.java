public abstract class ConstraintSolver {
    public PuzzleImporter puzzles[];
    public String variation = "";

    public ConstraintSolver(PuzzleImporter array[], String variationName){
        puzzles = array;
        variation = variationName;
    }

    public void validateColumn(int puzzleIndex, int x, int y){

    }

    public void validateRow(int puzzleIndex, int x, int y){

    }

    public void validate3x3Box(int puzzleIndex, int x, int y){

    }

}
