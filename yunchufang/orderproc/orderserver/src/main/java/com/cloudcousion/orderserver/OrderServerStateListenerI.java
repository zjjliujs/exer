package com.cloudcousion.orderserver;

public interface OrderServerStateListenerI {
    /**
     * Dispatch server state change message
     */
    void serverStateChanged();
}
