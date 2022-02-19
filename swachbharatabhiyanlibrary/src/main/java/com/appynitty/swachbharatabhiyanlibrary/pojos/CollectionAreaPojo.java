package com.appynitty.swachbharatabhiyanlibrary.pojos;

/**
 * Created by Ayan Dey on 26/11/18.
 */
public class CollectionAreaPojo {

    private String id;

    private String area;

    private String areaMar;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getArea ()
    {
        return area;
    }

    public void setArea (String area)
    {
        this.area = area;
    }

    public String getAreaMar ()
    {
        return areaMar;
    }

    public void setAreaMar (String areaMar)
    {
        this.areaMar = areaMar;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", area = "+area+", areaMar = "+areaMar+"]";
    }

}
