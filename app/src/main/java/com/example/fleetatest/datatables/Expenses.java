package com.example.fleetatest.datatables;

public class Expenses {

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

    public double getInsurance() {
        return insurance;
    }

    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }

    public double getFines() {
        return fines;
    }

    public void setFines(double fines) {
        this.fines = fines;
    }

    public double getParking() {
        return parking;
    }

    public void setParking(double parking) {
        this.parking = parking;
    }

    public double getTollgate() {
        return tollgate;
    }

    public void setTollgate(double tollgate) {
        this.tollgate = tollgate;
    }

    private String vehicleId;
    private String driverId;
    private double insurance;
    private double fines;
    private double parking;
    private double tollgate;
}
