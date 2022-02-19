package com.appynitty.swachbharatabhiyanlibrary.pojos;

import androidx.annotation.NonNull;

public class UserLocationPojo {

    private String OfflineId;

    private String userId;

    private String Long;

    private String datetime;

    private String lat;

    private String distance;

    private Boolean isOffline;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getOfflineId() {
        return OfflineId;
    }

    public void setOfflineId(String id) {
        this.OfflineId = id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Boolean getIsOffline() {
        return isOffline;
    }

    public void setIsOffline(Boolean offline) {
        isOffline = offline;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserLocationPojo{" +
                "OfflineId='" + OfflineId + '\'' +
                ", userId='" + userId + '\'' +
                ", Long='" + Long + '\'' +
                ", datetime='" + datetime + '\'' +
                ", lat='" + lat + '\'' +
                ", distance='" + distance + '\'' +
                ", isOffline='" + isOffline + '\'' +
                '}';
    }
}
