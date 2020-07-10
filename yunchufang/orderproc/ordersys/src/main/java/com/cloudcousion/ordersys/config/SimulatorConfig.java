package com.cloudcousion.ordersys.config;

public class SimulatorConfig {
    //kitchen shelf config
    public int tempShelfCapacity = 10;
    public int overflowShelfCapacity = 15;
    //Courier config
    public int courierDelayMin = 2000;  //milliseconds
    public int courierDelayMax = 6000;  //milliseconds
    //Should in ascend or descend order! Because I used binarySearch on it
    public Integer[] dispatchRates = {2, 4, 6, 10, 16, 26, 42};
    public Integer defaultDispatchRate = 2;
}
