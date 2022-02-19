package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class ZoneWardAreaMasterPojo {

//  ZONE
    private String name;
    private String zoneId;

//  WardZone
    private String WardID;
    private String WardNo;
    private String Zone;

//  Area
    private String areaMar;
    private String area;
    private String id;
    private String wardId;
    private String Wardno;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getzoneId() {
        return zoneId;
    }

    public void setzoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getWardID() {
        return WardID;
    }

    public void setWardID(String wardID) {
        WardID = wardID;
    }

    public String getWardNo() {
        return WardNo;
    }

    public void setWardNo(String wardNo) {
        WardNo = wardNo;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getAreaMar() {
        return areaMar;
    }

    public void setAreaMar(String areaMar) {
        this.areaMar = areaMar;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getwardId() {
        return wardId;
    }

    public void setwardId(String wardId) {
        this.wardId = wardId;
    }

    public String getWardno() {
        return Wardno;
    }

    public void setWardno(String wardno) {
        Wardno = wardno;
    }

    @Override
    public String toString() {
        return "ZoneWardAreaMasterPojo{" +
                "name='" + name + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", WardID='" + WardID + '\'' +
                ", WardNo='" + WardNo + '\'' +
                ", Zone='" + Zone + '\'' +
                ", areaMar='" + areaMar + '\'' +
                ", area='" + area + '\'' +
                ", id='" + id + '\'' +
                ", wardId='" + wardId + '\'' +
                ", Wardno='" + Wardno + '\'' +
                '}';
    }
}
