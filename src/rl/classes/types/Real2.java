package rl.classes.types;

public class Real2 implements Field2<Real2> {
    public static final Real2 zero = new Real2(0);
    public static final Real2 unit = new Real2(1);

    public Real2() unit() {
        return unit;
    }

    public Real2() zero() {
        return zero;
    }

    private double value;

    public Real2(double value) {
        this.value = value;
    }

    public boolean isEqual(Real2 other) {
        return this.value == other.value;
    }


    public Real2 add(Real2 other) {
        return new Real2(this.value + other.value);
    }


    public Real2 sub(Real2 other) {
        return new Real2(this.value - other.value);
    }

    public Real2 mul(Field2<?> other) {
        return new Real2(this.value * other.value);
    }

    public Real2 inverse() {
        return new Real2(1/this.value);
    }

    public Real2 neg() {
        return new Real2(-this.value);
    }

    public boolean isZero() {
        return this.value == 0;
    }

    public String toString() {
        return "" + this.value;
    }
}

}
