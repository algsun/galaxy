package com.microwise.proxima.proxy;

import com.microwise.proxima.bean.OpticsDVPlaceBean;
import com.microwise.proxima.bean.PictureBean;

import java.util.Date;
import java.util.List;

/**
 * proxima图片Service代理层
 * 
 * @author xu.yuexi
 * @date 2013-3-28
 */
public interface PictureProxy {


    /**
     * 根据图片ID查询图片
     *
     * @param picId
     * @return
     */
    public PictureBean findById(String picId);


    /**
     * <pre>
     * 根据摄像机查询某个时间段内的图片
     *
     * @param dvPlaceId 点位id
     * @param startDate 开始时间
     * @param endDate   结束时间
     *
     * @author wangyunlong
     * @date 2013-3-27
     *
     * @return List<PictureBean> 图片列表
     * </pre>
     */
    public List<PictureBean> findByTime(String dvPlaceId, Date startDate,
                                        Date endDate);
    /**
     * <pre>
     * 查询所有的光学摄像机. 注意如果没有返回空集合
     *
     * @param siteId 站点id
     *
     * @return List<OpticsDVPlaceBean> 光学摄像机集合
     * </pre>
     */
    public List<OpticsDVPlaceBean> findAll(String siteId);

}
