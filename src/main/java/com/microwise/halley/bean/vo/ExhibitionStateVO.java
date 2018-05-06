package com.microwise.halley.bean.vo;

import com.microwise.blackhole.bean.User;
import com.microwise.halley.bean.po.PathPO;

import java.util.Date;

/**
 * 外展状态 VO
 *
 * @author xu.yuexi
 * @date 2013-10-23
 */
public class ExhibitionStateVO {

    /**
     * 记录ID
     */
    private int id;

    /**
     * 外展ID
     */
    private int exhibitionId;

    /**
     * 状态
     */
    private int state;

    /**
     * 状态实际开始时间
     */
    private Date beginTime;

    /**
     * 状态实际结束时间
     */
    private Date endTime;
    /**
     * 操作人Id
     */
    private int operator;
    /**
     * 下一个目的地
     */
    private PathPO pathPO;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PathPO getPathPO() {
        return pathPO;
    }

    public void setPathPO(PathPO pathPO) {
        this.pathPO = pathPO;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


}
