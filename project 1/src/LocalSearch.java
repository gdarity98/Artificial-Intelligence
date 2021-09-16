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
        // int[] current = new int[2];
        // int[] next = new int[2];
        int T0 = 1000000000; //I have no idea how this temp thing works rn
        int Tt;

        // I am initializing the board state
        initializePuzzle(puzzle);

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

            // getting the current board and setting up the nextBoard state
            int[][] currBoard = puzzle.getSudokuPuzzle();
            int[][] nextBoard = new int[9][9];
            for(int row = 0; row < nextBoard.length; row++){
                for(int col = 0; col <nextBoard[0].length; col++){
                    nextBoard[row][col] = currBoard[row][col];
                }
            }

            //randomly select a 3*3 and then swap two values within that 3*3
            Random random = new Random();
            int chosenCube = random.nextInt(9);
            int x1 = 0;
            int x2 = 0;
            int y1 = 0;
            int y2 = 0;
            boolean locked = true;

            while((x1 == x2 && y1 == y2) || locked){
                switch(chosenCube){
                    case 0:
                        y1 = random.nextInt(3);
                        y2 = random.nextInt(3);
                        x1 = random.nextInt(3);
                        x2 = random.nextInt(3);
                        break;
                    case 1:
                        y1 = random.nextInt(3)+3;
                        y2 = random.nextInt(3)+3;
                        x1 = random.nextInt(3);
                        x2 = random.nextInt(3);
                        break;
                    case 2:
                        y1 = random.nextInt(3)+6;
                        y2 = random.nextInt(3)+6;
                        x1 = random.nextInt(3);
                        x2 = random.nextInt(3);
                        break;
                    case 3:
                        y1 = random.nextInt(3);
                        y2 = random.nextInt(3);
                        x1 = random.nextInt(3)+3;
                        x2 = random.nextInt(3)+3;
                        break;
                    case 4:
                        y1 = random.nextInt(3)+3;
                        y2 = random.nextInt(3)+3;
                        x1 = random.nextInt(3)+3;
                        x2 = random.nextInt(3)+3;
                        break;
                    case 5:
                        y1 = random.nextInt(3)+6;
                        y2 = random.nextInt(3)+6;
                        x1 = random.nextInt(3)+3;
                        x2 = random.nextInt(3)+3;
                        break;
                    case 6:
                        y1 = random.nextInt(3);
                        y2 = random.nextInt(3);
                        x1 = random.nextInt(3)+6;
                        x2 = random.nextInt(3)+6;
                        break;
                    case 7:
                        y1 = random.nextInt(3)+3;
                        y2 = random.nextInt(3)+3;
                        x1 = random.nextInt(3)+6;
                        x2 = random.nextInt(3)+6;
                        break;
                    case 8:
                        y1 = random.nextInt(3)+6;
                        y2 = random.nextInt(3)+6;
                        x1 = random.nextInt(3)+6;
                        x2 = random.nextInt(3)+6;
                        break;
                    default:
                        break;
                }
                if(!(puzzle.isLocked(x1,y1) || puzzle.isLocked(x2,y2))){
                    locked = false;
                }else{
                    locked = true;
                }
            }

            int firstValue = nextBoard[x1][y1];
            int secondValue = nextBoard[x2][y2];
            nextBoard[x1][y1] = secondValue;
            nextBoard[x2][y2] = firstValue;


            //delta E: stands for energy (number of conflicts)
            int numConflictsNext = 0;
            int numConflictsCurr = 0;

            for(int z = 0; z < currBoard.length; z++){
                for(int y = 0; y < currBoard[0].length; y++){
                    puzzle.setSudokuPuzzle(nextBoard);
                    numConflictsNext += validateSpace(puzzle, z, y);
                    puzzle.setSudokuPuzzle(currBoard);
                    numConflictsCurr += validateSpace(puzzle, z, y);
                }
            }
            if(numConflictsCurr == 0){
                puzzle.setSudokuPuzzle(currBoard);
                return puzzle;
            }
            //System.out.println(numConflictsCurr);
            int deltaE = numConflictsNext - numConflictsCurr;

            //if the next has neg energy then we take it (fewer conflicts)
            if(deltaE < 0){
                // board was already changed above
                // current = next;
                puzzle.setSudokuPuzzle(nextBoard);
            //else we may take it due to a probability
            }else{
                //boltzmann probability (K tunable parameter)
                //when temp is high we should always take the worst
                // k is a tuned parameter. We are setting it to 1.
                // I dont know how to take based on the probability
                double k = 1;
                double nextProb = 1/(Math.exp((double)deltaE / (k*(double)Tt)));
                double rDouble = random.nextDouble();
                if(rDouble < nextProb){
                    puzzle.setSudokuPuzzle(nextBoard);
                }else{
                    puzzle.setSudokuPuzzle(currBoard);
                }
            }
        }
        // This is just auto return after for loop if we end up not returning but leaving the for loop
        return puzzle;
    }

    private void geneticAlgorithm() {
    }

    @Override
    public void initializePuzzle(PuzzleImporter puzzle) {
        int[][] newPuzzle = puzzle.getSudokuPuzzle();
        int[][] cubeValues = puzzle.getCubeValues();
        for(int row = 0; row < newPuzzle.length; row++){
            for(int col = 0; col < newPuzzle[0].length; col++){
                //if this space is not locked
                if(!puzzle.isLocked(row, col)){
                    //until we put in a number that doesn't already exist in the 3x3 cube
                    Boolean alreadyExists = true;
                    while(alreadyExists){
                        //pick a random number
                        Random random = new Random();
                        int value = random.nextInt(9)+1;
                        //put it in the new puzzle
                        newPuzzle[row][col] = value;
                        //find the values in the 3x3 cube by using the cubeValues map
                        int exists = 0;
                        for(int i = 0; i < newPuzzle.length; i++){
                            for(int j = 0; j < newPuzzle[0].length; j++){
                                if(cubeValues[i][j] == cubeValues[row][col]){
                                    //if the value is the same then it alreadyExists, and it is not looking at itself,
                                    //    pick a new number
                                    //otherwise done
                                     if(!(i == row && j == col)){
                                         if(value == newPuzzle[i][j]){
                                             exists++;
                                             break;
                                         }
                                     }
                                }
                            }
                        }
                        if(exists == 0){
                            alreadyExists = false;
                        }
                    }
                }
            }
        }
        puzzle.setSudokuPuzzle(newPuzzle);
    }

    public int schedule(int t, int T0){
        int nt = t;
        double tunedParameter = 1;
        int Tt = (int)((T0*tunedParameter)/(tunedParameter + nt));
        return Tt;
    }
}
