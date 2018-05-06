package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.*;
import com.microwise.orion.dao.*;
import com.microwise.orion.service.InventoryService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.vo.InventoryZoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文物盘点service 实现
 *
 * @author xubaoji
 * @date 2013-5-27
 * @check 2013-06-05 zhangpeng svn:4053
 */
@Service
@Orion
@Transactional
public class InventoryServiceImpl implements InventoryService {

    /**
     * 文物盘点Dao
     */
    @Autowired
    private InventoryDao inventoryDao;

    /**
     * 盘点出库 dao
     */
    @Autowired
    private InventoryOutDao inventoryOutDao;

    /**
     * 盘点标签数据dao
     */
    @Autowired
    private InventoryTagDao inventoryTagDao;

    /**
     * 盘点 异常文物 dao
     */
    @Autowired
    private InventoryErrorDao inventoryErrorDao;

    /**
     * 出库文物 dao
     */
    @Autowired
    private EventRelicDao eventRelicDao;

    /**
     * 盘点按区域统计 dao
     */
    @Autowired
    private InventoryZoneDao inventoryZoneDao;

    @Autowired
    private RelicDao relicDao;

    @Override
    public List<Inventory> findInventorys(String siteId, Integer index,
                                          Integer size) {
        return inventoryDao.findInventorys(siteId, index, size);
    }

    @Override
    public Integer findInventoryCount(String siteId) {
        return inventoryDao.findInventoryCount(siteId);
    }

    @Override
    public List<InventoryError> findInventoryErrors(Integer id) {
        return inventoryDao.findInventoryErrors(id);
    }

    @Override
    public Inventory findRealtimeInventoryReport(Integer id, String siteId) {
        // 查询盘点报告的基本信息
        Inventory inventory = packRealtimeInventoryData(id, siteId);
        inventory.setDeadlineDate(new Date());
        // 处理盘点 报告的 区域统计信息
        if (inventory != null) {
            disposeRealtimeInventoryStatistics(inventory, siteId);
        }
        return inventory;
    }

    @Override
    public List<InventoryOut> findRealtimeInventoryOuts(Integer id, String siteId) {
        List<InventoryOut> inventoryOuts = new ArrayList<InventoryOut>();
        List<EventRelic> eventRelics = eventRelicDao.findInventoryOut(siteId);
        Inventory inventory = new Inventory();
        inventory.setId(id);
        for (EventRelic eventRelic : eventRelics) {
            InventoryOut inventoryOut = new InventoryOut();
            inventoryOut.setInventory(inventory);
            inventoryOut.setOutEvent(eventRelic.getOutEvent());
            inventoryOut.setRelic(eventRelic.getRelic());
            inventoryOuts.add(inventoryOut);
        }
        return inventoryOuts;
    }

    @Override
    public List<InventoryError> findRealtimeInventoryErrors(Integer id, String siteId) {
        List<InventoryError> inventoryErrors = new ArrayList<InventoryError>();
        // 已出库 被扫描到 异常文物
        List<Integer> relics0Ids = inventoryTagDao.findScanAndOut(siteId);
        List<Relic> relics0 = new ArrayList<Relic>();
        if (relics0Ids.size() > 0) {
            relics0 = relicDao.findRelicByIds(relics0Ids);
        }
        // 在库未扫描到异常文物
        List<Integer> relicsIds = inventoryTagDao.findNoScanAndIn(siteId);

        List<Relic> relics = new ArrayList<Relic>();
        if (relicsIds.size() > 0) {
            relics = relicDao.findRelicByIds(relicsIds);
        }
        Inventory inventory = new Inventory();
        inventory.setId(id);
        for (Integer relicId : relicsIds) {

            InventoryError inventoryError = new InventoryError();
            for (Relic r : relics) {
                if (r.getId().intValue() == relicId.intValue()) {
                    inventoryError.setRelic(r);
                    inventoryError.setSysState(r.getState());
                    break;
                }
            }
            inventoryError.setInventory(inventory);
            inventoryError.setScanState(InventoryError.NO_SCAN_STATE);
            inventoryErrors.add(inventoryError);
        }
        for (Integer relicId : relics0Ids) {
            InventoryError inventoryError = new InventoryError();
            for (Relic r : relics0) {
                if (r.getId().intValue() == relicId.intValue()) {
                    inventoryError.setRelic(r);
                    inventoryError.setSysState(r.getState());
                }
            }
            inventoryError.setInventory(inventory);
            inventoryError.setScanState(InventoryError.IS_SCAN_STATE);
            inventoryErrors.add(inventoryError);
        }

        return inventoryErrors;
    }

    @Override
    public List<InventoryOut> findInventoryOuts(Integer id) {
        return inventoryDao.findInventoryOuts(id);
    }

