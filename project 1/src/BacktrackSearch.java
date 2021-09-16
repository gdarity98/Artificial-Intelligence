import java.util.Scanner;

public class BacktrackSearch {

    int[][] puzzleToSolve;

    public BacktrackSearch(int[][] puzzleToSolve) {
        this.puzzleToSolve = puzzleToSolve;
    }

    public boolean SimpleBacktrackSearch(int[][] puzzleToSolve, int n) {
        int row = -1;
        int column = -1;
        boolean empty = true;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (puzzleToSolve[i][j] == 0) {
                    row = i;
                    column = j;
                    empty = false;
                    break;
                }
            }
            if (!empty) {
                break;
            }
        }

        if (empty) {
            return true;
        }

        for (int i = 0; i <= n; i++) {
            if (LegalValue(puzzleToSolve, row, column, i)) {
                puzzleToSolve[row][column] = i;
                if (SimpleBacktrackSearch(puzzleToSolve, n)) {
                    return true;
                } else {
                    puzzleToSolve[row][column] = 0;
                }
            }
        }
        return false;
    }

    private static boolean LegalValue(int[][] puzzleToSolve, int row, int column, int possibleValue) {
        int boxRow = 0;
        int boxColumn = 0;

        //check each row for a duplicate value
        for (int i = 0; i < puzzleToSolve.length; i++) {
            if (puzzleToSolve[row][i] == possibleValue) {
                return false;
            }
        }

        //check each column for a duplicate value
        for (int i = 0; i < puzzleToSolve.length; i++) {
            if (puzzleToSolve[i][column] == possibleValue) {
                return false;
            }
        }

        //get the correct first row of the square
        if (row < 3) {
            boxRow = 0;
        }
        if (row >= 3 && row < 6) {
            boxRow = 3;
        }
        if (row >= 6) {
            boxRow = 6;
        }

        //get the correct first column of the square
        if (column < 3) {
            boxColumn = 0;
        }
        if (column >= 3 && column < 6) {
            boxColumn = 3;
        }
        if (column >= 6) {
            boxColumn = 6;
        }

        //check the box/group for duplicates
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxColumn; j < boxColumn + 3; j++) {
                if (puzzleToSolve[i][j] == possibleValue) {
                    return false;
                }
            }
        }

        //congrats, there are no restrictions
        return true;
    }

}
