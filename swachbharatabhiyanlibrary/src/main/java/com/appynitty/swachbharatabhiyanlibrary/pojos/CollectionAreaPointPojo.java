package com.appynitty.swachbharatabhiyanlibrary.pojos;

/**
 * Created by Ayan Dey on 26/11/18.
 */
public class CollectionAreaPointPojo {

    private String gpId;

    private String gpName;

    public String getGpId ()
    {
        return gpId;
    }

    public void setGpId (String gpId)
    {
        this.gpId = gpId;
    }

    public String getGpName ()
    {
        return gpName;
    }

    public void setGpName (String gpName)
    {
        this.gpName = gpName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [gpId = "+gpId+", gpName = "+gpName+"]";
    }

}
