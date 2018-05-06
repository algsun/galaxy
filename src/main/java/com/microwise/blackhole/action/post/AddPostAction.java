package com.microwise.blackhole.action.post;

import com.microwise.blackhole.bean.Post;
import com.microwise.blackhole.service.PostService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.BlackholeLoggerUtil;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author gaohui
 * @date 13-5-8 17:03
 */
@Beans.Action
@Blackhole
public class AddPostAction {
    private static final Logger log = LoggerFactory.getLogger(AddPostAction.class);

    @Autowired
    private PostService postService;
    @Autowired
    private BlackholeLoggerUtil logger;

    // input
    // 新闻标题
    private String title;
    // 新闻内容
    private String content;
    // 新闻可见性
    private int scope;

    // 新闻创建时间
    private Date createDate;
    /**
     * 跳转到添加页面
     *
     * @return
     */
    public String toAdd() {
        createDate = new Date();
        return Action.SUCCESS;
    }


    /**
     * 执行添加
     *
     * @return
     */
    public String doAdd() {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setScope(scope);
        post.setCreateDate(createDate);

        try {
            postService.save(post);
            ActionMessage.createByAction().success("添加成功");
            logger.log("新闻管理", "添加新闻");
        } catch (Exception e) {
            log.error("添加新闻", e);
            return Action.INPUT;
        }

        return Action.SUCCESS;
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

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
