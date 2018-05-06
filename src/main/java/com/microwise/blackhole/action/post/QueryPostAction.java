package com.microwise.blackhole.action.post;

import com.microwise.blackhole.bean.Post;
import com.microwise.blackhole.service.PostService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 查询新闻
 *
 * @author gaohui
 * @date 13-5-8 17:01
 */
@Beans.Action
@Blackhole
public class QueryPostAction {
    @Autowired
    private PostService postService;
    @Autowired
    private BlackholeLoggerUtil logger;
    //input
    // 可见性
    private int scope = -1; // 默认全部

    //input & output
    // 当前页
    private int page = 1; // 默认第一页

    //output
    // 新闻
    private List<Post> posts;
    // 总页数
    private int pageCount;

    public String execute() {
        ActionMessage.createByAction().consume();

        int count = postService.findCount(scope);
        pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);

        posts = postService.findLatest(scope, page, Constants.SIZE_PER_PAGE);
        logger.log("新闻管理", "查询新闻");
        return Action.SUCCESS;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
