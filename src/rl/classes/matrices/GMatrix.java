package rl.classes.matrices;

import rl.classes.types.*;

import java.lang.reflect.Array;
import java.util.Arrays;

public class GMatrix<T extends Field<T>> {
    private T[][] mat;
    public final int rows;
    public final int columns;

    public GMatrix(T[][] mat) {
        this.mat = mat;
        this.rows = mat.length;

        if (this.rows > 0)
            this.columns = mat[0].length;
        else
            this.columns = 0;
    }

    public static GMatrix<Real> getMatrix(double[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        Real[][] newMat = new Real[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                newMat[i][j] = new Real(mat[i][j]);
            }
        }

        return new GMatrix<Real>(newMat);
    }

    public static <S extends Field> GMatrix unit(int n) {
        System.out.println();
        Field[][] newMat = new Field[n][n];

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (i==j) {
                    newMat[i][j] = S.unit;
                    System.out.println(newMat[i][j] + "" + S.unit);
                } else {
                    newMat[i][j] = S.zero;
                }
            }
        }

        return new GMatrix<S>(newMat);
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

    public Field[][] getMat() {
        Field[][] res = new Field[rows][];

        for (int i=0; i<rows; i++) {
            res[i] = getRow(i+1);
        }

        return res;
    }


    public GMatrix<T> copy() {
        return new GMatrix<T>(this.getMat());
    }

    public boolean equals(GMatrix<T> B) {
        if (!Arrays.equals(this.size(), B.size())) {
            return false;
        }

        for (int i=0; i<this.rows; i++) {
            for (int j=0; j<this.columns; j++) {
                if (this.mat[i][j].equals(B.mat[i][j])) {
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
    public GMatrix<T> add(GMatrix<T> B) {
        Field[][] newMat = new Field[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                newMat[i][j] = mat[i][j].add(B.mat[i][j]);
            }
        }

        return new GMatrix<T>(newMat);
    }

    /**
     *
     * @pre this.size().equals(B.size())
     *
     * @post $ret.mat[i][j] == this.mat[i][j] - B.mat[i][j] for all i, j
     */
    public GMatrix<T> sub(GMatrix<T> B) {
        Field[][] newMat = new Field[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                newMat[i][j] = mat[i][j].sub(B.mat[i][j]);
            }
        }

        return new GMatrix<T>(newMat);
    }

    /**
     *
     * @post $ret.mat[i][j] == c*this.mat[i][j] for all i,j
     */
    public GMatrix<T> scalarMul(T c) {
        Field[][] newMat = new Field[rows][columns];

        for (int i = 0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                newMat[i][j] = c.mul(mat[i][j]);
            }
        }

        return new GMatrix<T>(newMat);
    }

    /**
     *
     * @pre 0 < i <= this.rows
     *
     * @post $ret.equals(this.mat[i-1])
     */
    public Field[] getRow(int i) {
        Field[] row = new Field[this.columns];

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
    public Field[] getCol(int j) {
        Field[] col = new Field[this.rows];

        for (int i=0; i<this.rows; i++) {
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
    public GMatrix<T> multiply(GMatrix<T> B) {
        Field[][] newMat = new Field[this.rows][B.columns];

        for (int i=0; i<this.rows; i++) {
            for (int j=0; j<B.columns; j++) {
                newMat[i][j] = GVector.<T>dot(this.mat[i], B.getCol(j + 1));
            }
        }

        return new GMatrix<T>(newMat);
    }

    public GVector<T> mulVec(GVector<T> v) {
        Field[] newVec = new Field[this.rows];

        for (int i=0; i<rows;i++) {
            newVec[i] = v.dot(this.mat[i]);
        }

        return new GVector<T>(newVec);
    }

    public GVector<T> mulVec(Field... vec) {
        GVector<T> v = new GVector<T>(vec);
        return this.mulVec(v);
    }

    /**
     *
     * @post $ret.mat[j][i] = this.mat[i][j] for all i, j
     */
    public GMatrix<T> transpose() {
        Field[][] newMat = new Field[columns][rows];

        for (int j=0; j<columns; j++) {
            for (int i=0; i<rows; i++) {
                newMat[j][i] = mat[i][j];
            }
        }

        return new GMatrix<T>(newMat);
    }

    /**
     *
     * @pre 0 < k, l <= this.rows
     *
     * replaces rows l-1, k-1
     */
    public GMatrix<T> P(int k, int l) {
        Field[][] newMat = new Field[rows][columns];

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

        return new GMatrix<T>(newMat);
    }

    /**
     *
     * @pre 0 < k <= this.rows && a != 0
     *
     * @post for all j for all 0<=i<this.rows: i == k @implies $ret.mat[i][j] == a*this.mat[i][j]
     *                                          else: $ret.mat[i][j] == this.mat[i][j]
     */
    public GMatrix<T> PM(int k, T a) {
        Field[][] newMat = new Field[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                if (i == k-1) {
                    newMat[i][j] = a.mul(mat[i][j]);
                } else {
                    newMat[i][j] = mat[i][j];
                }
            }
        }

        return new GMatrix<T>(newMat);
    }

    /**
     * @pre 0 < k, l <= this.rows && k != l
     *
     * @post for all j for all 0<=i<this.rows: i == k-1 @implies $ret.mat[i][j] == this.mat[i][j] + a*this.mat[l-1][j]
     *                                          else: $ret.mat[i][j] == a*this.mat[i][j]
     */
    public GMatrix<T> PA(int l, int k, T a) {
        Field[][] newMat = new Field[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                if (i == k-1) {
                    newMat[i][j] = mat[i][j].add(a.mul(mat[l-1][j]));
                } else {
                    newMat[i][j] = mat[i][j];
                }
            }
        }

        return new GMatrix<T>(newMat);
    }

    /**
     * @pre 0 <= k, l < this.rows
     *
     * replaces rows l, k
     */
    private void _P(int k, int l) {
        Field[] tmp = this.mat[k];
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
    private void _PM(int k, Field a) {
        for (int j=0; j<columns; j++) {
            this.mat[k][j] = a.mul(this.mat[k][j]);
        }
    }

    /**
     *
     * @pre 0 <= k, l < this.rows && k != l
     *
     * @post for all j for all 0<=i<this.rows: i == k @implies $ret.mat[i][j] == this.mat[i][j] + a*this.mat[l][j]
     *                                          else: $ret.mat[i][j] == a*this.mat[i][j]
     */
    private void _PA(int l, int k, Field a) {
        for (int j=0; j<columns; j++) {
            this.mat[k][j] = this.mat[k][j].add(a.mul(this.mat[l][j]));
        }
    }

    /**
     *
     * @post $ret == -1 @implies for all i, this.mat[j][i] == 0
     * @post $ret < 0 @implies this.mat[$ret][j] != 0
     */
    private int nonZeroIndexInColumn(int from, int j) {
        for (int i=from; i<rows; i++) {
            if (this.mat[i][j].isZero()) {
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

    private Field selfGaussElimination(int endCol, boolean calcDet) {
        int currRow = 0;
        Field det = T.unit;
        for (int j=0; j<endCol; j++) {
            int row = nonZeroIndexInColumn(currRow, j);
            if (row == -1) continue;

            this._P(currRow, row);

            if (calcDet) {
                det = det.mul(row == currRow ? T.unit : T.unit.neg());
                det = det.mul(this.mat[currRow][j]);
            }

            this._PM(currRow, this.mat[currRow][j].inverse());

            for (int i=0; i<rows; i++) {
                if (i == currRow) continue;

                Field c = this.mat[i][j].neg();
                this._PA(currRow, i, c);
            }
            currRow++;
        }

        return det;
    }

    public GMatrix<T> gaussElimination() {
        return gaussElimination(this.columns + 1);
    }

    public GMatrix<T> gaussElimination(int endCol) {
        GMatrix<T> newMatrix = this.copy();
        newMatrix.selfGaussElimination(endCol - 1);
        return newMatrix;
    }

    public Field det() {
        GMatrix<T> copy = this.copy();
        return copy.selfGaussElimination(columns, true);
    }

    public GVector<T>[] getVectors() {
        GVector<T>[] vectors = (GVector<T>[]) new GVector<?>[this.columns];

        for (int i=0; i<this.columns; i++) {
            vectors[i] = new GVector<T>(this.getCol(i + 1));
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
    public GMatrix<T> inverse() {
        GVector<T>[] thisVectors = this.getVectors();
        GVector<T>[] unitVectors = GMatrix.<T>unit(this.rows).getVectors();

        GVector<T>[] vectors = (GVector<T>[]) new GVector<?>[this.rows*2];

        for (int i=0; i<2*rows; i++) {
            if (i<rows)
                vectors[i] = thisVectors[i];
            else
                vectors[i] = unitVectors[i - rows];
        }

        GMatrix<T> toEliminate = GVector.<T>toMatrix(vectors);

        if (toEliminate.selfGaussElimination(rows - 1, true).isZero())
            return this;

        GVector<T>[] newVectors = toEliminate.getVectors();
        GVector<T>[] inverseVectors = Arrays.copyOfRange(newVectors, rows, 2*rows);

        return GVector.<T>toMatrix(inverseVectors);
    }

    public GMatrix<T> minor(int k, int l) {
        Field[][] newMat = new Field[rows - 1][columns - 1];

        for (int i=0; i<rows - 1; i++) {
            for (int j=0; j<columns - 1; j++) {
                int row = i<k ? i: i+1;
                int col = j<l ? j: j+1;

                newMat[row][col] = this.mat[row][col];
            }
        }

        return new GMatrix<T>(newMat);
    }
}
