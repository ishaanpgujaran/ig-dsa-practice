/* 
Swap two numbers WITHOUT using third variable.
*/

class Operators2 {
    
    public static void main(String[] args) {

        int a = 10;

        int b = 9;

        System.out.println("Before Swapping :");
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        b += a;
        a = b - a;
        b -= a;

        System.out.println("_____________________");

        System.out.println("After Swapping :");
        System.out.println("a = " + a);
        System.out.println("b = " + b);


    }
}
