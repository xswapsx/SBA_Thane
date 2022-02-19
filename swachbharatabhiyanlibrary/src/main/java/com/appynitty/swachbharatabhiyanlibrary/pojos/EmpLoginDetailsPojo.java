package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class EmpLoginDetailsPojo {

    private String message;

    private String status;

    private String qrEmpId;

    private String qrEmpPassword;

    private String qrEmpLoginId;

    private String type;

    private String messageMar;

    private boolean gtFeatures;

    private String appId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQrEmpId() {
        return qrEmpId;
    }

    public void setQrEmpId(String qrEmpId) {
        this.qrEmpId = qrEmpId;
    }

    public String getQrEmpPassword() {
        return qrEmpPassword;
    }

    public void setQrEmpPassword(String qrEmpPassword) {
        this.qrEmpPassword = qrEmpPassword;
    }

    public String getQrEmpLoginId() {
        return qrEmpLoginId;
    }

    public void setQrEmpLoginId(String qrEmpLoginId) {
        this.qrEmpLoginId = qrEmpLoginId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageMar() {
        return messageMar;
    }

    public void setMessageMar(String messageMar) {
        this.messageMar = messageMar;
    }

    public boolean isGtFeatures() {
        return gtFeatures;
    }

    public void setGtFeatures(boolean gtFeatures) {
        this.gtFeatures = gtFeatures;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "EmpLoginDetailsPojo{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", qrEmpId='" + qrEmpId + '\'' +
                ", qrEmpPassword='" + qrEmpPassword + '\'' +
                ", qrEmpLoginId='" + qrEmpLoginId + '\'' +
                ", type='" + type + '\'' +
                ", messageMar='" + messageMar + '\'' +
                ", gtFeatures=" + gtFeatures +
                ", appId='" + appId + '\'' +
                '}';
    }
}
