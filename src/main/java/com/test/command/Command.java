package com.test.command;

public interface Command {
    String getType();
    public static class Constants {
        public static final String REGISTER = "REGISTER";
        public static final String LOAD = "LOAD";
        public static final String LEVEL = "LEVEL";
        public static final String REWARDS = "REWARDS";
        public static final String ALL_REWARDS = "ALL_REWARDS";
    }
}