    @Override
    public Inventory findInventoryReport(Integer id) {
        // 查询盘点报告的基本信息
        Inventory inventory = inventoryDao.findById(id);
        // 处理盘点 报告的 区域统计信息
        if (inventory != null) {
            disposeInventoryStatistics(inventory);
        }
        return inventory;
    }

    @Override
    public Inventory createInventory(String siteId) {
        Inventory inventory = new Inventory();
        inventory.setSiteId(siteId);
        inventory.setState(false);
        // TODO WHY @gaohui 2014-03-27
        inventory.setDeadlineDate(new Date(new Date().getTime() + 1000));
        inventoryDao.save(inventory);
        return inventory;
    }

    @Override
    public void closeInventory(Integer id, String siteId) {
        // 处理盘点出库数据 和异常数据 存储进相应表
        disposeInventoryOut(id, siteId);
        disposeInventoryError(id, siteId);
        // 获得 盘点统计数据
        Inventory inventory = packInventoryData(id, siteId);
        // 处理盘点按区域统计信息
        disposeInventoryZone(id, siteId);
        // 将盘点状态置为 结束状态
        inventoryDao.update(inventory);
        // 删除标签数据
        inventoryTagDao.deleteInventoryTags(id);
    }

    @Override
    public void updateInventoryRemark(Integer id, String remark) {
        inventoryDao.updateInventoryRemark(id, remark);
    }

    @Override
    public Boolean isStartInventory(String siteId) {
        return inventoryDao.findStartInventory(siteId) != null;
    }

    @Override
    public Inventory findLastInventory(String siteId) {
        return inventoryDao.findLastInventory(siteId);
    }

    /**
     * 处理盘点报告按区域统计信息
     *
     * @param inventory 盘点报告对象
     * @author 许保吉
     * @date 2013-6-3
     */
    private void disposeInventoryStatistics(Inventory inventory) {
        // 获得 一个盘点 所有按区域统计信息
        List<InventoryZoneVo> inventoryStatisticsList = inventoryZoneDao
                .findAll(inventory.getId());
        List<InventoryZoneVo> countList = new ArrayList<InventoryZoneVo>();
        List<InventoryZoneVo> errorList = new ArrayList<InventoryZoneVo>();
        List<InventoryZoneVo> scanList = new ArrayList<InventoryZoneVo>();
        List<InventoryZoneVo> stockInList = new ArrayList<InventoryZoneVo>();
        List<InventoryZoneVo> stockOutList = new ArrayList<InventoryZoneVo>();
        List<InventoryZoneVo> tagList = new ArrayList<InventoryZoneVo>();
        if (inventoryStatisticsList != null
                && inventoryStatisticsList.size() > 0) {

            for (InventoryZoneVo inventoryZoneVo : inventoryStatisticsList) {
                // 将不同的 统计类型区分 从新组装
                switch (inventoryZoneVo.getStatisticsType()) {
                    case InventoryZone.TYPE_COUNT:
                        countList.add(inventoryZoneVo);
                        break;
                    case InventoryZone.TYPE_ERROR:
                        errorList.add(inventoryZoneVo);
                        break;
                    case InventoryZone.TYPE_SCAN:
                        scanList.add(inventoryZoneVo);
                        break;
                    case InventoryZone.TYPE_STOCK_IN:
                        stockInList.add(inventoryZoneVo);
                        break;
                    case InventoryZone.TYPE_STOCK_OUT:
                        stockOutList.add(inventoryZoneVo);
                        break;
                    case InventoryZone.TYPE_TAG:
                        tagList.add(inventoryZoneVo);
                        break;
                }
            }
        }
        // 将盘点 区域统计 信息 组装进 盘点 报告对象
        inventory.setCountList(countList);
        inventory.setErrorList(errorList);
        inventory.setStockInList(stockInList);
        inventory.setStockOutList(stockOutList);
        inventory.setScanList(scanList);
        inventory.setTagList(tagList);
    }

    /**
     * 处理盘点 按区域统计信息
     *
     * @param inventoryId 盘点 id
     * @param siteId      站点编号
     * @author 许保吉
     * @date 2013-6-3
     */
    private void disposeInventoryZone(Integer inventoryId, String siteId) {
        // 获得盘点 按区域统计数据
        List<InventoryZone> countList = inventoryZoneDao.findCountList(siteId);
        inventoryZoneDao.addInventoryZone(inventoryId, InventoryZone.TYPE_COUNT, countList);

        List<InventoryZone> stockOutList = inventoryZoneDao.findStockList(true, siteId);
        inventoryZoneDao.addInventoryZone(inventoryId, InventoryZone.TYPE_STOCK_OUT, stockOutList);

        List<InventoryZone> stockInList = inventoryZoneDao.findStockList(false, siteId);
        inventoryZoneDao.addInventoryZone(inventoryId, InventoryZone.TYPE_STOCK_IN, stockInList);

        List<InventoryZone> errorList = inventoryZoneDao.findErrorList( inventoryId, siteId);
        inventoryZoneDao.addInventoryZone(inventoryId, InventoryZone.TYPE_ERROR, errorList);

        List<InventoryZone> scanList = inventoryZoneDao.findScanList( inventoryId, siteId);
        inventoryZoneDao.addInventoryZone(inventoryId, InventoryZone.TYPE_SCAN, scanList);

        List<InventoryZone> tagList = inventoryZoneDao.findTagList(siteId);
        inventoryZoneDao.addInventoryZone(inventoryId, InventoryZone.TYPE_TAG, tagList);
    }

