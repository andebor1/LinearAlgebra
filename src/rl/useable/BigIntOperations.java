package rl.useable;

import rl.classes.types.fields.BigInt;
import rl.classes.types.fields.FieldElement;

import java.math.BigInteger;

public class BigIntOperations implements FractionsOperations {

    @Override
    public boolean usable() {
        return true;
    }

    private BigInt getValue(FieldElement other) {
        if (other instanceof BigInt d) {
            return d;
        }

        throw new IllegalArgumentException("can't accept other type than 'BigInt'");
    }

    @Override
    public FieldElement gcd(FieldElement element1, FieldElement element2) {
        BigInt e1 = getValue(element1);
        BigInt e2 = getValue(element2);
        BigInt gcd =  new BigInt(e1.value.gcd(e2.value));
        if (e2.value.compareTo(BigInteger.ZERO) < 0) {
            return gcd.neg();
        }

        return gcd;
    }

    @Override
    public FieldElement div(FieldElement element1, FieldElement element2) {
        BigInt e1 = getValue(element1);
        BigInt e2 = getValue(element2);
        return new BigInt(e1.value.divide(e2.value));
    }
}
