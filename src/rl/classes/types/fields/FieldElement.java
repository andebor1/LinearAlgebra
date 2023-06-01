package rl.classes.types.fields;

public interface FieldElement {
    FieldElement unit();
    FieldElement zero();
    FieldElement add(FieldElement other);
    default FieldElement sub(FieldElement other) {
        return this.add(other.neg());
    }
    FieldElement mul(FieldElement other);
    FieldElement inverse();
    default FieldElement divide(FieldElement other) {
        return this.mul(other.inverse());
    }
    FieldElement neg();
    boolean equals(FieldElement other);
    boolean isZero();
    String toString();
}
