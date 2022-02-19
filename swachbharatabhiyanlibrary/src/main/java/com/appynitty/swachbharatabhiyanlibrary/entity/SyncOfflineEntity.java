package com.appynitty.swachbharatabhiyanlibrary.entity;

/**
 * Created by Ayan Dey on 17/9/19.
 * Contains getters and setters for offline sync of data from Ghanta Gadi Employee
 */
public class SyncOfflineEntity {

    private String offlineId;
    private String offlineData;
    private String offlineRefId;
    private String offlineGcType;
    //added by rahul
    private String offlineCType;
    private String offlineRNC;
    private String offlineRBC;
    private String offlineRSC;
    private String offlineCW;
    private String offlineIsLocation;
    private String offlineDate;
    private String empType;

    public String getOfflineRNC() {
        return offlineRNC;
    }

    public void setOfflineRNC(String offlineRNC) {
        this.offlineRNC = offlineRNC;
    }

    public String getOfflineRBC() {
        return offlineRBC;
    }

    public void setOfflineRBC(String offlineRBC) {
        this.offlineRBC = offlineRBC;
    }

    public String getOfflineRSC() {
        return offlineRSC;
    }

    public void setOfflineRSC(String offlineRSC) {
        this.offlineRSC = offlineRSC;
    }

    public String getOfflineCW() {
        return offlineCW;
    }

    public void setOfflineCW(String offlineCW) {
        this.offlineCW = offlineCW;
    }

    public String getOfflineCType() {
        return offlineCType;
    }

    public void setOfflineCType(String offlineCType) {
        this.offlineCType = offlineCType;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getOfflineId() {
        return offlineId;
    }

    public void setOfflineId(String offlineId) {
        this.offlineId = offlineId;
    }

    public String getOfflineData() {
        return offlineData;
    }

    public void setOfflineData(String offlineData) {
        this.offlineData = offlineData;
    }

    public String getOfflineRefId() {
        return offlineRefId;
    }

    public void setOfflineRefId(String offlineRefId) {
        this.offlineRefId = offlineRefId;
    }

    public String getOfflineGcType() {
        return offlineGcType;
    }

    public void setOfflineGcType(String offlineGcType) {
        this.offlineGcType = offlineGcType;
    }

    public String getOfflineIsLocation() {
        return offlineIsLocation;
    }

    public void setOfflineIsLocation(String offlineIsLocation) {
        this.offlineIsLocation = offlineIsLocation;
    }

    public String getOfflineDate() {
        return offlineDate;
    }

    public void setOfflineDate(String offlineDate) {
        this.offlineDate = offlineDate;
    }

    @Override
    public String toString() {
        return "SyncOfflineEntity{" +
                "offlineId='" + offlineId + '\'' +
                ", offlineData='" + offlineData + '\'' +
                ", offlineRefId='" + offlineRefId + '\'' +
                ", offlineGcType='" + offlineGcType + '\'' +
                ", offlineCType='" + offlineCType + '\'' +

                ", offlineRNC='" + offlineRNC + '\'' +
                ", offlineRBC='" + offlineRBC + '\'' +
                ", offlineRSC='" + offlineRSC + '\'' +
                ", offlineCW='" + offlineCW + '\'' +

                ", offlineIsLocation='" + offlineIsLocation + '\'' +
                ", offlineDate='" + offlineDate + '\'' +
                ", offlineEmpType='" + empType + '\'' +
                '}';
    }
}
