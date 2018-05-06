package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.Level;
import com.microwise.orion.dao.LevelDao;
import com.microwise.orion.service.LevelService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 文物 级别 service 实现类
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3510
 */
@Service
@Orion
@Transactional
public class LevelServiceImpl implements LevelService {

    /**
     * 文物级别 dao
     */
    @Autowired
    private LevelDao levelDao;

    @Override
    public List<Level> findAllLevel() {
        return levelDao.findAllLevel();
    }

    @Override
    public void delete(Level level) {
        levelDao.delete(level);
    }

    @Override
    public Integer save(Level level) {
        Serializable x = levelDao.save(level);
        return Integer.parseInt(x.toString());
    }

    @Override
    public Integer deleteByName(String name) {
        int i=  levelDao.findIdByName(name);
        levelDao.deleteByName(name);
        return i;
    }

    @Override
    public boolean isLevelExist(String name) {
        return levelDao.isLevelExist(name);
    }
}
