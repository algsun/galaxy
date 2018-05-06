package com.microwise.phoenix.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.Inventory;
import com.microwise.orion.bean.InventoryError;
import com.microwise.orion.proxy.InventoryProxy;
import com.microwise.phoenix.bean.vo.HealthCheckItem;
import com.microwise.phoenix.bean.vo.healthCheck.Device;
import com.microwise.phoenix.bean.vo.healthCheck.FTP;
import com.microwise.phoenix.dao.HealthCheckDao;
import com.microwise.phoenix.service.HealthCheckService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.proxima.bean.FTPProfile;
import com.microwise.proxima.proxy.FTPproxy;
import com.microwise.uma.bean.PersonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 系统健康检测 service 实现
 *
 * @author xu.baoji
 * @date 2013-7-25
 * @check @duan.qixin 2013年7月29日 #4688
 */
@Service
@Phoenix
@Transactional
public class HealthCheckServiceImpl implements HealthCheckService {

    /**
     * 系统健康检测 dao
     */
    @Autowired
    private HealthCheckDao healthCheckDao;

    /**
     * 本体监测 ftp 服务代理
     */
    @Autowired
    private FTPproxy ftPproxy;

    /**
     * 藏品管理盘点代理服务
     */
    @Autowired
    private InventoryProxy inventoryProxy;

    @Override
    public List<HealthCheckItem> findHealthCheckItem() {
        List<HealthCheckItem> healthCheckItems = healthCheckDao.findHealthCheckItem();
        for (HealthCheckItem healthCheckItem : healthCheckItems) {
            healthCheckItem.setCheckItems(healthCheckDao.findCheckItem(healthCheckItem
                    .getSubsystemId()));
        }
        return healthCheckItems;
    }

    @Override
    public int findHealthCheckItemCount() {
        return healthCheckDao.findHealthCheckItemCount();
    }

    @Override
    public List<Integer> blueplanetDeviceCheck(String siteId, int number) {
        int deviceCount = healthCheckDao.blueplanetFindDeviceCount(siteId);
        int errorCount = healthCheckDao.blueplanetFindErrorDeviceCount(siteId);
        return disposeNumber(deviceCount, errorCount, number);
    }

    @Override
    public List<Device> blueplanetFindErrorDevices(String siteId) {

        return healthCheckDao.blueplanetFindErrorDevices(siteId);
    }

    // 此处存在漏洞，如果在检测 盘点后 用户再次 创建并结束一个盘点 时 ，这里获得的盘点异常数据就和上一次不一致
    @Override
    public List<InventoryError> orionFindLastInventoryErrorRelics(String siteId) {
        Inventory inventory = inventoryProxy.findLastInventory(siteId);
        return inventoryProxy.findInventoryErrors(inventory.getId());
    }

    @Override
    public List<Integer> orionLastInventoryCheck(String siteId, int number) {
        Inventory inventory = inventoryProxy.findLastInventory(siteId);
        if (inventory != null) {
            return disposeNumber(inventory.getTagCount(), inventory.getErrorCount(), number);
        } else {
            return Arrays.asList(0, 0);
        }

    }

    @Override
    public List<FTP> proximaFindErrorFtps(String siteId) {
        List<FTPProfile> ftps = ftPproxy.findAll(siteId);
        List<FTP> errorFtps = new ArrayList<FTP>();
        proximaFtpCheck(errorFtps, ftps);
        return errorFtps;
    }

    @Override
    public List<Integer> proximaFtpCheck(String siteId, int number) {
        List<FTPProfile> ftps = ftPproxy.findAll(siteId);
        int errorCount = proximaFtpCheck(null, ftps);
        return disposeNumber(ftps.size(), errorCount, number);
    }

    /***
     * ftp 检测
     *
     * @param errorFtps 异常ftp 列表
     * @param ftps      站点下所有ftp 服务列表
     * @return int 异常 ftp 服务数量
     * @author xu.baoji
     * @date 2013-7-25
     */
    private int proximaFtpCheck(List<FTP> errorFtps, List<FTPProfile> ftps) {
        int errorCount = 0;
        for (FTPProfile ftp : ftps) {
            String error = ftPproxy.testConnect(ftp);
            if (error != null) {// ftp 异常
                errorCount++;
                if (errorFtps != null) {
                    errorFtps.add(new FTP(error, ftp));
                }
            }
        }
        return errorCount;
    }

    @Override
    public List<Integer> umaUserCardCheck(String siteId, int number) {
        int errorCount = healthCheckDao.umaFindErrorUserCardCount(siteId);
        int count = healthCheckDao.umaFindUserCardCount(siteId);
        return disposeNumber(count, errorCount, number);
    }

    @Override
    public List<PersonBean> umaFindErrorUserCard(String siteId) {
        return healthCheckDao.umaFindErrorUserCards(siteId);
    }

    /**
     * 处理检测 返回参数
     *
     * @param count      总数量
     * @param errorCount 异常数量
     * @param number     总分数
     * @return
     * @author xu.baoji
     * @date 2013-7-25
     */
    private List<Integer> disposeNumber(int count, int errorCount, int number) {
        List<Integer> result = new ArrayList<Integer>();
        float f = (float) errorCount / count * number;
        result.add(Math.round(f));
        result.add(errorCount);
        return result;
    }
}
