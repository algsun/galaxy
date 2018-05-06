package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.RepairPhoto;
import com.microwise.orion.dao.RepairPhotoDao;
import com.microwise.orion.service.RepairPhotoService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 王耕
 * @date 15-10-22
 */
@Beans.Service
@Orion
@Transactional
public class RepairPhotoServiceImpl implements RepairPhotoService {
    @Autowired
    private RepairPhotoDao repairPhotoDao;

    @Override
    public void saveRepairPhoto(RepairPhoto repairPhoto) {
        repairPhotoDao.saveRepairPhoto(repairPhoto);
    }

    @Override
    public List<RepairPhoto> findByRepairRecordId(int repairRecordId,int type) {
        return repairPhotoDao.findByRepairRecordId(repairRecordId,type);
    }

    @Override
    public void deleteById(int id) {
        repairPhotoDao.deleteById(id);
    }

    @Override
    public RepairPhoto findById(int id) {
        return repairPhotoDao.findById(id);
    }
}
