package rl.classes.types;

import rl.classes.matrices.GMatrix;
import rl.classes.matrices.Matrix;

import java.util.Arrays;

public class GVector<T extends Field> {
    private Field[] vec;
    public final int len;

    public GVector(Field... elements) {
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
    public static <S extends Field> Field dot(Field[] v1, Field[] v2) {
        int n = v1.length;

        Field res = S.zero;

        for (int i=0; i<n; i++) {
            res = res.add(v1[i].mul(v2[i]));
        }

        return res;
    }

    public Field dot(GVector<T> v2) {
        return dot(this.vec, v2.vec);
    }

    public Field dot(Field... v2) {
        return dot(this.vec, v2);
    }

    public static <S extends Field> GMatrix<S> toMatrix(GVector<S>... vectors) {
        int n = vectors.length;
        if (n == 0) return new GMatrix<S>(new Field[][] {});

        int m = vectors[0].len;
        Field[][] mat = new Field[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                mat[i][j] = vectors[j].vec[i];
            }
        }

        return new GMatrix<S>(mat);
    }
}
