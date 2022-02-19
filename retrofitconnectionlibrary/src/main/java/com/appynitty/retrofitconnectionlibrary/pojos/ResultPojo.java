package com.appynitty.retrofitconnectionlibrary.pojos;

import androidx.annotation.Nullable;

public class ResultPojo {

    private String status;
    private String message;
    @Nullable
    private String messageMar;

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

    @Override
    public String toString() {
        return "ResultPojo{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", messageMar='" + messageMar + '\'' +
                '}';
    }
}
