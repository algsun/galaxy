package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.Era;
import com.microwise.orion.dao.EraDao;
import com.microwise.orion.service.EraService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 文物 时代 service 实现类
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3510
 */
@Service
@Orion
@Transactional
public class EraServiceImpl implements EraService {

    /**
     * 时代dao
     */
    @Autowired
    private EraDao eraDao;

    @Override
    public List<Era> findAllEra() {
        return eraDao.findAllEra();
    }

    @Override
    public void delete(Era era) {
        eraDao.delete(era);
    }

    @Override
    public Integer save(Era era) {
        Serializable x  =  eraDao.save(era);
        return Integer.parseInt(x.toString());
    }

    @Override
    public Integer deleteByName(String name) {
        int i=eraDao.findIdByName(name);
        eraDao.deleteByName(name);
        return i;
    }

    @Override
    public boolean isEraExist(String name) {
        return eraDao.isEraExist(name);
    }
}
