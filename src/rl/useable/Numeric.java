package rl.useable;

public class Numeric {

    public static int gcd(int m, int k) {
        if (k < 0) {
            return -gcd(m, -k);
        }
        if (m < 0) {
            return gcd(-m, k);
        }
        if (k == 0) {
            return m;
        }
        if (k > m) {
            return gcd(k, m);
        }

        return gcd(k, m%k);
    }
}
