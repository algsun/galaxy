package com.microwise.api.bean;

/**
 * 访问 API 返回结果
 */
public class ApiResult<T> {

    /**
     * 访问成功失败
     */
    private boolean success;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 链接实体
     */
    private Link link;

    /**
     * 返回数据类型
     */
    private T data;

    public ApiResult() {
    }

    public ApiResult(boolean success, String message, Link link, T data) {
        this.success = success;
        this.message = message;
        this.link = link;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
