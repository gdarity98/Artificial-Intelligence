import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        PuzzleImporter[] easyPuzzles = new PuzzleImporter[5];
        PuzzleImporter[] medPuzzles = new PuzzleImporter[5];
        PuzzleImporter[] hardPuzzles = new PuzzleImporter[5];
        PuzzleImporter[] evilPuzzles = new PuzzleImporter[5];

        for(int i = 0; i < easyPuzzles.length; i++){
            easyPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Easy-P"+(i+1)+".csv");
            //System.out.println("Sudoku Puzzle Easy " + (i+1));
            //easyPuzzles[i].printSudokuPuzzle();
            //System.out.println("");
            medPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Med-P"+(i+1)+".csv");
            //System.out.println("Sudoku Puzzle Medium " + (i+1));
            //medPuzzles[i].printSudokuPuzzle();
            //System.out.println("");
            hardPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Hard-P"+(i+1)+".csv");
            //System.out.println("Sudoku Puzzle Hard " + (i+1));
            //hardPuzzles[i].printSudokuPuzzle();
            //System.out.println("");
            evilPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Evil-P"+(i+1)+".csv");
            //System.out.println("Sudoku Puzzle Evil " + (i+1));
            //evilPuzzles[i].printSudokuPuzzle();
            //System.out.println("");
        }

//        for (int i = 0; i < easyPuzzles.length; i++) {
//            BacktrackSearch backtrackSearch = new BacktrackSearch(easyPuzzles[i]);
//            System.out.println(backtrackSearch.SimpleBacktrackSearch(easyPuzzles[i].getSudokuPuzzle(), easyPuzzles[i].getSudokuPuzzle().length));
//            System.out.println("Sudoku Puzzle Easy " + (i + 1));
//            easyPuzzles[i].printSudokuPuzzle();
//            System.out.println("");
//        }

//TODO Tune parameters simulatedAnnealing to find solution to hard and evil boards (increase T0 probably,
// or increase in scheduling)

//        LocalSearch lsEasy = new LocalSearch(easyPuzzles, "simulatedAnnealing");
//        System.out.println("LS SA - Easy Boards FINISHED");
//        System.out.println("Conflicts on Board");
//        for(PuzzleImporter puzzle : easyPuzzles){
//            System.out.println(puzzle.getNumConflictsBoard());
//        }
//
//        LocalSearch lsMedium = new LocalSearch(medPuzzles, "simulatedAnnealing");
//        System.out.println("LS SA - Medium Boards FINISHED");
//        System.out.println("Conflicts on Board");
//        for(PuzzleImporter puzzle : medPuzzles){
//            System.out.println(puzzle.getNumConflictsBoard());
//        }
//
//        LocalSearch lsHard = new LocalSearch(hardPuzzles, "simulatedAnnealing");
//        System.out.println("LS SA - Hard Boards FINISHED");
//        System.out.println("Conflicts on Board");
//        for(PuzzleImporter puzzle : hardPuzzles){
//            System.out.println(puzzle.getNumConflictsBoard());
//        }
//
//        LocalSearch lsEvil = new LocalSearch(evilPuzzles, "simulatedAnnealing");
//        System.out.println("LS SA - Evil Boards FINISHED");
//        System.out.println("Conflicts on Board");
//        for(PuzzleImporter puzzle : evilPuzzles){
//            System.out.println(puzzle.getNumConflictsBoard());
//        }

        //LocalSearch lsEasy = new LocalSearch(easyPuzzles, "geneticAlgorithm");

        for (int i = 0; i < easyPuzzles.length; i++) {
            int[][] domains = new int[81][9];
            for(int d= 0; d < domains.length; d++){
                for(int j = 0; j < domains[0].length; j++)
                    domains[d][j] = j+1;
            }

            int[][] immutableValues = easyPuzzles[i].getImmutableValues();
            int[][] aPuzzle = easyPuzzles[i].getSudokuPuzzle();
            for(int j = 0; j < immutableValues.length; j++){
                for(int k = 0; k< immutableValues[0].length; k++){
                    if (immutableValues[j][k] == 1){
                        for(int z = 0; z < domains[0].length; z++){
                            if(z+1 == aPuzzle[j][k]){
                                domains[(((j)*9) + (k))][z] = 0;
                            }
                        }
                        int[][] neighborDomains = new int[20][9];
                        int[][] neighbors = easyPuzzles[i].getNeighbors(j,k);
                        int count = 0;
                        for(int m= 0; m < domains.length; m++) {
                            for (int[] neighbor : neighbors) {
                                if(i+1 == ((neighbor[0])*9) + (neighbor[1]+1)){
                                    for (int z = 0; z < domains[0].length; z++) {
                                        if(domains[m][z] == aPuzzle[j][k]){
                                            domains[m][z] = 0;
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }

                    }
                }
            }
            BacktrackSearch backtrackSearch = new BacktrackSearch(easyPuzzles[i]);
            System.out.println(backtrackSearch.ArcConsistency(easyPuzzles[i].getSudokuPuzzle(), easyPuzzles[i].getSudokuPuzzle().length,domains));
            System.out.println("Sudoku Puzzle Easy " + (i + 1));
            easyPuzzles[i].printSudokuPuzzle();
            System.out.println("");
        }
    }
}
