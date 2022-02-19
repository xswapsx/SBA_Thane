package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class OutPunchPojo {

    private String endLong;

    private String daDate;

    private String userId;

    private String endTime;

    private String endLat;

    public String getEndLong() {
        return endLong;
    }

    public void setEndLong(String endLong) {
        this.endLong = endLong;
    }

    public String getDaDate() {
        return daDate;
    }

    public void setDaDate(String daDate) {
        this.daDate = daDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    @Override
    public String toString() {
        return "OutPunchPojo{" +
                "endLong='" + endLong + '\'' +
                ", daDate='" + daDate + '\'' +
                ", userId='" + userId + '\'' +
                ", endTime='" + endTime + '\'' +
                ", endLat='" + endLat + '\'' +
                '}';
    }
}
