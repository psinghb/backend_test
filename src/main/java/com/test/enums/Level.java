package com.test.enums;

import lombok.Getter;

@Getter
public enum Level {
    Ant(5, 0),
    Bee(7, 10),
    Cat(9, 50),
    Dog(12, 200),
    Elephant(19, 1000);

    int reward;
    int startSales;
    Level(int r, int ss) {
        reward = r;
        startSales = ss;
    }

    public static Level fromSalesCount(long sales) {
        if (sales >= 1000) return Level.Elephant;
        if (sales >= 200) return Level.Dog;
        if (sales >= 50) return Level.Cat;
        if (sales >= 10) return Level.Bee;
        return Level.Ant;
    }

    public int diff(Level level) {
        return this.reward - level.reward;
    }
}
