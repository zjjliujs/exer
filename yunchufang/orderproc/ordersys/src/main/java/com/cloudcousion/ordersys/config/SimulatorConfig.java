package com.cloudcousion.ordersys.config;

public class SimulatorConfig {
    //kitchen shelf config
    public int hotShelfCapacity = 10;
    public int coldShelfCapacity = 10;
    public int frozenShelfCapacity = 10;
    public int overflowShelfCapacity = 15;

    //Courier config
    public int courierDelayMin = 2000;  //milliseconds
    public int courierDelayMax = 6000;  //milliseconds

    public Integer dispatchRate = 2;
}
