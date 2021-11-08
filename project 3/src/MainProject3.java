import java.io.FileNotFoundException;

public class MainProject3 {
    public static void main(String args[]) throws FileNotFoundException {
        BayesianNetwork alarm = new BayesianNetwork("project 3/BNFiles/alarm.bif");
//        BayesianNetwork child = new BayesianNetwork("project 3/BNFiles/child.bif");
//        BayesianNetwork hailfinder = new BayesianNetwork("project 3/BNFiles/hailfinder.bif");
//        BayesianNetwork insurance = new BayesianNetwork("project 3/BNFiles/insurance.bif");
//        BayesianNetwork win95pts = new BayesianNetwork("project 3/BNFiles/win95pts.bif");

        String[] nameOfLittleAlarmEvidence = new String[3];
        String[] valueOfLittleAlarmEvidence = new String[3];

        nameOfLittleAlarmEvidence[0] = "HRBP";
        nameOfLittleAlarmEvidence[1] = "CO";
        nameOfLittleAlarmEvidence[2] = "BP";

        valueOfLittleAlarmEvidence[0] = "HIGH";
        valueOfLittleAlarmEvidence[1] = "LOW";
        valueOfLittleAlarmEvidence[2] = "HIGH";

        alarm.gibbsSample(nameOfLittleAlarmEvidence, valueOfLittleAlarmEvidence);


    }
}
