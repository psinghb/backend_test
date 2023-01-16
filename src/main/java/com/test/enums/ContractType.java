package com.test.enums;

import java.util.Arrays;
import java.util.Map;

public enum ContractType {
    TORTOISE("Tortoise"),
    RABBIT("Rabbit");

    String displayName;

    ContractType(String s) {
        displayName = s;
    }

    private static Map<String, ContractType> MAP = Map.of(TORTOISE.displayName, TORTOISE, RABBIT.displayName, RABBIT);

    public static ContractType from(String s) {
       return MAP.get(s);
    }
}
