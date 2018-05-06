package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.OutEvent;
import com.microwise.orion.vo.OutEventVo;
import com.microwise.orion.vo.RelicVo;

import java.util.Date;
import java.util.List;

/**
 * 出库记录 dao
 *
 * @author xubaoji
 * @date 2013-5-29
 */
public interface OutEventDao extends BaseDao<OutEvent> {

    /**
     * 出库申请表填写
     *
     * @param outEvent 出库事件记录(出库单) 实体
     */
    public void saveOutEventInfo(OutEvent outEvent);

    /**
     * 根据站点编号查询出库事件
     *
     * @param siteId 站点编号
     * @param start  分页开始位置
     * @param size   分页每页条数
     * @return 站点编号查询出库事件
     */
    public List<OutEvent> findOutEventsBySiteId(String siteId, int start,
                                                int size);

    /**
     * 根据id查询出库事件编号
     *
     * @param id 出库事件编号
     * @return
     */
    public OutEvent findById(String id);


    /**
     * 根据站点编号查询出库事件条数
     *
     * @param siteId 站点编号
     * @return
     */
    public int findOutEventCountBySiteId(String siteId);

    /**
     * 查询指定时间范围内的外出借展事件
     *
     * @param siteId 站点ID
     * @param begin  开始日期
     * @param end    结束日期
     * @return
     */
    List<OutEvent> findExhibitions(String siteId, Date begin, Date end);

    /**
     * 修改出库事件
     *
     * @param outEvent 出库事件
     */
    public void updateOutEvent(OutEvent outEvent);

    /**
     * 根据出库单编号列表 查询出库单 信息（不带有出库单文物信息）
     *
     * @param eventIds 出库单号列表
     * @return
     * @author 许保吉
     * @date 2013-5-31
     */
    public List<OutEventVo> findOutEventVo(List<String> eventIds);

    /**
     * 根据出库单 号 查询出库单文物信息
     *
     * @param eventId 出库单号
     * @return
     * @author 许保吉
     * @date 2013-5-31
     */
    public List<RelicVo> findOutEventRelicVo(String eventId);

    /**
     * 根据出库单状态查询对应出库单
     *
     * @param siteId
     * @param state
     * @return
     */
    List<OutEventVo> findOutEventVoByState(String siteId, int state);

}
