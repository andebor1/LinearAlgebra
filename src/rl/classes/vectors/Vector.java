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

    public static Vector e(int length, int index, FieldElement unit) {
        FieldElement[] vec = new FieldElement[length];
        for (int i=0; i<length; i++) {
            vec[i] = unit.zero();
        }

        vec[index] = unit;
        return new Vector(vec);
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

    public static Matrix toMatrixAsRows(Vector... vectors) {
        int m = vectors.length;
        if (m == 0) return new Matrix(new FieldElement[][] {});

        int n = vectors[0].len;
        FieldElement[][] mat = new FieldElement[m][n];
        for (int i=0; i<m; i++) {
            System.arraycopy(vectors[i].vec, 0, mat[i], 0, n);
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

    public static boolean dependent(Vector... vectors) {
        Matrix mat = Vector.toMatrix(vectors);
        return mat.getRank() < vectors.length;
    }

    public static Vector[] getIndependent(Vector... vectors) {
        Matrix mat = Vector.toMatrix(vectors);
        Matrix eliminated = mat.gaussElimination();
        Integer[] pivots = eliminated.getPivots();

        int r = eliminated.getRank();
        Vector[] independentVectors = new Vector[r];

        for (int i=0; i<r; i++) {
            independentVectors[i] = vectors[pivots[i]];
        }

        return independentVectors;
    }

    public Vector writeInBase(Vector... vectors) {
        Matrix mat = Vector.toMatrix(vectors);
        return mat.findSolution(this);
    }

    public Vector copyOf(int length) {
        return new Vector(Arrays.copyOf(this.vec, length));
    }

    public static Vector[] orthogonalForm(Vector... vectors) {
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

    public static Vector[] orthogonalSpaceBaseGramVersion(Vector... vectors) {
        int n = vectors.length;
        int m = vectors[0].len;
        Vector[] vectorsWithUnit = Arrays.copyOf(vectors, n + m);
        FieldElement unit = vectors[0].vec[0].unit();

        for (int i=0; i<m; i++) {
            vectorsWithUnit[n + i] = Vector.e(m, i, unit);
        }

        Vector[] completeBase = Vector.getIndependent(vectorsWithUnit);
        Vector[] orthogonalBase = Vector.orthogonalForm(completeBase);

        return Arrays.copyOfRange(orthogonalBase, n, orthogonalBase.length);
    }

    public static Vector[] orthogonalSpaceBase(Vector... vectors) {
        Matrix matrix = Vector.toMatrixAsRows(vectors);

        return matrix.nullSpaceBase();
    }

    public static String toString(Vector... vectors) {
        final String SEP = "    ";
        int n = vectors.length;

        if (n == 0) {
            return "()";
        }

        int m = vectors[0].len;
        String[][] stringValues = new String[m][n];
        int maxLength = 0;

        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                stringValues[i][j] = vectors[j].vec[i].toString();
                maxLength = Math.max(maxLength, stringValues[i][j].length());
            }
        }

        StringBuilder out = new StringBuilder();
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                out.append(String.format("%-" + maxLength + "s" + SEP, stringValues[i][j]));
            }
            out.append("\n");
        }

        return out.toString();
    }
}