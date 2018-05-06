package com.microwise.blueplanet.service;

/**
 * author : chenyaofei
 * date :2016-07-19
 */
public interface FollowerService {

    /**
     * 查询用户是否关注某区域
     *
     */
    public int findFollower(int userId,String LocationId);

    /**
     * 取消关注
     */
    public void unFollower(int userId,String locationId);

    /**
     * 关注
     */
    public void toFollower(int userId,String locationId);
}
