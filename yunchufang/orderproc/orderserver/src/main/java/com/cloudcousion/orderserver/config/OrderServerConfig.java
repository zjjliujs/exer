package com.cloudcousion.orderserver.config;

public class OrderServerConfig {
    private static int dispatchRate;   //times per seconds

    public static int getDispatchRate() {
        return dispatchRate;
    }

    public static void setDispatchRate(int dispatchRate) {
        OrderServerConfig.dispatchRate = dispatchRate;
    }
}
