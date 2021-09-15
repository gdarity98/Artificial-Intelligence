import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PuzzleImporter {
    private int[][] sudokuPuzzle = new int[9][9];
    private int[][] immutableValues = new int[9][9];
    private int[][] cubeValues = new int[9][9];

    public PuzzleImporter(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));
        scanner.useDelimiter("\n");
        int i = 0;
        int j = 0;
        while(scanner.hasNext()){
            String line = scanner.next();
            line = line.replace('\r', ' ');
            line = line.trim();
            String[] spaces = line.split(",");
            for(int k = 0; k < spaces.length; k++){
                if (!spaces[k].contains("?")) {
                    // I was getting some weird errors when reading in the trimmed version of the
                    // .csv file if the very first item was a number. Something to do with BOM chars?
                    // What I did below fixed it
                    // https://stackoverflow.com/questions/21891578/removing-bom-characters-using-java
                    // https://stackoverflow.com/questions/40311348/numberformatexception-with-integer-parseint
                    String UTF8_BOM = "\uFEFF";
                    if (spaces[k].startsWith(UTF8_BOM)) {
                        spaces[k] = spaces[k].substring(1);
                    }
                    sudokuPuzzle[i][j] = Integer.parseInt(spaces[k]);
                    immutableValues[i][j] = 1;
                }
                j++;
                if(j == 9){
                    i++;
                    j = 0;
                }
            }
        }
        //setting up 3x3 cubeValues
        for(int k = 0; k < 9; k++){
            for(int l = 0; l < 9; l++){
                if(k >= 0 && k <= 2) {
                    if (l >= 0 && l <= 2) {
                        cubeValues[k][l] = 0;
                    }
                    else if(l >= 3 && l <= 5){
                        cubeValues[k][l] = 1;
                    }
                    else if(l >= 6 && l <= 8){
                        cubeValues[k][l] = 2;
                    }
                }
                else if(k >= 3 && k <= 5) {
                    if (l >= 0 && l <= 2) {
                        cubeValues[k][l] = 3;
                    }
                    else if(l >= 3 && l <= 5){
                        cubeValues[k][l] = 4;
                    }
                    else if(l >= 6 && l <= 8){
                        cubeValues[k][l] = 5;
                    }
                }
                else if(k >= 6 && k <= 8) {
                    if (l >= 0 && l <= 2) {
                        cubeValues[k][l] = 6;
                    }
                    else if(l >= 3 && l <= 5){
                        cubeValues[k][l] = 7;
                    }
                    else if(l >= 6 && l <= 8){
                        cubeValues[k][l] = 8;
                    }
                }
            }
        }
        scanner.close();
    }

    public int[][] getSudokuPuzzle(){
        return sudokuPuzzle;
    }

    public void setSudokuPuzzle(int[][] puzzle){ sudokuPuzzle = puzzle; }

    public void printSudokuPuzzle(){
        for(int[] x : sudokuPuzzle){
            for(int y : x){
                System.out.print(y + "  ");
            }
            System.out.print("\n");
        }
    }

    public Boolean isLocked(int x, int y){
        if(immutableValues[x][y] == 1){
            return true;
        }else{
            return false;
        }
    }
    //x is row y is column
    public int[][] getNeighbors(int x, int y) {
        //System.out.println(sudokuPuzzle[x][y]);
        int[][] neighbors = new int[20][2];
        int neighborNum = 0;
        int cubeValue = cubeValues[x][y];
        for (int i = 0; i < 9; i++) {
            //adding row to neighbors
            if(i != y){
                neighbors[neighborNum][0] = x;
                neighbors[neighborNum][1] = i;
                neighborNum++;
            }
            //adding column to neighbors
            if(i != x) {
                neighbors[neighborNum][0] = i;
                neighbors[neighborNum][1] = y;
                neighborNum++;
            }
        }
        //adding 3x3 to neighbors
        for (int i = 0; i < sudokuPuzzle.length; i++) {
            for (int j = 0; j < sudokuPuzzle[0].length; j++) {
                if (cubeValues[i][j] == cubeValue) {
                    if(x != i && y != j){
                        neighbors[neighborNum][0] = i;
                        neighbors[neighborNum][1] = j;
                        neighborNum++;
                    }
                }
            }
        }
        return neighbors;
    }
}
