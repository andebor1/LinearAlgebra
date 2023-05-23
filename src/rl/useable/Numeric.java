package rl.useable;

public class Numeric {

    public static int gcd(int m, int k) {
        if (k == 0) {
            return m;
        }
        if (k > m) {
            return gcd(k, m);
        }
        if (m < 0) {
            return -gcd(-m, -k);
        }

        return gcd(k, m%k);
    }
}
