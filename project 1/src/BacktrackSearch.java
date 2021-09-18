import java.util.Scanner;

public class BacktrackSearch {

    int[][] puzzleToSolve;

    public BacktrackSearch() {
    }

    public boolean SimpleBacktrackSearch(int[][] puzzleToSolve, int n) {
        //We don't want any conflicts and these get reassigned
        int row = -1;
        int column = -1;
        //assume that we are done
        boolean empty = true;

        //for every square we check for a value
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (puzzleToSolve[i][j] == 0) {
                    //get the index of the empty box and set up to start assigning values
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

        //if we are full then be done
        if (empty) {
            return true;
        }

        //check the value fits constraints then recursive calls to the next cell
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

    public boolean ForwardCheck(int[][] puzzleToSolve, int n) {
        //We don't want any conflicts and these get reassigned
        int row = -1;
        int column = -1;
        //assume that we are done
        boolean empty = true;

        //for every square we check for a value
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (puzzleToSolve[i][j] == 0) {
                    //get the index of the empty box and set up to start assigning values
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

        //if we are full then be done
        if (empty) {
            return true;
        }

        //check the value fits constraints then recursive calls to the next cell
        for (int i = 0; i <= n; i++) {
            if (LegalForwardValue(puzzleToSolve, row, column, i)) {
                puzzleToSolve[row][column] = i;
                if (ForwardCheck(puzzleToSolve, n)) {
                    return true;
                } else {
                    puzzleToSolve[row][column] = 0;
                }
            }
        }
        return false;
    }

    private static boolean LegalForwardValue(int[][] puzzleToSolve, int row, int column, int possibleValue) {
        int boxRow = 0;
        int boxColumn = 0;
        boolean hasValuesRemaining;
        int countRemainingValues = 0;

        //check each row for a duplicate value
        for (int i = 0; i < puzzleToSolve.length; i++) {
            if (puzzleToSolve[row][i] == possibleValue) {
                return false;
            }
        }

        for (int i = 0; i < puzzleToSolve.length; i++) {
            for (int j = 1; j < 10; j++) {
                if (LegalValue(puzzleToSolve, row, i, j)) {
                    countRemainingValues++;
                }
            }
        }

        if (countRemainingValues == 0) {
            return false;
        } else {
            countRemainingValues = 0;
        }

        //check each column for a duplicate value
        for (int i = 0; i < puzzleToSolve.length; i++) {
            if (puzzleToSolve[i][column] == possibleValue) {
                return false;
            }
        }

        for (int i = 0; i < puzzleToSolve.length; i++) {
            for (int j = 1; j < 10; j++) {
                if (LegalValue(puzzleToSolve, i, column, j)) {
                    countRemainingValues++;
                }
            }
        }

        if (countRemainingValues == 0) {
            return false;
        } else {
            countRemainingValues = 0;
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

        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = 0; j < boxColumn + 3; j++) {
                for (int k = 1; k < 10; k++) {
                    if (LegalValue(puzzleToSolve, i, j, k)) {
                        countRemainingValues++;
                    }
                }
            }
        }

        if (countRemainingValues == 0) {
            return false;
        } else {
            countRemainingValues = 0;
        }
        //congrats, there are no restrictions
        return true;
    }
}
