package com.microwise.blackhole.service;

/**
 * 个性化设置Service
 *
 * @author zhangpeng
 * @date 2013-1-18
 */
public interface IndividuationService {

    /**
     * 根据个性化设置的key查询个性化设置
     *
     * @param key 用户个性化设置的key
     * @return 个性化设置值
     * @author zhangpeng
     * @date 2013-1-18
     */
    public String findByKey(String key);

    /**
     * 保存或更新用户个性化设置
     *
     * @param userId
     * @param key
     * @param value
     * @return void
     * @author zhangpeng
     * @date 2013-1-18
     */
    public void saveOrUpdate(String userId, String key, String value);

}
