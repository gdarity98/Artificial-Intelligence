public abstract class ConstraintSolver {
    public PuzzleImporter puzzles[];
    public String variation = "";

    public ConstraintSolver(PuzzleImporter array[], String variationName){
        puzzles = array;
        variation = variationName;
    }

    public int validateSpace(PuzzleImporter puzzle, int x, int y){
        int numConflicts = 0;
        //get neighbors and check for same number
        int[][] neighbors = puzzle.getNeighbors(x,y);
        int[][] board = puzzle.getSudokuPuzzle();
        for(int[] neighbor : neighbors){
            if(board[neighbor[0]][neighbor[1]] == board[x][y]){
                numConflicts++;
            }
        }
        return numConflicts;
    }

    public void initializePuzzle(PuzzleImporter puzzle){

    };
}
