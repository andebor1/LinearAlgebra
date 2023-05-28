package rl.classes.types.fields;

import rl.classes.types.Polynomial;
import rl.useable.Numeric;

public class RationalPolynomial implements FieldElement {

    public final Polynomial numerator;
    public final Polynomial denominator;

    public RationalPolynomial(Polynomial numerator, Polynomial denominator) {
        if (denominator.isZero()) {
            throw new IllegalArgumentException("Can't have zero in the denominator!!");
        }

        Polynomial q = Numeric.polyGCD(numerator, denominator);
        this.numerator = Numeric.polyDiv(numerator, q);
        this.denominator = Numeric.polyDiv(denominator, q);
    }

    public RationalPolynomial(Polynomial polynomial) {
        this(polynomial, polynomial.unit());
    }

    public String toString() {
        return "(" + this.numerator + ") / (" + this.denominator + ")";
    }

    private RationalPolynomial getValue(FieldElement other) {
        if (other instanceof RationalPolynomial a) {
            return a;
        }

        throw new IllegalArgumentException("Function must get an instance of Real class");
    }


    @Override
    public FieldElement unit() {
        Polynomial unit = this.numerator.unit();
        Polynomial unit2 = this.numerator.unit();
        return new RationalPolynomial(unit, unit2);
    }

    @Override
    public FieldElement zero() {
        Polynomial unit = this.numerator.unit();
        Polynomial zero = this.numerator.zero();
        return new RationalPolynomial(zero, unit);
    }

    @Override
    public FieldElement add(FieldElement other) {
        RationalPolynomial rOther = getValue(other);
        return new RationalPolynomial(this.numerator.mul(rOther.denominator).add(this.denominator.mul(rOther.numerator)),
                this.denominator.mul(rOther.denominator));
    }

    @Override
    public FieldElement sub(FieldElement other) {
        return this.add(other.neg());
    }

    @Override
    public FieldElement mul(FieldElement other) {
        RationalPolynomial rOther = getValue(other);
        return new RationalPolynomial(this.numerator.mul(rOther.numerator),
                this.denominator.mul(rOther.denominator));
    }

    @Override
    public FieldElement inverse() {
        return new RationalPolynomial(this.denominator, this.numerator);
    }

    @Override
    public FieldElement neg() {
        return new RationalPolynomial(this.numerator.neg(), this.denominator);
    }

    @Override
    public boolean equals(FieldElement other) {
        RationalPolynomial rOther = getValue(other);
        return this.numerator.mul(rOther.denominator).equals(rOther.numerator.mul(this.denominator));
    }

    @Override
    public boolean isZero() {
        return this.numerator.isZero();
    }
}
