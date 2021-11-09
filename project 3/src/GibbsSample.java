/*  Function Gibbs-Ask(X, evidence, bayesNet, N(umberOfLoops???) returns an estimate of R(X|e)
        local variables: C, a vector of counts for each value of X, init to 0
                         Z, the non-evidence variables in bayesNet
                         currentState, the current state of the network, initialized from e
        for k=1 to N do
            choose any variable Z_i from Z according to any distribution p(i)
            set the value of Z_i in currentState by sampling from P(Z_i|mb(Z_i))
            C[j] <- C[j] +1 where x_j is the value of X in currentState
        return NORMALIZE(C)
*/

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Random;

public class GibbsSample {
    String[] evidenceNames;
    String[] evidenceValues;
    String[] whatToLookAt;
    Hashtable<String, Probability> probabilityNetwork;
    Hashtable<String, Variable> variableNetwork;
    int countLoops;

    public GibbsSample(Hashtable<String, Probability> probabilityDictionary, Hashtable<String, Variable> variableDictionary, String[] nameOfEvidence, String[] valueOfEvidence, int countLoops, String[] whatToLookAt) {
        this.probabilityNetwork = probabilityDictionary;
        this.variableNetwork = variableDictionary;
        this.whatToLookAt = whatToLookAt;
        this.evidenceNames = nameOfEvidence;
        this.evidenceValues = valueOfEvidence;
        this.countLoops = countLoops;
    }
    //TODO: Do a Gibbs sample loopy bit

    // Possibly useless code: Variable variable, Hashtable<String, Variable> evidence, Hashtable<String, Probability> bayesNet, int loopCount) {

    public BayesianNetwork GibbsAsk() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        double[] numberOfRepetitions = new double[20];
        Hashtable<String, Variable> evidence = new Hashtable<>();
        Hashtable<String, Variable> guess = variableNetwork;
        Hashtable<String, Variable> currentState = variableNetwork;

        //If we get null this line prints and then everything will probably break
        if (variableNetwork.isEmpty()) {
            System.out.println("No Variables made it here");
        }

        //for every variable in our evidence we remove it from evidence and then add it to our actual evidence
        for (int i = 0; i < Objects.requireNonNull(evidenceNames).length; i++) {
            if (evidenceNames[i].equals(variableNetwork.get(evidenceNames[i]).varName)) {
                evidence.put(evidenceNames[i], guess.remove(evidenceNames[i]));
            }
        }

        //Initialize current state with random guess for all non-evidence states
        //This is a janky way to da it, but I think it works properly

        for (Variable variable : currentState.values()) {
            int seedMax = 0;
            for (int i = 0; i < evidenceNames.length; i++) {
                if (variable.varName.equals(evidenceNames[i])) {
                    variable.currentState = evidenceValues[i];
                } else if (variable.parents == null) {
                    Double[] distribution = probabilityNetwork.get(variable.varName).cptDictionary.get("table");
                    for (Double aDouble : distribution) {
                        double compare = (double) seedMax / 100;
                        if (compare <= aDouble) {
                            seedMax = (int) (aDouble * 100);
                        }
                    }

                    double compare = (double) (seedMax / 100);
                    if (distribution.length == 2) {
                        if (compare <= distribution[1]) {
                            variable.currentState = variable.stateTypes[0];
                        } else {
                            variable.currentState = variable.stateTypes[1];
                        }
                    } else {
                        boolean setValue = false;
                        for (int k = 0; k < distribution.length; k++) {
                            if (compare <= distribution[k] && !setValue) {
                                variable.currentState = variable.stateTypes[k];
                                setValue = true;
                            }
                        }
                    }

                } else {
                    Double[] distribution = new Double[variableNetwork.get(variable.varName).numStates];
                    int guessedValue = random.nextInt(distribution.length);
                    variable.currentState = variable.stateTypes[guessedValue];
                }
            }

        }

        for (int i = 0; i < countLoops; i++) {
            for (Variable variable : currentState.values()) {
                for (int j = 0; j < evidenceNames.length; j++) {
                    if (variable.varName.equals(evidenceNames[j])) {
                        variable.currentState = evidenceValues[j];
                        break;
                    }
                }
                if (variable.parents == null) {
                    Double[] distribution = probabilityNetwork.get(variable.varName).cptDictionary.get("table");
                    int seedMax = 0;
                    double compare = 0.0;
                    boolean setValue = false;
                    for (Double aDouble : distribution) {
                        compare = (double) seedMax / 100;
                        if (compare <= aDouble) {
                            seedMax = (int) (aDouble * 100);
                        }
                    }

                    if (distribution.length == 2) {
                        if (compare <= distribution[1]) {
                            variable.currentState = variable.stateTypes[0];
                        } else {
                            variable.currentState = variable.stateTypes[1];
                        }
                    } else {
                        for (int j = 0; j < distribution.length; j++) {
                            if (compare <= distribution[j] && !setValue) {
                                setValue = true;
                                variable.currentState = variable.stateTypes[j];
                            }
                        }
                    }
                } else {
                    String[] parentsValue = new String[variable.parents.length];
                    String lookupString = "";
                    String probLookup = "";
                    Enumeration<String> keys = variableNetwork.keys();

                    for (int j = 0; j < parentsValue.length; j++) {
                        parentsValue[j] = variable.parents[j];
                        probLookup = probLookup + ", " + variable.parents[j];
                    }

                    for (int j = 0; j < parentsValue.length; j++) {
                        if (j == 0) {
                            lookupString = parentsValue[j];
                        } else {
                            lookupString = lookupString + ", " + parentsValue[j];
                        }
                    }
                    Double[] distribution = new Double[20];
                    //distribution = probabilityNetwork.get(variable.varName + " | " + probLookup).cptDictionary.get(lookupString);
                    probabilityNetwork.keys();
                    int seedMax = 0;
                    double compare = 0.0;
                    boolean setValue = false;
                    for (Double aDouble : distribution) {
                        compare = (double) seedMax / 100;
                        if (compare <= aDouble) {
                            seedMax = (int) (aDouble * 100);
                        }
                    }

                    if (distribution.length == 2) {
                        if (compare <= distribution[1]) {
                            variable.currentState = variable.stateTypes[0];
                        } else {
                            variable.currentState = variable.stateTypes[1];
                        }
                    } else {
                        for (int j = 0; j < distribution.length; j++) {
                            if (compare <= distribution[j] && !setValue) {
                                setValue = true;
                                variable.currentState = variable.stateTypes[j];
                            }
                        }
                    }

                }
                for (int j = 0; j < evidenceNames.length; j++) {
                    if (variable.varName.equals(evidenceNames[j])) {
                        variable.currentState = evidenceValues[j];
                        break;
                    }
                }
            }
        }
        BayesianNetwork bayesianNetwork = new BayesianNetwork(currentState, probabilityNetwork);
        return bayesianNetwork;
    }
}


//        double[] doubleArray = new double[bayesNet.size()];
//        Hashtable<String, Variable> notEvidence = new Hashtable<String, Variable>();
//        //make the order of variables into a big long order, Maybe just do bottom up for times sake?
//        for (int i = 0; i < evidence.size(); i++) {
//            Variable holder = evidence.get(i);
//            notEvidence.remove(i);
//        }
//
//        //set of the current world based on evidence
//        Hashtable<Integer, Variable> currentState = new Hashtable<>();
//        for (int i = 0; i < evidence.size(); i++) {
//            currentState.put(i, evidence.get(i));
//
//        }

//    }


//TODO: Normalize this so it is actually readable back to data

//TODO: Push to main branch

