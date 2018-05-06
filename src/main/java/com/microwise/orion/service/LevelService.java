package com.microwise.orion.service;

import com.microwise.orion.bean.Level;

import java.util.List;

/**
 * 文物级别service
 *
 * @author xubaoji
 * @date 2013-5-17
 * @check 2013-06-04 zhangpeng svn:3510
 */
public interface LevelService {

    /**
     * 查询所有 文物级别
     *
     * @return List<Level> 级别列表
     * @author 许保吉
     * @date 2013-5-17
     */
    public List<Level> findAllLevel();

    void delete(Level level);

    Integer save(Level level);

    Integer deleteByName(String name);

    boolean isLevelExist(String name);
}
