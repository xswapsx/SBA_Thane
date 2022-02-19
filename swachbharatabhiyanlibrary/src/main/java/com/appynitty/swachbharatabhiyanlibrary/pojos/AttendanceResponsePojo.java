package com.appynitty.swachbharatabhiyanlibrary.pojos;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;

/**
 * Created by Ayan Dey on 3/12/19.
 */
public class AttendanceResponsePojo extends ResultPojo {

    private String IsInSync;

    private String IsOutSync;

    private String isAttendenceOff;

    private String ID;

    public String getIsInSync() {
        return IsInSync;
    }

    public void setIsInSync(String isInSync) {
        IsInSync = isInSync;
    }

    public String getIsOutSync() {
        return IsOutSync;
    }

    public void setIsOutSync(String isOutSync) {
        IsOutSync = isOutSync;
    }

    public String getIsAttendenceOff() {
        return isAttendenceOff;
    }

    public void setIsAttendenceOff(String isAttendenceOff) {
        this.isAttendenceOff = isAttendenceOff;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "AttendanceResponsePojo{" +
                "IsInSync='" + IsInSync + '\'' +
                ", IsOutSync='" + IsOutSync + '\'' +
                ", isAttendenceOff='" + isAttendenceOff + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }
}
