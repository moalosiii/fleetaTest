package com.example.fleetatest.datatables;

public class Trip {
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public long getOdometer() {
        return Odometer;
    }

    public void setOdometer(long odometer) {
        Odometer = odometer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVehicleIssues() {
        return vehicleIssues;
    }

    public void setVehicleIssues(String vehicleIssues) {
        this.vehicleIssues = vehicleIssues;
    }

    private String vehicleId;
    private String driverId;
    private long Odometer;
    private String location;
    private String vehicleIssues;

}
