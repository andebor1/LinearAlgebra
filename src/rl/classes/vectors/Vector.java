package rl.classes.vectors;

import rl.classes.matrices.Matrix;
import rl.classes.types.fields.FieldElement;

import java.util.Arrays;

public class Vector {
    public final FieldElement[] vec;
    public final int len;

    public Vector(FieldElement... elements) {
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

    public Vector add(Vector other) {
        if (this.len != other.len) {
            throw new IllegalArgumentException("Can't add vectors with different lengths");
        }

        FieldElement[] newVec = new FieldElement[len];
        for (int i=0; i<len; i++) {
            newVec[i] = this.vec[i].add(other.vec[i]);
        }

        return new Vector(newVec);
    }

    public Vector sub(Vector other) {
        if (this.len != other.len) {
            throw new IllegalArgumentException("Can't add vectors with different lengths");
        }

        FieldElement[] newVec = new FieldElement[len];
        for (int i=0; i<len; i++) {
            newVec[i] = this.vec[i].sub(other.vec[i]);
        }

        return new Vector(newVec);
    }

    /**
     *
     * @pre v1.length == v2.length
     *
     * @post $ret == v1*v2 (dot product)
     */
    public static FieldElement dot(FieldElement[] v1, FieldElement[] v2) {
        int n = v1.length;

        FieldElement res = v1[0].mul(v2[0]);

        for (int i=1; i<n; i++) {
            res = res.add(v1[i].mul(v2[i]));
        }

        return res;
    }

    public FieldElement dot(Vector v2) {
        return dot(this.vec, v2.vec);
    }

    public FieldElement dot(FieldElement... v2) {
        return dot(this.vec, v2);
    }

    public FieldElement normSquare() {
        return this.dot(this);
    }

    public static Matrix toMatrix(Vector... vectors) {
        int n = vectors.length;
        if (n == 0) return new Matrix(new FieldElement[][] {});

        int m = vectors[0].len;
        FieldElement[][] mat = new FieldElement[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                mat[i][j] = vectors[j].vec[i];
            }
        }

        return new Matrix(mat);
    }

    public Vector mul(FieldElement c) {
        int n = vec.length;
        FieldElement[] newVec = new FieldElement[n];

        for (int i=0; i<n; i++) {
            newVec[i] = vec[i].mul(c);
        }

        return new Vector(newVec);
    }

    public boolean equals(Vector other) {
        return Arrays.equals(this.vec, other.vec);
    }

    public boolean isZero() {
        for (FieldElement val : vec) {
            if (!val.isZero()) {
                return false;
            }
        }

        return true;
    }

    public Vector neg() {
        FieldElement[] newVec = this.vec.clone();
        for (int i=0; i< newVec.length; i++) {
            newVec[i] = this.vec[i].neg();
        }

        return new Vector(newVec);
    }

    public boolean dependent(Vector... vectors) {
        Matrix mat = Vector.toMatrix(vectors).addVector(this);
        return mat.getRank() < vectors.length + 1;
    }

    public Vector writeInBase(Vector... vectors) {
        Matrix mat = Vector.toMatrix(vectors);
        return mat.findSolution(this);
    }

    public Vector copyOf(int length) {
        return new Vector(Arrays.copyOf(this.vec, length));
    }

    public static Vector[] orthogonalBase(Vector... vectors) {
        int n = vectors.length;
        Vector[] newBase = new Vector[n];
        FieldElement[] normsSquare = new FieldElement[n];

        for (int k=0; k<n; k++) {
            newBase[k] = vectors[k];

            for (int i=0; i<k; i++) {
                FieldElement c = vectors[k].dot(newBase[i]).divide(normsSquare[i]);
                newBase[k] = newBase[k].sub(newBase[i].mul(c));
            }

            normsSquare[k] = newBase[k].normSquare();
        }

        return newBase;
    }
}