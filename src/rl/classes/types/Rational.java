package rl.classes.types;

import rl.useable.Numeric;
public record Rational(int numerator, int denominator) implements FieldElement{

    public static final Rational unit = new Rational(1, 1);
    public static final Rational zero = new Rational(0, 1);

    public Rational(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Can't have zero in the denominator!");
        }

        int q = Numeric.gcd(numerator, denominator);

        this.numerator = numerator/q;
        this.denominator = denominator/q;
    }

    public Rational(int r) {
        this(r, 1);
    }

    private Rational getRationalValue(FieldElement other) {
        if (other instanceof Rational a) {
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
        Rational rOther = getRationalValue(other);
        return new Rational(this.numerator*rOther.denominator + rOther.numerator*this.denominator, this.denominator*rOther.denominator);
    }

    @Override
    public FieldElement sub(FieldElement other) {
        return this.add(other.neg());
    }

    @Override
    public FieldElement mul(FieldElement other) {
        Rational rOther = getRationalValue(other);
        return new Rational(this.numerator*rOther.numerator, this.denominator*rOther.denominator);
    }

    @Override
    public FieldElement inverse() {
        if (this.numerator == 0) {
            return zero;
        }
        return new Rational(this.denominator, this.numerator);
    }

    @Override
    public FieldElement neg() {
        return new Rational(-this.numerator, this.denominator);
    }

    @Override
    public boolean equals(FieldElement other) {
        Rational rOther = getRationalValue(other);
        return this.numerator == rOther.numerator && this.denominator == rOther.denominator;
    }

    @Override
    public boolean isZero() {
        return this.numerator == 0;
    }

    public String toString() {
        return this.numerator + "/" + this.denominator;
    }

    public static Rational[] convertList(int ... elements) {
        int n = elements.length;
        Rational[] arr = new Rational[n];

        for (int i=0; i<n; i++) {
            arr[i] = new Rational(elements[i]);
        }

        return arr;
    }
}
