package com.test.enums;

import java.util.Map;

public enum ContractAction {
    BEGIN,
    END;
    private static Map<String, ContractAction> MAP = Map.of(BEGIN.toString(), BEGIN, END.toString(), END);

    public static ContractAction from(String s) {
        return MAP.get(s);
    }
}
