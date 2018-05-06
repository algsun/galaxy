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

/**
 * 删除新闻
 *
 * @author gaohui
 * @date 13-5-9 11:28
 */
@Beans.Action
@Blackhole
public class DeletePostAction {
    public static final Logger log = LoggerFactory.getLogger(DeletePostAction.class);

    @Autowired
    private PostService postService;
    @Autowired
    private BlackholeLoggerUtil logger;

    //input
    private int id;

    public String execute() {
        try {
            Post post = new Post();
            post.setId(id);
            postService.delete(post);
            ActionMessage.createByAction().success("删除成功");
            logger.log("新闻管理", "删除新闻");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("删除失败");
            log.error("删除新闻", e);
        }
        return Action.SUCCESS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
