package rl.classes.types;

import rl.classes.types.fields.FieldElement;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @imp_inv !this.factors[this.factors.length - 1].isZero() // last element is not zero
 */
public class Polynomial {
    private final FieldElement[] factors;
    private int degree;
    

    public Polynomial(FieldElement ... factors) {
        this.factors = factors;
        
        setDegree();
    }
    
    private void setDegree() {
        int n = this.factors.length;
        
        this.degree = 0;
        for (int i=n-1; i>=0; i--) {
            if (!factors[i].isZero()) {
                this.degree = i;
                break;
            }
        }
    }

    public static Polynomial xPower(int i, FieldElement unit) {
        FieldElement[] factors = new FieldElement[i + 1];

        for (int j=0; j<i; j++) {
            factors[j] = unit.zero();
        }

        factors[i] = unit;
        return new Polynomial(factors);
    }
    
    public int degree() {
        return degree;
    }

    public String toString() {
        StringBuilder out = new StringBuilder();

        out.append(this.factors[0]);

        int n = this.degree + 1;
        for (int i=1; i<n; i++) {
            out.append(" + ");
            out.append(this.factors[i]);
            out.append("*x^");
            out.append(i);
        }

        return out.toString();
    }

    public boolean equals(Polynomial other) {
        if (this.degree != other.degree) {
            return false;
        }

        int n = this.degree + 1;
        for (int i=0; i<n; i++) {
            if (this.factors[i] != other.factors[i]) {
                return false;
            }
        }

        return true;
    }

    public FieldElement getCoefficient(int i) {
        return factors[i];
    }

    public Polynomial neg() {
        int n = this.degree + 1;

        FieldElement[] newFactors = Arrays.copyOf(this.factors, n);
        for (int i=0; i<n; i++) {
            newFactors[i] = newFactors[i].neg();
        }

        return new Polynomial(newFactors);
    }
    
    public Polynomial add(Polynomial other) {
        int n = this.degree + 1;
        int m = other.degree + 1;
        
        if (m > n) {
            return other.add(this);
        }
        
        FieldElement[] newFactors = Arrays.copyOf(this.factors, n);
        for (int i=0; i<m; i++) {
            newFactors[i] =  newFactors[i].add(other.factors[i]);
        }
        
        return new Polynomial(newFactors);
    }

    public Polynomial mul(Polynomial other) {
        int n = this.degree;
        int m = other.degree;

        if (m > n) {
            return other.mul(this);
        }
        FieldElement[] newFactors = new FieldElement[n + m + 1];

        FieldElement zeroElement = this.factors[0].zero();
        for (int i=0; i<=n+m; i++ ) {
            newFactors[i] = zeroElement;
        }

        for (int k=0; k<=n+m; k++) {
            for (int i=Math.max(k-n, 0); i<=k && i<=m; i++) {
                newFactors[k] = newFactors[k].add(other.factors[i].mul(this.factors[k-i]));
            }
        }

        return new Polynomial(newFactors);
    }

    public Polynomial mul(FieldElement c) {
        int n = this.degree + 1;

        FieldElement[] newFactors = Arrays.copyOf(this.factors, n);

        for (int i=0; i<n; i++) {
            newFactors[i] = newFactors[i].mul(c);
        }

        return new Polynomial(newFactors);
    }

    public Polynomial unit() {
        return new Polynomial(this.factors[0].unit());
    }

    public Polynomial zero() {
        return new Polynomial(this.factors[0].zero());
    }

    public boolean isZero() {
        return this.degree == 0 && this.factors[0].isZero();
    }

    public Polynomial normalize() {
        FieldElement highInverse = this.getCoefficient(this.degree).inverse();
        return this.mul(highInverse);
    }
}
