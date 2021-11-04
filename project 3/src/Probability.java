import java.util.Hashtable;

public class Probability {
    public String pName;
    public Hashtable<String, Double[]> cptDictionary = new Hashtable<String, Double[]>();


    public Probability(String name, Hashtable<String, Variable> variableDictionary){
        name = name.replace("probability ( ", "");
        name = name.replace(" ) {", "");
        name = name.replace("\r","");
        pName = name;
        if(pName.contains("|")){
            String child = name.substring(0, name.indexOf("|"));
            child = child.replace(" ", "");
            Variable var = variableDictionary.get(child);
            String parents = name.substring(name.indexOf("|")+1);
            parents = parents.replace(" ", "");
            if(parents.contains(",")){
                parents = parents.replace("\r", "");
                var.setParents(parents.split(","));
            }else{
                parents = parents.replace("\r", "");
                String[] parentsA = new String[1];
                parentsA[0] = parents;
                var.setParents(parentsA);
            }
        }else{
            Variable var = variableDictionary.get(pName);
            var.setParents(null);
        }
    }

    public void addProb(String info){
        String key;
        String[] sValue;
        Double[] value;
        if(info.contains(" table")){
            key = "table";
            info = info.replace("table", "");
        }else{
            info = info.substring(info.indexOf("(")+1);
            key = info.substring(0, info.indexOf(")"));
            info = info.replace(key + ")", "");
        }
        info = info.replace(" ", "");
        info = info.replace(";", "");
        sValue = info.split(",");
        value = new Double[sValue.length];
        for(int i = 0; i < sValue.length; i++){
            value[i] = Double.parseDouble(sValue[i]);
        }
        cptDictionary.put(key, value);

    }
}
