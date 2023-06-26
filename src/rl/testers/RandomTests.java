package rl.testers;

import rl.useable.RandomGenerator;

public class RandomTests {

    public static void main(String[] args) {
        testRandom();
    }

    public static void testRandom() {
        RandomGenerator r1 = new RandomGenerator();
        System.out.println("r1.getDouble() = " + r1.getDouble());
        RandomGenerator r2 = new RandomGenerator(121);
        System.out.println("r2.getDouble() = " + r2.getDouble());
    }
}
