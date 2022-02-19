package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class CheckAttendancePojo {

    private boolean isAttendenceOff;

    private String message;

    private String messageMar;

    private String status;

    public boolean isAttendenceOff() {
        return isAttendenceOff;
    }

    public void setAttendenceOff(boolean attendenceOff) {
        isAttendenceOff = attendenceOff;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageMar() {
        return messageMar;
    }

    public void setMessageMar(String messageMar) {
        this.messageMar = messageMar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CheckAttendancePojo{"
                + "isAttendenceOff=" + isAttendenceOff
                + ", message='" + message + '\''
                + ", messageMar='" + messageMar + '\''
                + ", status='" + status + '\'' + '}';
    }
}
