import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Hashtable;
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
            if (line.contains("variable")) {
                //System.out.println("V");
                Variable var = new Variable(line);
                String vInfo = scanner.next();
                var.addVar(vInfo);
                variableDictionary.put(var.varName, var);
            } else if (line.contains("probability")) {
                //System.out.println("P");
                Probability p = new Probability(line, variableDictionary);
                while (!scanner.hasNext("}\r") && !scanner.hasNext("}")) {
                    String pInfo = scanner.next();
                    p.addProb(pInfo);
                }
                probabilityDictionary.put(p.pName, p);
            }
        }
    }

    public void eliminateVar() {

    }

    public void gibbsSample() {
        //TODO: Fix this up so it calls right
        //alarms new evidence LIst HRBP=HIGH

//        for (int i = 0; i < probabilityDictionary.size(); i++) {
//            GibbsSample gibbsSample = new GibbsSample(probabilityDictionary, variableDictionary, variableDictionary/*this needs to be the nonEvidence*/, 5000);
//
//        }

    }

    public void gibbsSample(String[] nameOfEvidence, String[] valueOfEvidence) {
        Enumeration<String> keys = variableDictionary.keys();
        GibbsSample gibbsSample = new GibbsSample(probabilityDictionary, variableDictionary, nameOfEvidence, valueOfEvidence, 10000);
        gibbsSample.GibbsAsk();
//        gibbsSample();
    }
}
