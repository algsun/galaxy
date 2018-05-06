package com.microwise.proxima.service;

import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.PictureBean;

import java.util.Date;
import java.util.List;

/**
 * @author zhang.licong
 * @date 2012-7-10
 */
public interface PictureService {

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * <p/>
     * 根据摄像机点位查询图片信息
     *
     * @param dvPlaceId 摄像机点位ID
     * @author zhang.licong
     * @date 2012-9-17
     */
    public List<PictureBean> findPictures(String dvPlaceId);


    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * <p/>
     * 根据图片查询对应的摄像机点位
     *
     * @param picId
     * @return
     */
    public DVPlaceBean findDVPlaceByPictureId(String picId);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * <p/>
     * 根据图片ID查询图片
     *
     * @param picId
     * @return
     */
    public PictureBean findById(String picId);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * <p/>
     * 根据摄像机点位，查询某个时间之后的 n 张图片
     *
     * @param dvPlaceId 摄像机点位
     * @param dateAfter 时间
     * @param max       最大图片数量
     * @return
     */
    public List<PictureBean> findPicturesAfter(String dvPlaceId, Date dateAfter,
                                               int max);

    /**
     * <pre>
     * 根据摄像机查询某个时间段内的图片
     *
     * @param dvPlaceId 点位id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<PictureBean> 图片列表
     * </pre>
     * @author wangyunlong
     * @date 2013-3-27
     */
    public List<PictureBean> findByTime(String dvPlaceId, Date startDate,
                                        Date endDate);

    /**
     * <pre>
     * 获得最近的n张图片
     *
     * @param dvPlaceId 摄像机id
     * @param count     需要的数量
     * @return List<PictureBean> 图片对象列表
     * </pre>
     * @author wangyunlong
     * @date 2013-3-26
     */
    public List<PictureBean> findRecentPictures(String dvPlaceId, int count);

}
