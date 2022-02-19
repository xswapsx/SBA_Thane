package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class GcResultPojo {

    private String name;
    private String nameMar ;
    private String mobile ;
    private String status;
    private String message;
    private String messageMar;
    private boolean isAttendenceOff;
    private String CType;

    public String getCType() {
        return CType;
    }

    public void setCType(String CType) {
        this.CType = CType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameMar() {
        return nameMar;
    }

    public void setNameMar(String nameMar) {
        nameMar = nameMar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public boolean isAttendenceOff() {
        return isAttendenceOff;
    }

    public void setAttendenceOff(boolean attendenceOff) {
        isAttendenceOff = attendenceOff;
    }

    @Override
    public String toString() {
        return "GcResultPojo{" + "name='" + name + '\''
                               + ", NameMar='" + nameMar + '\''
                               + ", mobile='" + mobile + '\''
                               + ", status='" + status + '\''
                               + ", message='" + message + '\''
                               + ", messageMar='" + messageMar + '\''
                               + ", isAttendenceOff='" + isAttendenceOff + '\''+ '}';
    }
}
