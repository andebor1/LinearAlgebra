package rl.classes.types;

import rl.classes.matrices.GMatrix;
import rl.classes.matrices.Matrix;

import java.util.Arrays;

public class GVector<T extends Field2> {
    private final T[] vec;
    public final int len;

    @SafeVarargs
    public GVector(T... elements) {
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
    public static <S extends Field2<S>> S dot(S[] v1, S[] v2) {
        int n = v1.length;

        S res = v1[0].zero();

        for (int i=0; i<n; i++) {
            res = res.add(v1[i].mul(v2[i]));
        }

        return res;
    }

    public T dot(GVector<T> v2) {
        return GVector.<T>dot(this.vec, v2.vec);
    }

    public T dot(T... v2) {
        return GVector.<T>dot(this.vec, v2);
    }

    @SafeVarargs
    public static <S extends Field2<S>> S toMatrix(GVector<S>... vectors) {
        int n = vectors.length;
        if (n == 0) return new GMatrix<S>(new S[][] {});

        int m = vectors[0].len;
        S[][] mat = new S[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                mat[i][j] = vectors[j].vec[i];
            }
        }

        return new GMatrix<S>(mat);
    }
}
