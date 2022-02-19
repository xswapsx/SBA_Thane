package com.appynitty.swachbharatabhiyanlibrary.pojos;

/**
 * Created by Ayan Dey on 29/04/19.
 */
public class CollectionDumpYardPointPojo {

    private String dyId;

    private String dyName;

    public String getDyId ()
    {
        return dyId;
    }

    public void setDyId (String dyId)
    {
        this.dyId = dyId;
    }

    public String getDyName ()
    {
        return dyName;
    }

    public void setDyName (String dyName)
    {
        this.dyName = dyName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dyId = "+dyId+", dyName = "+dyName+"]";
    }

}
