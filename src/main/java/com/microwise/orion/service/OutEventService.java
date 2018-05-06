package com.microwise.orion.service;

import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.bean.OutEventAttachment;
import com.microwise.orion.vo.OutEventVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 出库 记录 （出库单） service
 *
 * @author xubaoji
 * @date 2013-5-29
 * @check duan qixin svn:3998
 */
public interface OutEventService {

    /**
     * 出库申请表填写
     *
     * @param outEvent    出库事件记录(出库单) 实体
     * @param relicIdList 出库文物编号集合
     */
    public void saveOutEventInfo(OutEvent outEvent, List<Integer> relicIdList);

    /**
     * 根据站点编号查询出库事件
     *
     * @param siteId 站点编号
     * @param start  分页开始位置
     * @param size   分页每页条数
     * @return 站点编号查询出库事件
     */
    public List<OutEvent> findOutEventsBySiteId(String siteId, int start, int size);

    /**
     * 根据站点编号查询出库事件条数
     *
     * @param siteId 站点编号
     * @return
     */
    public int findOutEventCountBySiteId(String siteId);

    /**
     * 查询指定日期范围内的外出借展事件（带有外展出库单文物信息和文物流转信息）
     *
     * @param siteId 站点ID
     * @param begin  开始时间
     * @param end    结束时间
     * @return
     */
    public List<OutEvent> findExhibitions(String siteId, Date begin, Date end);

    /**
     * 根据id查询出库事件编号
     *
     * @param id 出库事件编号
     * @return
     */
    public OutEvent findById(String id);

    /**
     * 修改出库事件
     *
     * @param outEvent 出库事件
     * @param relicIds 文物编号集合
     */
    public void updateOutEvent(OutEvent outEvent, List<Integer> relicIds);

    List<OutEventVo> findOutEventVoByState(String siteId, int state);

    /**
     * 删除文物出库事件
     *
     * @param outEventId 出库事件编号
     */
    public void deleteOutEvent(String outEventId);

    /**
     * 根据 出库单号查询 出库但列表（为http 服务提供）
     *
     * @param outEventIds 出库单列表
     * @return List<OutEventVo> 出库单 vo 实体（带有出库单文物信息）
     * @author 许保吉
     * @date 2013-5-31
     */
    public List<OutEventVo> findOutEventVo(Map<String, String> outEventIds);

    /**
     * 根据outEventId修改文物状态
     *
     * @param outEventId
     */
    public void updateRelicState(String outEventId, int relicState);

    /**
     * 文物入库
     *
     * @param outEventId 文物出库事件编号
     */
    public void backToStoreRoom(String outEventId);

    /**
     * 文物出库
     *
     * @param outEventId
     */
    void doStockOut(String outEventId);

    /**
     * 添加一条出库事件文档记录
     *
     * @param eventId 出库事件id
     * @param userId  上传用户id
     * @param path    文档路径
     * @param date    上传日期
     * @author xu.baoji
     * @date 2013-8-1
     */
    public void addOutEventAttachment(String eventId, int userId, String path, Date date);

    /***
     * 删除 出库事件文档记录
     *
     * @author xu.baoji
     * @date 2013-8-1
     *
     * @param attachmentId 出库事件文档记录id
     */
    public void deleteOutEventAttachment(int attachmentId);

    /***
     * 通过 出库事件id 查询所有出库事件文档记录
     *
     * @author xu.baoji
     * @date 2013-8-1
     *
     * @param eventId 出库事件id
     * @return List<OutEventAttachment> 出库事件文档记录实体列表
     */
    public List<OutEventAttachment> findOutEventAttachmentByEventId(String eventId);
}