    /**
     * 处理实时盘点报告按区域统计信息
     *
     * @param inventory 盘点报告对象
     * @author wang.geng
     * @date 2014-3-10
     */
    private void disposeRealtimeInventoryStatistics(Inventory inventory, String siteId) {

        List<InventoryZoneVo> countRealtimeVOList = inventoryZoneDao.findRealtimeCountList(siteId);
        List<InventoryZoneVo> errorRealtimeVOList = inventoryZoneDao.findRealtimeErrorList(siteId);
        List<InventoryZoneVo> scanRealtimeVOList = inventoryZoneDao.findRealtimeScanList(inventory.getId(), siteId);
        List<InventoryZoneVo> stockRealtimeInList = inventoryZoneDao.findRealtimeStockList(false, siteId);
        List<InventoryZoneVo> stockRealtimeOutList = inventoryZoneDao.findRealtimeStockList(true, siteId);
        List<InventoryZoneVo> tagRealtimeList = inventoryZoneDao.findRealtimeTagList(siteId);

        for (InventoryZoneVo inventoryZoneVo : countRealtimeVOList) {
            inventoryZoneVo.setInventoryId(inventory.getId());
            inventoryZoneVo.setStatisticsType(InventoryZone.TYPE_COUNT);
        }
        for (InventoryZoneVo inventoryZoneVo : errorRealtimeVOList) {
            inventoryZoneVo.setInventoryId(inventory.getId());
            inventoryZoneVo.setStatisticsType(InventoryZone.TYPE_ERROR);
        }
        for (InventoryZoneVo inventoryZoneVo : scanRealtimeVOList) {
            inventoryZoneVo.setInventoryId(inventory.getId());
            inventoryZoneVo.setStatisticsType(InventoryZone.TYPE_SCAN);
        }
        for (InventoryZoneVo inventoryZoneVo : stockRealtimeInList) {
            inventoryZoneVo.setInventoryId(inventory.getId());
            inventoryZoneVo.setStatisticsType(InventoryZone.TYPE_STOCK_IN);
        }
        for (InventoryZoneVo inventoryZoneVo : stockRealtimeOutList) {
            inventoryZoneVo.setInventoryId(inventory.getId());
            inventoryZoneVo.setStatisticsType(InventoryZone.TYPE_STOCK_OUT);
        }
        for (InventoryZoneVo inventoryZoneVo : tagRealtimeList) {
            inventoryZoneVo.setInventoryId(inventory.getId());
            inventoryZoneVo.setStatisticsType(InventoryZone.TYPE_TAG);
        }

        // 将实时盘点 区域统计 信息 组装进 盘点 报告对象
        inventory.setCountList(countRealtimeVOList);
        inventory.setErrorList(errorRealtimeVOList);
        inventory.setStockInList(stockRealtimeInList);
        inventory.setStockOutList(stockRealtimeOutList);
        inventory.setScanList(scanRealtimeVOList);
        inventory.setTagList(tagRealtimeList);
    }

    /**
     * 处理盘点 出库数据
     *
     * @param inventoryId 盘点记录id
     * @param siteId      站点编号
     * @return void
     * @author 许保吉
     * @date 2013-5-28
     */
    private void disposeInventoryOut(Integer inventoryId, String siteId) {
        List<InventoryOut> inventoryOuts = new ArrayList<InventoryOut>();
        List<EventRelic> eventRelics = eventRelicDao.findInventoryOut(siteId);
        Inventory inventory = new Inventory();
        inventory.setId(inventoryId);
        for (EventRelic eventRelic : eventRelics) {
            InventoryOut inventoryOut = new InventoryOut();
            inventoryOut.setInventory(inventory);
            inventoryOut.setOutEvent(eventRelic.getOutEvent());
            inventoryOut.setRelic(eventRelic.getRelic());
            inventoryOuts.add(inventoryOut);
        }
        inventoryOutDao.addInventoryOut(inventoryOuts);
    }

