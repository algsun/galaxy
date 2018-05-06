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
 * 编辑新闻
 *
 * @author gaohui
 * @date 13-5-10 14:46
 */
@Beans.Action
@Blackhole
public class UpdatePostAction {
    public static final Logger log = LoggerFactory.getLogger(UpdatePostAction.class);

    @Autowired
    private PostService postService;
    @Autowired
    private BlackholeLoggerUtil logger;

    //input
    private int id;

    // 新闻标题
    private String title;
    // 新闻内容
    private String content;
    // 新闻可见性
    private int scope;
    // 新闻创建时间
    private Date createDate;

    //output
    private Post post;

    /**
     * 跳转到修改页面
     *
     * @return
     */
    public String toUpdate() {
        post = postService.findById(id);
        return Action.SUCCESS;
    }


    /**
     * 执行修改
     *
     * @return
     */
    public String doUpdate() {
        post = postService.findById(id);
        try {
            post.setTitle(title);
            post.setContent(content);
            post.setScope(scope);
            post.setCreateDate(createDate);

            postService.update(post);

            ActionMessage.createByAction().success("编辑成功");
            logger.log("新闻管理", "修改新闻");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("编辑失败");
            log.error("更新新闻", e);
        }
        return Action.SUCCESS;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
