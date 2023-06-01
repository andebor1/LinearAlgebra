package rl.classes.testers;

import rl.classes.matrices.*;
import rl.classes.types.fields.*;
import rl.classes.vectors.*;

public class Test1 {
    public static void main(String[] args) {
        testField();
    }


    public static void A8Q1() {
        DMatrix A = new DMatrix(new double[][] {{6, -9, 5, 4}, {7, -13, 8, 7}, {8, -17, 11, 8}, {1, -2, 1, 3}});
        DMatrix mat2 = A.sub(DMatrix.unit(4).scalarMul(2));
        DMatrix mat3 = A.sub(DMatrix.unit(4).scalarMul(1));
        DVector v1 = new DVector(3, 6, 7, 1);
        DVector v2 = new DVector(2, 1, 0, 0);
        DVector v3 = mat2.mulVec(v2); // new Vector(-1, 0, 4, 16);
        DVector v4 = new DVector(-1, 0, 0, 1);
        DMatrix P = DVector.toMatrix(v1, v2, v3, v4);
        DMatrix P_inv = P.inverse();
        DMatrix J = new DMatrix(new double[][] {{2, 0, 0, 0}, {1, 2, 0, 0}, {0, 0, -2, 0}, {0, 0, 1, -2}});

        System.out.println(P_inv.multiply(A).multiply(P));
    }

    public static void A8Q2() {
        DMatrix A = new DMatrix(new double[][] {{2, 1, 2}, {-1, 0, -2}, {0, 0, 3}});
        DMatrix mat2 = A.sub(DMatrix.unit(3).scalarMul(1));
        DMatrix mat3 = A.sub(DMatrix.unit(3).scalarMul(3));
        DVector v1 = new DVector(1, 1, 0);
        DVector v2 = mat2.mulVec(v1);
        DVector v3 = new DVector(1, -1, 1);
        DMatrix P = DVector.toMatrix(v1, v2, v3);
        DMatrix P_inv = P.inverse();
        DMatrix J = new DMatrix(new double[][] {{2, 0, 0, 0}, {1, 2, 0, 0}, {0, 0, -2, 0}, {0, 0, 1, -2}});

        System.out.println(A.pow(8));
    }

    private static void testField() {
        Vector v1 = new Vector(BigInt.toRational(20, 200, 3, 1));
        Vector v2 = new Vector(BigInt.toRational(7, 12, 3, 5));
        Vector v3 = new Vector(BigInt.toRational(8, -3, -30, 2));

        Matrix mat = Vector.toMatrix(v1, v2, v3, v1);

        System.out.println(mat.characteristicPolynomial());
    }
}