    /**
     * 处理盘点 异常文物信息
     *
     * @param inventoryId 盘点id
     * @param siteId      站点编号
     * @return void
     * @author 许保吉
     * @date 2013-5-28
     */
    private void disposeInventoryError(Integer inventoryId, String siteId) {
        List<InventoryError> inventoryErrors = new ArrayList<InventoryError>();
        // 已出库 被扫描到 异常文物
        List<Integer> relics0 = inventoryTagDao.findScanAndOut(siteId);
        // 在库未扫描到异常文物
        List<Integer> relics = inventoryTagDao.findNoScanAndIn(siteId);
        Inventory inventory = new Inventory();
        inventory.setId(inventoryId);
        for (Integer relicId : relics) {
            Relic relic = new Relic();
            relic.setId(relicId);
            InventoryError inventoryError = new InventoryError();
            inventoryError.setInventory(inventory);
            inventoryError.setRelic(relic);
            inventoryError.setScanState(InventoryError.NO_SCAN_STATE);
            inventoryError.setSysState(Relic.STATE_IN);
            inventoryErrors.add(inventoryError);
        }
        for (Integer relicId : relics0) {
            Relic relic = new Relic();
            relic.setId(relicId);
            InventoryError inventoryError = new InventoryError();
            inventoryError.setInventory(inventory);
            inventoryError.setRelic(relic);
            inventoryError.setScanState(inventoryError.IS_SCAN_STATE);
            inventoryError.setSysState(Relic.STATE_OUT);
            inventoryErrors.add(inventoryError);
        }

        // 入库 存储
        inventoryErrorDao.addInventoryErrorS(inventoryErrors);
    }

    /**
     * 封装 盘点记录统计数据
     *
     * @param inventoryId 盘点记录id
     * @param siteId      站点编号
     * @return inventory 盘点记录实体
     * @author 许保吉
     * @date 2013-5-28
     */
    public Inventory packInventoryData(Integer inventoryId, String siteId) {
        Inventory inventory = new Inventory(inventoryId, siteId, true, new Date());
        inventory.setSumCount(inventoryDao.findSumCount(siteId));
        inventory.setSumNumber(inventoryDao.findSumNumber(siteId));

        inventory.setErrorCount(inventoryDao .findErrorCount(inventoryId, siteId));
        inventory.setErrorNumber(inventoryDao.findErrorNumber(inventoryId, siteId));

        inventory.setScanCount(inventoryDao.findScanCount(inventoryId, siteId));
        inventory.setScanNumber(inventoryDao .findScanNumber(inventoryId, siteId));

        inventory.setStockInCount(inventoryDao.findStockCount(false, siteId));
        inventory.setStockInNumber(inventoryDao.findStockNumber(false, siteId));

        inventory.setStockOutCount(inventoryDao.findStockCount(true, siteId));
        inventory.setStockOutNumber(inventoryDao.findStockNumber(true, siteId));

        inventory.setTagCount(inventoryDao.findTagCount(siteId));
        inventory.setTagNumber(inventoryDao.findTagNumber(siteId));
        return inventory;
    }

    public Inventory packRealtimeInventoryData(Integer inventoryId, String siteId) {
        //实时报告生成，盘点未结束
        Inventory inventory = new Inventory(inventoryId, siteId, false, new Date());
        //文物总数与总件数
        inventory.setSumCount(inventoryDao.findSumCount(siteId));
        inventory.setSumNumber(inventoryDao.findSumNumber(siteId));

        //错误文物总数与总件数
        // 已出库 被扫描到 异常文物与件数
        List<Integer> relics0 = inventoryTagDao.findScanAndOut(siteId);
        Integer relics0Count = inventoryTagDao.findScanAndOutCount(siteId);
        // 在库   未扫描到 异常文物与件数
        List<Integer> relics = inventoryTagDao.findNoScanAndIn(siteId);
        Integer relicsCount = inventoryTagDao.findNoScanAndInCount(siteId);
        inventory.setErrorCount(relics0.size() + relics.size());
        inventory.setErrorNumber(relics0Count + relicsCount);

        //库存盘点，扫描到的文物总数与总件数
        inventory.setScanCount(inventoryDao.findScanCount(inventoryId, siteId));
        inventory.setScanNumber(inventoryDao.findScanNumber(inventoryId, siteId));

        //出库文物总数与总件数
        inventory.setStockInCount(inventoryDao.findStockCount(false, siteId));
        inventory.setStockInNumber(inventoryDao.findStockNumber(false, siteId));

        //在库文物总数与总件数
        inventory.setStockOutCount(inventoryDao.findStockCount(true, siteId));
        inventory.setStockOutNumber(inventoryDao.findStockNumber(true, siteId));

        //盘点中，有标签的文物总数与总件数
        inventory.setTagCount(inventoryDao.findTagCount(siteId));
        inventory.setTagNumber(inventoryDao.findTagNumber(siteId));

        return inventory;
    }
}
