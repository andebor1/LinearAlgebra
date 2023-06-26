package rl.uses;

import rl.classes.matrices.Matrix;
import rl.classes.types.fields.Complex;
import rl.classes.types.fields.Rational;
import rl.classes.types.fields.Real;
import rl.classes.vectors.Vector;

import java.util.Arrays;

public class A12 {

    public static void main(String[] args) {
        E03c();
    }

    public static void E01() {
        Matrix A = Matrix.newMatrix(3, 4, 2, 2, 2, 4, 2, 2, 2, 2);
        Vector[] base = A.findDiagonalizingBase(Real.convertList(2, 4 - Math.sqrt(12), 4 + Math.sqrt(12)));
        Matrix P = Vector.toMatrix(base);
        System.out.println("P.multiply(A).multiply(P.transpose()) = \n" + P.inverse().multiply(A).multiply(P));
    }

    public static void E03a() {
        Matrix A = Matrix.newMatrix(3, Rational.convertList(2, -1, 2, 2, 2, -1, -1, 2, 2))
                .scalarMul(new Rational(1, 3));
        System.out.println("Vector.toString(A.sub(Matrix.scalarMatrix(3, Real.unit)).nullSpaceBase()) = \n"
                + Vector.toString(A.sub(Matrix.scalarMatrix(3, Rational.unit)).nullSpaceBase()));
        System.out.println(Arrays.toString(A.findEigenvaluesForRational(-10, 20)));
        System.out.println("A.det() = " + A.det());

        System.out.println("A.multiply(A).sub(A).add(Matrix.scalarMatrix(3, Rational.unit)) = \n"
                + A.multiply(A).sub(A).add(Matrix.scalarMatrix(3, Rational.unit)));

        A = Matrix.newMatrix(3, Real.convertList(2, -1, 2, 2, 2, -1, -1, 2, 2))
                .scalarMul(new Real(1.0/3));
        Vector v1 = new Vector(Real.convertList(1/Math.sqrt(3), 1/Math.sqrt(3), 1/Math.sqrt(3)));
        Vector v2 = new Vector(Real.convertList(1/Math.sqrt(2), -1/Math.sqrt(2), 0));
        Vector v3 = new Vector(Real.convertList(1/Math.sqrt(6), 1/Math.sqrt(6), -2/Math.sqrt(6)));
        Matrix P = Vector.toMatrix(v1, v2, v3);
        System.out.println(P.transpose().multiply(A).multiply(P));
    }

    public static void E03b() {
        Matrix A = Matrix.newMatrix(3, Real.convertList(0, 0, 1, 0, 1, 0, 1, 0, 0));
        Vector v1 = new Vector(Real.convertList(0, 1, 0));
        Vector v2 = new Vector(Real.convertList(1/Math.sqrt(2), 0, 1/Math.sqrt(2)));
        Vector v3 = new Vector(Real.convertList(1/Math.sqrt(2), 0, -1/Math.sqrt(2)));
        Matrix P = Vector.toMatrix(v1, v2, v3);
        System.out.println(P.transpose().multiply(A).multiply(P));
    }

    public static void E03c() {
        Matrix A = Matrix.newMatrix(4, Real.convertList(0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0));
        Vector v1 = new Vector(Real.convertList(1.0/2, 1.0/2, 1.0/2, 1.0/2));
        Vector v2 = new Vector(Real.convertList(1.0/2, 1.0/2, -1.0/2, -1.0/2));
        Vector v3 = new Vector(Real.convertList(1/Math.sqrt(2), -1/Math.sqrt(2), 0, 0));
        Vector v4 = new Vector(Real.convertList(0, 0, -1/Math.sqrt(2), 1/Math.sqrt(2)));
        Matrix P = Vector.toMatrix(v1, v2, v3, v4);
        System.out.println(P.transpose().multiply(A).multiply(P));
    }
}
