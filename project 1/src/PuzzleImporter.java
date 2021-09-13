import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PuzzleImporter {
        int[][] sudokuPuzzle = new int[9][9];

        public PuzzleImporter(String file) throws FileNotFoundException {
            Scanner scanner = new Scanner(new File(file));
            scanner.useDelimiter("\n");
            int i = 0;
            int j = 0;
            while(scanner.hasNext()){
                String line = scanner.next();
                line = line.replace('\r',' ');
                line = line.trim();
                String[] spaces = line.split(",");
                for(int k = 0; k < spaces.length; k++){
                    if (!spaces[k].contains("?")) {
                    sudokuPuzzle[i][j] = Integer.parseInt(spaces[k]);
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

        public void printSudokuPuzzle(){
            for(int[] x : sudokuPuzzle){
                for(int y : x){
                    System.out.print(y + "  ");
                }
                System.out.print("\n");
            }
        }
}
