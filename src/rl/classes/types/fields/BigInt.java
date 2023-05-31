package rl.classes.types.fields;

import rl.useable.BigIntOperations;
import rl.useable.FractionsOperations;

import java.math.BigInteger;

public class BigInt implements FieldElement {
    public static final BigInt unit = new BigInt(1);
    public static final BigInt zero = new BigInt(0);

    public final BigInteger value;

    public BigInt(BigInteger value) {
        this.value = value;
    }

    public BigInt(Integer value) {
        this.value = new BigInteger(value.toString());
    }

    private BigInt getValue(FieldElement other) {
        if (other instanceof BigInt a) {
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
        BigInt rOther = getValue(other);
        return new BigInt(this.value.add(rOther.value));
    }

    @Override
    public FieldElement sub(FieldElement other) {
        return this.add(other.neg());
    }

    @Override
    public FieldElement mul(FieldElement other) {
        BigInt rOther = getValue(other);
        return new BigInt(this.value.multiply(rOther.value));
    }

    @Override
    public FieldElement inverse() {
        return new BigInt(0);
    }

    @Override
    public FieldElement neg() {
        return new BigInt(this.value.negate());
    }

    @Override
    public boolean equals(FieldElement other) {
        BigInt rOther = getValue(other);
        return this.value.equals(rOther.value);
    }

    @Override
    public boolean isZero() {
        return this.value.equals(BigInteger.ZERO);
    }

    public String toString() {
        return value.toString();
    }

    public static GRational[] toRational(int ... elements) {
        int n = elements.length;
        GRational[] rationals = new GRational[n];

        FractionsOperations op =new BigIntOperations();

        for (int i=0; i<n; i++) {
            rationals[i] = new GRational(new BigInt(elements[i]), op);
        }

        return rationals;
    }
}
