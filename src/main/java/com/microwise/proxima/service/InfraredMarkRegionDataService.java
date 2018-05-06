package com.microwise.proxima.service;

import com.microwise.proxima.bean.InfraredMarkRegionBean;
import com.microwise.proxima.bean.InfraredMarkRegionDataBean;
import com.microwise.proxima.bean.InfraredPictureDataBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.daemon.infraredImageResolution.ColorWheel;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 红外标记区域数据 service
 *
 * @author gaohui
 * @date 2012-9-5
 * @check li.jianfei liu.zhu 2014-4-15 #8323
 */
public interface InfraredMarkRegionDataService {

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * <p/>
     * 保存红外区域数据
     *
     * @author zhang.licong
     * @date 2012-9-6
     */
    public void save(InfraredMarkRegionDataBean infraredMarkRegionData);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * <p/>
     * 根据红外图片区域查询没有解析的图片
     *
     * @param markRegionId 区域ID
     * @param dvPlaceId    摄像机点位ID
     * @return
     * @author zhang.licong
     * @date 2012.9.9
     */
    public List<PictureBean> findNoResolutionPictures(int markRegionId,
                                                      String dvPlaceId);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * <p/>
     * 根据指定点位 ID、区域id 和起始日期查询所有图片区域最高温的最大值
     *
     * @param dvPlaceId    红外摄像机点位 ID
     * @param markRegionId 标记区域id
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 查询到的最大值
     * @author li.jianfei
     * @date 2012-09-11
     */
    public double findMaxHighTemperature(String dvPlaceId, String markRegionId,
                                         Date startDate, Date endDate);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * <p/>
     * 根据指定点位 ID 和起始日期查询所有图片最低温的最小值
     *
     * @param dvPlaceId    红外摄像机点位 ID
     * @param markRegionId 标记区域id
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 查询到的最大值
     * @author li.jianfei
     * @date 2012-09-11
     */
    public double findMinLowTemperature(String dvPlaceId, String markRegionId,
                                        Date startDate, Date endDate);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * <p/>
     * 根据指定点位 ID 和起始日期查询区域数据集合
     *
     * @param dvPlaceId    红外摄像机点位 ID
     * @param markRegionId 标记区域id
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 红外图片标记区域集合
     * @author li.jianfei
     * @date 2012-09-11
     */
    public List<InfraredMarkRegionDataBean> findListForChart(String dvPlaceId,
                                                             String markRegionId, Date startDate, Date endDate);

    /**
     * 返回某个图片，在此位置对应的标记区域数据。如果没有，返回null
     *
     * @param dvPlaceId 摄像机点位ID
     * @param pictureId 图片ID
     * @param x         位置
     * @param y         位置
     * @return
     */
    public InfraredMarkRegionDataBean findAt(String dvPlaceId, String pictureId, int x, int y);

    /**
     * 计算单个红外区域的高、低、平均温度
     *
     * @param inf
     * @param infraredPictureData
     * @param file
     * @param colorWheel
     * @param picture
     * @throws Exception
     * @author 刘柱
     * @date 2014-8-7
     */
    public void infraredSingleRegionAnalyzer(InfraredMarkRegionBean inf,
                                             InfraredPictureDataBean infraredPictureData, File file,
                                             ColorWheel colorWheel, PictureBean picture) throws IOException;

}
