package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class EmpLoginPojo {

    private String qrEmpLoginId;

    private String qrEmpPassword;

    public String getQrEmpLoginId() {
        return qrEmpLoginId;
    }

    public void setQrEmpLoginId(String qrEmpLoginId) {
        this.qrEmpLoginId = qrEmpLoginId;
    }

    public String getQrEmpPassword() {
        return qrEmpPassword;
    }

    public void setQrEmpPassword(String qrEmpPassword) {
        this.qrEmpPassword = qrEmpPassword;
    }

    @Override
    public String toString() {
        return "EmpLoginPojo{" +
                "qrEmpLoginId='" + qrEmpLoginId + '\'' +
                ", qrEmpPassword='" + qrEmpPassword + '\'' +
                '}';
    }
}
