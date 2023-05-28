package rl.classes.types.fields;

public interface FieldElement {
    FieldElement unit();
    FieldElement zero();
    FieldElement add(FieldElement other);
    FieldElement sub(FieldElement other);
    FieldElement mul(FieldElement other);
    FieldElement inverse();
    FieldElement neg();
    boolean equals(FieldElement other);
    boolean isZero();
    String toString();
}
