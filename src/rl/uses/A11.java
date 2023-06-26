package rl.uses;

import rl.classes.matrices.Matrix;
import rl.classes.types.fields.Rational;
import rl.classes.vectors.Vector;

import java.util.Arrays;

public class A11 {
    public static void main(String[] args) {
        E06();
    }
    
    public static void E06() {
        Matrix mat = Matrix.newMatrix(3, Rational.convertList(1, 0, 2, 2, -1, 3, 4, 1, 8));
        Vector[] vectors = mat.getVectors();

        Vector[] newBase = Vector.orthogonalForm(vectors);
        System.out.println("newBase[0].normSquare() = " + newBase[0].normSquare());
        System.out.println("newBase[1].normSquare() = " + newBase[1].normSquare());
        System.out.println("newBase[2].normSquare() = " + newBase[2].normSquare());
        System.out.println(vectors[0].writeInBase(newBase));
        System.out.println(vectors[1].writeInBase(newBase));
        System.out.println(vectors[2].writeInBase(newBase));

        Matrix Q = Matrix.newMatrix(3, 1/Math.sqrt(21), -2/Math.sqrt(798), 6/Math.sqrt(38),
                2/Math.sqrt(21), -25/Math.sqrt(798), -1/Math.sqrt(38),
                4/Math.sqrt(21), 13/Math.sqrt(798), -1/Math.sqrt(38));
        Matrix R = Matrix.newMatrix(3, Math.sqrt(21), 2/Math.sqrt(21), 40/Math.sqrt(21),
                0, Math.sqrt(38.0 /21), 25/Math.sqrt(798),
                0, 0, 1/Math.sqrt(38));

        System.out.println(Q.multiply(R));
    }
}
