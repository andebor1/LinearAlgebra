package rl.useable;

import rl.classes.types.Polynomial;
import rl.classes.types.fields.FieldElement;

public class PolyOperations implements FractionsOperations {

    public boolean usable() {
        return true;
    }

    public static Polynomial getValue(FieldElement element) {
        if (element instanceof Polynomial p) {
            return p;
        }

        throw new IllegalArgumentException("can't accept not Polynomial");
    }

    @Override
    public FieldElement gcd(FieldElement element1, FieldElement element2) {
        return (FieldElement) polyGCD(getValue(element1), getValue(element2));
    }

    @Override
    public FieldElement div(FieldElement element1, FieldElement element2) {
        return (FieldElement) polyDiv(getValue(element1), getValue(element2));
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

        int k = p2.degree();
        FieldElement highInv = p2.getCoefficient(k).inverse();

        Polynomial left = p1.mul(highInv);
        Polynomial divider = p2.normalize();

        while (left.degree() >= divider.degree()) {
            int d = left.degree();
            FieldElement c = left.getCoefficient(d).neg();

            left = left.add(divider.mul(Polynomial.xPower(d - k, c.unit()).mul(c)));
        }

        return left.mul(highInv.inverse());
    }

    public static Polynomial polyDiv(Polynomial p1, Polynomial p2) {
        int k = p2.degree();
        int l = p1.degree();

        if (k > l) {
            return p1.zero();
        }

        FieldElement highInv = p2.getCoefficient(k).inverse();

        Polynomial left = p1.mul(highInv);
        Polynomial divider = p2.normalize();


        FieldElement[] resFactors = new FieldElement[l - k + 1];

        for (int i = l - k; i>=0; i--) {
            int d = left.degree();
            FieldElement c = left.getCoefficient(d);

            left = left.add(divider.mul(Polynomial.xPower(d - k, c.unit()).mul(c.neg())));

            resFactors[i] = c;
        }

        return new Polynomial(resFactors);
    }


}
