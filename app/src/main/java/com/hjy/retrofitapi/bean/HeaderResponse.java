package com.hjy.retrofitapi.bean;

public class HeaderResponse {

    private int status;//响应码
    private String message;//描述
    private String markid;//缓存ID

    public String getMarkid () {
        return markid;
    }

    public void setMarkid (String markid) {
        this.markid = markid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HeaderResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", markid='" + markid + '\'' +
                '}';
    }
}
