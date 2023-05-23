package rl.classes.types;

public interface Field2<T extends Field2<T>> {
    boolean isEqual(T other);

    T zero();
    T unit();
    T add(T other);
    T sub(T other);
    T mul(T other);
    T inverse();
    T neg();
    boolean isZero();

}