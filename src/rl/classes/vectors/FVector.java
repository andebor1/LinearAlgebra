package rl.classes.vectors;

import rl.classes.matrices.FMatrix;
import rl.classes.types.FieldElement;

import java.util.Arrays;

public class FVector {
    public final FieldElement[] vec;
    public final int len;

    public FVector(FieldElement... elements) {
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
    public static FieldElement dot(FieldElement[] v1, FieldElement[] v2) {
        int n = v1.length;

        FieldElement res = v1[0].mul(v2[0]);

        for (int i=1; i<n; i++) {
            res = res.add(v1[i].mul(v2[i]));
        }

        return res;
    }

    public FieldElement dot(FVector v2) {
        return dot(this.vec, v2.vec);
    }

    public FieldElement dot(FieldElement... v2) {
        return dot(this.vec, v2);
    }

    public static FMatrix toMatrix(FVector... vectors) {
        int n = vectors.length;
        if (n == 0) return new FMatrix(new FieldElement[][] {});

        int m = vectors[0].len;
        FieldElement[][] mat = new FieldElement[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                mat[i][j] = vectors[j].vec[i];
            }
        }

        return new FMatrix(mat);
    }

    public boolean isZero() {
        for (FieldElement val : vec) {
            if (!val.isZero()) {
                return false;
            }
        }

        return true;
    }
}