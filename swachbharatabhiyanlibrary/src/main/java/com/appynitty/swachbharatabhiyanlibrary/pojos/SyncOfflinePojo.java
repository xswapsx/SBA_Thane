package com.appynitty.swachbharatabhiyanlibrary.pojos;

/**
 * Created by Ayan Dey on 17/9/19.
 */
public class SyncOfflinePojo {

    private String OfflineID;
    private String Lat;
    private String Long;
    private String note;
    private String userId;
    private String ReferenceID;
    private String vehicleNumber;
    private String garbageType;
    private String gcType;
    private String totalGcWeight;
    private String totalDryWeight;
    private String totalWetWeight;
    private String gcDate;
    private String batteryStatus;
    private String Distance;
    private String IsLocation;
    private Boolean isOffline;
    private String empType;         //added by swapnil

    private String gpBeforImage;
    private String gpBeforImageTime;
    private String gpAfterImage;
    private String gpAfterImageTime;

    private String LevelOS;
    private String TNS;
    private String TOT;
    private String TOR;

    private String Wet;
    private String Dry;
    private String Domestic;
    private String Sanitary;


    public String getGpBeforImageTime() {
        return gpBeforImageTime;
    }

    public void setGpBeforImageTime(String gpBeforImageTime) {
        this.gpBeforImageTime = gpBeforImageTime;
    }

    public String getGpAfterImageTime() {
        return gpAfterImageTime;
    }

    public void setGpAfterImageTime(String gpAfterImageTime) {
        this.gpAfterImageTime = gpAfterImageTime;
    }

    public String getWet() {
        return Wet;
    }

    public void setWet(String wet) {
        Wet = wet;
    }

    public String getDry() {
        return Dry;
    }

    public void setDry(String dry) {
        Dry = dry;
    }

    public String getDomestic() {
        return Domestic;
    }

    public void setDomestic(String domestic) {
        Domestic = domestic;
    }

    public String getSanitary() {
        return Sanitary;
    }

    public void setSanitary(String sanitary) {
        Sanitary = sanitary;
    }

    public String getTOR() {
        return TOR;
    }

    public void setTOR(String TOR) {
        this.TOR = TOR;
    }

    public String getTNS() {
        return TNS;
    }

    public void setTNS(String TNS) {
        this.TNS = TNS;
    }

    public String getTOT() {
        return TOT;
    }

    public void setTOT(String TOT) {
        this.TOT = TOT;
    }

    public String getLevelOS() {
        return LevelOS;
    }

    public void setLevelOS(String levelOS) {
        LevelOS = levelOS;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getGpBeforImage() {
        return gpBeforImage;
    }

    public void setGpBeforImage(String gpBeforImage) {
        this.gpBeforImage = gpBeforImage;
    }

    public String getGpAfterImage() {
        return gpAfterImage;
    }

    public void setGpAfterImage(String gpAfterImage) {
        this.gpAfterImage = gpAfterImage;
    }


    public Boolean getOffline() {
        return isOffline;
    }

    public void setOffline(Boolean offline) {
        isOffline = offline;
    }


    public String getOfflineID() {
        return OfflineID;
    }

    public void setOfflineID(String offlineID) {
        OfflineID = offlineID;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReferenceID() {
        return ReferenceID;
    }

    public void setReferenceID(String referenceID) {
        ReferenceID = referenceID;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getGarbageType() {
        return garbageType;
    }

    public void setGarbageType(String garbageType) {
        this.garbageType = garbageType;
    }

    public String getGcType() {
        return gcType;
    }

    public void setGcType(String gcType) {
        this.gcType = gcType;
    }

    public String getTotalGcWeight() {
        return totalGcWeight;
    }

    public void setTotalGcWeight(String totalGcWeight) {
        this.totalGcWeight = totalGcWeight;
    }

    public String getTotalDryWeight() {
        return totalDryWeight;
    }

    public void setTotalDryWeight(String totalDryWeight) {
        this.totalDryWeight = totalDryWeight;
    }

    public String getTotalWetWeight() {
        return totalWetWeight;
    }

    public void setTotalWetWeight(String totalWetWeight) {
        this.totalWetWeight = totalWetWeight;
    }

    public String getGcDate() {
        return gcDate;
    }

    public void setGcDate(String gcDate) {
        this.gcDate = gcDate;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getIsLocation() {
        return IsLocation;
    }

    public void setIsLocation(String isLocation) {
        IsLocation = isLocation;
    }

    public Boolean getIsOffline() {
        return this.isOffline;
    }

    public void setIsOffline(Boolean offline) {
        this.isOffline = offline;
    }

    @Override
    public String toString() {
        return "SyncOfflinePojo{" +
                "OfflineID='" + OfflineID + '\'' +
                ", Lat='" + Lat + '\'' +
                ", Long='" + Long + '\'' +
                ", note='" + note + '\'' +
                ", userId='" + userId + '\'' +
                ", ReferenceID='" + ReferenceID + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", garbageType='" + garbageType + '\'' +
                ", gcType='" + gcType + '\'' +
                ", totalGcWeight='" + totalGcWeight + '\'' +
                ", totalDryWeight='" + totalDryWeight + '\'' +
                ", totalWetWeight='" + totalWetWeight + '\'' +
                ", gcDate='" + gcDate + '\'' +
                ", batteryStatus='" + batteryStatus + '\'' +
                ", Distance='" + Distance + '\'' +
                ", IsLocation='" + IsLocation + '\'' +
                ", isOffline='" + isOffline + '\'' +
                ", gpBeforImage='" + gpBeforImage + '\'' +
                ", gpAfterImage='" + gpAfterImage + '\'' +
                '}';
    }
}
