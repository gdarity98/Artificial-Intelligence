import java.util.Scanner;

public class BacktrackSearch {


    public static int[][] BacktrackSearch(int[][] puzzleToSolve, int n) {
        int row = -1;
        int column = -1;
        //Return when the end of the puzzle is reached
        if (row == 8 && column == 9) {
            return puzzleToSolve;
        }

        //At the end of each row, move down and left
        if (column == 9) {
            row++;
            column = 0;
        }

        //Skip the given values
        if (puzzleToSolve[row][column] != 0) {
            return BacktrackSearch(puzzleToSolve, n);
        }

        //loop this to assign 1-9 or loop over whole puzzle N-queens style???
        if (LegalValue(puzzleToSolve, row, column, n)) {
            puzzleToSolve[row][column] = 1;
        }


        //here while writing so no error is thrown
        return new int[9][9];

    }

    private static boolean LegalValue(int[][] puzzleToSolve, int row, int column, int n) {
        //Constraints go here
        return true;
    }


}
