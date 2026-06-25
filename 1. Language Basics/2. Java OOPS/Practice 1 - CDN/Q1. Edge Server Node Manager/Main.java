/* 
Question 1 : Creating CDN Edge Server Node Manager

Concepts Tested :
Class
Objects
Static
Access Specifier
Constructors
Constructor Chaining
Encapsulation : Getter & Setter
*/


class EdgeServerNodeManager {
    // Global Attributes
    public static int totalActiveNodes = 0;
    public static double globalBandwidth = 0.0;

    // Node Attributes
    private String nodeID;
    private String region;
    private double currentLoadpercentage;
    private double allocatedBandwidth;

    //Node Set & Global Update - Construtors
    // C1 - When No explicit abw
    EdgeServerNodeManager(String nodeID, String region) {
        //Constructor Chaining - Calling C2
        this(nodeID, region, 10.0);
    }

    //C2 - When abw provided
    EdgeServerNodeManager(String nodeID, String region, double allocatedBandwidth) {
        //Setting Node
        this.nodeID = nodeID;
        this.region = region;
        this.allocatedBandwidth = allocatedBandwidth;
        
        //Setting Node Current Load - Call Setter
        // First Thought : setNode(nodeID, 0.0); (But this is redudant as nodeID passing again)
        setNode(0.0);

        //Success Message
        System.out.println("Node " + nodeID + " created in " + region + " Successfully.");

        //Update Global Stats
        updateGlobalStats(allocatedBandwidth);
    }


    //Setter
    // First Thought : public String setNode(String nodeID, double currentLoadpercentage) But this is redudant as nodeID passing again)
    public String setNode(double currentLoadpercentage){
        //Validation First
        String message; 
        if (currentLoadpercentage >= 0.0 && currentLoadpercentage <= 100.0) {
            //Set Node Current Load 
            
            this.currentLoadpercentage = currentLoadpercentage;

            message = "Node " + nodeID + " Current Load Successfully Updated to " + currentLoadpercentage;

            return message;

        } else {
            //Print Error
            message = "Error: Load Percentage must be between 0.0 to 100.0.";

            return message;
        }
    }

    //Global Stat Updater
    public void updateGlobalStats(double allocatedBandwidth) {
        totalActiveNodes++;
        globalBandwidth += allocatedBandwidth;
    } 

    //Getter
    public void getNode() {
        System.out.println("_________ Node Info _________");
        System.out.println("Node ID : " + nodeID);
        System.out.println("Region : " + region);
        System.out.println("Allocated Bandwidth : " + allocatedBandwidth + " Gbps");
        System.out.println("Current Load : " + currentLoadpercentage + " %");
    }

    //Print GlobalStats
    public void getGlobalStats() {
        System.out.println("_________ Global CDN Stats _________");
        System.out.println("Total Active Nodes : " + totalActiveNodes);
        System.out.println("Global Bandwidth Allocated : " + globalBandwidth);
    }
}


public class Main {
    public static void main(String[]args) {

        //Create Node 1
        EdgeServerNodeManager node1 = new EdgeServerNodeManager("IN-MUM-109", "Mumbai", 20.0);

        System.out.println("");

        //Create Node 2
        EdgeServerNodeManager node2 = new EdgeServerNodeManager("JP-TOK-910", "Tokyo");

        System.out.println("");

        //Print Node 1
        System.out.println("Node 1");
        node1.getNode();

        System.out.println("");

        //Print Node 2
        System.out.println("Node 2");
        node2.getNode();

        System.out.println("");

        //Print Global Stat
        node1.getGlobalStats();
        
        System.out.println("");

        node2.getGlobalStats();

        System.out.println("");

        //Invalid Update
        String op = node1.setNode(109.00);
        System.out.println(op);

        System.out.println("");

        //Valid Set
        op = node1.setNode(55.0);
        System.out.println(op);
    }
}