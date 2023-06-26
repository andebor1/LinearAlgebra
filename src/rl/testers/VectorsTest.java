package rl.testers;

import rl.classes.types.fields.Rational;
import rl.classes.vectors.Vector;

import java.util.Arrays;

public class VectorsTest {
    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        Vector v1 = new Vector(Rational.convertList(1, 2, 3));
        Vector v2 = new Vector(Rational.convertList(1, 0, 0));
        Vector v3 = new Vector(Rational.convertList(1, 1, 0));

        System.out.println(Arrays.toString(Vector.orthogonalForm(v2, v3, v1)));
    }
}
