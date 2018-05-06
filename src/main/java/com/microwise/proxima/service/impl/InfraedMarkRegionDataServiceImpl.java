package com.microwise.proxima.service.impl;

import com.microwise.common.util.GalaxyIdUtil;
import com.microwise.proxima.bean.InfraredMarkRegionBean;
import com.microwise.proxima.bean.InfraredMarkRegionDataBean;
import com.microwise.proxima.bean.InfraredPictureDataBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.daemon.infraredImageResolution.ColorWheel;
import com.microwise.proxima.dao.InfraredMarkRegionDao;
import com.microwise.proxima.dao.InfraredMarkRegionDataDao;
import com.microwise.proxima.service.InfraredMarkRegionDataService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.util.InfraredImage;
import com.microwise.proxima.util.PositionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 红外图片区域数据服务层
 *
 * @author zhang.licong
 * @date 2012-9-6
 */
@Service
@Transactional
@Proxima
public class InfraedMarkRegionDataServiceImpl implements
        InfraredMarkRegionDataService {

    @Autowired
    private InfraredMarkRegionDataDao infraredMarkRegionDataDao;
    @Autowired
    private InfraredMarkRegionDao markRegionDao;

    @Autowired
    private InfraredMarkRegionDataDao markRegionDataDao;

    /**
     * 保留 1 位小数
     */
    private static final int PRECISION = 1;

    /**
     * 获取色轮索引时的上下浮动范围
     */
    private static final int MAX_DELTA = 10;


    @Override
    public void save(InfraredMarkRegionDataBean infraredMarkRegionData) {
        this.infraredMarkRegionDataDao.save(infraredMarkRegionData);

    }

    @Override
    public List<PictureBean> findNoResolutionPictures(int markRegionId,
                                                      String dvPlaceId) {
        return this.infraredMarkRegionDataDao.findNoResolutionPictures(
                markRegionId, dvPlaceId);
    }

    @Override
    public double findMaxHighTemperature(String dvPlaceId, String markRegionId,
                                         Date startDate, Date endDate) {
        return infraredMarkRegionDataDao.findMaxHighTemperature(dvPlaceId,
                markRegionId, startDate, endDate);
    }

    @Override
    public double findMinLowTemperature(String dvPlaceId, String markRegionId,
                                        Date startDate, Date endDate) {
        return infraredMarkRegionDataDao.findMinLowTemperature(dvPlaceId,
                markRegionId, startDate, endDate);
    }

    @Override
    public List<InfraredMarkRegionDataBean> findListForChart(String dvPlaceId,
                                                             String markRegionId, Date startDate, Date endDate) {
        return infraredMarkRegionDataDao.findListForChart(dvPlaceId, markRegionId, startDate, endDate);
    }

    @Override
    public InfraredMarkRegionDataBean findAt(String dvPlaceId,String pictureId,int x, int y) {
        // 找出此位置的标记区域
        InfraredMarkRegionBean markRegion = markRegionDao.findAt(dvPlaceId, x, y);
        if (markRegion == null) {
            return null;
        }

        //查找对应的标记区域数据
        return infraredMarkRegionDataDao.findByMarkRegionIdAndPicId(markRegion.getId(),pictureId);
    }

    @Override
    public void infraredSingleRegionAnalyzer(InfraredMarkRegionBean inf, InfraredPictureDataBean infraredPictureData, File file, ColorWheel colorWheel, PictureBean picture) throws IOException {

        // 如果图片不被解析, 则直接设置当前区域的温度都为 0
        if (infraredPictureData.getHighTemperature() == 0 && infraredPictureData.getLowTemperature() == 0) {
            InfraredMarkRegionDataBean imrd = new InfraredMarkRegionDataBean();
            imrd.setId(GalaxyIdUtil.get64UUID());
            imrd.setHighTemperature(0);
            imrd.setLowTemperature(0);
            imrd.setAverageTemperature(0);
            imrd.setPicture(picture);
            imrd.setMarkRegion(inf);
            markRegionDataDao.save(imrd);
            return;
        }


        // 加载图片信息
        InputStream inputStream = new FileInputStream(file);
        BufferedImage image = ImageIO.read(inputStream);
        inputStream.close();

        // 获取红外图片的高度
        int pointx = inf.getPositionX();
        int pointy = inf.getPositionY();
        int pointWidth = inf.getRegionWidth();
        int pointHeight = inf.getRegionHeight();

        int count = 0;// 区域点数
        double sumTemperature = 0.00;// 区域温度总和
        double highTemperature = 0.00;// 高温
        double lowTemperature = 0.00;// 低温
        double averageTemperature = 0.00;// 平均温度

        List<Double> listValue = new ArrayList<Double>();

        // 遍历图片中的区域，并获取当前点温度
        for (int x = pointx; x < pointx + pointWidth; x++) {
            for (int y = pointy; y < pointy + pointHeight; y++) {

                if (!InfraredImage.inBlackDimensions(x, y)) {// 去除黑名单的像素
                    // 获取当前点在色轮中的索引
                    int rgb = image.getRGB(x, y);
                    int heightOfImageWheel = colorWheel.closestHeightOfColor(
                            rgb, MAX_DELTA);

                    // 根据当前索引获取对应的高度
                    int imageWheelMaxHeight = colorWheel.getRgbSAverage().length;

                    // 返回值为-1时不计算该点温度
                    if (heightOfImageWheel == -1) {
                        continue;
                    }

                    // 求出色轮百分比
                    double rate = (imageWheelMaxHeight - heightOfImageWheel)
                            / (float) imageWheelMaxHeight;

                    // 求出当前点的温度
                    double value = rate
                            * (infraredPictureData.getHighTemperature() - infraredPictureData
                            .getLowTemperature())
                            + infraredPictureData.getLowTemperature();
                    listValue.add(value);

                    // 计数器及温度总和处理
                    count++;
                    sumTemperature = sumTemperature + value;
                }
            }
        }
        // 计算区域高低温
        Collections.sort(listValue);

        // 温度保留1位小数

        highTemperature = PositionUtil.round(
                listValue.get(listValue.size() - 1), PRECISION);// 高温
        lowTemperature = PositionUtil.round((listValue.get(0)), PRECISION);// 低温
        averageTemperature = PositionUtil.round(sumTemperature / count,
                PRECISION);// 平均温度
        InfraredMarkRegionDataBean imrd = new InfraredMarkRegionDataBean();
        imrd.setId(GalaxyIdUtil.get64UUID());
        imrd.setHighTemperature(highTemperature);
        imrd.setLowTemperature(lowTemperature);
        imrd.setAverageTemperature(averageTemperature);
        imrd.setPicture(picture);
        imrd.setMarkRegion(inf);
        // 数据库保存
        markRegionDataDao.save(imrd);
    }
}
