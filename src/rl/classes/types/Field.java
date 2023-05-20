package rl.classes.types;

public interface Field {
    Field unit = null;
    Field zero = null;
    boolean equals(Field other);
    Field add(Field other);
    Field sub(Field other);
    Field mul(Field other);
    Field inverse();
    Field neg();
    boolean isZero();

}
