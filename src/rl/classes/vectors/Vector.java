package rl.classes.vectors;

import rl.classes.matrices.Matrix;

import java.util.Arrays;

public class Vector {
    public final double[] vec;
    public final int len;

    public Vector(double... elements) {
        len = elements.length;
        this.vec = Arrays.copyOf(elements, len);
    }

    public String toString() {
        StringBuilder out = new StringBuilder();

        for (int i=0; i<len; i++) {
            out.append(this.vec[i]);
            out.append('\n');
        }

        return out.toString();
    }

    /**
     *
     * @pre v1.length == v2.length
     *
     * @post $ret == v1*v2 (dot product)
     */
    public static double dot(double[] v1, double[] v2) {
        int n = v1.length;

        double res = 0;

        for (int i=0; i<n; i++) {
            res += v1[i]*v2[i];
        }

        return res;
    }

    public double dot(Vector v2) {
        return dot(this.vec, v2.vec);
    }

    public double dot(double... v2) {
        return dot(this.vec, v2);
    }

    public static Matrix toMatrix(Vector... vectors) {
        int n = vectors.length;
        if (n == 0) return new Matrix(new double[][] {});

        int m = vectors[0].len;
        double[][] mat = new double[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                mat[i][j] = vectors[j].vec[i];
            }
        }

        return new Matrix(mat);
    }

    public boolean isZero() {
        for (double val : vec) {
            if (val != 0) {
                return false;
            }
        }

        return true;
    }
}
