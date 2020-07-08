package com.cloudcousion.orderserver.utils;

public interface OrderLoggerI {
    void logDebug(String msg);

    void logInfo(String msg);

    void logWarning(String msg);

    void logError(String msg);
}
