package rl.classes.testers;

import rl.classes.types.*;
import rl.classes.types.fields.GRational;
import rl.classes.types.fields.RationalPolynomial;
import rl.classes.types.fields.Real;
import rl.useable.*;

public class PolyTest {

    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
        Polynomial p1 = new Polynomial(Real.convertList(1, 3));
        Polynomial p2 = p1.mul(p1).add(p1);
        p1 = new Polynomial(Real.convertList(1, 2));

        System.out.println(p2 + " " + p1);
        System.out.println(Numeric.polyDiv(p2, p1).mul(p1).add(Numeric.polyMod(p2, p1)));
    }

    public static void test2() {
        Polynomial p1 = new Polynomial(Real.convertList(1, 1));
        RationalPolynomial q = new RationalPolynomial(p1, p1);
        GRational p = new GRational(q);

        System.out.println(p);
    }
}
