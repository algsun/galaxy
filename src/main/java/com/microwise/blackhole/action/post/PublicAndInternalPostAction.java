package com.microwise.blackhole.action.post;

import com.microwise.blackhole.bean.Post;
import com.microwise.blackhole.service.PostService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 外部新闻与内部新闻显示
 *
 * @author : wang.geng
 * @date: 13-5-9  上午11:23
 */

@Beans.Action
@Blackhole
public class PublicAndInternalPostAction {

    @Autowired
    private PostService postService;

    //output
    /**
     * 新闻
     */
    private List<Post> posts;

    //input
    /**
     * 显示的新闻最大条数
     */
    private int max;

    /**
     * 加载新闻类型
     * 1：外部新闻
     * 2：内部新闻
     * -1：所有内部新闻与外部新闻
     */
    private int scope;

    public String execute() {
        posts = postService.findLatest(scope, max);
        return Action.SUCCESS;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }
}
