/* 
Final Boss Level Challenge
Question 3 : Modular CDN Ecosystem

Concepts Tested :
- Abstraction 
- Abstract Classes & Absract Methods
- Interfaces 
*/


//Defining Netwrok Plugin as Interface 
interface NetworkPlugin {

    //Method whichs is abstract meaning child will have to provide specific implementation
    void execute(String nodeID) ;
}


//Plugin Childs Implementing Interfaces

//Child 1
class WafSecurityPlugin implements NetworkPlugin {

    //Provides specific implementation of abstract method
    @Override 
    public void execute(String nodeID) {
        System.out.println("[WAF] Scrubbing traffics payload for node : " + nodeID);
    }
}

//Child 2
class AnalyticsPlugin implements NetworkPlugin {

    //Provides specific implementation of abstract method
    @Override
    public void execute(String nodeID) {
        System.out.println("[Analytics] Logging request metrics for node : " + nodeID);
    }
}


//Base Class changes to Abstract Class 
abstract class EdgeNode {
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

    //Getter to get clp
    public double getLoadPercentage() {
        return this.currentLoadPercentage;
    }

    //Getter to get NodeID
    public String getID() {
        return this.nodeID;
    }


    //Abstract Method - Child Class must provide specific implementation
    public abstract String getNode() ;


    //Plugin Management Fields
    private NetworkPlugin[] attachedPlugins = new NetworkPlugin[2];
    private int pluginCount = 0;


    //Plugin Registration Method
    public void registerPlugin(NetworkPlugin plugin) {
        
        //Was doing without any check of Array Full which can lead to Array Index Out of Bound Error
        //Gemini Suggested Production Approach
        if (pluginCount >= attachedPlugins.length) {
            throw new IllegalStateException("Plugin registry capacity exceeded for node : " + nodeID);
        }

        //If Space left then only register plugin
        attachedPlugins[pluginCount] = plugin;
        pluginCount++;

        //Gemini Optimized Register 
        // attachedPlugins[pluginCount++] = plugin;
    }


    //Method for Looping through Plugins
    public void runPlugins() {
        for (int i = 0; i < attachedPlugins.length; i++) {
            if (attachedPlugins[i] != null) {
                attachedPlugins[i].execute(nodeID);
            }
            
        }
    }

    //Modified Process Request Method to use Plugins before calc
    public void processRequest(double bandwidth) {
        
        //First Loop through Plugins
        runPlugins();

        //Starting Message
        System.out.println(getNode() + " Processing " + bandwidth + " Gbps traffic allocation.");

        //No new setter needed Calling Setter we already have
        //We directly cannot modify CLP hence modify via single channel
        double targetLoad = this.currentLoadPercentage + bandwidth;
        setLoadPercentage(targetLoad); 
    }
}


//Child Class 1 - Standard Node Extends Base
class StandardNode extends EdgeNode {

    //Constructor Calling Parent Constructor
    StandardNode(String nodeID, String region) {
        super(nodeID, region);
    }


    //Specific Implementation of Abstract Method
    @Override
    public String getNode() {
        return "[Standard Node]";
    }
}


//Child Class 2 - Premium Node Extends Base 
class PremiumExpressNode extends EdgeNode {

    // Constructor calling the Parent Constructor
    PremiumExpressNode(String nodeID, String region) {
        super(nodeID, region);
    }


    //Specific Implementation of Abstract Method
    @Override 
    public String getNode() {
        return "[Premium Express Node]";
    }


    //Overridden Method Process Request
    @Override
    public void processRequest(double bandwidth) {
        
        //First Loop through Plugins
        runPlugins();

        //Getting CLP
        double currentLoadPercentage = getLoadPercentage();

        if (bandwidth < 20) {
            //Starting Message
            System.out.println(getNode() + " Processing " + bandwidth + " Gbps traffic allocation.");

            //No new setter needed Calling Setter we already have
            //We directly cannot modify CLP hence modify via single channel
            double targetLoad = currentLoadPercentage + bandwidth;
            setLoadPercentage(targetLoad);
            
            return;
        }
        
        //Starting Message
        System.out.println(getNode() + " High-demand streaming detected! Compressing traffic impact...");
        bandwidth -= (bandwidth * 0.2);

        double targetLoad = currentLoadPercentage + bandwidth;
        setLoadPercentage(targetLoad);

        //Success Message
        System.out.println(getNode() + " Successfully allocated optimized bandwidth footprint of " + bandwidth + " Gbps.");
    }
}



public class Main {
    public static void main(String[] args) {
        
        try {
            //Edge Node Array with Parent Ref
            EdgeNode[] nodes = new EdgeNode[2];

            //Instantiating Childs
            nodes[0] = new StandardNode("IN-STD-01", "Mumbai");
            nodes[1] = new PremiumExpressNode("IN-PRM-02", "Mumbai");

            //Instantiating Plugin - Child Classes of Interface
            NetworkPlugin WafSecurityPlugin = new WafSecurityPlugin();
            NetworkPlugin AnalyticsPlugin = new AnalyticsPlugin();
        
            System.out.println("=== Booting CDN Cluster Traffic Simulation ===");
            
            //Registering Plugins in Standard Node
            nodes[0].registerPlugin(AnalyticsPlugin);

            //Registering Plugins in Premium Node
            nodes[1].registerPlugin(WafSecurityPlugin);
            nodes[1].registerPlugin(AnalyticsPlugin);


            //Passing Bandwidth Request to both using Array
            for (int i = 0; i < nodes.length; i++) {
                
                //Printing Which Node is Deploying
                System.out.println("\nDeploying Request to Node : " + nodes[i].getID() + " | Type : " + nodes[i].getNode());
                
                //Processing Bandwidth
                nodes[i].processRequest(25.0);

                System.out.println("\n------------------------------------------------------------------");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
