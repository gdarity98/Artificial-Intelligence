import java.util.Hashtable;
import java.util.Objects;

public class GibbsSample {
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

/*
???
NORMALIZE
    add probability of each state up
    then divide each value by the total
    i.e. probs come out to <.12, .08>
        .12+.08 = .2
        .12/.2 = .6
        .08/.2 = .4
        the true prob is <.6,.4>
*/

/*
TAKEN FROM GABE:
 Implemented the BayesianNetwork from the .bif files
-BayesianNetwork contains two dictionaries: variableDict, probabilityDict
-variableDict contains all of the nodes of the graph in the form of a Variable class. Each Variable contains a string list of its parents keys. This should be helpful in finding the Markov Blanket.
-probabilityDict contains all of the conditionalProbabilityTables (CPTs) for a conditional probability. The keys for probabilityDict are the general conditional probability. Example probabilityDict keys from child.bif, "BirthAsphyxia" and "HypDistrib | DuctFlow, CardiacMixing". The returned object is a Probability class .
-These CPTs are stored in a dictionary within the Probability class. Where the key for the CPT is the given evidence value (Example for "Birth Asphyxia" would be Key "table", for  "HypeDistrib | DuctFlow, CardiacMixing" would be key "Lt_to_Rt, None") and the returned object is a Double[] with the probabilities.
-All data held in these classes are public for easy access.
*/

//    Hashtable<String, Probability> probabilityNetwork;
//    Hashtable<String, Variable> evidence;
//    Hashtable<String, Variable> notEvidence;
//    int countWantedValue;

//    public GibbsSample(Hashtable<String, Probability> probabilityNetwork, Hashtable<String, Variable> evidence, Hashtable<String, Variable> notEvidence, int countWantedValue) {
//        this.probabilityNetwork = probabilityNetwork;
//        this.evidence = evidence;
//        this.notEvidence = notEvidence;
//        this.countWantedValue = countWantedValue;
//    }

    String[] evidenceNames;
    String[] evidenceValues;
    Hashtable<String, Probability> probabilityNetwork;
    Hashtable<String, Variable> variableNetwork;
    int countLoops;


    public GibbsSample(Hashtable<String, Probability> probabilityDictionary, Hashtable<String, Variable> variableDictionary, String[] nameOfEvidence, String[] valueOfEvidence, int countLoops) {
        this.probabilityNetwork = probabilityDictionary;
        this.variableNetwork = variableDictionary;
        this.evidenceNames = nameOfEvidence;
        this.evidenceValues = valueOfEvidence;
        this.countLoops = countLoops;
    }
    //TODO: Do a Gibbs sample loopy bit

    // Possibly useless code: Variable variable, Hashtable<String, Variable> evidence, Hashtable<String, Probability> bayesNet, int loopCount) {

    public void GibbsAsk() {
        double[] numberOfRepetitions = new double[5];
        Hashtable<String, String> evidence = new Hashtable<>();
        Hashtable<String, Variable> guess = variableNetwork;
        Hashtable<String, Variable> currentState = variableNetwork;

        //If we get null this line prints and then everything will probably break
        if (variableNetwork.isEmpty()) {
            System.out.println("No Variables made it here");
        }

        //for every variable in our evidence we remove it from evidence and then add it to our actual evidence
        for (int i = 0; i < Objects.requireNonNull(evidenceNames).length; i++) {
            if (evidenceNames[i].equals(variableNetwork.get(evidenceNames[i]).varName)) {
                guess.remove(evidenceNames[i]);
                evidence.put(evidenceNames[i], evidenceNames[i]);
            }
        }

        //Initialize current state with random guess for all non-evidence states
        for (Variable var : currentState.values()) {
//            probabilityNetwork.get()
//            currentState.put(var.varName, )
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

    }


    //TODO: Normalize this so it is actually readable back to data

    //TODO: Push to main branch

}