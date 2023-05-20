package rl.classes.types;

public interface Field<T> {
    boolean isEqual(T other);
    T add(T other);
    T sub(T other);
    T mul(T other);
    T inverse();
    T neg();
    boolean isZero();

}
