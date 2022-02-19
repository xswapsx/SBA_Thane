package com.appynitty.swachbharatabhiyanlibrary.pojos;

/**
 * Created by Ayan Dey on 28/11/19.
 */
public class AttendancePojo {

    private String startLat;

    private String OfflineID;

    private String userId;

    private String vtId;

    private String startLong;

    private String daDate;

    private String endLat;

    private String batteryStatus;

    private String endLong;

    private String vehicleNumber;

    private String startTime;

    private String endTime;

    private String daEndDate;

    private String imagePath;

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getOfflineID() {
        return OfflineID;
    }

    public void setOfflineID(String offlineID) {
        OfflineID = offlineID;
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

    public String getStartLong() {
        return startLong;
    }

    public void setStartLong(String startLong) {
        this.startLong = startLong;
    }

    public String getDaDate() {
        return daDate;
    }

    public void setDaDate(String daDate) {
        this.daDate = daDate;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getEndLong() {
        return endLong;
    }

    public void setEndLong(String endLong) {
        this.endLong = endLong;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDaEndDate() {
        return daEndDate;
    }

    public void setDaEndDate(String daEndDate) {
        this.daEndDate = daEndDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "AttendancePojo{"
                + "startLat='" + startLat + '\''
                + ", OfflineID='" + OfflineID + '\''
                + ", userId='" + userId + '\''
                + ", vtId='" + vtId + '\''
                + ", startLong='" + startLong + '\''
                + ", daDate='" + daDate + '\''
                + ", endLat='" + endLat + '\''
                + ", batteryStatus='" + batteryStatus + '\''
                + ", endLong='" + endLong + '\''
                + ", vehicleNumber='" + vehicleNumber + '\''
                + ", startTime='" + startTime + '\''
                + ", endTime='" + endTime + '\''
                + ", daEndDate='" + daEndDate + '\''
                + ", imagePath='" + imagePath + '\'' + '}';
    }
}
