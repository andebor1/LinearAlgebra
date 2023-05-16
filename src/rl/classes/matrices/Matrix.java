package rl.classes.matrices;

import java.util.Arrays;

public class Matrix {

    private double[][] mat;
    public final int rows;
    public final int columns;

    public Matrix(double[][] mat) {
        this.mat = mat;
        this.rows = mat.length;

        if (this.rows > 0)
            this.columns = mat[0].length;
        else
            this.columns = 0;
    }

    /**
     *
     * @post $ret.length == 2 &&
     *                          $ret[0] == rows
     *                          $ret[1] == columns
     */
    public int[] size() {
        return new int[] {this.rows, this.columns};
    }

    public String toString() {
        StringBuilder out = new StringBuilder();

        for (int i=1; i<=rows; i++) {
            out.append(Arrays.toString(getRow(i)));
            out.append("\n");
        }

        return out.toString();
    }

    public double[][] getMat() {
        double[][] res = new double[rows][];

        for (int i=0; i<rows; i++) {
            res[i] = getRow(i+1);
        }

        return res;
    }

    /**
     *
     * @pre this.size().equals(B.size())
     *
     * @post $ret.mat[i][j] == this.mat[i][j] + B.mat[i][j] for all i, j
     */
    public Matrix add(Matrix B) {
        double[][] newMat = new double[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                newMat[i][j] = mat[i][j] + B.mat[i][j];
            }
        }

