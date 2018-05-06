package com.microwise.blueplanet.service.impl;

import com.google.common.base.Strings;
import com.microwise.blueplanet.bean.po.DimensionalLocationPO;
import com.microwise.blueplanet.bean.po.ThreeDimensionalPO;
import com.microwise.blueplanet.dao.ThreeDimensionalDao;
import com.microwise.blueplanet.service.ThreeDimensionalService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.UpLoadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * @author 王耕
 * @date 15-6-10
 */
@Beans.Service
@Transactional
@Blueplanet
public class ThreeDimensionalServiceImpl implements ThreeDimensionalService {

    @Autowired
    private ThreeDimensionalDao threeDimensionalDao;

    @Override
    public void saveDimensional(ThreeDimensionalPO threeDimensionalPO) {
        threeDimensionalDao.saveDimensional(threeDimensionalPO);
    }

    @Override
    public List<ThreeDimensionalPO> findThreeDimensionals(String siteId) {
        return threeDimensionalDao.findThreeDimensionals(siteId);
    }

    @Override
    public ThreeDimensionalPO findThreeDimenById(int dimensionalId) {
        return threeDimensionalDao.findThreeDimenById(dimensionalId);
    }

    @Override
    public ThreeDimensionalPO findThreeDimenByPath(String path) {
        return threeDimensionalDao.findThreeDimenByPath(path);
    }

    @Override
    public void deleteDimensionalById(int dimensionalId, String fileName) {
        //删除模型记录
        threeDimensionalDao.deleteDimensionalById(dimensionalId);
        //删除关联关系
        threeDimensionalDao.deleteDimensionalLocationRelation(dimensionalId);
        if (!Strings.isNullOrEmpty(fileName)) {
            //删除服务器上的文件
            String configPath = UpLoadFileUtil.getUploadPath(File.separator + "blueplanet")
                    + File.separator + "file" + File.separator + "threedimensional";
            File files = new File(configPath);
            if (files.exists() && files.isDirectory()) {
                File[] configFileList = files.listFiles();
                if (configFileList != null) {
                    for (File configF : configFileList) {
                        if (configF.getName().equals(fileName)) {
                            configF.delete();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateDimensionalRemark(String remark, int dimensionalId) {
        threeDimensionalDao.updateDimensionalRemark(remark, dimensionalId);
    }

    @Override
    public void saveDimensionalLocationRelation(String siteId, int dimensionalId, String[] locationIds) {
        threeDimensionalDao.saveDimensionalLocationRelation(siteId, dimensionalId, locationIds);
    }

    @Override
    public List<DimensionalLocationPO> findDimensionalLocationRelations(String siteId, int dimensionalId) {
        return threeDimensionalDao.findDimensionalLocationRelations(siteId, dimensionalId);
    }

    @Override
    public void updateDimensionalLocationRelation(String siteId,int dimensionalId,String[] locationIds){
               threeDimensionalDao.deleteDimensionalLocationRelation(dimensionalId);
               threeDimensionalDao.saveDimensionalLocationRelation(siteId,dimensionalId,locationIds);

    };
}
