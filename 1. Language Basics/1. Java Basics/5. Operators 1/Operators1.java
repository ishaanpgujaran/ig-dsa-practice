/* 
Without using multiplication operator (*), calculate square of a number.
*/


class Operators1 {
    
    public static void main(String[] args) {

        //Solving using Loops
        int n = 10;
        int sq = 0;

        for (int i = 0; i < n; i++) {
            sq += n;
        }

        System.out.println("Square of " + n + " = " + sq);
    }
}