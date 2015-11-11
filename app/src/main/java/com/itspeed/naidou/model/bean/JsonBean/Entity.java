package com.itspeed.naidou.model.bean.JsonBean;

/**
 * Created by jafir on 10/27/15.
 * API返回数据的基础格式
 */
public class Entity<T> {

    //接口操作是否成功 比如点赞是否成功等
    private boolean isSuccess;
    //访问服务器是否成功
    private boolean status;
    //如果访问失败 则返回code  比如404
    private int errorCode;
    //如果失败 返回的提示消息 比如 密码错误
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //    //数据内容  可以再解析
    private T data;


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "Entity{" +
                "isSuccess=" + isSuccess +
                ", status=" + status +
                ", errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
