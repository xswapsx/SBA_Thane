package com.appynitty.swachbharatabhiyanlibrary.pojos;


/**
 * Created by Swapnil Lanjewar on 08/01/2022.
 */

public class EmpOfflineCollectionCount {

    String houseCount;
    String dumpYardCount;
    String streetSweepCount;
    String liquidWasteCount;
    String commercialWasteCount;
    String date;

    /**** added by Rahul Rokade ***/
    String resNCollection;
    String resBCollection;
    String resSCollection;
    String commercialCollection;
    String cadCollection;
    String hortCollection;
    String ctptCollection;
    String swmCollection;

    public EmpOfflineCollectionCount(String houseCount, String dumpYardCount, String streetSweepCount, String liquidWasteCount, String date,String resNCollection,String resBCollection,String resSCollection,String commercialCollection,String cadCollection,String hortCollection,String ctptCollection, String swmCollection) {
        this.houseCount = houseCount;
        this.dumpYardCount = dumpYardCount;
        this.streetSweepCount = streetSweepCount;
        this.liquidWasteCount = liquidWasteCount;
        this.commercialWasteCount = commercialWasteCount;
        this.date = date;
        this.resNCollection = resNCollection;
        this.resBCollection = resBCollection;
        this.resSCollection = resSCollection;
        this.commercialCollection = commercialCollection;
        this.cadCollection = cadCollection;
        this.hortCollection = hortCollection;
        this.ctptCollection = ctptCollection;
        this.swmCollection = swmCollection;
    }

    public String getCtptCollection() {
        return ctptCollection;
    }

    public void setCtptCollection(String ctptCollection) {
        this.ctptCollection = ctptCollection;
    }

    public String getSwmCollection() {
        return swmCollection;
    }

    public void setSwmCollection(String swmCollection) {
        this.swmCollection = swmCollection;
    }

    public String getResNCollection() {
        return resNCollection;
    }

    public void setResNCollection(String resNCollection) {
        this.resNCollection = resNCollection;
    }

    public String getResBCollection() {
        return resBCollection;
    }

    public void setResBCollection(String resBCollection) {
        this.resBCollection = resBCollection;
    }

    public String getResSCollection() {
        return resSCollection;
    }

    public void setResSCollection(String resSCollection) {
        this.resSCollection = resSCollection;
    }

    public String getCommercialCollection() {
        return commercialCollection;
    }

    public void setCommercialCollection(String commercialCollection) {
        this.commercialCollection = commercialCollection;
    }

    public String getCadCollection() {
        return cadCollection;
    }

    public void setCadCollection(String cadCollection) {
        this.cadCollection = cadCollection;
    }

    public String getHortCollection() {
        return hortCollection;
    }

    public void setHortCollection(String hortCollection) {
        this.hortCollection = hortCollection;
    }

    public String getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(String houseCount) {
        this.houseCount = houseCount;
    }

    public String getDumpYardCount() {
        return dumpYardCount;
    }

    public void setDumpYardCount(String dumpYardCount) {
        this.dumpYardCount = dumpYardCount;
    }

    public String getStreetSweepCount() {
        return streetSweepCount;
    }

    public void setStreetSweepCount(String streetSweepCount) {
        this.streetSweepCount = streetSweepCount;
    }

    public String getLiquidWasteCount() {
        return liquidWasteCount;
    }

    public void setLiquidWasteCount(String liquidWasteCount) {
        this.liquidWasteCount = liquidWasteCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
