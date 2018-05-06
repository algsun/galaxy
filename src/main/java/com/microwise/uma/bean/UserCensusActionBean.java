package com.microwise.uma.bean;

/**
 * @author gaohui
 * @date 13-4-27 13:35
 */
public class UserCensusActionBean extends AbstractUserActionBean {
    // 停留时间(秒)
    protected long duration;

    // 往
    protected UserActionBean goAction;

    // 返
    protected UserActionBean backAction;

    public UserActionBean getGoAction() {
        return goAction;
    }

    public void setGoAction(UserActionBean goAction) {
        this.goAction = goAction;
    }

    public UserActionBean getBackAction() {
        return backAction;
    }

    public void setBackAction(UserActionBean backAction) {
        this.backAction = backAction;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
