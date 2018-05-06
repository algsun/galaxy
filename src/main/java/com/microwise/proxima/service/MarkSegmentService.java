package com.microwise.proxima.service;

import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.MarkSegmentBean;
import com.microwise.proxima.bean.MarkSegmentPositionBean;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 负责 MarkSegmentBean 的 service
 *
 * @author gaohui
 * @date 2012-7-13
 */
public interface MarkSegmentService {

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 根据 摄像机点位 ID 查询所有的 标记段
     *
     * @param dvPlaceId
     * @return
     */
    public List<MarkSegmentBean> findAllMarkSegmentByDvPlaceId(String dvPlaceId);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 添加标记段同时添加标记段坐标
     *
     * @param dvPlaceId
     * @param markName
     * @param picId
     * @param positionX
     * @param positionY
     * @param positionX2
     * @param positionY2
     * @return
     */
    public MarkSegmentPositionBean addMarkSegmentWidthPoistion(int dvPlaceId,
                                                               String markName, int picId, int positionX, int positionY,
                                                               int positionX2, int positionY2);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 取消/注销 某标记段
     *
     * @param markSegmentId
     */
    public void cancelMarkSegment(int markSegmentId);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 根据标记段名称，查询某个摄像机点位下的标记段
     *
     * @param dvPlaceId
     * @param markSegmentName
     * @return
     */
    public MarkSegmentBean findMarkSegmentOfDVPlaceByName(int dvPlaceId,
                                                          String markSegmentName);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 查询所有标记段信息
     *
     * @return
     * @author li.jianfei
     * @deprecated 需要按站点查询 @gaohui 2013-06-14
     */
    public Map<Integer, List<MarkSegmentBean>> findAllMarkSegment();

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 根据标记段查询对应摄像机点位
     *
     * @param markSegmentId
     * @return
     */
    public DVPlaceBean findDVPlaceByMarkSegmentId(String markSegmentId);

    /**
     * 根据 siteId 查找对应站点下的所有标记段.
     *
     * @param siteId
     * @return
     */
    Map<DVPlaceBean, Collection<MarkSegmentBean>> findAllBySiteId(String siteId);
}
