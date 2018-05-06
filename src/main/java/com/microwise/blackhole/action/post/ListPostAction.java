package com.microwise.blackhole.action.post;

import com.microwise.blackhole.bean.Post;
import com.microwise.blackhole.service.PostService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 新闻列表
 *
 * @author gaohui
 * @date 13-5-10 10:20
 */
@Beans.Action
@Blackhole
public class ListPostAction {
    // 内容页面
    public static final String _pagePath = "/blackhole/pages/post/list-post-view.ftl";

    @Autowired
    private PostService postService;

    //input
    private int scope = 1; //可见性，默认公共

    // input & output
    private int page = 1; //当前页, 从一开始，默认第一 页
    // output
    private int pageCount; // 总页数
    private List<Post> posts; //新闻

    public String execute() {
        int count = postService.findCount(scope);
        pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);

        posts = postService.findLatest(scope, page, Constants.SIZE_PER_PAGE);

        if (scope == 1) {
            return "not-login";
        }
        return Action.SUCCESS;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
