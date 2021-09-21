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
            medPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Med-P"+(i+1)+".csv");
            hardPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Hard-P"+(i+1)+".csv");
            evilPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Evil-P"+(i+1)+".csv");
        }

        StateSpaceSearch SSS_SBEasy = new StateSpaceSearch(easyPuzzles, "simpleBacktracking");
        StateSpaceSearch SSS_SBMed = new StateSpaceSearch(medPuzzles, "simpleBacktracking");
        StateSpaceSearch SSS_SBHard = new StateSpaceSearch(hardPuzzles, "simpleBacktracking");
        StateSpaceSearch SSS_SBEvil = new StateSpaceSearch(evilPuzzles, "simpleBacktracking");
        System.out.println(SSS_SBEasy.tracker);
        System.out.println(SSS_SBMed.tracker);
        System.out.println(SSS_SBHard.tracker);
        System.out.println(SSS_SBEvil.tracker);

        for(int i = 0; i < easyPuzzles.length; i++){
            easyPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Easy-P"+(i+1)+".csv");
            medPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Med-P"+(i+1)+".csv");
            hardPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Hard-P"+(i+1)+".csv");
            evilPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Evil-P"+(i+1)+".csv");
        }

        StateSpaceSearch SSS_FCEasy = new StateSpaceSearch(easyPuzzles, "forwardChecking");
        StateSpaceSearch SSS_FCMed = new StateSpaceSearch(medPuzzles, "forwardChecking");
        StateSpaceSearch SSS_FCHard = new StateSpaceSearch(hardPuzzles, "forwardChecking");
        StateSpaceSearch SSS_FCEvil = new StateSpaceSearch(evilPuzzles, "forwardChecking");
        System.out.println(SSS_FCEasy.tracker);
        System.out.println(SSS_FCMed.tracker);
        System.out.println(SSS_FCHard.tracker);
        System.out.println(SSS_FCEvil.tracker);

        for(int i = 0; i < easyPuzzles.length; i++){
            easyPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Easy-P"+(i+1)+".csv");
            medPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Med-P"+(i+1)+".csv");
            hardPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Hard-P"+(i+1)+".csv");
            evilPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Evil-P"+(i+1)+".csv");
        }
        StateSpaceSearch SSS_ACEasy = new StateSpaceSearch(easyPuzzles, "arcConsistency");
        StateSpaceSearch SSS_ACMed = new StateSpaceSearch(medPuzzles, "arcConsistency");
        StateSpaceSearch SSS_ACHard = new StateSpaceSearch(hardPuzzles, "arcConsistency");
        StateSpaceSearch SSS_ACEvil = new StateSpaceSearch(evilPuzzles, "arcConsistency");
        System.out.println(SSS_ACEasy.tracker);
        System.out.println(SSS_ACMed.tracker);
        System.out.println(SSS_ACHard.tracker);
        System.out.println(SSS_ACEvil.tracker);

        for(int i = 0; i < easyPuzzles.length; i++){
            easyPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Easy-P"+(i+1)+".csv");
            medPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Med-P"+(i+1)+".csv");
            hardPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Hard-P"+(i+1)+".csv");
            evilPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Evil-P"+(i+1)+".csv");
        }

        LocalSearch lsEasy = new LocalSearch(easyPuzzles, "simulatedAnnealing");
        System.out.println("LS SA - Easy Boards FINISHED");
        System.out.println("Conflicts on Board");
        for(PuzzleImporter puzzle : easyPuzzles){
            System.out.println(puzzle.getNumConflictsBoard());
        }

        LocalSearch lsMedium = new LocalSearch(medPuzzles, "simulatedAnnealing");
        System.out.println("LS SA - Medium Boards FINISHED");
        System.out.println("Conflicts on Board");
        for(PuzzleImporter puzzle : medPuzzles){
            System.out.println(puzzle.getNumConflictsBoard());
        }


        LocalSearch lsHard = new LocalSearch(hardPuzzles, "simulatedAnnealing");
        System.out.println("LS SA - Hard Boards FINISHED");
        System.out.println("Conflicts on Board");
        for(PuzzleImporter puzzle : hardPuzzles){
            System.out.println(puzzle.getNumConflictsBoard());
        }


        LocalSearch lsEvil = new LocalSearch(evilPuzzles, "simulatedAnnealing");
        System.out.println("LS SA - Evil Boards FINISHED");
        System.out.println("Conflicts on Board");
        for(PuzzleImporter puzzle : evilPuzzles){
            System.out.println(puzzle.getNumConflictsBoard());
        }
        System.out.println(lsEasy.tracker);
        System.out.println(lsMedium.tracker);
        System.out.println(lsHard.tracker);
        System.out.println(lsEvil.tracker);

        for(int i = 0; i < easyPuzzles.length; i++){
            easyPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Easy-P"+(i+1)+".csv");
            medPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Med-P"+(i+1)+".csv");
            hardPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Hard-P"+(i+1)+".csv");
            evilPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Evil-P"+(i+1)+".csv");
        }
        LocalSearch gaEasy = new LocalSearch(easyPuzzles, "geneticAlgorithm");
        LocalSearch gaMed = new LocalSearch(medPuzzles, "geneticAlgorithm");
        LocalSearch gaHard = new LocalSearch(hardPuzzles, "geneticAlgorithm");
        LocalSearch gaEvil = new LocalSearch(evilPuzzles, "geneticAlgorithm");
        System.out.println(gaEasy.tracker);
        System.out.println(gaMed.tracker);
        System.out.println(gaHard.tracker);
        System.out.println(gaEvil.tracker);
    }
}
