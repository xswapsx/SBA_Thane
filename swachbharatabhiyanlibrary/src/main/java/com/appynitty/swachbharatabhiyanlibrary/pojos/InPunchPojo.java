package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class InPunchPojo {

    private String startTime;

    private String daDate;

    private String startLong;

    private String startLat;

    private String userId;

    private String vtId;

    private String vehicleNumber;

    public String getEmpType() {
        return EmpType;
    }

    public void setEmpType(String empType) {
        EmpType = empType;
    }

    private String EmpType;

    public String getDaDate() {
        return daDate;
    }

    public void setDaDate(String daDate) {
        this.daDate = daDate;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartLong() {
        return startLong;
    }

    public void setStartLong(String startLong) {
        this.startLong = startLong;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVtId() {
        return vtId;
    }

    public void setVtId(String vtId) {
        this.vtId = vtId;
    }

    @Override
    public String toString() {
        return "InPunchPojo{" +
                "startTime='" + startTime + '\'' +
                ", daDate='" + daDate + '\'' +
                ", startLong='" + startLong + '\'' +
                ", startLat='" + startLat + '\'' +
                ", userId='" + userId + '\'' +
                ", vtId='" + vtId + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                '}';
    }
}
