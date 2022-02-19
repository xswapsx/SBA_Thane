package com.appynitty.swachbharatabhiyanlibrary.entity;


import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

public class UserDailyAttendanceEntity {

    private String AttendanceId;
    private String AttendanceData;
    private String IsAttendanceIn;
    private String AttendanceInDate;
    private String AttendanceOutDate;
    private String AttendanceInSync;
    private String AttendanceOutSync;

    public String getAttendanceId() {
        return AttendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        AttendanceId = attendanceId;
    }

    public String getAttendanceData() {
        return AttendanceData;
    }

    public void setAttendanceData(String attendanceData) {
        AttendanceData = attendanceData;
    }

    public String getIsAttendanceIn() {
        return IsAttendanceIn;
    }

    public void setIsAttendanceIn(String isAttendanceIn) {
        IsAttendanceIn = isAttendanceIn;
    }

    public String getAttendanceInDate() {
        return AttendanceInDate;
    }

    public void setAttendanceInDate(String attendanceInDate) {
        AttendanceInDate = attendanceInDate;
    }

    public String getAttendanceOutDate() {
        return AttendanceOutDate;
    }

    public void setAttendanceOutDate(String attendanceOutDate) {
        AttendanceOutDate = attendanceOutDate;
    }

    public String getAttendanceInSync() {
        return AttendanceInSync;
    }

    public void setAttendanceInSync(String attendanceInSync) {
        AttendanceInSync = attendanceInSync;
    }

    public String getAttendanceOutSync() {
        return AttendanceOutSync;
    }

    public void setAttendanceOutSync(String attendanceOutSync) {
        AttendanceOutSync = attendanceOutSync;
    }

    @Override
    public String toString() {
        return "UserDailyAttendanceEntity{" +
                "AttendanceId='" + AttendanceId + '\'' +
                ", AttendanceData='" + AttendanceData + '\'' +
                ", IsAttendanceIn='" + IsAttendanceIn + '\'' +
                ", AttendanceInDate='" + AttendanceInDate + '\'' +
                ", AttendanceOutDate='" + AttendanceOutDate + '\'' +
                ", AttendanceInSync='" + AttendanceInSync + '\'' +
                ", AttendanceOutSync='" + AttendanceOutSync + '\'' +
                '}';
    }
}
