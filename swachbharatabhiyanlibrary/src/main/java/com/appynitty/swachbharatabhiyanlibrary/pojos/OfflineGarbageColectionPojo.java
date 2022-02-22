package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class OfflineGarbageColectionPojo {

    private String note;

    private String empType;

    private String totalDryWeight;

    private String totalGcWeight;

    private String ReferenceID;

    private String garbageType;

    private String OfflineID;

    private String Long;

    private String gcType;

    private String vehicleNumber;

    private String totalWetWeight;

    private String userId;

    private String Lat;

    private String gcDate;

    private String distance;

    private Boolean isOffline;

    private String gpBeforImage;
    private String gpAfterImage;

    private String CType;
    private String LevelOS;
    private String TNS;
    private String TOT;


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

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTotalDryWeight() {
        return totalDryWeight;
    }

    public void setTotalDryWeight(String totalDryWeight) {
        this.totalDryWeight = totalDryWeight;
    }

    public String getTotalGcWeight() {
        return totalGcWeight;
    }

    public void setTotalGcWeight(String totalGcWeight) {
        this.totalGcWeight = totalGcWeight;
    }

    public String getReferenceID() {
        return ReferenceID;
    }

    public void setReferenceID(String referenceID) {
        ReferenceID = referenceID;
    }

    public String getGarbageType() {
        return garbageType;
    }

    public void setGarbageType(String garbageType) {
        this.garbageType = garbageType;
    }

    public String getOfflineID() {
        return OfflineID;
    }

    public void setOfflineID(String offlineID) {
        OfflineID = offlineID;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public String getGcType() {
        return gcType;
    }

    public void setGcType(String gcType) {
        this.gcType = gcType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getTotalWetWeight() {
        return totalWetWeight;
    }

    public void setTotalWetWeight(String totalWetWeight) {
        this.totalWetWeight = totalWetWeight;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getGcDate() {
        return gcDate;
    }

    public void setGcDate(String gcDate) {
        this.gcDate = gcDate;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Boolean getIsOffline() {
        return this.isOffline;
    }

    public void setIsOffline(Boolean isOffline) {
        this.isOffline = isOffline;
    }

    public String getCType() {
        return CType;
    }

    public void setCType(String CType) {
        this.CType = CType;
    }

    @Override
    public String toString() {
        return "OfflineGarbageColectionPojo{" +
                "note='" + note + '\'' +
                ", totalDryWeight='" + totalDryWeight + '\'' +
                ", totalGcWeight='" + totalGcWeight + '\'' +
                ", ReferenceID='" + ReferenceID + '\'' +
                ", garbageType='" + garbageType + '\'' +
                ", OfflineID='" + OfflineID + '\'' +
                ", Long='" + Long + '\'' +
                ", gcType='" + gcType + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", totalWetWeight='" + totalWetWeight + '\'' +
                ", userId='" + userId + '\'' +
                ", Lat='" + Lat + '\'' +
                ", gcDate='" + gcDate + '\'' +
                ", distance='" + distance + '\'' +
                ", isOffline='" + isOffline + '\'' +
                ", gpBeforImage='" + gpBeforImage + '\'' +
                ", gpAfterImage='" + gpAfterImage + '\'' +
                '}';
    }
}
