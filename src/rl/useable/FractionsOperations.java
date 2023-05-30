package rl.useable;

import rl.classes.types.fields.FieldElement;

public interface FractionsOperations {

    boolean usable();
    FieldElement gcd(FieldElement element1, FieldElement element2);
    FieldElement div(FieldElement element1, FieldElement element2);

}
