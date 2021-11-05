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

???
NORMALIZE
    add probability of each state up
    then divide each value by the total

    i.e. probs come out to <.12, .08>
        .12+.08 = .2
        .12/.2 = .6
        .08/.2 = .4
        the true prob is <.6,.4>

TAKEN FROM GABE:
 Implemented the BayesianNetwork from the .bif files

-BayesianNetwork contains two dictionaries: variableDict, probabilityDict

-variableDict contains all of the nodes of the graph in the form of a Variable class. Each Variable contains a string list of its parents keys. This should be helpful in finding the Markov Blanket.

-probabilityDict contains all of the conditionalProbabilityTables (CPTs) for a conditional probability. The keys for probabilityDict are the general conditional probability. Example probabilityDict keys from child.bif, "BirthAsphyxia" and "HypDistrib | DuctFlow, CardiacMixing". The returned object is a Probability class .

-These CPTs are stored in a dictionary within the Probability class. Where the key for the CPT is the given evidence value (Example for "Birth Asphyxia" would be Key "table", for  "HypeDistrib | DuctFlow, CardiacMixing" would be key "Lt_to_Rt, None") and the returned object is a Double[] with the probabilities.

-All data held in these classes are public for easy access.

*/
    //TODO: Kyler writes all this and it works first try! Right?!

    //TODO: Set up local variables
    //TODO: Do a Gibbs sample loopy bit
    //TODO: Normalize this so it is actually readable back to data
    //TODO: Push to main branch

}
