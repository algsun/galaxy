package com.microwise.phoenix.service.impl;

import com.microwise.blueplanet.dao.LocationDao;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.phoenix.bean.po.LocationDate;
import com.microwise.phoenix.service.ZoneDataDistributionService;
import com.microwise.phoenix.sys.Phoenix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 区域数据分布Service实现
 * author : chenyaofei
 * date : 2016-10-10
 */

@Phoenix
@Beans.Service
@Transactional
public class ZoneDataDistributionServiceImpl implements ZoneDataDistributionService {

    @Autowired
    LocationDao locationDao;

    public List<LocationDate> findDataDistribution(String zoneId, int sensorId, Date startDate, Date endDate) {
        return locationDao.findLocationDataBySensor(zoneId, sensorId, startDate, endDate);
    }

    public List<Double> findFourQuantile(String locationId, int sensorId, Date startDate, Date endDate, int valuePrecision) {
        List<Double> locationSensorList = locationDao.findSensorValues(locationId, sensorId, startDate, endDate);
        int count = locationSensorList.size();

        if (count == 0) {
            return null;
        }
        List<Double> fourQuantile = new ArrayList<Double>();
        if (count == 1) {
            Double numberOne = locationSensorList.get(0);
            fourQuantile.add(numberOne);
            fourQuantile.add(numberOne);
            fourQuantile.add(numberOne);
            fourQuantile.add(numberOne);
            fourQuantile.add(numberOne);
            return fourQuantile;
        }

        Double minNum;
        Double q1;
        Double q2;
        Double q3;
        Double maxNum;
        maxNum = locationSensorList.get(count -1);
        minNum = locationSensorList.get(0);

        Double number1;
        Double number2;

        //中位数
        if ((count % 2) > 0) {
            int mid = (count + 1) / 2;
            q2 = locationSensorList.get(mid - 1);
        }else{
            int midNum = count / 2;
            number1 = locationSensorList.get(midNum - 1);
            number2 = locationSensorList.get(midNum);
            q2 = (number1 + number2) / 2;
        }


        //Q1
        int headSize;
        if ((count % 2) > 0) {
            headSize = (count - 1) / 2;
        } else {
            headSize = count / 2;
        }

        if ((headSize % 2) > 0) {
            int mid = (headSize + 1) / 2;
            q1 = locationSensorList.get(mid - 1);
        }else{
            int q1MidNum = headSize / 2;
            number1 = locationSensorList.get(q1MidNum - 1);
            number2 = locationSensorList.get(q1MidNum);
            q1 = (number1 + number2) / 2;
        }

        //Q3
        int tailSize;
        int tailStart;

        if ((count % 2) > 0) {
            tailSize = (count - 1) / 2;
            tailStart = tailSize + 2;
        } else {
            tailSize = count / 2;
            tailStart = tailSize + 1;
        }
        if ((tailSize % 2) > 0) {
            int q3Mid = (tailSize + 1) / 2;
            q3 = locationSensorList.get(tailStart + q3Mid - 2);
        }else{
            int q3MidNum = tailSize / 2;
            number1 = locationSensorList.get(tailStart + q3MidNum - 2);
            number2 = locationSensorList.get(tailStart + q3MidNum - 1);
            q3 = (number1 + number2) / 2;
        }

        // 整理返回
        fourQuantile.add(minNum);
        fourQuantile.add(q1);
        fourQuantile.add(q2);
        fourQuantile.add(q3);
        fourQuantile.add(maxNum);

        //处理数字格式
        BigDecimal bg;
        for(int i=0;i<fourQuantile.size();i++){
            bg = new BigDecimal(fourQuantile.get(i));
            Double number= bg.setScale(valuePrecision, BigDecimal.ROUND_HALF_UP).doubleValue();
            fourQuantile.remove(i);
            fourQuantile.add(i,number);
        }
        return fourQuantile;
    }

}

