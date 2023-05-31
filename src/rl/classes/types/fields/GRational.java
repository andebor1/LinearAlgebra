package rl.classes.types.fields;

import rl.useable.*;

public class GRational implements FieldElement{

    public final FieldElement numerator;
    public final FieldElement denominator;
    FractionsOperations op = new EmptyOperations();

    public GRational(FieldElement numerator, FieldElement denominator, FractionsOperations op) {
        if (denominator.isZero()) {
            throw new IllegalArgumentException("Can't have zero in the denominator!");
        }

        FieldElement q = op.gcd(numerator, denominator);

        this.numerator = op.div(numerator, q);
        this.denominator = op.div(denominator, q);
        this.op = op;
    }

    public GRational(FieldElement numerator, FieldElement denominator) {
        if (denominator.isZero()) {
            throw new IllegalArgumentException("Can't have zero in the denominator!");
        }

        this.numerator = numerator;
        this.denominator = denominator;
    }

    public GRational(FieldElement r, FractionsOperations op) {
        this(r);
        this.op = op;
    }


    public GRational(FieldElement r) {
        this(r, r.unit());
    }

    private GRational getRationalValue(FieldElement other) {
        if (other instanceof GRational a) {
            return a;
        }

        throw new IllegalArgumentException("Function must get an instance of Rational class");
    }


    @Override
    public FieldElement unit() {
        return new GRational(this.numerator.unit(), op);
    }

    @Override
    public FieldElement zero() {
        return new GRational(this.numerator.zero(), op);
    }

    @Override
    public FieldElement add(FieldElement other) {
        GRational rOther = getRationalValue(other);
        return new GRational(this.numerator.mul(rOther.denominator).add(rOther.numerator.mul(this.denominator)), this.denominator.mul(rOther.denominator), op);
    }

    @Override
    public FieldElement sub(FieldElement other) {
        return this.add(other.neg());
    }

    @Override
    public FieldElement mul(FieldElement other) {
        GRational rOther = getRationalValue(other);
        return new GRational(this.numerator.mul(rOther.numerator), this.denominator.mul(rOther.denominator), op);
    }

    @Override
    public FieldElement inverse() {
        if (this.numerator.isZero()) {
            return zero();
        }
        return new GRational(this.denominator, this.numerator, op);
    }

    @Override
    public FieldElement neg() {
        return new GRational(this.numerator.neg(), this.denominator, op);
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
            arr[i] = new GRational(elements[i], new BigIntOperations());
        }

        return arr;
    }
}
