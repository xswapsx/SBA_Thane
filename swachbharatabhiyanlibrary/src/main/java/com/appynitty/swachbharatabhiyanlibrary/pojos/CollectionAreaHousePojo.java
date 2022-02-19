package com.appynitty.swachbharatabhiyanlibrary.pojos;

/**
 * Created by Ayan Dey on 26/11/18.
 */
public class CollectionAreaHousePojo {

    private String houseid;

    private String houseNumber;

    public String getHouseid ()
    {
        return houseid;
    }

    public void setHouseid (String houseid)
    {
        this.houseid = houseid;
    }

    public String getHouseNumber ()
    {
        return houseNumber;
    }

    public void setHouseNumber (String houseNumber)
    {
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [houseid = "+houseid+", houseNumber = "+houseNumber+"]";
    }

}
