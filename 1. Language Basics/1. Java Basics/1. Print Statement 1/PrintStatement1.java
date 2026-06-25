/*
Print the following pattern : 
*
**
***
****
*****
*/

class PrintStatement1 {

    public static void main(String[] args) {
        //Solving using Print Statements
        System.out.println("## Printing using just Print Statements");
        System.out.println("_________________________________________");
        System.out.println("*");
        System.out.println("**");
        System.out.println("***");
        System.out.println("****");
        System.out.println("*****");

        //Solving using For Loop
        System.out.println("");
        System.out.println("");
        System.out.println("## Printing using For Loop");
        System.out.println("_________________________________________");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println("");
        }


    }


}