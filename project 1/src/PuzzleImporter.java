import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PuzzleImporter {
        int[][] sudokuPuzzle = new int[9][9];
        int[][] immutableValues = new int[9][9];

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

        public void getNeighbors(int x, int y){
            //idk if this is needed
        }
}
