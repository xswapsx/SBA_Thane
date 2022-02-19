package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class WorkHistoryDetailPojo {

    private String time;

    private String Refid;

    private String name;

    private String vehicleNumber;

    private String areaName;

    private String type;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRefid() {
        return Refid;
    }

    public void setRefid(String refid) {
        this.Refid = refid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "WorkHistoryDetailPojo{" +
                "time='" + time + '\'' +
                ", areaName='" + areaName + '\'' +
                ", Refid='" + Refid + '\'' +
                ", type='" + type + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
