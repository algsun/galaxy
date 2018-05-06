package com.microwise.halley.dao;

import com.microwise.halley.bean.vo.ExhibitionVO;

import java.util.Date;
import java.util.List;

/**
 * 外展 Dao
 *
 * @author li.jianfei
 * @date 2013-09-27
 * @check @wang.geng #5847 2013-10-10
 */
public interface ExhibitionDao {

    /**
     * 送出库记录中同步外展记录
     *
     * @param siteId 站点ID
     */
    public void syncExhibitions(String siteId);

    /**
     * 查询当前站点下所有外展记录
     *
     * @param siteId 站点ID
     * @return 外展记录集合
     */
    public List<ExhibitionVO> findExhibitionList(String siteId, int state);

    /**
     * 查询当前站点下所有外展记录
     *
     * @return 外展集合
     */
    public List<ExhibitionVO> findExhibitionListNotEnd(String siteId);

    /**
     * 查询当前站点下所有外展记录
     *
     * @param siteId 站点ID
     * @return 外展记录总数
     */
    public List<ExhibitionVO> findExhibitionListCount(String siteId, int state);

    /**
     * 获取某次外展的当前详细信息
     *
     * @param exhibitionId 外展ID
     * @return ExhibitionVO  外展信息
     */
    public ExhibitionVO findExhibition(int exhibitionId);

    /**
     * 更新外展记录开始时间
     *
     * @param exhibitionId 外展ID
     */
    public void updateBeginTime(int exhibitionId, Date beginTime);

    /**
     * 更新外展记录结束时间
     *
     * @param exhibitionId 外展ID
     */
    public void updateEndTime(int exhibitionId, Date endTime);

    /**
     * 更新外展记录结束时间
     *
     * @param exhibitionId 外展ID
     */
    public void updateEndTime(int exhibitionId);

}