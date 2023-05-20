package rl.classes.testers;

import rl.classes.matrices.*;
import rl.classes.types.*;

public class Test1 {
    public static void main(String[] args) {
        A8Q2();
    }

    public static void A8Q1() {
        Matrix A = new Matrix(new double[][] {{6, -9, 5, 4}, {7, -13, 8, 7}, {8, -17, 11, 8}, {1, -2, 1, 3}});
        Matrix mat2 = A.sub(Matrix.unit(4).scalarMul(2));
        Matrix mat3 = A.sub(Matrix.unit(4).scalarMul(1));
        Vector v1 = new Vector(3, 6, 7, 1);
        Vector v2 = new Vector(2, 1, 0, 0);
        Vector v3 = mat2.mulVec(v2); // new Vector(-1, 0, 4, 16);
        Vector v4 = new Vector(-1, 0, 0, 1);
        Matrix P = Vector.toMatrix(v1, v2, v3, v4);
        Matrix P_inv = P.inverse();
        Matrix J = new Matrix(new double[][] {{2, 0, 0, 0}, {1, 2, 0, 0}, {0, 0, -2, 0}, {0, 0, 1, -2}});

        System.out.println(P_inv.multiply(A).multiply(P));
    }

    public static void A8Q2() {
        Matrix A = new Matrix(new double[][] {{2, 1, 2}, {-1, 0, -2}, {0, 0, 3}});
        Matrix mat2 = A.sub(Matrix.unit(3).scalarMul(1));
        Matrix mat3 = A.sub(Matrix.unit(3).scalarMul(3));
        Vector v1 = new Vector(1, 1, 0);
        Vector v2 = mat2.mulVec(v1);
        Vector v3 = new Vector(1, -1, 1);
        Matrix P = Vector.toMatrix(v1, v2, v3);
        Matrix P_inv = P.inverse();
        Matrix J = new Matrix(new double[][] {{2, 0, 0, 0}, {1, 2, 0, 0}, {0, 0, -2, 0}, {0, 0, 1, -2}});

        System.out.println(A.pow(8));
    }
}
