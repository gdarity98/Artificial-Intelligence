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

        // LocalSearch ls = new LocalSearch(easyPuzzles, "simulatedAnnealing");
    }
}
