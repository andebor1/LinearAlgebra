package rl.classes.types.fields;

import rl.classes.matrices.Matrix;
import rl.useable.RandomGenerator;

public class Real implements FieldElement {
    public static final double ERROR = 0.00000001;

    public static final Real zero = new Real(0);
    public static final Real unit = new Real(1);


    public Real unit() {
        return unit;
    }

    public Real zero() {
        return zero;
    }

    private final double value;

    public double getValue() {
        return value;
    }

    public Real(double value) {
        this.value = value;
    }

    private Real getRealValue(FieldElement other) {
        if (other instanceof Real a) {
            return a;
        }

        throw new IllegalArgumentException("Function must get an instance of Real class");
    }

    public boolean equals(FieldElement otherF) {
        Real other = getRealValue(otherF);
        return this.value == other.value;
    }


    public FieldElement add(FieldElement otherF) {
        Real other = getRealValue(otherF);
        return new Real(this.value + other.value);
    }


    public FieldElement sub(FieldElement otherF) {
        Real other = getRealValue(otherF);
        return new Real(this.value - other.value);
    }

    public FieldElement mul(FieldElement otherF) {
        Real other = getRealValue(otherF);
        return new Real(this.value * other.value);
    }

    public FieldElement inverse() {
        return new Real(1/this.value);
    }

    public FieldElement neg() {
        return new Real(-this.value);
    }

    public boolean isZero() {
        return Math.abs(this.value) <= ERROR;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public static Real[] convertList(double ... elements) {
        int n = elements.length;
        Real[] r = new Real[n];

        for (int i=0; i<n; i++) {
            r[i] = new Real(elements[i]);
        }

        return r;
    }

    public static Real[][] convertMatrix(double[][] mat) {
        int m = mat.length;
        int n = m == 0 ? 0 : mat[0].length;

        Real[][] matrix = new Real[m][n];
        for (int i=0; i<m; i++) {
            matrix[i] = Real.convertList(mat[i]);
        }

        return matrix;
    }

    public static Matrix randomMatrix(int m, int n, double minVal, double maxVal) {
        Real[][] matrix = new Real[m][n];

        RandomGenerator rand = new RandomGenerator();
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                matrix[i][j] = new Real(rand.getDouble(minVal, maxVal));
            }
        }

        return new Matrix(matrix);
    }

    public static Matrix randomMatrix(int m, int n, double bound) {
        return randomMatrix(m, n, 0, bound);
    }

    public static Matrix randomMatrix(int m, int n) {
        return randomMatrix(m, n, 1);
    }
}
