package com.microwise.orion.service;

import com.microwise.orion.bean.Era;

import java.util.List;

/**
 * 年代service
 *
 * @author xubaoji
 * @date 2013-5-17
 * @check 2013-06-04 zhangpeng svn:3510
 */
public interface EraService {

    /**
     * 查询所有 文物时代
     *
     * @return List<Era> 时代列表
     * @author 许保吉
     * @date 2013-5-17
     */
    public List<Era> findAllEra();

    void delete(Era era);

    Integer save(Era era);

    Integer deleteByName(String name);

    boolean isEraExist(String name);
}
