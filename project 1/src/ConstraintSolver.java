public abstract class ConstraintSolver {
    public PuzzleImporter puzzles[];

    public ConstraintSolver(PuzzleImporter array[]){
        puzzles = array;
    }

    public void validateColumn(int puzzleIndex, int x, int y){

    }

    public void validateRow(int puzzleIndex, int x, int y){

    }

    public void validate3x3Box(int puzzleIndex, int x, int y){

    }

}
