public class Variable {
    public String varName = "";
    public int numStates = 0;
    public String[] stateTypes;
    public String[] parents = new String[1];

    public Variable(String name) {
        name = name.replace("variable ", "");
        name = name.replace(" {", "");
        name = name.replace("\r", "");
        varName = name;
    }

    public void addVar(String info) {
        info = info.replace("  type discrete [ ", "");
        //System.out.println(info.charAt(0));
        numStates = Integer.parseInt(String.valueOf(info.charAt(0)));
        StringBuffer sbInfo = new StringBuffer(info);
        sbInfo.replace(0, 5, "");
        info = sbInfo.toString().replace(" };", "");
        ;
        info = info.replace(" ", "");
        info = info.replace("\r", "");
        stateTypes = info.split(",");
        //System.out.println(stateTypes[0]);
    }

    public void setParents(String[] parents) {
        this.parents = parents;
    }
}
