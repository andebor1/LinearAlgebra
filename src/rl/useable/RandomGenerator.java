package rl.useable;

import java.util.Random;

public class RandomGenerator {
    Random random;

    public RandomGenerator() {
        this.random = new Random();
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
