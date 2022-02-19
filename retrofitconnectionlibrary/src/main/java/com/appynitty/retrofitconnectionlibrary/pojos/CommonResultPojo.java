package com.appynitty.retrofitconnectionlibrary.pojos;

/**
 * Created by Richali Pradhan Gupte on 2019-11-22.
 */
public class CommonResultPojo {

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CommonResultPojo{" + "code='" + code + '\'' + ", message='" + message + '\'' + '}';
    }   
}
