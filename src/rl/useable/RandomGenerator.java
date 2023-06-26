package rl.useable;

import java.util.Random;

public class RandomGenerator {

    public static final int RANDOMSEED = -1;
    Random random;

    public RandomGenerator(int seed) {
        if (seed == RANDOMSEED) {
            this.random = new Random();
        } else {
            this.random = new Random(seed);
        }
    }

    public RandomGenerator() {
        this(RANDOMSEED);
    }

    public double getDouble(double minVal, double maxVal) {
        return minVal + (maxVal - minVal) * random.nextDouble();
    }

    public double getDouble(double maxValue) {
        return getDouble(0, maxValue);
    }

    public double getDouble() {
        return getDouble(0, 1);
    }

    public int getInt(int minVal, int maxVal) {
        return random.nextInt(minVal, maxVal);
    }
}
