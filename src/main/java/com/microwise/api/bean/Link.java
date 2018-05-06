package com.microwise.api.bean;

/**
 * 链接信息实体
 *
 * @date 14-6-30 上午9:23
 */
public class Link {
    /**
     * 链接地址
     */
    private String url;

    public Link() {
    }

    public Link(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
