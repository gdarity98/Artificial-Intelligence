import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String args[]) throws FileNotFoundException {
        PuzzleImporter easyPuzzles[] = new PuzzleImporter[5];
        Object medPuzzles[] = new Object[5];
        Object hardPuzzles[] = new Object[5];
        Object evilPuzzles[] = new Object[5];

        for(int i = 0; i < easyPuzzles.length; i++){
            easyPuzzles[i] = new PuzzleImporter("project 1/sudoku puzzles/Easy-P"+(i+1)+".csv");
            System.out.println("Sudoku Puzzle Easy " + (i+1));
            easyPuzzles[i].printSudokuPuzzle();
            System.out.println("");
        }
    }
}
