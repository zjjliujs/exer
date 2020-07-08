package com.cloudcousion.orderserver.utils;

public class ConsoleLogger implements OrderLoggerI {
    private static OrderLoggerI instance = null;

    private ConsoleLogger() {
    }

    public static synchronized OrderLoggerI getInstance() {
        if (instance == null) {
            instance = new ConsoleLogger();
        }
        return instance;
    }

    @Override
    public void logDebug(String msg) {
        System.out.println(msg);
    }

    @Override
    public void logInfo(String msg) {
        System.out.println(msg);
    }

    @Override
    public void logWarning(String msg) {
        System.out.println(msg);
    }

    @Override
    public void logError(String msg) {
        System.err.println(msg);
    }
}
