/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

public class Test {
    public static void main(String[] args) {
        double sum = 0;
        double T = 400;
        for (double i = 1.0; i <= 204; i++) {
            sum += (i / T);
        }
        System.out.println(sum);
    }
}
