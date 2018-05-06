package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.DrawRegister;
import com.microwise.orion.dao.DrawRegisterDao;
import com.microwise.orion.service.DrawRegisterService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 绘图登记实现
 *
 * @author liuzhu
 * @date 2015-11-10
 */

@Beans.Service
@Orion
@Transactional
public class DrawRegisterServiceImpl implements DrawRegisterService {

    @Autowired
    private DrawRegisterDao drawRegisterDao;

    @Override
    public void save(DrawRegister drawRegister) {
        drawRegisterDao.save(drawRegister);
    }

    @Override
    public List<DrawRegister> findDrawRegisters(int repairRecordId) {
        return drawRegisterDao.findDrawRegisters(repairRecordId);
    }

    @Override
    public void update(DrawRegister drawRegister) {
        drawRegisterDao.update(drawRegister);
    }

    @Override
    public DrawRegister findById(int id) {
        return drawRegisterDao.findById(id);
    }

    @Override
    public DrawRegister findNewDrawRegister(int repairRecordId)  {
        return drawRegisterDao.findNewDrawRegister(repairRecordId);
    }

    @Override
    public void delete(DrawRegister drawRegister) {
        drawRegisterDao.delete(drawRegister);
    }

    @Override
    public DrawRegister findDrawRegister(int id) {
        return drawRegisterDao.findDrawRegister(id);
    }
}
