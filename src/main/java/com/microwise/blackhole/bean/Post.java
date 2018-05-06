package com.microwise.blackhole.bean;

import java.util.Date;

/**
 * 新闻
 *
 * @author gaohui
 * @date 13-5-8 16:29
 */
public class Post {

    // 公共
    public static final int SCOPE_PUBLIC = 1;
    // 内部
    public static final int SCOPE_INTERNAL = 2;


    private int id;
    // 标题
    private String title;
    // 内容
    private String content;
    // 时间
    private Date createDate;

    // 新闻可见性
    private int scope;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }
}
