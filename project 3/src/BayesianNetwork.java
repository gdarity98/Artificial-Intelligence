import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;

public class BayesianNetwork {
    public Hashtable<String, Variable> variableDictionary = new Hashtable<String, Variable>();
    public Hashtable<String, Probability> probabilityDictionary = new Hashtable<String, Probability>();
    public BayesianNetwork(String path) throws FileNotFoundException {
        //System.out.println(path);
        Scanner scanner = new Scanner(new File(path));
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            String line = scanner.next();
            if(line.contains("variable")){
                //System.out.println("V");
                Variable var = new Variable(line);
                String vInfo = scanner.next();
                var.addVar(vInfo);
                variableDictionary.put(var.varName, var);
            }else if(line.contains("probability")){
                //System.out.println("P");
                Probability p = new Probability(line, variableDictionary);
                while(!scanner.hasNext("}\r")){
                    String pInfo = scanner.next();
                    p.addProb(pInfo);
                }
                probabilityDictionary.put(p.pName,p);
            }
        }
    }

    public void eliminateVar(){

    }

    public void gibbsSample(){

    }
}
