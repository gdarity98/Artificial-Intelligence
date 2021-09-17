import java.util.Random;

public class LocalSearch extends ConstraintSolver{

    public LocalSearch(PuzzleImporter[] array, String variationName) {
        super(array, variationName);
        if(variationName.equals("simulatedAnnealing")){
            for(int i = 0; i < puzzles.length; i++){
                puzzles[i] = simulatedAnnealing(puzzles[i]);
            }
        }else if(variationName.equals("geneticAlgorithm")){
            for(int i = 0; i < puzzles.length; i++){
                puzzles[i] = geneticAlgorithm(puzzles[i]);
            }
        }else{
            System.out.println("Variation does not exist");
        }
    }

    public PuzzleImporter simulatedAnnealing(PuzzleImporter puzzle){
        // need input of problem and schedule
        // current, next, T local variables
        // int[] current = new int[2];
        // int[] next = new int[2];
        int T0 = 25000;
        // (250000 works, 100000 works better, 50,000?, 25,000 AMAZING, 600 breaks it, 700 breaks less often, 900 breaks it too but way less often)
        int Tt;
        int t = 0;
        // I am initializing the board state
        initializePuzzle(puzzle, "3x3Constrained");

        //for i until inf or until a set fitness or number of runs or something that stops the program
        for(int i = 0; i < 1000000000; i ++) {
            //Set the Temperature for the iteration we are on
            //schedule slowly reduces the temp down to 0
            Tt = schedule(t, T0);

            //if temp = 0
            if (Tt == 0) {
                //   return current even if not completed
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
            int y1 = 0;
            int x2 = 0;
            int y2 = 0;
            boolean locked = true;
            int numTimesInWhile = 0;
            while((x1 == x2 && y1 == y2) || locked){
                numTimesInWhile++;
                if(numTimesInWhile > 5){
                    chosenCube = random.nextInt(9);
                }
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
            puzzle.setSudokuPuzzle(nextBoard);
            puzzle.validateBoard();
            numConflictsNext = puzzle.getNumConflictsBoard();
            puzzle.setSudokuPuzzle(currBoard);
            puzzle.validateBoard();
            numConflictsCurr = puzzle.getNumConflictsBoard();

            if(numConflictsCurr == 0){
                puzzle.setSudokuPuzzle(currBoard);
                // System.out.println("------------------------------FINISHED A BOARD-----------------------");
                return puzzle;
            }

            //System.out.println(numConflictsCurr);
            int deltaE = numConflictsNext - numConflictsCurr;

            //if the next has neg energy then we take it (fewer conflicts)
            if(deltaE <= 0){
                // board was already changed above
                // current = next;
                puzzle.setSudokuPuzzle(nextBoard);
                t++;
            //else we may take it due to a probability
            }else{
                //boltzmann probability (K tunable parameter)
                //when temp is high we should always take the worst
                // k is a tuned parameter. We are setting it to 1.
                // I dont know if Im doing this right
                double k = 1;
                double nextProb = 1 / (Math.exp(((double)deltaE / (k*(double)Tt))));
                double rDouble = random.nextDouble();
                if(rDouble < nextProb){
                    puzzle.setSudokuPuzzle(nextBoard);
                    t++;
                }else{
                    puzzle.setSudokuPuzzle(currBoard);
                }
            }
        }
        // This is just auto return after for loop if we end up not returning but leaving the for loop
        return puzzle;
    }

    private PuzzleImporter geneticAlgorithm(PuzzleImporter puzzle) {
        // population of puzzles
        int[][][] population = new int[100][9][9];
        // random assignments
        for(int i = 0; i < population.length; i++){
            int[][] newA = new int[9][9];
            initializePuzzle(puzzle, "Random");
            int[][] randomPuzzle = puzzle.getSudokuPuzzle();

            for(int row = 0; row < newA.length; row++) {
                for (int col = 0; col < newA[0].length; col++) {
                    newA[row][col] = randomPuzzle[row][col];
                }
            }
            population[i] = newA;
        }

        // Temp assigned a value according to schedule (same as simulatedAnnealing?)
        int T0 = 25000;
        // (250000 works, 100000 works better, 50,000?, 25,000 AMAZING, 600 breaks it, 700 breaks less often, 900 breaks it too but way less often)
        int Tt;
        int t = 0;
        //Set the Temperature for the iteration we are on
        //schedule slowly reduces the temp down to 0
        Tt = schedule(t, T0);

        //repeat
        //for i until inf or until a set fitness or number of runs or something that stops the program
        for(int i = 0; i < 1000000000; i ++) {
            //if temp = 0
            if (Tt == 0) {
                //   return current even if not completed
                return puzzle;
            }
            //if some A in Pop satisfies all constraints (has no conflicts) then return
            for(int[][] A : population){
                puzzle.setSudokuPuzzle(A);
                puzzle.validateBoard();
                int numConflictsA = puzzle.getNumConflictsBoard();
                if(numConflictsA == 0){
                    puzzle.setSudokuPuzzle(A);
                    // System.out.println("------------------------------FINISHED A BOARD-----------------------");
                    return puzzle;
                }
            }

            //make a new empty pop
            int[][][] newPopulation = new int[100][9][9];
            int k=100; // set to population size and then making offspring to completely replace old pop
                       //    could make it so fewer offspring are made and then do a different type of replacement.
            //repeat k/2 times
            for(int times = 0; times < k/2; times+=2){
                //pick two people in pop
                //when picking need to make sure we did not pick the same board
                Boolean sameA = true;
                int[][] A1 = new int[9][9];
                int[][] A2 = new int[9][9];
                while(sameA){
                    //A1 := random_selection(Pop,T)
                    A1 = random_selection(population, Tt, puzzle);
                    //A2 := random_selection(Pop,T)
                    A2 = random_selection(population,Tt, puzzle);
                    // as long as they are not at the same
                    int numSame = 0;
                    for(int x = 0; x< A1.length; x++) {
                        for (int y = 0; y < A1[0].length; y++) {
                            if(A1[x][y] == A2[x][y]){
                                numSame++;
                            }
                        }
                    }
                    if(numSame != 81){
                        sameA = false;
                    }
                }

                //create two offspring
                //N1, N2 := crossover(A1, A2)
                int[][][] offspring = crossover(A1,A2, puzzle);

                //new pop := new pop U {mutate(N1), mutate(N2)}
                offspring[0] = mutate(offspring[0],puzzle);
                offspring[1] = mutate(offspring[1],puzzle);
                // add offspring to newPop
                newPopulation[times] = offspring[0];
                newPopulation[times+1] = offspring[1];
            }
            //Pop := new pop (generational replacement)
            population = newPopulation;
            t++; // (t is supposed to increase with every update performed)
                 // t++ everytime a member of the population is changed or everytime population gets replaced???
                 // currently, doing it everytime population gets replaced.

            //T is updated
            Tt = schedule(t, T0);

        }
        return puzzle;
    }

    @Override
    public void initializePuzzle(PuzzleImporter puzzle, String initializeType) {
        if(initializeType.equals("3x3Constrained")){
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
        }else if (initializeType.equals("Random")){
            int[][] newPuzzle = puzzle.getSudokuPuzzle();
            for(int row = 0; row < newPuzzle.length; row++) {
                for (int col = 0; col < newPuzzle[0].length; col++) {
                    //if this space is not locked set space to random value
                    if (!puzzle.isLocked(row, col)) {
                        Random random = new Random();
                        int value = random.nextInt(9)+1;
                        newPuzzle[row][col] = value;
                    }
                }
            }
            puzzle.setSudokuPuzzle(newPuzzle);
        }
    }

    public int schedule(int t, int T0){
        // not sure if this is right c:
        int nt = t;
        double tunedParameter = 1;
        int Tt = (int)((T0*tunedParameter)/(tunedParameter + nt));
        return Tt;
    }

    public int[][] random_selection(int[][][] population, int T, PuzzleImporter puzzle){
        // tournament selection
        // add probability (same as SA) with no replacement, (DO WE NEED PROBABILITY? IDK)
        // then make sure two chosen after that are not the same
        int[][] A = new int[9][9];
        int[][] A1 = new int[9][9];
        int[][] A2 = new int[9][9];
        Random random = new Random();
        // until A1 and A2 are not the same
        while(A1[0] == A2[0] && A1[1] == A2[1]){
            int value = random.nextInt(100)+1;
            A1 = population[value];
            value = random.nextInt(100)+1;
            A2 = population[value];
        }

        // calculate conflicts on A1 and A2
        puzzle.setSudokuPuzzle(A1);
        puzzle.validateBoard();
        int conflictsA1 = puzzle.getNumConflictsBoard();

        puzzle.setSudokuPuzzle(A2);
        puzzle.validateBoard();
        int conflictsA2 = puzzle.getNumConflictsBoard();

        //return the A with fewer conflicts (that is the A that won the tournament)
        if(conflictsA1 < conflictsA2){
            A = A1;
        }else{
            A= A2;
        }

        return A;
    }

    public int[][][] crossover(int[][] A1, int[][] A2, PuzzleImporter puzzle){
        //one point crossover
        int[][] N1 = new int[9][9];
        int[][] N2 = new int[9][9];
        int[][][] offspring = new int[2][9][9];

        //pick random point to cross over at
        Random random = new Random();
        int valueRow = random.nextInt(9);
        int valueCol = random.nextInt(9);
        for(int i= 0; i< N1.length; i++){
            for(int j = 0; j < N1[0].length;j++){
                if(!puzzle.isLocked(i,j)){
                    if(i == valueRow && j >= valueCol){
                        N1[i][j] = A2[i][j];
                        N2[i][j] = A1[i][j];
                    }else if(i > valueRow){
                        N1[i][j] = A2[i][j];
                        N2[i][j] = A1[i][j];
                    }else{
                        N1[i][j] = A1[i][j];
                        N2[i][j] = A2[i][j];
                    }
                }else{
                    N1[i][j] = puzzle.getSudokuPuzzle()[i][j];
                    N2[i][j] = puzzle.getSudokuPuzzle()[i][j];
                }
            }
        }

        offspring[0] = N1;
        offspring[1] = N2;
        return offspring;
    }

    public int[][] mutate(int[][] A, PuzzleImporter puzzle){
        //Mutation Row
        //picks a random row
        Random random = new Random();
        int valueRow = random.nextInt(9);
        //randomize values in the row
        for(int i= 0; i< A.length; i++) {
            if(i == valueRow) {
                for (int j = 0; j < A[0].length; j++) {
                    if(!puzzle.isLocked(i,j)){
                        int value = random.nextInt(9)+1;
                        A[i][j] = value;
                    }
                }
            }
        }
        return A;
    }
}
