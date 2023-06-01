package rl.uses;

import rl.classes.matrices.Matrix;
import rl.classes.types.fields.Complex;
import rl.classes.types.fields.Rational;
import rl.classes.vectors.Vector;

import java.util.Arrays;

public class A10 {

    public static void main(String[] args) {
        E6();
    }

    static void E4() {
        Vector v1 = new Vector(Rational.convertList(1, 1, -1, 1, 0));
        Vector v2 = new Vector(Rational.convertList(1, -1, -2, 0, 1));

        System.out.println(Vector.toString(Vector.orthogonalForm(Vector.orthogonalSpaceBase(v1, v2))));
    }

    static void E6() {
        Matrix mat = Matrix.newMatrix(3, new Complex(2, 0), new Complex(1, -1), new Complex(0, 0),
                new Complex(3, 2), new Complex(0, 0), new Complex(0, -4),
                new Complex(0, 2), new Complex(4, -3), new Complex(-3, 0));

        System.out.println(mat.transpose());
    }
}
