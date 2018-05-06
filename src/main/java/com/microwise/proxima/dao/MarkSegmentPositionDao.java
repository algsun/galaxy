package com.microwise.proxima.dao;

import com.microwise.proxima.bean.MarkSegmentPositionBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.dao.base.BaseDao;

import java.util.Date;
import java.util.List;

/**
 * MarkSegmentPositionBean dao
 *
 * @author gaohui
 * @date 2012-7-13
 */
public interface MarkSegmentPositionDao extends
        BaseDao<MarkSegmentPositionBean> {

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 根据图片ID 查询所有标记段坐标
     *
     * @param picId
     * @return
     */
    public List<MarkSegmentPositionBean> findByPicId(String picId);

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 查询某个属于某个图片的所有有效的标记段坐标
     *
     * @param picId
     * @return
     */
    public List<MarkSegmentPositionBean> findActiveByPicId(String picId);

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 查询某个图片之前最后一次编辑的某个图片的 标记段坐标.
     *
     * @param picId
     * @return
     */
    public List<MarkSegmentPositionBean> findLatestBefore(String picId);

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 查找最后一次被编辑图片ID
     *
     * @param dvPlaceId
     * @return
     */
    public PictureBean findLatestEditedPicture(String dvPlaceId);

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 查询某个标记段在某个图片上的标记段坐标
     *
     * @param markSegmentId
     * @param picId
     * @return
     */
    public MarkSegmentPositionBean find(String markSegmentId, String picId);

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 查询某个图片之前，最后一次修改的有效的标记段坐标
     *
     * @param picId
     * @return
     */
    public List<MarkSegmentPositionBean> findLatestActiveBefore(String picId);

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 查询所有已编辑图片中的标记段长度信息
     * <p/>
     *
     * @param dvPlaceId
     * @param markId
     * @param startDate
     * @param endDate
     * @return
     * @author li.jianfe
     */
    public List<MarkSegmentPositionBean> findAllMarkedSegmentData(
            String dvPlaceId, String markId, Date startDate, Date endDate);

    /**
     * 查询所有已编辑图片中的标记段长度信息
     *
     * @param markId 标记段id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return MarkSegmentPositionBean集合
     * @author liuzhu
     */
    public List<MarkSegmentPositionBean> findMarkedSegmentData(String markId, Date startDate, Date endDate);


    /**
     * <p>
     * TODO 未验证
     * </p>
     * 查询所有已编辑图片中的标记段位置信息
     *
     * @param dvPlaceId
     * @param startDate
     * @param endDate
     * @return
     * @author li.jianfe
     */
    public List<MarkSegmentPositionBean> findAllMarkPositionData(
            String dvPlaceId, Date startDate, Date endDate);


    /**
     * <p>
     * TODO 未验证
     * </p>
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
     * <p>
     * TODO 未验证
     * </p>
     * 查询某个图片之后，修改的第一个图片
     *
     * @param picId
     * @return
     */
    public PictureBean findLastedEditedPictureAfter(String picId);

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 查询某个图片之前，最后一次修改的图片
     *
     * @param picId
     * @return
     */
    public PictureBean findLastedEditedPictureBefore(String picId);

}
