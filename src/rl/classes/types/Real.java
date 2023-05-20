package rl.classes.types;

public class Real implements Field {
    public static final Field unit = new Real(1);
    public static final Field zero = new Real(0);

    private double value;

    public Real(double num) {
        value = num;
    }

    public Field unit() {
        return unit;
    }

    public Field zero() {
        return zero;
    }

    @Override
    public boolean equals(Field other) {
        if (other instanceof Real otherReal) {
            return this.value == otherReal.value;
        }
        return false;
    }

    @Override
    public Field add(Field other) {
        if (other instanceof Real otherReal) {
            return new Real(this.value + otherReal.value);
        }
        return null; // or throw an exception to indicate unsupported operation
    }

    @Override
    public Field sub(Field other) {
        if (other instanceof Real otherReal) {
            return new Real(this.value - otherReal.value);
        }
        return null; // or throw an exception to indicate unsupported operation
    }

    @Override
    public Field mul(Field other) {
        if (other instanceof Real otherReal) {
            return new Real(this.value * otherReal.value);
        }
        return null; // or throw an exception to indicate unsupported operation
    }

    @Override
    public Field inverse() {
        if (value != 0) {
            return new Real(1 / this.value);
        }
        return null; // or throw an exception to indicate unsupported operation
    }

    @Override
    public Field neg() {
        return new Real(-this.value);
    }

    @Override
    public boolean isZero() {
        return value == 0;
    }

    public String toString() {
        return "" + this.value;
    }
}
