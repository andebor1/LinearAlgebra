package rl.useable;

import rl.classes.types.fields.FieldElement;

public class EmptyOperations implements FractionsOperations {

    @Override
    public boolean usable() {
        return false;
    }

    @Override
    public FieldElement gcd(FieldElement element1, FieldElement element2) {
        return null;
    }

    @Override
    public FieldElement div(FieldElement element1, FieldElement element2) {
        return null;
    }
}
