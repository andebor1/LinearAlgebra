package rl.classes.types.fields;

import rl.useable.Numeric;

public record RationalInt(int numerator, int denominator) implements FieldElement{

    public static final RationalInt unit = new RationalInt(1, 1);
    public static final RationalInt zero = new RationalInt(0, 1);

    public RationalInt(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Can't have zero in the denominator!");
        }

        int q = Numeric.gcd(numerator, denominator);

        this.numerator = numerator/q;
        this.denominator = denominator/q;
    }

    public RationalInt(int r) {
        this(r, 1);
    }

    private RationalInt getRationalValue(FieldElement other) {
        if (other instanceof RationalInt a) {
            return a;
        }

        throw new IllegalArgumentException("Function must get an instance of Rational class");
    }


    @Override
    public FieldElement unit() {
        return unit;
    }

    @Override
    public FieldElement zero() {
        return zero;
    }

    @Override
    public FieldElement add(FieldElement other) {
        RationalInt rOther = getRationalValue(other);
        return new RationalInt(this.numerator*rOther.denominator + rOther.numerator*this.denominator, this.denominator*rOther.denominator);
    }

    @Override
    public FieldElement sub(FieldElement other) {
        return this.add(other.neg());
    }

    @Override
    public FieldElement mul(FieldElement other) {
        RationalInt rOther = getRationalValue(other);
        return new RationalInt(this.numerator*rOther.numerator, this.denominator*rOther.denominator);
    }

    @Override
    public FieldElement inverse() {
        if (this.numerator == 0) {
            return zero;
        }
        return new RationalInt(this.denominator, this.numerator);
    }

    @Override
    public FieldElement neg() {
        return new RationalInt(-this.numerator, this.denominator);
    }

    @Override
    public boolean equals(FieldElement other) {
        RationalInt rOther = getRationalValue(other);
        return this.numerator == rOther.numerator && this.denominator == rOther.denominator;
    }

    @Override
    public boolean isZero() {
        return this.numerator == 0;
    }

    public String toString() {
        return this.numerator + "/" + this.denominator;
    }

    public static RationalInt[] convertList(int ... elements) {
        int n = elements.length;
        RationalInt[] arr = new RationalInt[n];

        for (int i=0; i<n; i++) {
            arr[i] = new RationalInt(elements[i]);
        }

        return arr;
    }
}
