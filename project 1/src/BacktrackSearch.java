import java.util.Scanner;

public class BacktrackSearch {

    int[][] puzzleToSolve;
    private PuzzleImporter puzzle;

    public BacktrackSearch(PuzzleImporter puzzle) {
        this.puzzle = puzzle;
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

    public boolean ArcConsistency(int[][] puzzleToSolve, int n, int[][] domains) {
//        System.out.println("--------------------------------");
        puzzle.setSudokuPuzzle(puzzleToSolve);
//        puzzle.printSudokuPuzzle();
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
            //puzzle.printSudokuPuzzle();
            return true;
        }

        //check the value fits constraints,using arc consistency, then recursive calls to the next cell
        // WHAT DOES ARC CONSISTENCY DO WHEN CHECKING A VALUE
        // make domains outside
        // if I pick a value for one domain, update all other domains,
        // if any domain becomes empty then
        //     reset domains to before the value was picked
        //     return false (not a consistent value)
        //
        //get neighbor domains (arcs that have the space we are in)
        int[][] neighborDomains = new int[20][9];
        int[][] neighbors = puzzle.getNeighbors(row,column);
        int count = 0;
        for(int i= 0; i < domains.length; i++) {
            for(int[] neighbor : neighbors){
                if(i+1 == ((neighbor[0])*9) + (neighbor[1]+1)){
                    neighborDomains[count] = domains[i];
                    count++;
                    break;
                }
            }
        }

        //for the value we are going to try check legality, if we can use it then update domains
        //  BUT if the previous change we made caused a domain to be empty, don't update domains, go back
        for (int i = 1; i <= n; i++) {
            Boolean legal = true;
            int domainEmpty = 0;
            // check legality
            for(int x = 0; x < neighborDomains.length; x++){
                if(!(puzzle.isLocked(neighbors[x][0], neighbors[x][1]))){
                    for(int y = 0; y < neighborDomains[0].length; y++) {
                        if(neighborDomains[x][y] == 0){
                            domainEmpty++;
                        }
                    }
                }
            }

            if (domainEmpty == 9){
                break;
            }

            for(int x = 0; x < puzzleToSolve.length; x++) {
                for (int y = 0; y < puzzleToSolve[0].length; y++) {
                    if(!(x == row && y == column)){
                        for(int z = 0; z < neighbors.length; z++){
                            if((x == neighbors[z][0] && y == neighbors[z][1])){
                                if(i == puzzleToSolve[neighbors[z][0]][neighbors[z][1]]){
                                    legal = false;
                                }
                                break;
                            }
                        }
                    }
                }
            }

            int[][] newDomains = LegalConsistentValue(puzzleToSolve, row, column, i, domains);
            if(legal) {
                puzzleToSolve[row][column] = i;
                if (ArcConsistency(puzzleToSolve, n, newDomains)) {
                    return true;
                } else {
                    for(int t= 0; t < domains.length; t++) {
                        if(t+1 == ((row)*9) + (column+1)){
                            for(int s = 0; s< domains[0].length; s++){
                                if(newDomains[t][s] == puzzleToSolve[row][column]){
                                    newDomains[t][s] = 0;
                                    break;
                                }
                            }
                        }
                    }
                    puzzleToSolve[row][column] = 0;
                }
            }
        }
        return false;
    }

    private int[][] LegalConsistentValue(int[][] puzzleToSolve, int row, int column, int possibleValue, int[][] domains){
        int[][] newDomains = new int[81][9];
        for(int i= 0; i < domains.length; i++){
            for(int j = 0; j < domains[0].length; j++) {
                newDomains[i][j] = domains[i][j];
            }
        }
        //remove from the space we are on domain
        for(int j = 0; j < domains[0].length; j++) {
            if(newDomains[((row+1)*(column+1))-1][j] == possibleValue){
                newDomains[((row+1)*(column+1))-1][j] = 0;
            }

        }

        // if I pick a value for one domain, update all other domains that neighbor chosen space
        int[][] neighbors = puzzle.getNeighbors(row,column);
        for(int i= 0; i < newDomains.length; i++) {
            for(int[] neighbor : neighbors){
                if(i+1 == ((neighbor[0])*9) + (neighbor[1]+1)){
                    for (int j = 0; j < newDomains[0].length; j++) {
                        if(newDomains[i][j] == possibleValue){
                            newDomains[i][j] = 0;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return newDomains;
    }
}