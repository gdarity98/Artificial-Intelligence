import java.util.ArrayList;

public class InferenceBuilder {

    //Called to create a potential new rule
    public static boolean makeInference(String rule, ArrayList<String> rulesList, World world, ResolutionBased resolutionBased) {
        String assume = "";

        //Create a base for the knowledge base that will be updated
        ArrayList<String> knowledge = new ArrayList<>();
        for (String a : rulesList) {
            knowledge.add(a);
        }

        //Resolve our new rule to make sure it doesn't break something
        String res = resolveLoop(assume, knowledge, world, resolutionBased);

        return res != null;
    }

    private static ArrayList<String[]> Unify(String string1, String string2, ResolutionBased resolutionBased) throws Exception {
        ArrayList<String[]> replace = new ArrayList<>();
        boolean impossible = true;

        //Look to see if we are adding a possible rule
        if ((string1.contains("Wumpus")
                || string1.contains("Stench")
                || string1.contains("Glitter")
                || string1.contains("Breeze"))
                && (string2.contains("Wumpus")
                || string2.contains("Stench")
                || string2.contains("Glitter")
                || string2.contains("Breeze"))) {
            impossible = false;
            //Make sure we aren't repeating ourselves and that we are using general rules
            if (!string1.equals(string2)) {
                //unify
                String[] replaceVar = UnifyVariables(string1, string2, resolutionBased);
                replace.add(replaceVar);
                return replace;
            }
        }

        //If fail, fail upwards
        if (impossible) {
            throw new Exception("Can't Unify");
        } else {
            return replace;
        }
    }

    //If we check against a rule with constants break out, If it has X or Y we swap out so that we have a location
    private static String[] UnifyVariables(String string1, String string2, ResolutionBased resolutionBased) {
        String[] replacement = new String[2];

        if (string1.contains("X") || string1.contains("Y")) {
            replacement[0] = string2;

            if (string2.contains("+")) {
                replacement[1] = String.valueOf(resolutionBased.getPlayerPosition() + string1);
            } else if (string2.contains("-")) {
                replacement[1] = String.valueOf(resolutionBased.getPlayerPosition());
            } else {
                replacement[1] = string1;
            }
        } else if (string2.contains("X") || string2.contains("Y")) {
            replacement[0] = string1;

            if (string1.contains("+")) {
                replacement[1] = String.valueOf(resolutionBased.getPlayerPosition() + string2);
            } else if (string1.contains("-")) {
                replacement[1] = String.valueOf(resolutionBased.getPlayerPosition());
            } else {
                replacement[1] = string2;
            }
        } else {
            //Tried to unify a rule to a rule
            System.out.println("Unification on two variables!");
            System.exit(42);
        }

        return replacement;
    }

    //Make a new rule and check to make sure it fits
    public static String ResolveNewRule(String string1, String string2, ResolutionBased resolutionBased) {
        try {
            ArrayList<String[]> substitution = Unify(string1, string2, resolutionBased);
        } catch (Exception e) {
            return null;
        }
        String newRule = string1;
        boolean matching = string2.equals(string1);

        if (!matching) {
            newRule = newRule.toString() + string2;
        }

        return newRule;
    }

    //Add the new rules to the knowledge base
    public static String ResolveToKnowledge(String rule, World world) {
        String newRule = rule.toString();

        String resolvedString = ResolveToKnowledge(rule, world);

        if (resolvedString.isEmpty()) {
            return resolvedString;
        }

        if ((rule.contains("Wumpus")
                || rule.contains("Stench")
                || rule.contains("Glitter")
                || rule.contains("Breeze"))) {
            int[] position = new int[2];
            boolean isConstant = false;

            for (int i = 0; i < rule.length(); i++) {
                if (rule.charAt(i) != 'X' || rule.charAt(i) != 'Y') {
                    position[i % 2] = Integer.parseInt(String.valueOf(rule.charAt(i)));
                    isConstant = true;
                }
            }

            if (isConstant) {
                try {
                    //There should be more stuff here
                    //Needs to look at the predicates and do unify off it
                    //change new rule to be the actual new rule

                    //This should never be reached, and we panic toss it upwards if we do
                    if (newRule.isEmpty()) {
                        return newRule;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return newRule;
    }

    //Recursivly call a new rule to the knowledge base and add it if it does not exist
    public static String resolveLoop(String assumption, ArrayList<String> knowledge, World world, ResolutionBased resolutionBased) {
        for (String string : knowledge) {
            String resolution = ResolveNewRule(assumption, string, resolutionBased);

            if (resolution == null) {
                continue;
            }

            resolution = ResolveToKnowledge(resolution, world);

            if (resolution.isEmpty()) {
                return resolution;
            } else {
                ArrayList<String> knowledgeBase = new ArrayList<>();
                for (String temp : knowledge) {
                    knowledgeBase.add(temp.toString());
                }
                knowledgeBase.removeIf(temp -> temp.equals(string));

                resolution = resolveLoop(resolution, knowledgeBase, world, resolutionBased);

                if (resolution != null && resolution.isEmpty()) {
                    return resolution;
                }
            }

        }
        //Failed so we throw it upwards
        return null;
    }
}
