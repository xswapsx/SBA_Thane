package com.appynitty.swachbharatabhiyanlibrary.pojos;

/**
 * Created by Ayan Dey on 28/5/19.
 */
public class EmpRegistrationPojo {

    private String gcId;
    private String houseId;
    private String gpId;
    private String dyId;
    private String name;
    private String namemar;
    private String Address;
    private String houseNumber;
    private String areaId;
    private String wardId;
    private String zoneId;
    private String mobileno;
    private String Status;
    private String Message;



    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getGpId() {
        return gpId;
    }

    public void setGpId(String gpId) {
        this.gpId = gpId;
    }

    public String getDyId() {
        return dyId;
    }

    public void setDyId(String dyId) {
        this.dyId = dyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamemar() {
        return namemar;
    }

    public void setNamemar(String namemar) {
        this.namemar = namemar;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return "EmpRegistrationPojo{" +
                "gcId='" + gcId + '\'' +
                ", houseId='" + houseId + '\'' +
                ", gpId='" + gpId + '\'' +
                ", dyId='" + dyId + '\'' +
                ", name='" + name + '\'' +
                ", namemar='" + namemar + '\'' +
                ", Address='" + Address + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", areaId='" + areaId + '\'' +
                ", wardId='" + wardId + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", mobileno='" + mobileno + '\'' +
                ", Status='" + Status + '\'' +
                ", Message='" + Message + '\'' +
                '}';
    }
}
