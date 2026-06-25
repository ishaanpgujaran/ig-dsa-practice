//Check if a number is even or odd WITHOUT using %.

class Operators3 {
    
    public static void main(String[] args) {

        // Ishaan Approach
        System.out.println("#Ishaan Approach - Optimal");
        System.out.println("___________________________");

        int n1 = 10;
        int n2 = 19;

        int d1 = n1 / 2;
        int d2 = n2 / 2;

        if ((d1 * 2) == n1) {
            System.out.println("Number " + n1 + " is EVEN.");
        } else {
            System.out.println("Number " + n1 + " is ODD.");
        }

        System.out.println("");


        if ((d2 * 2) == n2) {
            System.out.println("Number " + n2 + " is EVEN.");
        } else {
            System.out.println("Number " + n2 + " is ODD.");
        }

        System.out.println("");
        System.out.println("");

        //Shivani Approach 
        System.out.println("#Shivani Approach");
        System.out.println("___________________________");
        int n3 = 2;
        int n4 = 3;

        int x = n3;
        int y = n4;


        //Reducing Numbers
        while (x != 0 && x != 1) {
            x = x - 2;
        }

        while (y != 0 && y != 1) {
            y = y - 2;
        }

        //Checking
        if (x == 0) {
            System.out.println("Number " + n3 + " is EVEN.");
        } else {
            System.out.println("Number " + n3 + " is ODD.");
        }

        System.out.println("");

        if (y == 0) {
            System.out.println("Number " + n4 + " is EVEN.");
        } else {
            System.out.println("Number " + n4 + " is ODD.");
        }

    }
}
