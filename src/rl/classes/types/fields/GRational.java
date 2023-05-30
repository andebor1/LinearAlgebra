package rl.classes.types.fields;

import rl.useable.*;

public record GRational(FieldElement numerator, FieldElement denominator) implements FieldElement{

    public GRational(FieldElement numerator, FieldElement denominator, FractionsOperations op) {
        if (denominator.isZero()) {
            throw new IllegalArgumentException("Can't have zero in the denominator!");
        }

        FieldElement q = op.gcd(numerator, denominator);

        this.numerator = op.div(numerator, q);
        this.denominator = op.div(denominator, q);
    }

    public static GRational create(FieldElement numerator, FieldElement denominator) {
        return new GRational(numerator, denominator, new EmptyOperations());
    }


    public static GRational create(FieldElement r) {
        GRational.create(r, r.unit());
    }

    private GRational getRationalValue(FieldElement other) {
        if (other instanceof GRational a) {
            return a;
        }

        throw new IllegalArgumentException("Function must get an instance of Rational class");
    }


    @Override
    public FieldElement unit() {
        return GRational.create(this.numerator.unit());
    }

    @Override
    public FieldElement zero() {
        return GRational.create(this.numerator.zero());
    }

    @Override
    public FieldElement add(FieldElement other) {
        GRational rOther = getRationalValue(other);
        return GRational.create(this.numerator.mul(rOther.denominator).add(rOther.numerator.mul(this.denominator)), this.denominator.mul(rOther.denominator));
    }

    @Override
    public FieldElement sub(FieldElement other) {
        return this.add(other.neg());
    }

    @Override
    public FieldElement mul(FieldElement other) {
        GRational rOther = getRationalValue(other);
        return GRational.create(this.numerator.mul(rOther.numerator), this.denominator.mul(rOther.denominator));
    }

    @Override
    public FieldElement inverse() {
        if (this.numerator.isZero()) {
            return zero();
        }
        return GRational.create(this.denominator, this.numerator);
    }

    @Override
    public FieldElement neg() {
        return GRational.create(this.numerator.neg(), this.denominator);
    }

    @Override
    public boolean equals(FieldElement other) {
        GRational rOther = getRationalValue(other);
        return this.numerator.equals(rOther.numerator) && this.denominator.equals(rOther.denominator);
    }

    @Override
    public boolean isZero() {
        return this.numerator.isZero();
    }

    public String toString() {
        return this.numerator + "/" + this.denominator;
    }

    public static GRational[] convertList(FieldElement ... elements) {
        int n = elements.length;
        GRational[] arr = new GRational[n];

        for (int i=0; i<n; i++) {
            arr[i] = GRational.create(elements[i]);
        }

        return arr;
    }
}
