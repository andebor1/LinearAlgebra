package rl.classes.matrices;

import rl.classes.types.Vector;

import java.util.Arrays;
import java.util.HashSet;

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

    public static Matrix unit(int n) {
        double[][] newMat = new double[n][n];

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (i==j) {
                    newMat[i][j] = 1;
                } else {
                    newMat[i][j] = 0;
                }
            }
        }

        return new Matrix(newMat);
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
            out.append(getRow(i));
            out.append("\n");
        }

        return out.toString();
    }

    public double[][] getMat() {
        double[][] res = new double[rows][];

        for (int i=0; i<rows; i++) {
            res[i] = getRow(i+1).vec;
        }

        return res;
    }


    public Matrix copy() {
        return new Matrix(this.getMat());
    }

    public boolean equals(Matrix B) {
        if (!Arrays.equals(this.size(), B.size())) {
            return false;
        }

        for (int i=0; i<this.rows; i++) {
            for (int j=0; j<this.columns; j++) {
                if (this.mat[i][j] != B.mat[i][j]) {
                    return false;
                }
            }
        }

        return true;
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
     * @pre 0 < i <= this.rows
     *
     * @post $ret.equals(this.mat[i-1])
     */
    public Vector getRow(int i) {
        double[] row = new double[this.columns];

        for (int j=0; j<this.columns; j++) {
            row[j] = this.mat[i - 1][j];
        }

        return new Vector(row);
    }

    /**
     *
     * @pre 0 < j <= this.columns
     *
     * @post $ret[i] == this.mat[i][j - 1] for all 0<=i<this.rows
     */
    public Vector getCol(int j) {
        double[] col = new double[this.rows];

        for (int i=0; i<this.rows; i++) {
            col[i] = this.mat[i][j - 1];
        }

        return new Vector(col);
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
                newMat[i][j] = B.getCol(j + 1).dot(this.mat[i]);
            }
        }

        return new Matrix(newMat);
    }

    /**
     *
     * @pre this.rows == this.columns
     *
     * @post $ret == this to the power of k
     */
    public Matrix pow(int k) {
        Matrix newMatrix = Matrix.unit(this.rows);

        for (int i=0; i<k; i++) {
            newMatrix = newMatrix.multiply(this);
        }

        return newMatrix;
    }

    public Vector mulVec(Vector v) {
        double[] newVec = new double[this.rows];

        for (int i=0; i<rows;i++) {
            newVec[i] = v.dot(this.mat[i]);
        }

        return new Vector(newVec);
    }

    public Vector mulVec(double... vec) {
        Vector v = new Vector(vec);
        return this.mulVec(v);
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
        this.selfGaussElimination(endCol, approx, false);
    }

    private double selfGaussElimination(int endCol, boolean approx, boolean calcDet) {
        int currRow = 0;
        double det = 1;
        for (int j=0; j<endCol; j++) {
            int row = nonZeroIndexInColumn(currRow, j, approx);
            if (row == -1) continue;

            this._P(currRow, row);

            if (calcDet) {
                det *= row == currRow ? 1 : -1;
                det *= this.mat[currRow][j];
            }

            this._PM(currRow, 1/this.mat[currRow][j]);

            for (int i=0; i<rows; i++) {
                if (i == currRow) continue;

                double c = - this.mat[i][j];
                this._PA(currRow, i, c);
            }
            currRow++;
        }

        return det;
    }

    public Matrix gaussElimination() {
        return gaussElimination(this.columns + 1);
    }

    public Matrix gaussElimination(int endCol) {
        Matrix newMatrix = this.copy();
        newMatrix.selfGaussElimination(endCol - 1);
        return newMatrix;
    }

    public double det() {
        Matrix copy = this.copy();
        return copy.selfGaussElimination(columns, true, true);
    }

    public Vector[] getVectors() {
        Vector[] vectors = new Vector[this.columns];

        for (int i=0; i<this.columns; i++) {
            vectors[i] = this.getCol(i + 1);
        }

        return vectors;
    }

    /**
     *
     * @pre this.rows == this.columns
     *
     * @post this.det == 0 @implies $ret == this
     * @post this.det != 0 @implies this.multiply($ret).equals(Matrix.unit(this.rows))
     */
    public Matrix inverse() {
        Vector[] thisVectors = this.getVectors();
        Vector[] unitVectors = Matrix.unit(this.rows).getVectors();

        Vector[] vectors = new Vector[this.rows*2];

        for (int i=0; i<2*rows; i++) {
            if (i<rows)
                vectors[i] = thisVectors[i];
            else
                vectors[i] = unitVectors[i - rows];
        }

        Matrix toEliminate = Vector.toMatrix(vectors);

        if (toEliminate.selfGaussElimination(rows, false, true) == 0)
            return this;

        Vector[] newVectors = toEliminate.getVectors();
        Vector[] inverseVectors = Arrays.copyOfRange(newVectors, rows, 2*rows);

        return Vector.toMatrix(inverseVectors);
    }

    public Matrix minor(int k, int l) {
        double[][] newMat = new double[rows - 1][columns - 1];

        for (int i=0; i<rows - 1; i++) {
            for (int j=0; j<columns - 1; j++) {
                int row = i<k ? i: i+1;
                int col = j<l ? j: j+1;

                newMat[row][col] = this.mat[row][col];
            }
        }

        return new Matrix(newMat);
    }

    public int getRank() {
        Matrix eliminated = this.gaussElimination();

        int rank = 0;
        for (int i=0; i<rows; i++, rank++) {
            if (eliminated.getRow(i + 1).isZero()) {
                break;
            }
        }

        return rank;
    }

    public Vector[] nullSpaceBase() {
        int n = rows;
        Matrix eliminated = this.gaussElimination();

        int r = eliminated.getRank();
        HashSet<Integer> pivotIndexes = new HashSet<>();

        for (int i=0; i<r; i++) {
            double[] row = eliminated.getRow(i + 1).vec;
            for (int j=0; j<n; j++) {
                if (row[j] == 1) {
                    pivotIndexes.add(j);
                }
            }
        }

        Vector[] base = new Vector[n - r];
        int curr=0;
        for (int i=0; i<n-r; i++, curr++) {
            while (pivotIndexes.contains(curr)) {
                curr++;
            }

            double[] baseVector = new double[n];
            baseVector[curr] = 1;
            for (int j=0; j<curr; j++) {
                if (pivotIndexes.contains(j)) {
                    baseVector[j] = -this.mat[j][curr];
                } else {
                    baseVector[j] = 0;
                }
            }

            base[i] = new Vector(baseVector);
        }

        return base;
    }
}
