package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.Subscribe;

import java.util.List;

/**
 * 订阅 service
 *
 * @author xubaoji
 * @date 2013-6-13
 * @check li.jianfei 2013-06-20 #4227
 */
public interface SubscribeService {

    /**
     * 添加订阅
     *
     * @param subscribe 订阅实体
     * @author 许保吉
     * @date 2013-6-13
     */
    public void addSubscribe(Subscribe subscribe);

    /**
     * 取消用户所有订阅信息
     *
     * @param userId 用户id
     * @param type   类型
     * @param siteId 站点Id
     * @author 许保吉
     * @date 2013-6-13
     */
    public void deleteSubscribe(Integer userId, Integer type, String siteId, String locationId);

    /**
     * TODO 接口修改，KDJ业务有影响到该接口
     * 查询一个站点下同种类型订阅的所有订阅实体 （携带有用户信息）
     *
     * @param siteId 站点编号
     * @param type   订阅类型
     * @return List<Subscribe> 订阅实体对象列表
     * @author 许保吉
     * @date 2013-6-14
     */
    public List<Subscribe> findSubscribeBySite(String siteId, Integer type);

    /**
     * TODO 接口修改，KDJ业务有影响到该接口
     * <p/>
     * 查询 用户 在一个站点下订阅的报表列表
     *
     * @param siteId 站点编号
     * @param userId 用户id
     * @return List<Subscribe> 订阅列表
     * @author 许保吉
     * @date 2013-6-18
     */
    public List<Subscribe> findSubscribeByUser(String siteId, int userId);

    /**
     * 根据类型查询订阅实体
     *
     * @param type 订阅类型
     * @return List<Subscribe> 订阅列表
     * @author 王耕
     * @date 2015-2-26
     */
    public List<Subscribe> findSubscribes(int type);

    /**
     * 查询当前用户是否订阅了某位置点
     *
     * @param userId     系统登录的当前用户
     * @param locationId 位置点ID
     * @param type       订阅类型
     * @return Subscribe实体
     * @author 王耕
     * @date 2015-2-26
     */
    public List<Subscribe> findSubscribe(int userId, String locationId, int type);

}
