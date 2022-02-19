package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class EmpWorkHistoryDetailPojo {

    private String time;

    private String Date;

    private String type;

    private String HouseNo;

    private String DumpYardNo;

    private String LiquidNo;

    private String StreetNo;

    private String PointNo;

    private String ResidentNO;
    private String ResidentBNO;
    private String ResidentSNO;
    private String CommercialNO;

    public String getLiquidNo() {
        return LiquidNo;
    }

    public void setLiquidNo(String liquidNo) {
        LiquidNo = liquidNo;
    }

    public String getStreetNo() {
        return StreetNo;
    }

    public void setStreetNo(String streetNo) {
        StreetNo = streetNo;
    }

    public String getResidentNO() {
        return ResidentNO;
    }

    public void setResidentNO(String residentNO) {
        ResidentNO = residentNO;
    }

    public String getResidentBNO() {
        return ResidentBNO;
    }

    public void setResidentBNO(String residentBNO) {
        ResidentBNO = residentBNO;
    }

    public String getResidentSNO() {
        return ResidentSNO;
    }

    public void setResidentSNO(String residentSNO) {
        ResidentSNO = residentSNO;
    }

    public String getCommercialNO() {
        return CommercialNO;
    }

    public void setCommercialNO(String commercialNO) {
        CommercialNO = commercialNO;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLiquidWasteNo() {
        return LiquidNo;
    }

    public void setLiquidWasteNo(String liquidWasteNo) {
        LiquidNo = liquidWasteNo;
    }

    public String getStreetWasteNo() {
        return StreetNo;
    }

    public void setStreetWasteNo(String streetWasteNo) {
        StreetNo = streetWasteNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String houseNo) {
        HouseNo = houseNo;
    }

    public String getDumpYardNo() {
        return DumpYardNo;
    }

    public void setDumpYardNo(String dumpYardNo) {
        DumpYardNo = dumpYardNo;
    }

    public String getPointNo() {
        return PointNo;
    }

    public void setPointNo(String pointNo) {
        PointNo = pointNo;
    }

    @Override
    public String toString() {
        return "EmpWorkHistoryDetailPojo{" +
                "time='" + time + '\'' +
                ", Date='" + Date + '\'' +
                ", type='" + type + '\'' +
                ", HouseNo='" + HouseNo + '\'' +
                ", DumpYardNo='" + DumpYardNo + '\'' +
                ", PointNo='" + PointNo + '\'' +
                '}';
    }
}
