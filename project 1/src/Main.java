import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String args[]) throws FileNotFoundException {
        PuzzleImporter easy1 = new PuzzleImporter("project 1/sudoku puzzles/Easy-P1.csv");
        easy1.printSudokuPuzzle();
    }
}
