import java.util.Random;

public class LocalSearch extends ConstraintSolver{

    public LocalSearch(PuzzleImporter[] array, String variationName) {
        super(array, variationName);
        if(variationName.equals("simulatedAnnealing")){
            simulatedAnnealing();
        }else if(variationName.equals("geneticAlgorithm")){
            geneticAlgorithm();
        }else{
            System.out.println("Variation does not exist");
        }
    }

    public PuzzleImporter[] simulatedAnnealing(){
        // need input of problem and schedule
        // current, next, T local variables
        int[][] current = new int[1][2];
        int[][] next = new int[1][2];
        int T0 = 1000000; //I have no idea how this temp thing works rn
        int Tt;


        //initialize the current node with the puzzle we have (random state?)
        // I am initializing all puzzles in the puzzle array
        for(int i = 0; i < puzzles.length; i++){
            initializePuzzle(puzzles[i]);
        }

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
                return puzzles;
            }
            //end if

            //next = random neighbor of current (random number from 1-9 that is not current)

            //delta E: stands for energy (number of conflicts)
            //if the next has neg energy then we take it (less conflicts)
            //    current = next
            //else we take it due to a probability
            //    boltzmann probability (K tunable parameter)
            //        when temp is high we should always take the worst
            //end if

        }
        //end for

        // This is just auto return after for loop if we end up not returning but leaving the for loop
        return new PuzzleImporter[0];
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
