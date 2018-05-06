package com.microwise.blueplanet.action.location;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.service.FollowerService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author : chenyaofei
 * date : 2016-07-19
 */

@Beans.Action
@Blueplanet
public class FollowerAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(IndexAction.class);
    @Autowired
    FollowerService followerService;

    /**
     *位置点关注
     */
    private String locationId;

    /**
     * 关注 0：未关注 1：已关注
     */
    private int follower;

    @Route("/blueplanet/location/editFollower")
    public String editFollower() {
        boolean flag = false;
        try{
            int userId = Sessions.createByAction().currentUser().getId();
            if (follower == 1) {
                //取消
                followerService.unFollower(userId, locationId);
                flag = true;
            } else {
                //关注
                followerService.toFollower(userId, locationId);
                flag = true;
            }
        }
        catch(Exception e){
            log.error("位置点关注",e);
        }
        return Results.json().asRoot(flag).done();
    }




    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }
}
