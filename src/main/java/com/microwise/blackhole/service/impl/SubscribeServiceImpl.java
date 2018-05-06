package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.Subscribe;
import com.microwise.blackhole.dao.SubscribeDao;
import com.microwise.blackhole.service.SubscribeService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订阅dao 实现
 *
 * @author xubaoji
 * @date 2013-6-13
 * @check li.jianfei  2013-06-20 #4227
 */
@Service
@Blackhole
@Transactional
public class SubscribeServiceImpl implements SubscribeService {

    /**
     * 订阅 到
     */
    @Autowired
    private SubscribeDao subscribeDao;

    @Override
    public void addSubscribe(Subscribe subscribe) {
        subscribeDao.save(subscribe);
    }

    @Override
    public void deleteSubscribe(Integer userId, Integer type, String siteId, String locationId) {
        subscribeDao.deleteSubscribe(userId, type, siteId, locationId);
    }

    @Override
    public List<Subscribe> findSubscribeBySite(String siteId, Integer type) {
        return subscribeDao.findSubscribeBySite(siteId, type);
    }

    @Override
    public List<Subscribe> findSubscribeByUser(String siteId, int userId) {
        return subscribeDao.findSubscribeByUser(siteId, userId);
    }

    @Override
    public List<Subscribe> findSubscribes(int type) {
        return subscribeDao.findSubscribes(type);
    }

    @Override
    public List<Subscribe> findSubscribe(int userId, String locationId, int type) {
        return subscribeDao.findSubscribe(userId, locationId, type);
    }

}
