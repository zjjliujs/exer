package com.cloudcousion.ordersys.config;

public class SimulatorConfig {
    //kitchen shelf config
    public int hotShelfCapacity = 10;
    public int coldShelfCapacity = 10;
    public int frozenShelfCapacity = 10;
    public int overflowShelfCapacity = 15;

    //Courier config
    public int courierDelayMin = 2;  //seconds
    public int courierDelayMax = 6;  //seconds

    public Integer dispatchRate = 2;
}
