package com.microwise.proxima.service;

import com.microwise.proxima.bean.MarkSegmentPositionBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * MarkSegmentPositionBean service
 *
 * @author gaohui
 * @date 2012-7-13
 */
public interface MarkSegmentPositionService {

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 在某个图片之前的最后标记段坐标
     *
     * @param picId
     * @return
     */
    public List<MarkSegmentPositionBean> findLatestBefore(String picId);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 某个图片之后没被编辑过，或者是最后一次编辑
     *
     * @param picId
     * @return
     */
    public boolean isNewOrLatestEditedPicture(String picId);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 查询某个属于某个图片的所有标记段坐标
     *
     * @param picId
     * @return
     */
    public List<MarkSegmentPositionBean> findByPicId(String picId);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 查询某个属于某个图片的所有有效的标记段坐标
     *
     * @param picId
     * @return
     */
    public List<MarkSegmentPositionBean> findActiveByPicId(String picId);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     */
    public void update(MarkSegmentPositionBean t);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     */
    public Serializable save(MarkSegmentPositionBean t);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     */
    public MarkSegmentPositionBean findById(int id);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 添加或者修改标记段坐标
     *
     * @param picId
     * @param markSegments
     */
    public void addOrUpdateMarkSegmentPosition(String picId, String markSegments);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 查询某个图片之前，最后一次修改的有效的标记段坐标
     *
     * @param picId
     * @return
     */
    public List<MarkSegmentPositionBean> findLatestActiveBefore(String picId);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 查询所有已编辑图片中的标记段长度信息
     *
     * @param dvPlaceId
     * @param markId
     * @return List
     * @author li.jianfei
     */
    public List<MarkSegmentPositionBean> findAllMarkedSegementData(
            String dvPlaceId, String markId, Date startDate, Date endDate);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 查询所有已编辑图片中的标记段位置信息
     *
     */
    public List<MarkSegmentPositionBean> findAllMarkPositionData(String dvPlaceId,
                                                                 Date startDate, Date endDate);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 查询指定时间范围内标记段的最大positionX、postionY
     *
     * @param dvPlaceId
     * @param startDate
     * @param endDate
     * @return
     * @author li.jianfei
     */
    public Integer[] findMaxXY(String dvPlaceId, Date startDate, Date endDate);

    /**
     * 查询所有已编辑图片中的标记段长度信息
     *
     * @param markId    标记段id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return MarkSegmentPositionBean集合
     * @author liuzhu
     */
    public List<MarkSegmentPositionBean> findMarkedSegmentData(String markId, Date startDate, Date endDate);


}
