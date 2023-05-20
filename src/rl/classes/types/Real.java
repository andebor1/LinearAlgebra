public class Real  implements Field<Double> {
    public static final double unit = 1;
    public static final double zero = 0;

    public double unit() {
        return unit;
    }

    public double zero() {
        return zero;
    }

    private double value;

    public Real(double value) {
        this.value = value;
    }
    @Override
    public boolean isEqual(Double other) {
        return this.value == other;
    }

    @Override
    public Double add(Double other) {
        return this.value + other;
    }

    @Override
    public Double sub(Double other) {
        return this.value + other;
    }

    @Override
    public Double mul(Double other) {
        return this.value*other;
    }

    @Override
    public Double inverse() {
        return 1/this.value;
    }

    @Override
    public Double neg() {
        return -this.value;
    }

    @Override
    public boolean isZero() {
        return this.value == 0;
    }

    public String toString() {
        return "" + this.value;
    }
}
