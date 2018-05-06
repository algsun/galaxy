package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.vo.TextureThresholdVO;
import com.microwise.blueplanet.bean.vo.ThresholdVO;
import com.microwise.blueplanet.dao.LocationDao;
import com.microwise.blueplanet.dao.TextureThresholdDao;
import com.microwise.blueplanet.dao.ThresholdDao;
import com.microwise.blueplanet.service.TextureThresholdService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 质地阈值service
 */

@Beans.Service
@Transactional
@Blueplanet
public class TextureThresholdServiceImpl implements TextureThresholdService {

    @Autowired
    private TextureThresholdDao textureThresholdDao;


    @Autowired
    private LocationDao locationDao;

    @Autowired
    private ThresholdDao thresholdDao;

    @Override
    public void insertTextureThreshold(TextureThresholdVO textureThresholdVO) {

        //添加质地阈值
        textureThresholdDao.insertTextureThreshold(textureThresholdVO);

        //查询已绑定质地的位置点
        List<String> locationIds = textureThresholdDao.findBindLocationId(textureThresholdVO.getTextureId());
        for (String locationId : locationIds) {
            //查询位置点是否有阈值
            List<ThresholdVO> thresholds = thresholdDao.findThresholds(locationId);
            if (thresholds.size() > 0) {
                //是否处理位置点阈值业务
                boolean locationFlag = false;
                //是否处理区域阈值业务
                boolean zoneFlag = false;
                for (ThresholdVO thresholdVO : thresholds) {
                    //如果位置点有监测指标阈值和质地监测指标阈值冲突。则不处理位置点阈值的业务
                    if (textureThresholdVO.getSensorPhysicalId() == thresholdVO.getSensorPhysicalId()
                            && thresholdVO.getThresholdType() == 0) {
                        locationFlag = true;
                        break;
                    }

                    if (textureThresholdVO.getSensorPhysicalId() == thresholdVO.getSensorPhysicalId()
                            && thresholdVO.getThresholdType() == 1) {
                        zoneFlag = true;
                        break;
                    }
                }

                ThresholdVO threshold = new ThresholdVO();
                threshold.setLocationId(locationId);
                threshold.setSensorPhysicalId(textureThresholdVO.getSensorPhysicalId());
                threshold.setConditionType(textureThresholdVO.getConditionType());
                threshold.setTarget(textureThresholdVO.getTarget());
                threshold.setFloating(textureThresholdVO.getFloating());
                threshold.setThresholdType(2);
                if (!locationFlag) {
                    thresholdDao.save(threshold);
                }

                if (!zoneFlag) {
                    //删除之前的区域的阈值条件。
                    thresholdDao.delete(locationId, textureThresholdVO.getSensorPhysicalId());
                    thresholdDao.save(threshold);
                }
            }
        }
    }

    @Override
    public TextureThresholdVO findTextureThreshold(TextureThresholdVO textureThresholdVO) {
        return textureThresholdDao.findTextureThreshold(textureThresholdVO);
    }

    @Override
    public List<TextureThresholdVO> findTextureThresholds(int textureThresholdId) {
        return textureThresholdDao.findTextureThresholds(textureThresholdId);
    }

    @Override
    public void deleteTextureThreshold(int id, int textureId, int sensorId) {
        //查询已绑定质地的位置点
        textureThresholdDao.deleteTextureThreshold(id);
        List<String> locationIds = textureThresholdDao.findBindLocationId(textureId);
        for (String locationId : locationIds) {
            thresholdDao.delete(locationId, sensorId);
        }
    }
}
