package com.appynitty.swachbharatabhiyanlibrary.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HouseCTypePojo {

    @SerializedName("houseid")
    @Expose
    private String houseid;
    @SerializedName("houseNumber")
    @Expose
    private String houseNumber;
    @SerializedName("Ctype")
    @Expose
    private String ctype;
    @SerializedName("THcount")
    @Expose
    private String houseCount;

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

    public Object getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(String houseCount) {
        this.houseCount = houseCount;
    }

    //THcount
}
