package rl.classes.testers;

import rl.classes.matrices.*;

public class Test1 {
    public static void main(String[] args) {
        Matrix mat1 = new Matrix(new double[][] {{1, 2, 3}, {1, 1, 5}, {0, 2, 1}});
        Matrix mat2 = new Matrix(new double[][] {{1.8, -0.8, -1.4}, {0.2, -0.2, 0.4}, {-0.4, 0.4, 0.2}});
        System.out.println(mat1.multiply(mat2));
    }
}
