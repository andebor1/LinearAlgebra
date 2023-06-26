package rl.classes.types.fields;

public class Complex implements FieldElement {
    public static final Complex unit = new Complex(1, 0);
    public static final Complex zero = new Complex(0, 0);

    public final double re;
    public final double im;
    public final double theta;
    public final double abs;

    public Complex(double a, double b) {
        this.re = a;
        this.im = b;
        this.theta = Math.atan2(a, b);
        this.abs = Math.sqrt(a*a + b*b);
    }

    public Complex(double abs, double theta, boolean polar) {
        this.abs = abs;
        this.theta = theta;
        this.re = abs*Math.cos(theta);
        this.im = abs*Math.sin(theta);
    }

    public static Complex[] convertList(double ... elements) {
        int n = elements.length;
        Complex[] converted = new Complex[n];
        for (int i=0; i<n; i++) {
            converted[i] = new Complex(elements[i], 0);
        }

        return converted;
    }

    private Complex getValue(FieldElement other) {
        if (other instanceof Complex z) {
            return z;
        }

        throw new IllegalArgumentException("can't do this with " + other.getClass());
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
        Complex rOther = getValue(other);
        return new Complex(this.re + rOther.re, this.im + rOther.im, true);
    }

    @Override
    public FieldElement mul(FieldElement other) {
        Complex rOther = getValue(other);
        return new Complex(this.abs*rOther.abs, this.theta + rOther.theta);
    }

    @Override
    public FieldElement inverse() {
        double d = abs*abs;
        return new Complex(this.re/d, -this.im/d);
    }

    @Override
    public FieldElement neg() {
        return new Complex(-this.re, -this.im);
    }

    @Override
    public boolean equals(FieldElement other) {
        Complex rOther = getValue(other);
        return this.re == rOther.re && this.im == rOther.im;
    }

    @Override
    public boolean isZero() {
        return this.re == 0 && this.im == 0;
    }

    @Override
    public String toString() {
        return this.re + " + " + this.im + "i";
    }
}
