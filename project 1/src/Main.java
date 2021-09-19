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

        StateSpaceSearch SSS_SBEasy = new StateSpaceSearch(easyPuzzles, "simpleBacktracking");
        StateSpaceSearch SSS_FCEasy = new StateSpaceSearch(easyPuzzles, "forwardChecking");
        StateSpaceSearch SSS_ACEasy = new StateSpaceSearch(easyPuzzles, "arcConsistency");

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
        //10:14 -
        LocalSearch lsEasy = new LocalSearch(easyPuzzles, "geneticAlgorithm");
    }
}
