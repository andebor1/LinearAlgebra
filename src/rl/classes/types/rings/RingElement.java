package rl.classes.types.rings;

import rl.classes.types.fields.FieldElement;

public interface RingElement {
    RingElement unit();
    RingElement zero();
    RingElement add(RingElement other);
    default RingElement sub(RingElement other) {
        return this.add(other.neg());
    }
    RingElement mul(RingElement other);
    RingElement neg();
    boolean equals(RingElement other);
    boolean isZero();
    String toString();
}
