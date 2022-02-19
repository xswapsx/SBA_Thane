package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class EmpInPunchPojo {

    private String qrEmpId;

    private String startLat;

    private String startLong;

    private String startTime;

    private String startDate;

    public String getQrEmpId() {
        return qrEmpId;
    }

    public void setQrEmpId(String qrEmpId) {
        this.qrEmpId = qrEmpId;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLong() {
        return startLong;
    }

    public void setStartLong(String startLong) {
        this.startLong = startLong;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "EmpInPunchPojo{" +
                "qrEmpId='" + qrEmpId + '\'' +
                ", startLat='" + startLat + '\'' +
                ", startLong='" + startLong + '\'' +
                ", startTime='" + startTime + '\'' +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
