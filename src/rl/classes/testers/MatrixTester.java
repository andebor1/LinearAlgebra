package rl.classes.testers;

import rl.classes.matrices.Matrix;
import rl.classes.vectors.Vector;
import rl.classes.types.fields.*;

public class MatrixTester {
    static final boolean PRINT = false;
    static boolean allWorked = true;
    static int testNum = 0;
    public static void main(String[] args) {
        testCases1();
        testCases2();
        testCasesRandom(5000, 3, 3, 0, 10);

        if (allWorked) {
            System.out.println("Passes all tests!! (" + testNum + ")");
        }
    }

    public static void testCases1() {
        // Test case 1
        Vector v1 = new Vector(Real.convertList(1, 2, 3));
        Vector v2 = new Vector(Real.convertList(2, -1, 4));
        Matrix matrix1 = Vector.toMatrix(v1, v2);
        testNullSpaceBase(matrix1);

        // Test case 2 (edge case with zero matrix)
        Matrix matrix2 = new Matrix(new double[][]{{0, 0}, {0, 0}});
        testNullSpaceBase(matrix2);

        // Test case 3 (edge case with full rank matrix)
        Matrix matrix3 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}});
        testNullSpaceBase(matrix3);

        // Test case 4 (general case with 3x3 matrix)
        Vector v3 = new Vector(Real.convertList(1, 0, -1));
        Matrix matrix4 = Vector.toMatrix(v1, v2, v3);
        testNullSpaceBase(matrix4);

        // Test case 5 (general case with 4x3 matrix)
        Vector v4 = new Vector(Real.convertList(1, 2, 3, -1));
        Matrix matrix5 = Vector.toMatrix(v1, v2, v3, v4);
        testNullSpaceBase(matrix5);

        // Test case 6 (general case with Rational numbers)
        Vector r1 = new Vector(new Rational(1, 2), new Rational(3, 4));
        Vector r2 = new Vector(new Rational(2, 3), new Rational(-1, 2));
        Matrix matrix6 = Vector.toMatrix(r1, r2);
        testNullSpaceBase(matrix6);

        // Test case 7 (general case with Complex numbers)
        Vector c1 = new Vector(new Complex(1, 2), new Complex(3, 4));
        Vector c2 = new Vector(new Complex(2, 3), new Complex(-1, 2));
        Matrix matrix7 = Vector.toMatrix(c1, c2);
        testNullSpaceBase(matrix7);
    }

    public static void testCases2() {
//         Test case 1 (2x2 matrix with real numbers)
        Vector v1 = new Vector(Real.convertList(1, 2));
        Vector v2 = new Vector(Real.convertList(3, 4));
        Matrix matrix1 = Vector.toMatrix(v1, v2);
        testNullSpaceBase(matrix1);

//         Test case 2 (3x3 matrix with real numbers)
        Vector v3 = new Vector(Real.convertList(1, 2, 3));
        Vector v4 = new Vector(Real.convertList(4, 5, 6));
        Vector v5 = new Vector(Real.convertList(7, 8, 9));
        Matrix matrix2 = Vector.toMatrix(v3, v4, v5);
        testNullSpaceBase(matrix2);

//         Test case 3 (2x2 matrix with complex numbers)
        Vector c1 = new Vector(new Complex(1, 2), new Complex(3, 4));
        Vector c2 = new Vector(new Complex(2, 3), new Complex(-1, 2));
        Matrix matrix3 = Vector.toMatrix(c1, c2);
        testNullSpaceBase(matrix3);

//         Test case 4 (3x3 matrix with complex numbers)
        Vector c3 = new Vector(new Complex(1, 2), new Complex(3, 4), new Complex(5, 6));
        Vector c4 = new Vector(new Complex(2, 3), new Complex(-1, 2), new Complex(4, 5));
        Vector c5 = new Vector(new Complex(3, -1), new Complex(6, -2), new Complex(9, -3));
        Matrix matrix4 = Vector.toMatrix(c3, c4, c5);
        testNullSpaceBase(matrix4);

//         Test case 5 (1x1 matrix with real number)
        Vector v6 = new Vector(Real.convertList(1));
        Matrix matrix5 = Vector.toMatrix(v6);
        testNullSpaceBase(matrix5);

//         Test case 6 (Empty matrix)
        Matrix matrix6 = new Matrix(0, 0);
        testNullSpaceBase(matrix6);

//         Test case 7 (2x2 matrix with zero elements)
        Vector v7 = new Vector(Real.convertList(0, 0));
        Vector v8 = new Vector(Real.convertList(0, 0));
        Matrix matrix7 = Vector.toMatrix(v7, v8);
        testNullSpaceBase(matrix7);

//         Test case 8 (3x3 matrix with zero elements)
        Vector v9 = new Vector(Real.convertList(0, 0, 0));
        Vector v10 = new Vector(Real.convertList(0, 0, 0));
        Vector v11 = new Vector(Real.convertList(0, 0, 0));
        Matrix matrix8 = Vector.toMatrix(v9, v10, v11);
        testNullSpaceBase(matrix8);

//         Test case 9 (1x1 matrix with complex number)
        Vector c6 = new Vector(new Complex(2, -1));
        Matrix matrix9 = Vector.toMatrix(c6);
        testNullSpaceBase(matrix9);

//         Test case 10 (3x3 matrix with large real numbers)
        Vector v12 = new Vector(Real.convertList(1000000, 2000000, 3000000));
        Vector v13 = new Vector(Real.convertList(4000000, 5000000, 6000000));
        Vector v14 = new Vector(Real.convertList(7000000, 8000000, 9000000));
        Matrix matrix10 = Vector.toMatrix(v12, v13, v14);
        testNullSpaceBase(matrix10);

//         Test case 11 (2x2 matrix with rational numbers)
        Vector r1 = new Vector(new Rational(1, 2), new Rational(3, 4));
        Vector r2 = new Vector(new Rational(2, 3), new Rational(-1, 2));
        Matrix matrix11 = Vector.toMatrix(r1, r2);
        testNullSpaceBase(matrix11);

//         Test case 12 (3x3 matrix with rational numbers)
        Vector r3 = new Vector(new Rational(1, 2), new Rational(3, 4), new Rational(5, 6));
        Vector r4 = new Vector(new Rational(2, 3), new Rational(-1, 2), new Rational(4, 5));
        Vector r5 = new Vector(new Rational(3, -1), new Rational(6, -2), new Rational(9, -3));
        Matrix matrix12 = Vector.toMatrix(r3, r4, r5);
        testNullSpaceBase(matrix12);

//         Test case 13 (2x2 matrix with negative real numbers)
        Vector v15 = new Vector(Real.convertList(-1, -2));
        Vector v16 = new Vector(Real.convertList(-3, -4));
        Matrix matrix13 = Vector.toMatrix(v15, v16);
        testNullSpaceBase(matrix13);

//         Test case 14 (3x3 matrix with negative real numbers)
        Vector v17 = new Vector(Real.convertList(-1, -2, -3));
        Vector v18 = new Vector(Real.convertList(-4, -5, -6));
        Vector v19 = new Vector(Real.convertList(-7, -8, -9));
        Matrix matrix14 = Vector.toMatrix(v17, v18, v19);
        testNullSpaceBase(matrix14);

//         Test case 15 (2x2 matrix with large negative real numbers)
        Vector v20 = new Vector(Real.convertList(-1000000, -2000000));
        Vector v21 = new Vector(Real.convertList(-3000000, -4000000));
        Matrix matrix15 = Vector.toMatrix(v20, v21);
        testNullSpaceBase(matrix15);

//         Test case 16 (3x3 matrix with large negative real numbers)
        Vector v22 = new Vector(Real.convertList(-1000000, -2000000, -3000000));
        Vector v23 = new Vector(Real.convertList(-4000000, -5000000, -6000000));
        Vector v24 = new Vector(Real.convertList(-7000000, -8000000, -9000000));
        Matrix matrix16 = Vector.toMatrix(v22, v23, v24);
        testNullSpaceBase(matrix16);

//         Test case 17 (2x2 matrix with positive and negative real numbers)
        Vector v25 = new Vector(Real.convertList(1, -2));
        Vector v26 = new Vector(Real.convertList(-3, 4));
        Matrix matrix17 = Vector.toMatrix(v25, v26);
        testNullSpaceBase(matrix17);

//         Test case 18 (3x3 matrix with positive and negative real numbers)
        Vector v27 = new Vector(Real.convertList(1, -2, 3));
        Vector v28 = new Vector(Real.convertList(-4, 5, -6));
        Vector v29 = new Vector(Real.convertList(7, -8, 9));
        Matrix matrix18 = Vector.toMatrix(v27, v28, v29);
        testNullSpaceBase(matrix18);

//         Test case 19 (2x2 matrix with positive and negative complex numbers)
        Vector c7 = new Vector(new Complex(1, -2), new Complex(-3, 4));
        Vector c8 = new Vector(new Complex(-2, 3), new Complex(1, -2));
        Matrix matrix19 = Vector.toMatrix(c7, c8);
        testNullSpaceBase(matrix19);

//         Test case 20 (3x3 matrix with positive and negative complex numbers)
        Vector c9 = new Vector(new Complex(1, -2), new Complex(-3, 4), new Complex(5, -6));
        Vector c10 = new Vector(new Complex(-2, 3), new Complex(1, -2), new Complex(-4, 5));
        Vector c11 = new Vector(new Complex(3, -1), new Complex(-6, 2), new Complex(9, -3));
        Matrix matrix20 = Vector.toMatrix(c9, c10, c11);
        testNullSpaceBase(matrix20);

//         Test case 21 (2x2 matrix with rational numbers)
        Vector r6 = new Vector(new Rational(1, 2), new Rational(3, -4));
        Vector r7 = new Vector(new Rational(2, 3), new Rational(-1, 2));
        Matrix matrix21 = Vector.toMatrix(r6, r7);
        testNullSpaceBase(matrix21);

//         Test case 22 (3x3 matrix with rational numbers)
        Vector r8 = new Vector(new Rational(1, 2), new Rational(-3, 4), new Rational(5, -6));
        Vector r9 = new Vector(new Rational(-2, 3), new Rational(1, -2), new Rational(-4, 5));
        Vector r10 = new Vector(new Rational(3, -1), new Rational(-6, 2), new Rational(9, -3));
        Matrix matrix22 = Vector.toMatrix(r8, r9, r10);
        testNullSpaceBase(matrix22);
    }

    public static void testCasesRandom(int amount, int m, int n, int origin, int bound) {
        Matrix mat;
        for (int i=0; i<amount; i++) {
            mat = Rational.randomIntMatrix(m, n, origin, bound);
            testNullSpaceBase(mat);
        }
    }

    private static void testNullSpaceBase(Matrix matrix) {
        if (PRINT) {
            System.out.println("Matrix:");
            matrix.print();
        }

        Vector[] nullSpaceBase = matrix.nullSpaceBase();
        int nullSpaceDim = matrix.columns - matrix.getRank();

        if (PRINT) {
            System.out.println("Null space basis (dimension: " + nullSpaceDim + "):");
            System.out.println(Vector.toString(nullSpaceBase));
        }

        if (PRINT)
            System.out.println("Verification:");

        boolean verified = true;
        for (Vector vector : nullSpaceBase) {
            Vector result = matrix.mulVec(vector);
            if (!result.isZero()) {
                System.out.println("Multiplying the matrix by the null space vector does not yield zero vector.");

                matrix.print();
                System.out.println("Null space basis (dimension: " + nullSpaceDim + "):");
                System.out.println(Vector.toString(nullSpaceBase));
                verified = false;
                allWorked = false;
                break;
            }
        }

        if (verified && nullSpaceBase.length == nullSpaceDim) {
            if (Vector.dependent(nullSpaceBase)) {
                allWorked = false;
                System.out.println("Base vectors are linearly dependent!");
                matrix.print();
                return;
            }
            testNum++;
            if (PRINT)
                System.out.println("Test " + testNum + " is successful!");
        }
        else {
            allWorked = false;
            System.out.println("Not the right dimension!");
            matrix.print();
        }
    }
}
