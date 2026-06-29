/* 
Question 2 : Tiered Traffic Routing Engine

Concepts Tested :
Inheritacne
Polymorphism : Method Overriding & Runtime Polymorphism
super Keyword
Runtime Binding
*/

//Parent Class 
//Cleaned Version of Q1 by resolving mistakes
class EdgeNode {
    // Global Attributes should also be private & static to avoid access
    private static int totalActiveNodes = 0;
    private static double globalBandwidth = 0.0;

    // Node Attributes encapsulated
    private final String nodeID;
    private final String region;
    //Above are final because they are properties of a hardware which should not change
    private double currentLoadPercentage;
    private double allocatedBandwidth;

    //Node Set & Global Update - Construtors
    
    // C1 - When No explicit abw
    EdgeNode(String nodeID, String region) {
        
        //Constructor Chaining - Calling C2
        this(nodeID, region, 10.0);
    }

    //C2 - When abw provided
    EdgeNode(String nodeID, String region, double allocatedBandwidth) {
        //Setting Node
        this.nodeID = nodeID;
        this.region = region;
        this.allocatedBandwidth = allocatedBandwidth;
        this.currentLoadPercentage = 0.0;
        
        
        //Automatic Updation of Global Variables inside Constructor
        totalActiveNodes++;
        globalBandwidth += allocatedBandwidth;

        //All Printing messages should be handled by Main Method & Not by Construtor or Methods
    }


    //Setter
    // First Thought : public String setNode(String nodeID, double currentLoadPercentage) But this is redudant as nodeID passing again)
    // Correction Naming Convention should be proper not setNode as only modifying clp
    public void setLoadPercentage(double currentLoadPercentage){
        //Validation First using Guard Clause
        if (currentLoadPercentage < 0.0 || currentLoadPercentage > 100.0) {
            throw new IllegalArgumentException("Error: Load Percentage must be between 0.0 to 100.0.");
        }
        this.currentLoadPercentage = currentLoadPercentage;

        //Setter should only perform setting no printing
    } 

    //Getter for Total Active Node - Should Only return a value
    public static int getTotalActiveNodes() {
        return totalActiveNodes;
    }

    //Getter for Global Bandwidth
    public static double getGlobalBandwidth() {
        return globalBandwidth;
    }

    //Q2. Process Request Method
    public void processRequest(double bandwidth) {
        
        //Starting Message
        System.out.println("[Standard Node] Processing " + bandwidth + " Gbps traffic allocation.");

        //No new setter needed Calling Setter we already have
        //We directly cannot modify CLP hence modify via single channel
        double targetLoad = this.currentLoadPercentage + bandwidth;
        setLoadPercentage(targetLoad); 
    }

    //Getter to get clp
    public double getLoadPercentage() {
        return this.currentLoadPercentage;
    }


}


//Child Class - Premium Extends Base 
class PremiumExpressNode extends EdgeNode {

    // Constructor calling the Parent Constructor
    PremiumExpressNode(String nodeID, String region) {
        super(nodeID, region);
    }

    //Overridden Method Process Request
    @Override
    public void processRequest(double bandwidth) {
        
        //Getting CLP
        double currentLoadPercentage = getLoadPercentage();

        if (bandwidth < 20) {
            //Starting Message
            System.out.println("[Premium Node] Processing " + bandwidth + " Gbps traffic allocation.");

            //No new setter needed Calling Setter we already have
            //We directly cannot modify CLP hence modify via single channel
            double targetLoad = currentLoadPercentage + bandwidth;
            setLoadPercentage(targetLoad);
            

            return;
        }
        
        //Starting Message
        System.out.println("[Premium Node] High-demand streaming detected! Compressing traffic impact...");
        bandwidth -= (bandwidth * 0.2);

        double targetLoad = currentLoadPercentage + bandwidth;
        setLoadPercentage(targetLoad);

        //Success Message
        System.out.println("[Premium Node] Successfully allocated optimized bandwidth footprint.");
    }
}


public class Main {

    public static void main(String[] args) {

        //Array of Parent Type
        EdgeNode[] nodes = new EdgeNode[2];

        //Instiantiating Objects
        nodes[0] = new EdgeNode("NJ-STD-01", "New Jersey");
        nodes[1] = new PremiumExpressNode("NJ-PRM-02", "New Jersey");
        

        //Loop to pass 25 Gbps bw
        for (int i = 0; i < nodes.length; i++) {
            try {
                nodes[i].processRequest(25.0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
        }
    }
    
}
