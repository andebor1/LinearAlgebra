package rl.classes.types;

public class Real implements FieldElement {
    public static final Real zero = new Real(0);
    public static final Real unit = new Real(1);

    public Real unit() {
        return unit;
    }

    public Real zero() {
        return zero;
    }

    private double value;

    public Real(double value) {
        this.value = value;
    }

    private Real getRealValue(FieldElement other) {
        if (other instanceof Real a) {
            return a;
        }

        throw new IllegalArgumentException("Function must get an instance of Real class");
    }

    public boolean equals(FieldElement otherF) {
        Real other = getRealValue(otherF);
        return this.value == other.value;
    }


    public FieldElement add(FieldElement otherF) {
        Real other = getRealValue(otherF);
        return new Real(this.value + other.value);
    }


    public FieldElement sub(FieldElement otherF) {
        Real other = getRealValue(otherF);
        return new Real(this.value - other.value);
    }

    public FieldElement mul(FieldElement otherF) {
        Real other = getRealValue(otherF);
        return new Real(this.value * other.value);
    }

    public FieldElement inverse() {
        return new Real(1/this.value);
    }

    public FieldElement neg() {
        return new Real(-this.value);
    }

    public boolean isZero() {
        return this.value == 0;
    }

    public String toString() {
        return "" + this.value;
    }

    public static Real[] convertList(double ... elements) {
        int n = elements.length;
        Real[] r = new Real[n];

        for (int i=0; i<n; i++) {
            r[i] = new Real(elements[i]);
        }

        return r;
    }
}
