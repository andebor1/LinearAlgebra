package rl.classes.types;

import rl.useable.Numeric;
public record Rational(int numerator, int denominator) {

    public Rational(int numerator, int denominator) {
        int q = Numeric.gcd(numerator, denominator);

        this.numerator = numerator/q;
        this.denominator = denominator/q;
    }


    public String toString() {
        return this.numerator + "/" + this.denominator;
    }
}
