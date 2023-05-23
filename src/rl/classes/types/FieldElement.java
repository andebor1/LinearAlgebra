package rl.classes.types;

public interface FieldElement {
    FieldElement add(FieldElement other);
    FieldElement sub(FieldElement other);
    FieldElement mul(FieldElement other);
    FieldElement inverse();
    boolean equals(FieldElement other);
    String toString();
}
