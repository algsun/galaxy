package com.microwise.blueplanet.dao;

/**
 * author : chenyaofei
 * date : 2016-07-19
 */
public interface FollowerDao {
    /**
     * 查找区域下关注者
     */
    public int findFollower(int userId,String locationId);

    /**
     * 删除用户关注区域
     */
    public void deleteFollower(int userId,String locationId);

    /**
     *添加用户关注区域
     */
    public void addFollower(int userId,String locationId);
}
