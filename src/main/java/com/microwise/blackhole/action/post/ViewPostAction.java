package com.microwise.blackhole.action.post;

import com.microwise.blackhole.bean.Post;
import com.microwise.blackhole.service.PostService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author gaohui
 * @date 13-5-10 10:48
 */
@Beans.Action
@Blackhole
public class ViewPostAction {
    public static final String _pagePath = "/blackhole/pages/post/post-view.ftl";

    @Autowired
    private PostService postService;

    //input
    private int id;
    private int scope = 1;

    //output
    private Post post;
    private List<Post> posts;

    public String execute() {
        post = postService.findById(id);

        if (scope == 1) {
            posts = postService.findLatest(1, 10);
            return "not-login";
        } else {
            posts = postService.findLatest(-1, 10);
        }

        return Action.SUCCESS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public static String get_pagePath() {
        return _pagePath;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
