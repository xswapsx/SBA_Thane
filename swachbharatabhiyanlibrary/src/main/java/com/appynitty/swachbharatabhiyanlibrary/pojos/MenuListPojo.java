package com.appynitty.swachbharatabhiyanlibrary.pojos;

/**
 * Created by Ayan Dey on 25/10/18.
 */
public class MenuListPojo {

    private String menuName;
    private Integer image;
    private Class<?> nextIntentClass;
    private Boolean checkAttendance;

    public MenuListPojo(String menuName, Integer image) {
        this.menuName = menuName;
        this.image = image;
    }

    public MenuListPojo(String menuName, Integer image, Class<?> nextIntentClass, Boolean checkAttendance) {
        this.menuName = menuName;
        this.image = image;
        this.nextIntentClass = nextIntentClass;
        this.checkAttendance = checkAttendance;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Class<?> getNextIntentClass() {
        return nextIntentClass;
    }

    public void setNextIntentClass(Class<?> nextIntentClass) {
        this.nextIntentClass = nextIntentClass;
    }

    public Boolean getCheckAttendance() {
        return checkAttendance;
    }

    public void setCheckAttendance(Boolean checkAttendance) {
        this.checkAttendance = checkAttendance;
    }
}
