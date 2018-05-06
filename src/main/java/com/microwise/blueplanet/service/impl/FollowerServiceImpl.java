package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.dao.FollowerDao;
import com.microwise.blueplanet.service.FollowerService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/7/19.
 */
@Blueplanet
@Beans.Service
public class FollowerServiceImpl implements FollowerService{
    @Autowired
    FollowerDao followerDao;


    public int findFollower(int userId, String locationId) {
        int followerNum = followerDao.findFollower(userId,locationId);
        if(followerNum > 0){
            return 1;
        }
        return 0;
    }

    public void unFollower(int userId, String zoneId) {
        followerDao.deleteFollower(userId, zoneId);
    }

    public void toFollower(int userId, String locationId) {
        followerDao.addFollower(userId,locationId);
    }
}
