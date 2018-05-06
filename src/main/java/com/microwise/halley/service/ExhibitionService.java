package com.microwise.halley.service;

import com.microwise.halley.bean.vo.ExhibitionVO;

import java.util.Date;
import java.util.List;

/**
 * 外展 Service
 *
 * @author li.jianfei
 * @date 2013-10-08
 * @check @wang.geng #5847 2013-10-10
 */
public interface ExhibitionService {

    /**
     * 查询当前站点下指定状态的外展记录
     *
     * @param siteId 站点ID
     * @param state  外展状态
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
     * @param siteId   站点ID
     * @param state    外展状态
     * @param page     当前页码
     * @param pageSize 每页分页数
     * @return 外展记录集合
     */
    public List<ExhibitionVO> findExhibitionList(String siteId, int state, int page, int pageSize, Date startDate, Date endDate);

    /**
     * 查询当前站点下所有外展记录
     *
     * @param siteId 站点ID
     * @return 外展记录总数
     */
    public int findExhibitionListCount(String siteId, int state, Date startDate, Date endDate);

    /**
     * 获取某次外展的当前详细信息
     *
     * @param exhibitionId 外展ID
     * @return ExhibitionVO
     */
    public ExhibitionVO findExhibition(int exhibitionId);

    /**
     * 结束外展
     *
     * @param exhibitionId 外展ID
     * @param operatorId   操作人ID
     */
    public void finishExhibition(int exhibitionId, int operatorId);
}
