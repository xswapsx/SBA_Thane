package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class UserLocationResultPojo {

    private boolean isAttendenceOff;

    private String message;

    private String messageMar;

    private String status;

    private String ID;

    public boolean getIsAttendenceOff() {
        return isAttendenceOff;
    }

    public void setIsAttendenceOff(boolean isAttendenceOff) {
        this.isAttendenceOff = isAttendenceOff;
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

    public String getId() {
        return ID;
    }

    public void setId(String id) {
        this.ID = id;
    }

    @Override
    public String toString() {
        return "UserLocationResultPojo{"
                + "isAttendenceOff='" + isAttendenceOff + '\''
                + ", message='" + message + '\''
                + ", messageMar='" + messageMar + '\''
                + ", status='" + status + '\''
                + ", id='" + ID + '\''+ '}';
    }
}
