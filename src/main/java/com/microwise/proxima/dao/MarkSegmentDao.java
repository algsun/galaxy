package com.microwise.proxima.dao;

import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.MarkSegmentBean;
import com.microwise.proxima.dao.base.BaseDao;

import java.util.List;

/**
 * MarkSegment dao
 * 
 * @author gaohui
 * @date 2012-7-13
 */
public interface MarkSegmentDao extends BaseDao<MarkSegmentBean> {

	/**
	 * <p>
	 * TODO 未验证
	 * </p>
	 * 根据 摄像机点位 ID 查询所有的 标记段
	 * 
	 * @param dvPlaceId
	 * @return
	 */
	public List<MarkSegmentBean> findAllMarkSegmentByDvPlaceId(String dvPlaceId);

	/**
	 * <p>
	 * TODO 未验证
	 * </p>
	 * 根据标记段名称，查询某个摄像机点位下的标记段
	 * 
	 * @param dvPlaceId
	 * @param markSegmentName
	 * @return
	 */
	public MarkSegmentBean findMarkSegmentOfDVPlaceByName(int dvPlaceId,
			String markSegmentName);

	/**
	 * <p>
	 * TODO 未验证
	 * </p>
	 * 查询所有标记段信息
	 * 
	 * @author li.jianfei
	 * @return
	 */
	public List<MarkSegmentBean> findAllMarkSegment();

	/**
	 * <p>
	 * TODO 未验证
	 * </p>
	 * 根据标记段查询对应摄像机点位
	 * 
	 *
     * @param markSegmentId
     * @return
	 */
	public DVPlaceBean findDVPlaceByMarkSegmentId(String markSegmentId);

    /**
     * 根据siteId 查询一个站点下的所有标记段(按 dvPlaceId 和 createTime 排序)
     *
     * @param siteId
     * @return
     */
    List<MarkSegmentBean> findAllBySiteId(String siteId);
}
