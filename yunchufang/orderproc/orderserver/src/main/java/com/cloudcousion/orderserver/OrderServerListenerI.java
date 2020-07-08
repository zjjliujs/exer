package com.cloudcousion.orderserver;

public interface OrderServerListenerI {
    /**
     * Dispatch server state change message
     */
    void serverStateChanged();
}
