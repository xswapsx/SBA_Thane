package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class EmpOutPunchPojo {

    private String qrEmpId;

    private String endLat;

    private String endLong;

    private String endTime;

    private String endDate;

    public String getQrEmpId() {
        return qrEmpId;
    }

    public void setQrEmpId(String qrEmpId) {
        this.qrEmpId = qrEmpId;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getEndLong() {
        return endLong;
    }

    public void setEndLong(String endLong) {
        this.endLong = endLong;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "EmpOutPunchPojo{" +
                "qrEmpId='" + qrEmpId + '\'' +
                ", endLat='" + endLat + '\'' +
                ", endLong='" + endLong + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startDate='" + endDate + '\'' +
                '}';
    }
}
