package rl.classes.matrices;

import rl.classes.types.Polynomial;
import rl.classes.types.fields.RationalPolynomial;
import rl.classes.vectors.FVector;
import rl.classes.types.fields.FieldElement;

import java.util.Arrays;
import java.util.HashSet;

public class FMatrix {

    private final FieldElement[][] mat;
    private final FieldElement unit;
    public final int rows;
    public final int columns;

    public FMatrix(FieldElement[][] mat) {
        this.mat = mat;
        this.rows = mat.length;

        if (this.rows > 0)
            this.columns = mat[0].length;
        else
            this.columns = 0;

        this.unit = mat[0][0].unit();
    }

    public static FMatrix unit(int n, FieldElement unit) {
        FieldElement[][] newMat = new FieldElement[n][n];

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (i==j) {
                    newMat[i][j] = unit;
                } else {
                    newMat[i][j] = unit.zero();
                }
            }
        }

        return new FMatrix(newMat);
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
            out.append(Arrays.toString(getRow(i).vec));
            out.append("\n");
        }

        return out.toString();
    }

    public FieldElement[][] getMat() {
        FieldElement[][] res = new FieldElement[rows][];

        for (int i=0; i<rows; i++) {
            res[i] = getRow(i+1).vec;
        }

        return res;
    }


    public FMatrix copy() {
        return new FMatrix(this.getMat());
    }

    public boolean equals(FMatrix B) {
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
    public FMatrix add(FMatrix B) {
        FieldElement[][] newMat = new FieldElement[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                newMat[i][j] = mat[i][j].add(B.mat[i][j]);
            }
        }

        return new FMatrix(newMat);
    }

    /**
     *
     * @pre this.size().equals(B.size())
     *
     * @post $ret.mat[i][j] == this.mat[i][j] - B.mat[i][j] for all i, j
     */
    public FMatrix sub(FMatrix B) {
        FieldElement[][] newMat = new FieldElement[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                newMat[i][j] = mat[i][j].sub(B.mat[i][j]);
            }
        }

        return new FMatrix(newMat);
    }

    /**
     *
     * @post $ret.mat[i][j] == c*this.mat[i][j] for all i,j
     */
    public FMatrix scalarMul(FieldElement c) {
        FieldElement[][] newMat = new FieldElement[rows][columns];

        for (int i = 0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                newMat[i][j] = c.mul(mat[i][j]);
            }
        }

        return new FMatrix(newMat);
    }

    /**
     *
     * @pre 0 < i <= this.rows
     *
     * @post $ret.equals(this.mat[i-1])
     */
    public FVector getRow(int i) {
        FieldElement[] row = new FieldElement[this.columns];

        for (int j=0; j<this.columns; j++) {
            row[j] = this.mat[i - 1][j];
        }

        return new FVector(row);
    }

    /**
     *
     * @pre 0 < j <= this.columns
     *
     * @post $ret[i] == this.mat[i][j - 1] for all 0<=i<this.rows
     */
    public FVector getCol(int j) {
        FieldElement[] col = new FieldElement[this.rows];

        for (int i=0; i<this.rows; i++) {
            col[i] = this.mat[i][j - 1];
        }

        return new FVector(col);
    }

    /**
     *
     * @pre this.columns == B.rows
     *
     * @post $ret = this.mat * B.mat (matrix multiplication)
     */
    public FMatrix multiply(FMatrix B) {
        FieldElement[][] newMat = new FieldElement[this.rows][B.columns];

        for (int i=0; i<this.rows; i++) {
            for (int j=0; j<B.columns; j++) {
                newMat[i][j] = B.getCol(j + 1).dot(this.mat[i]);
            }
        }

        return new FMatrix(newMat);
    }

    /**
     *
     * @pre this.rows == this.columns
     *
     * @post $ret == this to the power of k
     */
    public FMatrix pow(int k) {
        FMatrix newMatrix = FMatrix.unit(this.rows, this.unit);

        for (int i=0; i<k; i++) {
            newMatrix = newMatrix.multiply(this);
        }

        return newMatrix;
    }

    public FVector mulVec(FVector v) {
        FieldElement[] newVec = new FieldElement[this.rows];

        for (int i=0; i<rows;i++) {
            newVec[i] = v.dot(this.mat[i]);
        }

        return new FVector(newVec);
    }

    public FVector mulVec(FieldElement... vec) {
        FVector v = new FVector(vec);
        return this.mulVec(v);
    }

    /**
     *
     * @post $ret.mat[j][i] = this.mat[i][j] for all i, j
     */
    public FMatrix transpose() {
        FieldElement[][] newMat = new FieldElement[columns][rows];

        for (int j=0; j<columns; j++) {
            for (int i=0; i<rows; i++) {
                newMat[j][i] = mat[i][j];
            }
        }

        return new FMatrix(newMat);
    }

    /**
     *
     * @pre 0 < k, l <= this.rows
     *
     * replaces rows l-1, k-1
     */
    public FMatrix P(int k, int l) {
        FieldElement[][] newMat = new FieldElement[rows][columns];

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

        return new FMatrix(newMat);
    }

    /**
     *
     * @pre 0 < k <= this.rows && a != 0
     *
     * @post for all j for all 0<=i<this.rows: i == k @implies $ret.mat[i][j] == a*this.mat[i][j]
     *                                          else: $ret.mat[i][j] == this.mat[i][j]
     */
    public FMatrix PM(int k, FieldElement a) {
        FieldElement[][] newMat = new FieldElement[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                if (i == k-1) {
                    newMat[i][j] = a.mul(mat[i][j]);
                } else {
                    newMat[i][j] = mat[i][j];
                }
            }
        }

        return new FMatrix(newMat);
    }

    /**
     * @pre 0 < k, l <= this.rows && k != l
     *
     * @post for all j for all 0<=i<this.rows: i == k-1 @implies $ret.mat[i][j] == this.mat[i][j] + a*this.mat[l-1][j]
     *                                          else: $ret.mat[i][j] == a*this.mat[i][j]
     */
    public FMatrix PA(int l, int k, FieldElement a) {
        FieldElement[][] newMat = new FieldElement[rows][columns];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                if (i == k-1) {
                    newMat[i][j] = mat[i][j].add(a.mul(mat[l-1][j]));
                } else {
                    newMat[i][j] = mat[i][j];
                }
            }
        }

        return new FMatrix(newMat);
    }

    /**
     * @pre 0 <= k, l < this.rows
     *
     * replaces rows l, k
     */
    private void _P(int k, int l) {
        FieldElement[] tmp = this.mat[k];
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
    private void _PM(int k, FieldElement a) {
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
    private void _PA(int l, int k, FieldElement a) {
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
            if (!this.mat[i][j].isZero()) {
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

    private FieldElement selfGaussElimination(int endCol, boolean calcDet) {
        int currRow = 0;
        FieldElement det = unit;
        for (int j=0; j<endCol; j++) {
            System.out.println(this);
            int row = nonZeroIndexInColumn(currRow, j);
            if (row == -1) continue;

            this._P(currRow, row);

            if (calcDet) {
                det = det.mul(row == currRow ? unit : unit.neg());
                det = det.mul(this.mat[currRow][j]);
            }

            this._PM(currRow, this.mat[currRow][j].inverse());

            for (int i=0; i<rows; i++) {
                if (i == currRow) continue;

                FieldElement c = this.mat[i][j].neg();
                this._PA(currRow, i, c);
            }
            currRow++;
        }

        return det;
    }

    public FMatrix gaussElimination() {
        return gaussElimination(this.columns + 1);
    }

    public FMatrix gaussElimination(int endCol) {
        FMatrix newMatrix = this.copy();
        newMatrix.selfGaussElimination(endCol - 1);
        return newMatrix;
    }

    public FieldElement det() {
        FMatrix copy = this.copy();
        return copy.selfGaussElimination(columns, true);
    }

    public FVector[] getVectors() {
        FVector[] vectors = new FVector[this.columns];

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
    public FMatrix inverse() {
        FVector[] thisVectors = this.getVectors();
        FVector[] unitVectors = FMatrix.unit(this.rows, unit).getVectors();

        FVector[] vectors = new FVector[this.rows*2];

        for (int i=0; i<2*rows; i++) {
            if (i<rows)
                vectors[i] = thisVectors[i];
            else
                vectors[i] = unitVectors[i - rows];
        }

        FMatrix toEliminate = FVector.toMatrix(vectors);

        if (toEliminate.selfGaussElimination(rows, true).isZero())
            return null;

        FVector[] newVectors = toEliminate.getVectors();
        FVector[] inverseVectors = Arrays.copyOfRange(newVectors, rows, 2*rows);

        return FVector.toMatrix(inverseVectors);
    }

    public FMatrix minor(int k, int l) {
        FieldElement[][] newMat = new FieldElement[rows - 1][columns - 1];

        for (int i=0; i<rows - 1; i++) {
            for (int j=0; j<columns - 1; j++) {
                int row = i<k ? i: i+1;
                int col = j<l ? j: j+1;

                newMat[row][col] = this.mat[row][col];
            }
        }

        return new FMatrix(newMat);
    }

    public int getRank() {
        FMatrix eliminated = this.gaussElimination();

        int rank = 0;
        for (int i=0; i<rows; i++, rank++) {
            if (eliminated.getRow(i + 1).isZero()) {
                break;
            }
        }

        return rank;
    }

    public FVector[] nullSpaceBase() {
        int n = rows;
        FMatrix eliminated = this.gaussElimination();

        int r = eliminated.getRank();
        HashSet<Integer> pivotIndexes = new HashSet<>();

        for (int i=0; i<r; i++) {
            FieldElement[] row = eliminated.getRow(i + 1).vec;
            for (int j=0; j<n; j++) {
                if (row[j].equals(unit)) {
                    pivotIndexes.add(j);
                    break;
                }
            }
        }

        FVector[] base = new FVector[n - r];
        int curr=0;
        for (int i=0; i<n-r; i++, curr++) {
            while (pivotIndexes.contains(curr)) {
                curr++;
            }

            FieldElement[] baseVector = new FieldElement[n];
            baseVector[curr] = unit;
            for (int j=0; j<curr; j++) {
                if (pivotIndexes.contains(j)) {
                    baseVector[j] = this.mat[j][curr].neg();
                } else {
                    baseVector[j] = unit.zero();
                }
            }

            base[i] = new FVector(baseVector);
        }

        return base;
    }

    public RationalPolynomial characteristicPolynomial() {
        FieldElement[][] mat = this.getMat();

        int n = this.rows;
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (i == j) {
                    mat[i][j] = new RationalPolynomial(new Polynomial(mat[i][j].neg(), unit));
                } else {
                    mat[i][j] = new RationalPolynomial(new Polynomial(mat[i][j]));
                }
            }
        }

        FMatrix polMatrix = new FMatrix(mat);
        return (RationalPolynomial) polMatrix.det();
    }
}
