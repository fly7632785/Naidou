package com.itspeed.naidou.model.bean.JsonBean;

/**
 * Created by jafir on 10/27/15.
 * API返回数据的基础格式
 */
public class Entity<T> {

    //接口操作是否成功 比如点赞是否成功等
    private boolean is_success;
    //访问服务器是否成功
    private boolean status;
    //如果访问失败 则返回code  比如404
    private int error_code;
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




    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean is_success() {
        return is_success;
    }

    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
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
                "is_success=" + is_success() +
                ", status=" + status +
                ", error_code=" + error_code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
