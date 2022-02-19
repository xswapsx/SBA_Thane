package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class OfflineGcResultPojo {

    private String ID;

    private String message;

    private String messageMar;

    private String status;

    private String CType;  //Swapnil

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getCType() {
        return CType;
    }

    public void setCType(String CType) {
        this.CType = CType;
    }

    @Override
    public String toString() {
        return "OfflineGcResultPojo{"
                + "ID='" + ID + '\''
                + ", message='" + message + '\''
                + ", messageMar='" + messageMar + '\''
                + ", status='" + status + '\'' + '}';
    }
}
