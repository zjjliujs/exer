package com.cloudcousion.orderserver.model;

public enum OrderTemperature {
    Hot, Cold, Frozen;

    @Override
    public String toString() {
        switch (this) {
            case Hot:
                return "Hot";
            case Cold:
                return "Cold";
            case Frozen:
                return "Fronzen";
            default:
                return "Error";
        }
    }
}
