package rl.useable;

import rl.classes.types.Polynomial;
import rl.classes.types.fields.FieldElement;

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

    public static Polynomial polyGCD(Polynomial p1, Polynomial p2) {
        if (p2.isZero()) {
            return p1;
        }
        if (p2.degree() > p1.degree()) {
            return polyGCD(p2, p1);
        }

        return polyGCD(p2, polyMod(p1, p2));
    }

    public static Polynomial polyMod(Polynomial p1, Polynomial p2) {
        if (p2.degree() == 0) {
            return p1.zero();
        }

        Polynomial left = p1;
        int k = p2.degree();

        while (left.degree() >= p2.degree()) {
            int d = left.degree();
            FieldElement c = left.getCoefficient(d).neg();

            left = left.add(p2.mul(Polynomial.xPower(d - k, c.unit()).mul(c)));
        }

        return left;
    }

    public static Polynomial polyDiv(Polynomial p1, Polynomial p2) {
        Polynomial left = p1;
        int k = p2.degree();
        FieldElement[] resFactors = new FieldElement[p1.degree() - p2.degree() + 1];

        for (int i=p1.degree()-p2.degree(); i>=0; i--) {
            int d = left.degree();
            FieldElement c = left.getCoefficient(d);

            left = left.add(p2.mul(Polynomial.xPower(d - k, c.unit()).mul(c.neg())));
            resFactors[i] = c;
        }

        return new Polynomial(resFactors);
    }
}
