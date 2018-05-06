package com.microwise.phoenix.bean.po.uma;

/**
 * 人员管理：人员统计，人员活动频率（获得次数或者百分比）
 *
 * @author xu.baoji
 * @date 2013-7-15
 */
public class UserStat {

    /**
     * 人员名称
     */
    private String userName;

    /**
     * 人员活动次数
     */
    private int userCount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

}