        return new Matrix(newMat);
    }

    /**
     *
     * @pre this.size().equals(B.size())
     *
     * @post $ret.mat[i][j] == this.mat[i][j] - B.mat[i][j] for all i, j
     */
    public Matrix sub(Matrix B) {
        double[][] newMat = new double[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                newMat[i][j] = mat[i][j] - B.mat[i][j];
            }
        }

        return new Matrix(newMat);
    }

    /**
     *
     * @post $ret.mat[i][j] == c*this.mat[i][j] for all i,j
     */
    public Matrix scalarMul(double c) {
        double[][] newMat = new double[rows][columns];

        for (int i = 0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                newMat[i][j] = c*mat[i][j];
            }
        }

        return new Matrix(newMat);
    }

    /**
     *
     * @pre v1.length == v2.length
     *
     * @post $ret == v1*v2 (dot product)
     */
    public static double mulVectors(double[] v1, double[] v2) {
        int n = v1.length;

        double res = 0;

        for (int i=0; i<n; i++) {
            res += v1[i]*v2[i];
        }

        return res;
    }

    /**
     *
     * @pre 0 < i <= this.rows
     *
     * @post $ret.equals(this.mat[i-1])
     */
    public double[] getRow(int i) {
        double[] row = new double[this.columns];

        for (int j=0; j<this.columns; j++) {
            row[j] = this.mat[i - 1][j];
        }

        return row;
    }

    /**
     *
     * @pre 0 < j <= this.columns
     *
     * @post $ret[i] == this.mat[i][j - 1] for all 0<=i<this.rows
     */
    public double[] getCol(int j) {
        double[] col = new double[this.columns];

        for (int i=0; i<this.columns; i++) {
            col[i] = this.mat[i][j - 1];
        }

        return col;
    }

    /**
     *
     * @pre this.columns == B.rows
     *
     * @post $ret = this.mat * B.mat (matrix multiplication)
     */
    public Matrix multiply(Matrix B) {
        double[][] newMat = new double[this.rows][B.columns];

        for (int i=0; i<this.rows; i++) {
            for (int j=0; j<B.columns; j++) {
                newMat[i][j] = mulVectors(this.mat[i], B.getCol(j + 1));
            }
        }

        return new Matrix(newMat);
    }

    /**
     *
     * @post $ret.mat[j][i] = this.mat[i][j] for all i, j
     */
    public Matrix transpose() {
        double[][] newMat = new double[columns][rows];

        for (int j=0; j<columns; j++) {
            for (int i=0; i<rows; i++) {
                newMat[j][i] = mat[i][j];
            }
        }

        return new Matrix(newMat);
    }

    /**
     *
     * @pre 0 < k, l <= this.rows
     *
     * replaces rows l-1, k-1
     */
    public Matrix P(int k, int l) {
        double[][] newMat = new double[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                if (i == k-1) {
                    newMat[i][j] = mat[l-1][j];
                } else if (i == l-1) {
                    newMat[i][j] = mat[k-1][j];
                } else {
                    newMat[i][j] = mat[i][j];
                }
            }
        }

        return new Matrix(newMat);
    }

    /**
     *
     * @pre 0 < k <= this.rows && a != 0
     *
     * @post for all j for all 0<=i<this.rows: i == k @implies $ret.mat[i][j] == a*this.mat[i][j]
     *                                          else: $ret.mat[i][j] == this.mat[i][j]
     */
    public Matrix PM(int k, double a) {
        double[][] newMat = new double[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                if (i == k-1) {
                    newMat[i][j] = a*mat[i][j];
                } else {
                    newMat[i][j] = mat[i][j];
                }
            }
        }

        return new Matrix(newMat);
    }

    /**
     * @pre 0 < k, l <= this.rows && k != l
     *
     * @post for all j for all 0<=i<this.rows: i == k-1 @implies $ret.mat[i][j] == this.mat[i][j] + a*this.mat[l-1][j]
     *                                          else: $ret.mat[i][j] == a*this.mat[i][j]
     */
    public Matrix PA(int l, int k, double a) {
        double[][] newMat = new double[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                if (i == k-1) {
                    newMat[i][j] = mat[i][j] + a*mat[l-1][j];
                } else {
                    newMat[i][j] = mat[i][j];
                }
            }
        }

        return new Matrix(newMat);
    }

    /**
     * @pre 0 <= k, l < this.rows
     *
     * replaces rows l, k
     */
    private void _P(int k, int l) {
        double[] tmp = this.mat[k];
        this.mat[k] = this.mat[l];
        this.mat[l] = tmp;
    }

    /**
     *
     * @pre 0 <= k < this.rows && a != 0
     *
     * @post for all j for all 0<=i<this.rows: i == k @implies this.mat[i][j] == a*($post)this.mat[i][j]
     *                                          else: this.mat[i][j] == ($post)this.mat[i][j]
     */
    private void _PM(int k, double a) {
        for (int j=0; j<columns; j++) {
            this.mat[k][j] = a*this.mat[k][j];
        }
    }

    /**
     *
     * @pre 0 <= k, l < this.rows && k != l
     *
     * @post for all j for all 0<=i<this.rows: i == k @implies $ret.mat[i][j] == this.mat[i][j] + a*this.mat[l][j]
     *                                          else: $ret.mat[i][j] == a*this.mat[i][j]
     */
    private void _PA(int l, int k, double a) {
        for (int j=0; j<columns; j++) {
            this.mat[k][j] = this.mat[k][j] + a*this.mat[l][j];
        }
    }

    /**
     *
     * @post $ret == -1 @implies for all i, this.mat[j][i] == 0
     * @post $ret < 0 @implies this.mat[$ret][j] != 0
     */
    private int nonZeroIndexInColumn(int from, int j) {
        return nonZeroIndexInColumn(from, j, false);
    }

    /**
     *
     * @post $ret == -1 @implies for all i, Math.abs(this.mat[j][i]) < e
     * @post $ret < 0 @implies Math.abs(this.mat[$ret][j]) > e
     */
    private int nonZeroIndexInColumn(int from, int j, boolean approx) {
        double e = 0.000001;

        for (int i=from; i<rows; i++) {
            if (Math.abs(this.mat[i][j]) > e) {
                return i;
            }
        }

        return -1;
    }

    private void selfGaussElimination() {
        this.selfGaussElimination(this.columns, false);
    }

    private void selfGaussElimination(int endCol) {
        this.selfGaussElimination(endCol, false);
    }

    private void selfGaussElimination(int endCol, boolean approx) {
        int currRow = 0;
        for (int j=0; j<endCol; j++) {
            int row = nonZeroIndexInColumn(currRow, j, approx);
            if (row == -1) continue;

            this._P(currRow, row);
            this._PM(currRow, 1/this.mat[currRow][j]);

            for (int i=0; i<rows; i++) {
                if (i == currRow) continue;

                double c = - this.mat[i][j];
                this._PA(currRow, i, c);
            }

            currRow++;
        }
    }

    public Matrix gaussElimination() {
        return gaussElimination(this.columns);
    }

    public Matrix gaussElimination(int endCol) {
        Matrix newMatrix = new Matrix(this.getMat());
        newMatrix.selfGaussElimination(endCol - 1);
        return newMatrix;
    }
}
