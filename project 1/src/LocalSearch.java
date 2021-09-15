import java.util.Random;

public class LocalSearch extends ConstraintSolver{

    public LocalSearch(PuzzleImporter[] array, String variationName) {
        super(array, variationName);
        if(variationName.equals("simulatedAnnealing")){
            for(int i = 0; i < puzzles.length; i++){
                puzzles[i] = simulatedAnnealing(puzzles[i]);
            }

        }else if(variationName.equals("geneticAlgorithm")){
            geneticAlgorithm();
        }else{
            System.out.println("Variation does not exist");
        }
    }

    public PuzzleImporter simulatedAnnealing(PuzzleImporter puzzle){
        // need input of problem and schedule
        // current, next, T local variables
        int[] current = new int[2];
        int[] next = new int[2];
        int T0 = 1000000; //I have no idea how this temp thing works rn
        int Tt;

        // I am initializing the puzzle
        initializePuzzle(puzzle);

        // initialize the current node with a random space we have
        Random random = new Random();
        int rowCurrent = random.nextInt(9);
        int colCurrent = random.nextInt(9);
        current[0] = rowCurrent;
        current[1] = colCurrent;

        //for i until inf or until a set fitness or number of runs or something that stops the program
        for(int i = 0; i < 1000000000; i ++) {

            //Set the Temperature for the iteration we are on
            //schedule slowly reduces the temp down to 0
            int t = i;
            Tt = schedule(t, T0);

            //if temp = 0
            if(Tt == 0){
                //   return current (idk if current is a space or a puzzle state lmfao)
                //   idk whats happening big fun
                return puzzle;
            }
            //end if

            //next = random neighbor of current
            // make sure the next value is not a locked one because we can't change that
            int[][] neighborsCurrent = puzzle.getNeighbors(current[0], current[1]);
            int randomNeighbor = random.nextInt(neighborsCurrent.length);
            next = neighborsCurrent[randomNeighbor];
            while(puzzle.isLocked(next[0], next[1])){
                randomNeighbor = random.nextInt(neighborsCurrent.length);
                next = neighborsCurrent[randomNeighbor];
            }

            //delta E: stands for energy (number of conflicts)
            int deltaE = validateSpace(puzzle, next[0], next[1]) - validateSpace(puzzle, current[0], current[1]);

            //if the next has neg energy then we take it (less conflicts)
            if(deltaE < 0){
                int[][] board = puzzle.getSudokuPuzzle();
                board[current[0]][current[1]] = board[next[0]][next[1]];
                puzzle.setSudokuPuzzle(board);
                current = next;
            //else we may take it due to a probability
            }else{
                //boltzmann probability (K tunable parameter)
                //when temp is high we should always take the worst
                // k is a tuned parameter. We are setting it to 1.
                double k = 1;
                double nextProb = Math.exp((double)deltaE / (k*(double)Tt));
                double currentProb = 1 - nextProb;
            }
            //end if
        }
        //end for
        // This is just auto return after for loop if we end up not returning but leaving the for loop
        return puzzle;
    }

    private void geneticAlgorithm() {
    }

    @Override
    public void initializePuzzle(PuzzleImporter puzzle) {
        int[][] newPuzzle = puzzle.getSudokuPuzzle();
        for(int row = 0; row < newPuzzle.length; row++){
            for(int col = 0; col < newPuzzle[0].length; col++){
                if(!puzzle.isLocked(row, col)){
                    Random random = new Random();
                    int value = random.nextInt(9)+1;
                    newPuzzle[row][col] = value;
                }
            }
        }
        puzzle.setSudokuPuzzle(newPuzzle);
    }

    public int schedule(int t, int T0){
        int nt = t;
        int tunedParameter = 1;
        int Tt = ((T0*tunedParameter)/(tunedParameter + nt));
        return Tt;
    }
}
